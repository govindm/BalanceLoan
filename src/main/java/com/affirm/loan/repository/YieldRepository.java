package com.affirm.loan.repository;

import com.affirm.loan.common.Yield;
import com.affirm.loan.exception.AffirmRepositoryException;
import com.affirm.loan.utility.CSVHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/*
  Reads the loan data from the loans.csv file.
 */
public class YieldRepository {
    private static final Logger logger = LoggerFactory.getLogger(YieldRepository.class);
    private final String filename;
    private static final String[] header = {"facility_id", "expected_yield"};
    public YieldRepository(String filename) {
        this.filename = filename;
    }
    public void writeYields(Collection<Yield> yields) throws AffirmRepositoryException {
        try {
            List<String[]> yieldData = yields.stream()
                    .map(Yield::convertToStringArray)
                    .collect(Collectors.toList());
            CSVHelper.writeToCsvFile(yieldData, header, filename);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    private AffirmRepositoryException handleException(Exception e) {
        return new AffirmRepositoryException("Failed to write yield data", e);
    }

}