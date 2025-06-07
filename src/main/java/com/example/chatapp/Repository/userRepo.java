package com.example.chatapp.Repository;


import com.example.chatapp.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepo extends MongoRepository<User, String> {

    Optional<User> findByEmail(String email);
}
