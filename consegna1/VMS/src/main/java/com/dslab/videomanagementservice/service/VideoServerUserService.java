package com.dslab.videomanagementservice.service;

import com.dslab.videomanagementservice.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class VideoServerUserService {
    @Autowired
    UserRepository repository;

    public User addUser(User user) {
        user.setRoles(Collections.singletonList("AUTHOR"));
        return repository.save(user);
    }


    public User getByEmail(String email){
        return repository.findByEmail(email);
    }
}
