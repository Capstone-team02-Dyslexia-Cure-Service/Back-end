package com.capstone.dyslexia.global.corsTest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class corsTestController {

    @GetMapping("/hello")
    public String hello() {
        return "CORS test 성공";
    }

    @PostMapping("/hello/post")
    public String helloPost() {
        return "Post CORS test 성공";
    }

    @DeleteMapping("/hello/delete")
    public String helloDelete() {
        return "Delete CORS test 성공";
    }

}
