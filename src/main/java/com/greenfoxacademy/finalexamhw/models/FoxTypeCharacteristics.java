package com.greenfoxacademy.finalexamhw.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FoxTypeCharacteristics {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column
  private String foxType;

  @Column
  private int price;

  @Column
  private String favFood;

  @Column
  private String favDrink;

  @Column
  private int startingHappinessLevel;

}
