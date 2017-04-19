package kieker.monitoring.probe.spring.flow;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import kieker.common.logging.Log;
import kieker.common.logging.LogFactory;
import kieker.monitoring.core.controller.IMonitoringController;
import kieker.monitoring.core.controller.MonitoringController;
import kieker.monitoring.core.registry.ControlFlowRegistry;
import kieker.monitoring.core.registry.SessionRegistry;
import kieker.monitoring.timer.ITimeSource;

public class ZuulPreInterceptor extends ZuulFilter {

	public static final String SIGNATURE = "public void " + ZuulPreInterceptor.class.getName()
			+ ".intercept(org.springframework.http.HttpRequest, byte[], org.springframework.http.client.ClientHttpRequestExecution)";

	private static final Log LOG = LogFactory.getLog(RestOutInterceptor.class);
	private static final IMonitoringController CTRLINST = MonitoringController.getInstance();
	private static final ITimeSource TIME = CTRLINST.getTimeSource();
	private static final String VMNAME = CTRLINST.getHostname();
	private static final ControlFlowRegistry CF_REGISTRY = ControlFlowRegistry.INSTANCE;
	private static final SessionRegistry SESSION_REGISTRY = SessionRegistry.INSTANCE;	
	
	@Override
	public boolean shouldFilter() {
		// TODO limit this
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		
		if (!CTRLINST.isMonitoringEnabled()) {
			return null;
		}
		boolean entrypoint = true;
		final String hostname = VMNAME;
		HttpServletRequest request = ctx.getRequest();
		// TODO handle thread IDs
		long traceId = -1L; // -1L if entry point
		ThreadSpecificInterceptedData tData = (ThreadSpecificInterceptedData) ctx.get("KiekerInfo");
		if(tData != null) {
			traceId = tData.getTraceId();
		}
		final int eoi; // this is executionOrderIndex-th execution in this trace
		final int ess; // this is the height in the dynamic call tree of this execution
		if (traceId == -1) {
			entrypoint = true;
			traceId = CF_REGISTRY.getAndStoreUniqueThreadLocalTraceId();
			CF_REGISTRY.storeThreadLocalEOI(0);
			CF_REGISTRY.storeThreadLocalESS(1); // next operation is ess + 1
			eoi = 0;
			ess = 0;
		} else {
			entrypoint = false;
			eoi = tData.getEoi() + 1;
			ess = tData.getEss();
			if ((eoi == -1) || (ess == -1)) {
				LOG.error("eoi and/or ess have invalid values:" + " eoi == " + eoi + " ess == " + ess);
				CTRLINST.terminateMonitoring();
			}
		}
		
		ctx.addZuulRequestHeader("KiekerTracingInfo", Long.toString(traceId) + "," + traceId + "," + Integer.toString(eoi) + "," + Integer.toString(ess));
		
		// measure before
		final long tin = TIME.getTime();
		
		String signature = request.getRequestURI().substring(1).replace("/", ".");
		if(Character.isDigit(signature.charAt(signature.length()-1))) {
			signature = signature.substring(0, StringUtils.indexOfAny(signature, "0123456789")-1);
			signature = signature + "." + "numbered";
		} else {
			signature = signature + ".";
		}
		signature = signature + request.getMethod();
		
		ThreadSpecificInterceptedData data = new ThreadSpecificInterceptedData(signature, request.getSession().getId(), traceId, tin, hostname, eoi, ess);
		ctx.set("KiekerInfo", data);
		
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
