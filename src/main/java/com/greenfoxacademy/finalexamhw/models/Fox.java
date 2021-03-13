package com.greenfoxacademy.finalexamhw.models;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

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
  private String foxName;

  @Column
  private String foxType;

  @Column
  private int foxPrice;

  @Column
  private String favFood;

  @Column
  private int happinessLevel;

  @Column
  private int hungerLevel;

  @Column
  private String status;

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Fox) {
      Fox other = (Fox) obj;
      return other.getId() == this.getId();
    }
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return (int) this.getId();
  }
}
