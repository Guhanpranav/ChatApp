package com.example.chatapp.Controller;

import com.example.chatapp.Entity.User;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.MessageDigest;

@Component
public class UserModelListener extends AbstractMongoEventListener<User> {
    @Override
    public void onBeforeConvert(BeforeConvertEvent<User> event) {
        User user = event.getSource();
        if (user.getId() == null) {
            String userId  = getMd5Hash(user.getEmail());
            user.setId(userId);
        }
    }

    public String getMd5Hash(String email){
        String md5 = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(email.getBytes());
            byte[] bytee = md.digest();
            String md5Hex = new BigInteger(1, bytee).toString(16);
            StringBuilder sb = new StringBuilder(md5Hex);
            while (sb.length() < 32) {
                sb.insert(0,"0");
            }
            md5 = sb.toString();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return md5;
    }
}
