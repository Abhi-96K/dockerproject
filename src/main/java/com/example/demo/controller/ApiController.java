package com.example.demo.controller;

import com.example.demo.model.ApiResponse;
import com.example.demo.model.EchoRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    // 1. Root greeting endpoint
    @GetMapping("/hello")
    public ResponseEntity<ApiResponse> getHello() {
        ApiResponse response = new ApiResponse(
                "SUCCESS",
                "Hello, Welcome to Spring Boot REST API deployed via Docker!",
                "Spring Boot REST API is running successfully."
        );
        return ResponseEntity.ok(response);
    }

    // 2. Personalized greeting with query parameter
    @GetMapping("/greet")
    public ResponseEntity<ApiResponse> greetUser(@RequestParam(defaultValue = "Guest") String name) {
        String greetingMessage = "Hello, " + name + "! Your REST API request was processed by Spring Boot Controller.";
        ApiResponse response = new ApiResponse("SUCCESS", greetingMessage, Map.of("recipient", name));
        return ResponseEntity.ok(response);
    }

    // 3. Application and Architecture Info endpoint
    @GetMapping("/info")
    public ResponseEntity<ApiResponse> getAppInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "Spring Boot REST API Docker Demo");
        info.put("version", "1.0.0");
        info.put("architecture", "User/Postman -> REST API Request -> Spring Boot Application -> Controller -> HTTP Response");
        info.put("containerized", true);
        info.put("javaVersion", System.getProperty("java.version"));
        info.put("osName", System.getProperty("os.name"));

        ApiResponse response = new ApiResponse(
                "SUCCESS",
                "Application metadata retrieved successfully.",
                info
        );
        return ResponseEntity.ok(response);
    }

    // 4. POST endpoint to test receiving and responding with JSON
    @PostMapping("/echo")
    public ResponseEntity<ApiResponse> echoPayload(@RequestBody EchoRequest request) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("receivedName", request.getName());
        responseData.put("receivedMessage", request.getMessage());
        responseData.put("processedAt", java.time.LocalDateTime.now());

        ApiResponse response = new ApiResponse(
                "SUCCESS",
                "Payload received and processed by Controller successfully.",
                responseData
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
