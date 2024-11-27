package com.api.my_finances.repositorys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.api.my_finances.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
    UserDetails findByUsername(String username);
}
