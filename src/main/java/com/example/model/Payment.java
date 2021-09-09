package com.example.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @NotNull
    private Integer invoice;

    @NotNull
    @Positive(message = "Amount should be a positive")
    private Integer amount;

    @NotBlank(message = "Currency is required.")
    private String currency;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cardholder_email")
    private CardHolder cardholder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_pan")
    private Card card;

}

