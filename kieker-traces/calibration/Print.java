import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Print {
	public static void main(String[] args) {
	
	String ret = "";
		
		final String basePath = "C:/Projekte/masterthesis/36_Petclinic/kieker-traces/calibration";
		final List<String> paths = new LinkedList<>();
		paths.add("api-gateway");
		paths.add("customers-service");
		paths.add("discovery-server");
		paths.add("vets-service");
		paths.add("visits-service");
		
		for (String path : paths) {
			File folder = new File(basePath + '/' + path);
			File[] listOfFiles = folder.listFiles();

			for (File f : listOfFiles) {
				if (f.getName().startsWith("kieker")) {
					ret += f.getAbsolutePath();
					ret += ";";
				}
			}
		}
		
		System.out.println(ret);
	}
}
