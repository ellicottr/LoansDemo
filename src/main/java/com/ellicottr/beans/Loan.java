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
    public String ssn;
    public LocalDate dob;
    public double loanAmount;
    public double rate;
    public int loanType;
    public int term;
    public double apr;

    public double calculateApr(Loan data) {
        double interest = getInterest(data);
        double principal = getPrincipal(data);
        int term = getTerm(data);

        double apr = ((((getFee(data.getLoanType()) + interest) / principal) / term) * 365) * 100;
        BigDecimal bd = new BigDecimal(Double.toString(apr));
        bd = bd.setScale(2, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }
    public double getInterest(Loan data) {
        return data.getLoanAmount() * (data.getRate()/100) * data.getTerm() / 100;
    }
    public double getPrincipal(Loan data) {
        return data.getLoanAmount();
    }
    public int getTerm(Loan data) {
        return data.getTerm();
    }

    public int getFee(int loanType) {
        // This could also be put into the database and looked up
        int fee;
        switch (loanType) {
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
        return fee;
    }
}
