package com.ellicottr.service;

import com.ellicottr.beans.Loan;
import com.ellicottr.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class LoanService {
    private LoanRepository loanRepository;

    @Autowired
    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public Loan createLoan(Loan data) {
        data.setApr(data.calculateApr(data));
        try {
            loanRepository.save(data);
        } catch (Exception e) {
            String message = e.getMessage();
        }
        return data;
    }

    public Loan getLoan(String ssn) {
        return loanRepository.findById(ssn).orElse(new Loan());
    }
}
