package com.example.dto;

import com.example.validation.ExpiryDateConstraint;
import com.example.validation.LuhnCodeConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Transient;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
public class CardDto {
    @NotBlank(message = "PAN is required.")
    @Size(min = 16, max = 16, message = "PAN should be 16 digits long.")
    @LuhnCodeConstraint()
    private String pan;

    @NotNull(message = "Expiration date is required.")
    @ExpiryDateConstraint
    private String expiry;

    @ToString.Exclude
    @NotBlank(message = "CVV code is required.")
    private String cvv;
}
