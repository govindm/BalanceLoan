package com.affirm.loan.repository;

import com.affirm.loan.common.Facility;
import com.affirm.loan.exception.AffirmRepositoryException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FacilityRepositoryTest
{
    @Test
    public void testFacilityRepository() throws AffirmRepositoryException {
        FacilityRepository facilityRepository = new FacilityRepository("facilities.csv");
        List<Facility> expectedFacilities = new ArrayList<>();
        expectedFacilities.add(new Facility(2l, 1l, 61104, 0.07f));
        expectedFacilities.add(new Facility(1l, 2l, 126122, 0.06f));
        List<Facility> actualFacilities = facilityRepository.loadFacilities();
        assertTrue(actualFacilities.size() == expectedFacilities.size()
                && actualFacilities.containsAll(expectedFacilities)
                && expectedFacilities.containsAll(actualFacilities));
    }
}
