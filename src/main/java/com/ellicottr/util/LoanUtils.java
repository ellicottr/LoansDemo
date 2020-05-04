package com.ellicottr.util;

import com.ellicottr.beans.Loan;
import org.springframework.stereotype.Component;

@Component
public class LoanUtils {
    public boolean validate(Loan loan) throws Exception {
        // I would validate all the loan fields here
         String message = validateSSN(loan.getSsn());
         if (message.length() > 0)
             throw new Exception(message);

        return true;
    }
    private String validateSSN(String ssn) {
        if (ssn.isEmpty() || ssn == null)
            return "SSN is not valid";
        if (ssn.length() != 9) {
            return "SSN is not the correct length";
        }
        return "";
    }
}
