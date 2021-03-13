package com.greenfoxacademy.finalexamhw;

import com.greenfoxacademy.finalexamhw.models.FoodPrice;
import com.greenfoxacademy.finalexamhw.repositories.FoodPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class FinalexamhwApplication implements CommandLineRunner {

  @Autowired
  FoodPriceRepository foodPriceRepository;

  public static void main(String[] args) {
    SpringApplication.run(FinalexamhwApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  public void run(String... args) throws Exception {
    FoodPrice foodPrice = FoodPrice.builder()
        .foodPrice(1000)
        .build();
    foodPriceRepository.save(foodPrice);
  }
}
