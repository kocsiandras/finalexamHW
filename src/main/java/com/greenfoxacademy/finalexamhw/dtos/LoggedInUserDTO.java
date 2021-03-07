package com.greenfoxacademy.finalexamhw.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoggedInUserDTO {

  String username;
  String token;
}
