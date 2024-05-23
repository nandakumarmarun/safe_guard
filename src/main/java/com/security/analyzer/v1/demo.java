package com.security.analyzer.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class demo {

    @GetMapping
    public ResponseEntity<String> sayHelo(){
        return ResponseEntity.ok("HEllo");
    }

}
