package com.greenfoxacademy.finalexamhw.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private String username;

  @Column
  private String password;

  @Column
  private int money;
}
