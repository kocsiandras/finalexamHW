package com.greenfoxacademy.finalexamhw.dtos;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDTO {
  private String username;
  private String password;
}