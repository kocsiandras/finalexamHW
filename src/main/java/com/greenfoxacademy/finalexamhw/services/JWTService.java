package com.greenfoxacademy.finalexamhw.services;

import java.util.Optional;

public interface JWTService {

  String createToken(String username);
  Optional<String> extractUsernameFromToken(String token);
}
