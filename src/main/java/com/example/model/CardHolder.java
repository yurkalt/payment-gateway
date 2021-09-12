package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "cardholder")
public class CardHolder {

    private String name;
    @Id
    private String email;

    @OneToMany(mappedBy = "cardholder")
    @JsonIgnore
    @ToString.Exclude
    private Set<Payment> payments;

}
