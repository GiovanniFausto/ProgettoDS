package com.dslab.videomanagementservice.entity;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    public User findByEmail(String email);
}
