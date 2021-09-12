package com.example.controller;

import com.example.dto.PaymentDto;
import com.example.model.Payment;
import com.example.service.PaymentService;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(value = "/v1")
public class PaymentController {

    public static final String DOT_IN_FIELD_NAME = ".";
    @Autowired
    private PaymentService paymentService;

    @PostMapping(value = "/submit", consumes =  "application/json")
    public ResponseEntity<ResponseDTO> submitPayment( @Valid @RequestBody PaymentDto payment){
        paymentService.processPayment(payment);
        return ResponseEntity.ok(ResponseDTO.builder().approved(true).build());
    }

    @GetMapping("/{invoiceId}")
    public ResponseEntity<PaymentDto> getPaymentByInvoiceId(@PathVariable String invoiceId){
        return ResponseEntity.ok(paymentService.getPaymentById(invoiceId));
    }

    @ExceptionHandler({MethodArgumentNotValidException.class })
    public ResponseEntity<ResponseDTO> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                fieldName = removeParentName(fieldName);
                String errorMessage = error.getDefaultMessage();
                errors.put(fieldName, errorMessage);
            });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO.builder().approved(false).errors(errors).build());
    }

    private String removeParentName(String fieldName) {
        if (fieldName.contains(DOT_IN_FIELD_NAME)) {
            int index = fieldName.indexOf(".") + 1;
            fieldName = fieldName.substring(index);
        }
        return fieldName;
    }

    @Data
    @Builder
    static class ResponseDTO {
        private Boolean approved;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private Map<String, String> errors;
    }

}
