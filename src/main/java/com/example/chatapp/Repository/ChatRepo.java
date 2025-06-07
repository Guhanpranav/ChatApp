package com.example.chatapp.Repository;

import com.example.chatapp.Entity.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ChatRepo extends MongoRepository<ChatMessage, String> {
}
