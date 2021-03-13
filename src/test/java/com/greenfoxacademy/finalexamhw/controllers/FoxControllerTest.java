package com.greenfoxacademy.finalexamhw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greenfoxacademy.finalexamhw.dtos.LoggedInUserDTO;
import com.greenfoxacademy.finalexamhw.models.Fox;
import com.greenfoxacademy.finalexamhw.models.User;
import com.greenfoxacademy.finalexamhw.repositories.FoxRepository;
import com.greenfoxacademy.finalexamhw.repositories.UserRepository;
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

  @BeforeEach
  public void deleteRepo() {
    foxRepository.deleteAll();
  }

  @Test
  public void shouldGetFoxById() throws Exception {
    User storedUser = User.builder()
        .username("username")
        .password(bCryptPasswordEncoder.encode("pw"))
        .build();
    User newUser = User.builder()
        .password("pw")
        .username("username")
        .build();
    userRepository.save(storedUser);
    MvcResult mvcResult = this.mockMvc.perform(post("/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(newUser)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().isCreated()).andReturn();
    LoggedInUserDTO objectMapper1 = new ObjectMapper()
        .readValue(mvcResult.getResponse().getContentAsString(), LoggedInUserDTO.class);
    Fox fox = Fox.builder()
        .id(1)
        .foxName("foxxy")
        .build();
    foxRepository.save(fox);
    MvcResult mvcResult2 = this.mockMvc.perform(get("/fox/{id}", 1)
        .contentType(MediaType.APPLICATION_JSON)
        .header("Authorization", "Bearer " + objectMapper1.getToken()))
        .andExpect(status().isOk()).andReturn();
    Fox result = new ObjectMapper().readValue(mvcResult2.getResponse().getContentAsString(), Fox.class);
    Assert.assertEquals(fox.getId(), result.getId());
    Assert.assertEquals("foxxy", result.getFoxName());
  }
}