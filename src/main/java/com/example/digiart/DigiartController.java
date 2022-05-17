package com.example.digiart;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class DigiartController {
    @GetMapping("/demo")
    public String demoEndpoint() {
        return "Hello World";
    }
}
