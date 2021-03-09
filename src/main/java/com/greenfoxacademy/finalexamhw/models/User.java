package com.greenfoxacademy.finalexamhw.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

  @OneToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "user_fox",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "fox_id"))
  private List<Fox> foxList = new ArrayList<>();
}
