package com.example.dto;

import com.example.model.Payment;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
public class CardHolderDto {
    @NotBlank(message = "Cart holder name is required.")
    private String name;

    @Email(message = "Invalid cardholder email format.")
    private String email;

}
