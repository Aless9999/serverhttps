package org.macnigor.serverhttps.repository;

import org.macnigor.serverhttps.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface UserRepository  {
    Optional<User> findByUsername(String username);
    Optional<User> findByApiKey(String apiKey);
    void save(User user);
    List<User> findAll();
}

