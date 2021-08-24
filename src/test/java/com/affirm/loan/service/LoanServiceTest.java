package com.affirm.loan.service;

import com.affirm.loan.common.Facility;
import com.affirm.loan.common.Loan;
import com.affirm.loan.repository.*;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoanServiceTest {

    @Test
    public void testLoanAssignment() throws Exception{
        FacilityRepository facilityRepository = new FacilityRepository("facilities.csv");
        CovenantsRepository covenantsRepository = new CovenantsRepository("covenants.csv");
        FacilityService facilityService = new FacilityService(facilityRepository, covenantsRepository);
        AssigmentRepository assigmentRepository = new AssigmentRepository("assignments.csv");
        YieldRepository yieldRepository = new YieldRepository("yields.csv");
        LoanService loanService = new LoanService(facilityService, assigmentRepository, yieldRepository);
        Map<Long, Long> expectedAssignments = new HashMap<>();
        expectedAssignments.put(1l,1l);
        expectedAssignments.put(2l,2l);
        expectedAssignments.put(3l,1l);
        for (Loan loan : getLoans()) {
            Optional<Facility> facility = loanService.assignFacilityToLoan(loan);
            assertEquals(expectedAssignments.get(loan.getId()), facility.get().getId());
        }
    }

    private List<Loan> getLoans() throws Exception{
        LoanRepository loanRepository = new LoanRepository("loans.csv");
        return loanRepository.loadLoans();
    }
}
