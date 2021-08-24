package com.affirm.loan.repository;

import com.affirm.loan.common.Facility;
import com.affirm.loan.exception.AffirmRepositoryException;
import com.affirm.loan.utility.CSVHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
public class FacilityRepository {
    private static final Logger logger = LoggerFactory.getLogger(FacilityRepository.class);
    private final String filename;
    public FacilityRepository (String filename){
       this.filename = filename;
    }
    public List<Facility> loadFacilities() throws AffirmRepositoryException{
        List<Facility> facilities = new ArrayList<>();
        try {
            for (String[] fields : CSVHelper.readFromCsvFile(filename)){
                facilities.add(
                        new Facility(Long.valueOf(fields[2]),
                                Long.valueOf(fields[3]),
                                Double.valueOf(fields[0]).intValue(), // input file has fractional data
                                Float.parseFloat(fields[1])));
            }
            return facilities;
        }catch (Exception e){
            throw handleException(e);
        }
    }

    private AffirmRepositoryException handleException(Exception e){
        return new AffirmRepositoryException("Failed to load Facility data", e);
    }
}
