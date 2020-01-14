package com.dslab.videomanagementservice.controller;

import com.dslab.videomanagementservice.entity.*;
import com.dslab.videomanagementservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/vms/register")
public class UserController {

    @Autowired
    VideoServerUserService videoServerUserService;


    //POST http://localhost:8080/vms/register/ (5)
    @PostMapping(path = "/")
    public @ResponseBody User register(@RequestBody User user) {
        return videoServerUserService.addUser(user);
    }

}
