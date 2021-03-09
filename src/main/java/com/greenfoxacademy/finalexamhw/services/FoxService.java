package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.models.Fox;

public interface FoxService {
  void saveFox(Fox fox);
  boolean isValidType(String foxType);
}
