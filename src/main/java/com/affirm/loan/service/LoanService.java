package com.affirm.loan.service;

import com.affirm.loan.common.Assignment;
import com.affirm.loan.common.Facility;
import com.affirm.loan.common.Loan;
import com.affirm.loan.common.Yield;
import com.affirm.loan.exception.AffirmRepositoryException;
import com.affirm.loan.exception.AffirmServiceException;
import com.affirm.loan.repository.AssigmentRepository;
import com.affirm.loan.repository.YieldRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class LoanService {

    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);
    private final FacilityService facilityService;
    private final AssigmentRepository assigmentRepository;
    private final YieldRepository yieldRepository;
    private List<Assignment> assignments;
    private final Map<Long, Yield> yields;

    public LoanService(FacilityService facilityService,
                       AssigmentRepository assigmentRepository,
                       YieldRepository yieldRepository) {
        this.facilityService = facilityService;
        this.assigmentRepository = assigmentRepository;
        this.yieldRepository = yieldRepository;
        yields = new HashMap<>();
        assignments = new ArrayList<>();
    }

    public Optional<Facility> assignFacilityToLoan(Loan loan) {
        Optional<Facility> facility = facilityService.findFacilityWithLowestRateAndHighestAmount(loan);
        if(!facility.isPresent()){
            logger.warn("No match found for loan id: " + loan.getId());
        }
        facility.ifPresent(f -> {
            f.setAmount(f.getAmount() - loan.getAmount());
            assignments.add(new Assignment(loan.getId(), f.getId()));
            BigDecimal yield = calculateYield(loan, f);
            Yield totalYield = yields.get(f.getId());
            if (totalYield == null) {
                yields.put(f.getId(), new Yield(f.getId(), yield));
            } else {
                totalYield.addYield(yield);
            }
        });
        return facility;
    }

    private static BigDecimal calculateYield(Loan loan, Facility facility) {
        BigDecimal loanAmount = new BigDecimal(loan.getAmount());
        BigDecimal loanDefaultLikelihood = BigDecimal.valueOf(loan.getDefaultLikelihood());
        BigDecimal loanInterestRate = BigDecimal.valueOf(loan.getInterestRate());
        BigDecimal expectInterest = BigDecimal.ONE
                .subtract(loanDefaultLikelihood)
                .multiply(loanInterestRate)
                .multiply(loanAmount);
        BigDecimal loss = loanAmount.multiply(loanDefaultLikelihood);
        BigDecimal facilityFee = loanAmount.multiply(BigDecimal.valueOf(facility.getInterestRate()));
        return expectInterest.subtract(loss).subtract(facilityFee);
    }

    public void writeAssignments() throws AffirmServiceException {
        try {
            assigmentRepository.writeAssignments(assignments);
        }catch (AffirmRepositoryException e){
            throw handleException(e);
        }
    }

    public void writeYields() throws AffirmServiceException {
        try {
            yieldRepository.writeYields(yields.values());
        }catch (AffirmRepositoryException e){
            throw handleException(e);
        }
    }

    private AffirmServiceException handleException(Exception e){
       return new AffirmServiceException(e);
    }
}
