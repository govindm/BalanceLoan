package com.affirm.loan.repository;

import com.affirm.loan.common.Loan;
import com.affirm.loan.exception.AffirmRepositoryException;
import com.affirm.loan.utility.CSVHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
/*
  Reads the loan data from the loans.csv file.
 */
public class LoanRepository {
    private static final Logger logger = LoggerFactory.getLogger(LoanRepository.class);
    private final String filename;
    public LoanRepository(String filename) {
        this.filename = filename;
    }
    public List<Loan> loadLoans() throws AffirmRepositoryException{
        List<Loan> loans = new ArrayList<>();
        try {
            for (String[] fields : CSVHelper.readFromCsvFile(filename)){
                loans.add(
                        new Loan(Long.valueOf(fields[2]),
                                Integer.valueOf(fields[1]),
                                Float.parseFloat(fields[3]),
                                Float.parseFloat(fields[0]),
                                fields[4]));
            }
            return loans;
        }catch (Exception e){
            throw handleException(e);
        }
    }
    private AffirmRepositoryException handleException(Exception e){
        return new AffirmRepositoryException("Failed to load loan data", e);
    }

}