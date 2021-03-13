package com.greenfoxacademy.finalexamhw.services;

import com.greenfoxacademy.finalexamhw.models.Role;
import com.greenfoxacademy.finalexamhw.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

  final
  RoleRepository roleRepository;

  public RoleServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public Role saveRole(Role role) {
    return null;
  }
}
