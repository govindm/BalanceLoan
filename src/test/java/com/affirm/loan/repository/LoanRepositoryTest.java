package com.affirm.loan.repository;

import com.affirm.loan.common.Loan;
import com.affirm.loan.exception.AffirmRepositoryException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoanRepositoryTest
{
    @Test
    public void testLoanRepository() throws AffirmRepositoryException {
        LoanRepository loanRepository = new LoanRepository("loans.csv");
        List<Loan> expectedLoans = new ArrayList<>();
        expectedLoans.add(new Loan(1l, 10552,0.02f, 0.15f, "MO"));
        expectedLoans.add(new Loan(2l, 51157,0.01f, 0.15f, "VT"));
        expectedLoans.add(new Loan(3l, 74965,0.06f, 0.35f, "AL"));
        List<Loan> actualLoans = loanRepository.loadLoans();
        assertEquals(expectedLoans, actualLoans);
        assertTrue(expectedLoans.size() == actualLoans.size()
                && expectedLoans.containsAll(actualLoans)
                && actualLoans.containsAll(expectedLoans));
    }
}
