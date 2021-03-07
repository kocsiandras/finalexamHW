package com.greenfoxacademy.finalexamhw.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fox {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private String name;

  @Column
  private Foxtype foxtype;

  @Column
  private int foxPrice;

  @Column
  private String favFood;

  @Column
  private String favDrink;

  @Column
  private int happinessLevel;

  @Column
  private int hungerLevel;

  @Column
  private int thirstLevel;
}
