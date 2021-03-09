package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.models.FoxTypeCharacteristics;

public interface FoxTypeCharacteristicsService {
  FoxTypeCharacteristics findByFoxType(String foxtype);
}
