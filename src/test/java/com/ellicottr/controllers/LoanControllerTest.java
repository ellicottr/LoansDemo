package com.ellicottr.controllers;

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
public class LoanControllerTest {
    @MockBean
    private LoanUtils loanUtils;

    @MockBean
    private LoanService loanService;

    @Autowired
    private MockMvc mockMvc;

    private final String getUrl = "/loans/getloan";
    private final String postUrl = "/loans/createloan";
    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    @Description("LoanControllerGetLoanTest")
    public void LoanControllerGetLoanTest() throws Exception {
        Loan expected = getLoanData();
        Mockito.doReturn(getLoanData()).when(loanService).getLoan(Mockito.anyString());

        ResultActions resultActions = mockMvc.perform(get(getUrl).param("ssn", String.valueOf(expected.getSsn())))
                                              .andExpect(status().isOk());
        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Loan actual = mapper.readValue(contentAsString, Loan.class);

        Assert.assertEquals(expected.getSsn(), actual.getSsn());
        assertEquals(expected.getDob(), actual.getDob());
        assertEquals(expected.getLoanAmount(), actual.getLoanAmount(), 0.0001);
        assertEquals(expected.getLoanType(), actual.getLoanType());
        assertEquals(expected.getRate(), actual.getRate(), 0.0001);
        assertEquals(expected.getTerm(), actual.getTerm());
    }

    @Test
    @Description("LoanControllerCreateLoanTest")
    public void LoanControllerCreateLoanTest() throws Exception {
        String expected = getJsonData();
        Mockito.doReturn(null).when(loanService).createLoan(Mockito.any(Loan.class));
        Mockito.doReturn(true).when(loanUtils).validate(Mockito.any(Loan.class));

        mockMvc.perform(post(postUrl)
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(getJsonData()))
                .andExpect(status().isOk());

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

    private String getJsonData() {
        String jsonString = "{\"ssn\": \"123456789\"," +
                                    "\"dob\": \"2000-06-06\"," +
                                    "\"loanAmount\": 10000," +
                                    "\"rate\": 4.0," +
                                    "\"loanType\": 1," +
                                    "\"term\": 30" +
                                    "}";
        return jsonString;
    }

}
