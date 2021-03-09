package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.models.Fox;
import com.greenfoxacademy.finalexamhw.repositories.FoxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

@Service
public class FoxServiceImpl implements FoxService{

  final
  FoxRepository foxRepository;

  public FoxServiceImpl(FoxRepository foxRepository) {
    this.foxRepository = foxRepository;
  }

  @Override
  public void saveFox(Fox fox) {
    foxRepository.save(fox);
  }

  @Override
  public boolean isValidType(String foxType) {
    List<String> foxTypeList = new ArrayList<>(Arrays.asList("Arcticfox", "Bengalfox", "Redfox"));
    return foxTypeList.contains(foxType);
  }


}
