package com.shadabdsw.thymeleafdemo.Repositories;

import java.util.Optional;

import com.shadabdsw.thymeleafdemo.Model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByphoneNumber(String phoneNumber);

}
