package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.dtos.NewFoxDTO;
import com.greenfoxacademy.finalexamhw.models.Food;
import com.greenfoxacademy.finalexamhw.models.Fox;
import com.greenfoxacademy.finalexamhw.models.User;

public interface FoxService {
  void saveFox(Fox fox);
  boolean isValidType(String foxType);
  boolean existsById(long id);
  Fox findById(long id);
  void feed(long id, long foodId, User user);
  Fox getNewFox(NewFoxDTO newFoxDTO);
  void deleteFox(long id);
}
