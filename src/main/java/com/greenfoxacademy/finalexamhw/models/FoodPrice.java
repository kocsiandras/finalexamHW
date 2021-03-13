package com.greenfoxacademy.finalexamhw.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FoodPrice {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column
  private double foodPrice;

  @Column(name = "updated_at")
  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

}
