package com.affirm.loan.repository;

import com.affirm.loan.common.Assignment;
import com.affirm.loan.exception.AffirmRepositoryException;
import com.affirm.loan.utility.CSVHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/*
  Reads the loan data from the loans.csv file.
 */
public class AssigmentRepository {
    private static final Logger logger = LoggerFactory.getLogger(AssigmentRepository.class);
    private final String filename;
    private static final String[] header = {"loan_id", "facility_id"};

    public AssigmentRepository(String filename) {
        this.filename = filename;
    }

    public void writeAssignments(List<Assignment> assignments) throws AffirmRepositoryException {
        try {
            List<String[]> assignmentData = assignments.stream()
                    .map(Assignment::convertToStringArray)
                    .collect(Collectors.toList());
            CSVHelper.writeToCsvFile(assignmentData, header, filename);
        } catch (Exception e) {
            throw handleException(e);
        }
    }

    private AffirmRepositoryException handleException(Exception e) {
        return new AffirmRepositoryException("Failed to write assignment data", e);
    }
}