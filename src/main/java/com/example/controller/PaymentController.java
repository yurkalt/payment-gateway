package com.example.controller;

import com.example.model.Payment;
import com.example.service.PaymentService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/v1")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Validated
    @PostMapping(value = "/submit", consumes =  "application/json")
    public ResponseEntity<ResponseDTO> submitPayment( @RequestBody Payment payment){
        paymentService.processPayment(payment);
        return ResponseEntity.ok(ResponseDTO.builder().approved(true).build());
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<Payment> getPaymentByInvoiceId(@PathVariable String invoiceId){
        return ResponseEntity.ok(paymentService.getPaymentById(invoiceId));
    }

    @ExceptionHandler({ConstraintViolationException.class })
    public ResponseEntity<ResponseDTO> handleValidationExceptions(
            ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
            ex.getConstraintViolations().forEach((violation) -> {
                String fieldName = violation.getPropertyPath().toString();
                String errorMessage = violation.getMessage();
                errors.put(fieldName, errorMessage);
            });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder().approved(false).errors(errors).build());
    }

    @Data
    @Builder
    static class ResponseDTO {
        private Boolean approved;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Map<String, String> errors;
    }

}
