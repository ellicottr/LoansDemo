package com.ellicottr.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Loans")
public class Loan {
    @Id
    private String ssn;
    private LocalDate dob;
    private double loanAmount;
    private double rate;
    private int loanType;
    private int term;
    private double apr;

    public double calculateApr(Loan data) {
        double interest = data.getLoanAmount() * (data.getRate()/100) * data.getTerm() / 100;
        double principal = data.getLoanAmount();
        int fee = 0;
        switch (data.getLoanType()) {
            case 2: //Auto
                fee = 500;
                break;
            case 3: //Personal
                fee = 750;
                break;
            case 4: //Mortgage
                fee = 1500;
                break;
            case 1: //Student
            default:
                fee = 0;
        }
        double apr = ((((fee + interest) / principal) / data.getTerm()) * 365) * 100;
        BigDecimal bd = new BigDecimal(Double.toString(apr));
        bd = bd.setScale(2, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }
}
