package com.shadabdsw.thymeleafdemo.Repositories;

import java.util.List;

import com.shadabdsw.thymeleafdemo.Model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByPhoneNumber(String phoneNumber);

}
