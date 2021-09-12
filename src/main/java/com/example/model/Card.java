package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "card")
public class Card {

   @Id
   private String pan;

   private String expiry;

   @Transient
   @ToString.Exclude
   private String cvv;

   @OneToMany(mappedBy = "card")
   @JsonIgnore
   @ToString.Exclude
   private Set<Payment> payments;
}
