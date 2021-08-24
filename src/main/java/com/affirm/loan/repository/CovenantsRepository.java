package com.affirm.loan.repository;

import com.affirm.loan.common.Covenants;
import com.affirm.loan.common.Facility;
import com.affirm.loan.exception.AffirmRepositoryException;
import com.affirm.loan.utility.CSVHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class CovenantsRepository {
    private static final Logger logger = LoggerFactory.getLogger(CovenantsRepository.class);
    private final String filename;
    public CovenantsRepository(String filename) {
        this.filename = filename;
    }
    public void loadCovenants(Map<Long, Facility> facilities,  Map<Long, Set<Long>> bankToFacilitiesMap) throws AffirmRepositoryException {
        try {
            for (String[] fields : CSVHelper.readFromCsvFile(filename)) {
                Long facilityId = fields[0] == null ? null : Long.valueOf(fields[0]);
                Long bankId = Long.valueOf(fields[2]);
                Float maxDefaultLikelihood = fields[1].isEmpty() ? null : Float.valueOf(fields[1]);
                String bannedState = fields[3];
                if(facilityId == null){
                    bankToFacilitiesMap.get(bankId).forEach(f -> {
                        Facility facility = facilities.get(f);
                        addCovenants(facility, maxDefaultLikelihood, bannedState);
                    });
                }else{
                    Facility facility = facilities.get(facilityId);
                    addCovenants(facility, maxDefaultLikelihood, bannedState);
                }
            }
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    private void addCovenants(Facility facility,  Float maxDefaultLikelihood, String bannedState) {
        Covenants covenants = facility.getCovenants();
        if (covenants == null) {
            covenants = new Covenants(maxDefaultLikelihood);
            facility.setCovenants(covenants);
        } else if(maxDefaultLikelihood != null) {
            if (covenants.getMaxDefaultLikelihood() == null) {
                covenants.setMaxDefaultLikelihood(maxDefaultLikelihood);
            } else {
                logger.warn("There are multiple values for max_default_likelihood for facility id: {}", facility.getId());
                covenants.setMaxDefaultLikelihood(Math.min(covenants.getMaxDefaultLikelihood(), maxDefaultLikelihood));
            }
        }
        if (bannedState != null) {
            covenants.addBandedState(bannedState);
        }
    }

    private AffirmRepositoryException handleException(Exception e){
        return new AffirmRepositoryException("Unable to load Covenants data", e);
    }
}
