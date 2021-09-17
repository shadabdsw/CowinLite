package com.shadabdsw.thymeleafdemo.Repositories;

import com.shadabdsw.thymeleafdemo.Model.Member;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemberRepository extends MongoRepository<Member, String> {

}
