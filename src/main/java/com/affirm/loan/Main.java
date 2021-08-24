package com.affirm.loan;

import com.affirm.loan.common.Loan;
import com.affirm.loan.repository.*;
import com.affirm.loan.service.FacilityService;
import com.affirm.loan.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args){

        FacilityRepository facilityRepository = new FacilityRepository("facilities.csv");
        LoanRepository loanRepository  = new LoanRepository("loans.csv");
        CovenantsRepository covenantsRepository = new CovenantsRepository("covenants.csv");
        AssigmentRepository assigmentRepository = new AssigmentRepository("assignments.csv");
        YieldRepository yieldRepository = new YieldRepository("yields.csv");
        try {
            FacilityService facilityService = new FacilityService(facilityRepository, covenantsRepository);
            LoanService loanService = new LoanService(facilityService, assigmentRepository, yieldRepository);
            for(Loan loan: loanRepository.loadLoans()) {
                loanService.assignFacilityToLoan(loan);
            }
            loanService.writeAssignments();
            loanService.writeYields();
        }catch (Exception e){
            logger.error("Failed to process loan", e);
        }
    }
}