package com.demo.web;

import com.demo.domain.User;
import com.demo.domain.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.invoke.MethodHandles;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());



    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/user/{name}")
    @ResponseBody
    public User findByName(@PathVariable("name") String name) {
        try {
            User u = userMapper.findByName(name);
            return u;
        } catch (Exception e) {
            logger.error("ex=", e);
        }
        return null;
    }

    @GetMapping(value = "/users")
    @ResponseBody
    public List<User> users() {
        return userMapper.findAll();
    }

}
