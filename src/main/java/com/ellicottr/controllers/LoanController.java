package com.ellicottr.controllers;

import com.ellicottr.beans.Loan;
import com.ellicottr.service.LoanService;
import com.ellicottr.util.LoanUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoanController {
    @Autowired
    private LoanUtils loanUtils;

    @Autowired
    private LoanService loanService;

    @PostMapping("/loans/createloan")
    @ResponseBody
    public ResponseEntity<String> createLoan(@RequestBody Loan data) throws Exception {
        try {
            if (loanUtils.validate(data)) {
                loanService.createLoan(data);
            }
        }
        catch (Exception e) {
            ObjectMapper mapper = new ObjectMapper();
            JavaTimeModule javaTimeModule = new JavaTimeModule();
            mapper.registerModule(javaTimeModule);
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/loans/getloan", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> getLoan(@RequestParam String ssn) throws Exception {
        Loan loan = loanService.getLoan(ssn);

        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        mapper.registerModule(javaTimeModule);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        if (loan.getSsn() != null) {
            String response = mapper.writeValueAsString(loan);
            return ResponseEntity.ok(response);
        }
        else {
            String response = mapper.writeValueAsString(new Loan());
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}

