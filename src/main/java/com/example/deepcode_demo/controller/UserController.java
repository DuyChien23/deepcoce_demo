package com.example.vulnerableproject.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @PersistenceContext
    private EntityManager entityManager;


    @GetMapping("/greet")
    public String greetUser(@RequestParam String name) {
        // Trực tiếp trả về đầu vào của user
        return "<h1>Welcome, " + name + "!</h1>";
    }

    // SQL Injection Vulnerability
    @GetMapping
    public List<Object> getUser(@RequestParam String id) {
        String query = "SELECT * FROM USERS WHERE ID = " + id; // Không sử dụng tham số an toàn
        return entityManager.createNativeQuery(query).getResultList();
    }

    @GetMapping("/divideByZero")
    public String crashApp() {
        int result = 10 / 0;
        return "App vẫn chạy dù có lỗi nghiêm trọng";
    }
}
