package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Data
@Entity
@Table(name = "cardholder")
public class CardHolder {

    @NotBlank(message = "Cart holder name is required.")
    private String name;
    @Id
    @Email(message = "Invalid cardholder email format.")
    private String email;

    @OneToMany(mappedBy = "cardholder")
    @JsonIgnore
    @ToString.Exclude
    private Set<Payment> payments;

}
