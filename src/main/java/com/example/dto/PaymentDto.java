package com.example.dto;

import com.example.model.Card;
import com.example.model.CardHolder;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class PaymentDto {

    @NotNull
    private Integer invoice;

    @NotNull
    @Positive(message = "Amount should be a positive")
    private Integer amount;

    @NotBlank(message = "Currency is required.")
    private String currency;

    @Valid
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cardholder_email")
    private CardHolderDto cardholder;

    @Valid
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_pan")
    private CardDto card;
}
