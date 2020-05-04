package com.ellicottr.beans;

import com.ellicottr.LoanApplication;
import com.ellicottr.beans.Loan;
import com.ellicottr.service.LoanService;
import com.ellicottr.util.LoanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Description;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(LoanApplication.class)
@AutoConfigureMockMvc
public class LoanTest {

    @Test
    @Description("LoanControllerGetLoanTest")
    public void LoanTestInterest() throws Exception {
        Loan loan = getLoanData();
        double interest = loan.getInterest(loan);
        double expected = 10000 * (5.0/100) * 30 / 100;
        assertEquals(expected, interest,.0001);
    }
    @Test
    @Description("Test Loan Fees")
    public void LoanTestFee() throws Exception {
        Loan loan = new Loan();

        int fee  = loan.getFee(1);
        assertEquals(0, fee);

        fee  = loan.getFee(2);
        assertEquals(500, fee);

        fee  = loan.getFee(3);
        assertEquals(750, fee);

        fee  = loan.getFee(4);
        assertEquals(1500, fee);


    }
    private Loan getLoanData() {
        Loan loan = new Loan();
        loan.setSsn("111224444");
        loan.setDob(LocalDate.of(2000, 2, 3));
        loan.setLoanAmount(10000);
        loan.setLoanType(2);
        loan.setRate(5.00);
        loan.setTerm(30);
        loan.setApr(0);
        return loan;
    }
}