package com.greenfoxacademy.finalexamhw.repositories;

import com.greenfoxacademy.finalexamhw.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
  boolean existsByUsername(String username);
  User save(User user);
  User findByUsername(String username);
}
