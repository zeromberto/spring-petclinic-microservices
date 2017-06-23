package org.springframework.samples.petclinic.customers.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.customers.domain.model.owner.Owner;
import org.springframework.samples.petclinic.customers.domain.model.owner.OwnerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import resourcedemand.ResourceDemandingBehaviour;

import java.util.Collection;
import java.util.List;

/**
 * @author Maciej Szarlinski
 */
@Service
public class OwnerService {

    private static final Logger LOG = LoggerFactory.getLogger(OwnerService.class);

    private final OwnerRepository ownerRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Transactional(readOnly = true)
    public Owner findOwnerById(int id) throws DataAccessException {
        new ResourceDemandingBehaviour().calculate(1);
        return ownerRepository.findOne(id);
    }

    //@Monitored
    @Transactional(readOnly = true)
    public Collection<Owner> findAll() throws DataAccessException {
        List<Owner> owners = ownerRepository.findAll();
        new ResourceDemandingBehaviour().calculate(owners.size());
        return owners;
    }

    //@Monitored
    @Transactional
    public void saveOwner(Owner owner) throws DataAccessException {
        LOG.info("Saving owner {}", owner);
        ownerRepository.save(owner);
    }

}
