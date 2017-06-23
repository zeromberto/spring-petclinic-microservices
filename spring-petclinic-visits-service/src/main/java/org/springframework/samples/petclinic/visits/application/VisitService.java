package org.springframework.samples.petclinic.visits.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.visits.domain.model.visit.Visit;
import org.springframework.samples.petclinic.visits.domain.model.visit.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import resourcedemand.ResourceDemandingBehaviour;

import java.util.List;

/**
 * @author mszarlinski on 2016-10-30.
 */
@Service
public class VisitService {

    private final VisitRepository visitRepository;

    @Autowired
    public VisitService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Transactional
    public void saveVisit(Visit visit) throws DataAccessException {
    	Visit savedVisit = visitRepository.findOneByDateAndPetId(visit.getDate(), visit.getPetId());
    	if(savedVisit != null) {
    		savedVisit.setDescription(visit.getDescription());
    	} else {
    		savedVisit = visit;
    	}
    	visitRepository.save(savedVisit);
    }

    @Transactional(readOnly = true)
    public List<Visit> findVisitsByPetId(final int petId) {
        new ResourceDemandingBehaviour().calculate(4);
        return visitRepository.findByPetId(petId);
    }

}
