package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.dtos.LoggedInUserDTO;
import com.greenfoxacademy.finalexamhw.dtos.RegistrationDTO;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.repositories.UserRepository;
import com.greenfoxacademy.finalexamhw.services.JWTServiceImpl;
import com.greenfoxacademy.finalexamhw.services.UserServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceTests {

  private final UserRepository userRepository = Mockito.mock(UserRepository.class);
  private final JWTServiceImpl jwtService = Mockito.mock(JWTServiceImpl.class);
  private final BCryptPasswordEncoder bCryptPasswordEncoder = Mockito.mock(BCryptPasswordEncoder.class);

  @Test
  public void shouldValidateCorrectPassword() {
    UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtService);
    RegistrationDTO userRegDTO = new RegistrationDTO();
    User storedUser = User.builder().build();
    String token = "asd";
    Mockito.when(userRepository.findByUsername(userRegDTO.getUsername())).thenReturn(storedUser);
    Mockito.when(jwtService.createToken(storedUser.getUsername())).thenReturn(token);
    Mockito.when(bCryptPasswordEncoder.matches(userRegDTO.getPassword(), storedUser.getPassword())).thenReturn(true);
    LoggedInUserDTO loggedInUserDTO = userService.validateUser(userRegDTO);
    Assert.assertEquals(userRegDTO.getUsername(), loggedInUserDTO.getUsername());
  }

  @Test
  public void saveUser() {
    UserServiceImpl userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, jwtService);
    User newUser = new User();
    Mockito.when(userRepository.save(newUser)).thenReturn(newUser);
    User user = userService.saveUser(newUser);
    Assert.assertEquals(user, newUser);
  }
}
