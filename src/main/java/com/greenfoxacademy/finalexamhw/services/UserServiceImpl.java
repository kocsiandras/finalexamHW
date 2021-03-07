package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.dtos.LoggedInUserDTO;
import com.greenfoxacademy.finalexamhw.dtos.RegistrationDTO;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

  final
  JWTServiceImpl jwtService;

  final
  BCryptPasswordEncoder bCryptPasswordEncoder;

  final
  UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, JWTServiceImpl jwtService) {
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.jwtService = jwtService;
  }

  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public User saveUser(User user) {
    return userRepository.save(user);
  }

  @Override
  public LoggedInUserDTO validateUser(RegistrationDTO registrationDTO) {
    User storedUser = userRepository.findByUsername(registrationDTO.getUsername());
    if(bCryptPasswordEncoder.matches(registrationDTO.getPassword(),storedUser.getPassword())){
      return new LoggedInUserDTO(registrationDTO.getUsername(), jwtService.createToken(storedUser.getUsername()));
    }
    return null;
  }

  @Override
  public User findByUsername(String username) {
    return null;
  }
}
