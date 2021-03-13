package com.greenfoxacademy.finalexamhw.repositories;

import com.greenfoxacademy.finalexamhw.models.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
}
