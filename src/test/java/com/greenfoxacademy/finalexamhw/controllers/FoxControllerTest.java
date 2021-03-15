package com.greenfoxacademy.finalexamhw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.finalexamhw.dtos.LoggedInUserDTO;
import com.greenfoxacademy.finalexamhw.models.Fox;
import com.greenfoxacademy.finalexamhw.models.Role;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.repositories.FoxRepository;
import com.greenfoxacademy.finalexamhw.repositories.UserRepository;
import com.greenfoxacademy.finalexamhw.services.JWTServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.hamcrest.core.Is.is;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FoxControllerTest {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  UserRepository userRepository;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  FoxRepository foxRepository;

  @Autowired
  JWTServiceImpl jwtService;

  @BeforeEach
  public void deleteRepo() {
    foxRepository.deleteAll();
  }

  @Test
  public void testGetFoxById() throws Exception {
    User newUser = User.builder()
        .password("pw")
        .username("user")
        .roles(new HashSet<Role>())
        .build();
    Fox fox = Fox.builder()
        .foxName("foxxy")
        .build();
    foxRepository.save(fox);
    userRepository.save(newUser);
    String token = jwtService.createToken("user");
    MvcResult mvcResult = this.mockMvc.perform(get("/fox/" + fox.getId())
        .header("Authorization", "Bearer " + token))
        .andExpect(status().isOk()).andReturn();
    Fox result = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), Fox.class);
    Assert.assertEquals(1, result.getId());
  }
}