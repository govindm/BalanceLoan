package com.affirm.loan.service;

import com.affirm.loan.common.Facility;
import com.affirm.loan.common.Loan;
import com.affirm.loan.repository.CovenantsRepository;
import com.affirm.loan.repository.FacilityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FacilityService {

    private static final Logger logger = LoggerFactory.getLogger(FacilityService.class);
    private final Map<Long, Facility> facilities;
    //Compare by interest rate and then amount
    private Comparator<Facility> compareByInterestRateAndAmount = Comparator
            .comparing(Facility::getInterestRate)
            .thenComparing(Facility::getAmount);
    public FacilityService(FacilityRepository facilityRepository, CovenantsRepository covenantsRepository) throws Exception {
        this.facilities = facilityRepository.loadFacilities().stream()
                .collect(Collectors.toMap(Facility::getId, Function.identity()));
        Map<Long, Set<Long>> bankToFacilitiesMap = buildBankToFacilityMap(facilities.values());
        covenantsRepository.loadCovenants(this.facilities, bankToFacilitiesMap);
    }

    public Optional<Facility> findFacilityWithLowestRateAndHighestAmount(Loan loan) {
        List<Facility> matchingFacilities = this.facilities.values().stream()
                .filter(f -> f.getAmount() > loan.getAmount() &&
                             (f.getCovenants() == null
                                     || f.getCovenants().isSatisfied(loan.getDefaultLikelihood(), loan.getState())))
                .sorted(compareByInterestRateAndAmount).collect(Collectors.toList());
        return matchingFacilities.stream().findFirst();
    }

    private Map<Long, Set<Long>> buildBankToFacilityMap(Collection<Facility> facilities) {
        return facilities.stream().collect(Collectors.groupingBy(Facility::getBankId,
                Collectors.mapping(Facility::getId, Collectors.toSet())));
    }
}
