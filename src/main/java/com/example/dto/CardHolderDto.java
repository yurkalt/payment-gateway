package com.example.dto;

import com.example.model.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
public class CardHolderDto {
    @NotBlank(message = "Cart holder name is required.")
    private String name;

    @Email(message = "Invalid cardholder email format.")
    private String email;

}
