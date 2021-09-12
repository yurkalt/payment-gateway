package com.example.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    private Integer invoice;

    private Integer amount;

    private String currency;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cardholder_email")
    private CardHolder cardholder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_pan")
    private Card card;

}

