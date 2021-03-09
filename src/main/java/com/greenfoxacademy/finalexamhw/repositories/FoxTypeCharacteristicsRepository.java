package com.greenfoxacademy.finalexamhw.repositories;

import com.greenfoxacademy.finalexamhw.models.FoxTypeCharacteristics;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoxTypeCharacteristicsRepository extends CrudRepository<FoxTypeCharacteristics, Long> {
  FoxTypeCharacteristics findByFoxType(String foxtype);
}
