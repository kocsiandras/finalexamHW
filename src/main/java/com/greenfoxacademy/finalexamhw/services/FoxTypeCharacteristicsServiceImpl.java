package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.models.FoxTypeCharacteristics;
import com.greenfoxacademy.finalexamhw.repositories.FoxTypeCharacteristicsRepository;
import org.springframework.stereotype.Service;

@Service
public class FoxTypeCharacteristicsServiceImpl implements FoxTypeCharacteristicsService{

  final
  FoxTypeCharacteristicsRepository foxTypeCharacteristicsRepository;

  public FoxTypeCharacteristicsServiceImpl(FoxTypeCharacteristicsRepository foxTypeCharacteristicsRepository) {
    this.foxTypeCharacteristicsRepository = foxTypeCharacteristicsRepository;
  }

  @Override
  public FoxTypeCharacteristics findByFoxType(String foxtype) {
    return foxTypeCharacteristicsRepository.findByFoxType(foxtype);
  }
}
