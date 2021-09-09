package com.example.model;

import com.example.validation.LuhnCodeConstraint;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

import javax.validation.constraints.*;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "card")
public class Card {

   @Id
   @NotBlank(message = "PAN is required.")
   @Size(min = 16, max = 16, message = "PAN should be 16 digits long.")
   @LuhnCodeConstraint()
   private String pan;

   @NotNull(message = "Expiration date is required.")
   @FutureOrPresent(message = "Payment card is expired.")
   @JsonFormat(pattern = "MMyy")
   private Date expiry;

   @JsonIgnore
   @Transient
   @ToString.Exclude
   //@NotBlank(message = "CVV code is required.")
   private String cvv;

   @OneToMany(mappedBy = "card")
   @JsonIgnore
   @ToString.Exclude
   private Set<Payment> payments;
}
