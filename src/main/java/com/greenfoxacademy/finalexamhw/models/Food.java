package com.greenfoxacademy.finalexamhw.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private String foodName;

  @Column
  private int foodPrice;

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Food) {
      Food other = (Food) obj;
      return other.getId() == this.getId();
    }
    return super.equals(obj);
  }

  @Override
  public int hashCode() {
    return (int) this.getId();
  }
}
