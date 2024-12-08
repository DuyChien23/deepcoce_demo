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

    // SQL Injection Vulnerability
    @GetMapping
    public List<Object> getUser(@RequestParam String id) {
        String query = "SELECT * FROM USERS WHERE ID = " + id; // Không sử dụng tham số an toàn
        return entityManager.createNativeQuery(query).getResultList();
    }

    // Memory Leak (lỗi quản lý bộ nhớ)
    private List<byte[]> memoryLeakList = new ArrayList<>();

    @GetMapping("/leak")
    public String triggerMemoryLeak() {
        for (int i = 0; i < 1000; i++) {
            memoryLeakList.add(new byte[1024 * 1024]); // Thêm dữ liệu không giải phóng
        }
        return "Memory leak triggered";
    }

    // Không xử lý lỗi đúng cách
    @GetMapping("/crash")
    public String crashApp() {
        try {
            int result = 10 / 0; // Lỗi chia cho 0
        } catch (Exception e) {
            System.out.println("Lỗi xảy ra nhưng không xử lý đúng cách");
        }
        return "App vẫn chạy dù có lỗi nghiêm trọng";
    }
}
