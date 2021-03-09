package com.greenfoxacademy.finalexamhw.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuoteResponse {
  List<StockChange> result;
}
