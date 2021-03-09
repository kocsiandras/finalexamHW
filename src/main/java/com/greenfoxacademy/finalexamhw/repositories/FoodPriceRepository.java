package com.greenfoxacademy.finalexamhw.repositories;

import com.greenfoxacademy.finalexamhw.models.FoodPrice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodPriceRepository extends CrudRepository<FoodPrice, Long> {
  FoodPrice findTopByOrderByIdDesc();
}
