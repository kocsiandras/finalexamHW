package com.greenfoxacademy.finalexamhw.repositories;

import com.greenfoxacademy.finalexamhw.models.Food;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodRepository extends CrudRepository<Food, Long> {
  Food findById(long id);

  boolean existsById(long id);
}
