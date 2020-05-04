package com.ellicottr.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name="LoanType")
public class LoanType {
    @Id
    private int loanType;
    private String description;
    private int amount;
}