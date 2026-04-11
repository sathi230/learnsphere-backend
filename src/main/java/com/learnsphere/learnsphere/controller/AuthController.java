package com.learnsphere.learnsphere.controller;

import com.learnsphere.learnsphere.JwtUtil;
import com.learnsphere.learnsphere.OtpService;
import com.learnsphere.learnsphere.entity.User;
import com.learnsphere.learnsphere.repository.UserRepository;
import com.learnsphere.learnsphere.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private OtpService otpService;


    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        try {
            userService.registerUser(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "User Registered Successfully");

            return ResponseEntity.ok(response);

        } catch (Exception e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    // ================= LOGIN =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {

        Optional<User> userOpt =
                userRepository.findByEmailAndPassword(
                        loginRequest.getEmail(),
                        loginRequest.getPassword());

        if (userOpt.isEmpty()) {

            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid Email or Password");
        }

        User user = userOpt.get();
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());


        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login Success");
        response.put("role", user.getRole());
        response.put("email", user.getEmail());
        response.put("userId", user.getId());
        response.put("name", user.getName());
        response.put("token", token);


        return ResponseEntity.ok(response);
    }
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String mobile) {

        Optional<User> user = userRepository.findByMobile(mobile);

        if (user.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Mobile number not registered");
        }

        String otp = otpService.generateOtp(mobile);

        // For testing purpose
        
        return ResponseEntity.ok("OTP Sent Successfully");
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestParam String mobile,
                                       @RequestParam String otp) {

        boolean valid = otpService.verifyOtp(mobile, otp);

        if (!valid) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or Expired OTP");
        }

        User user = userRepository.findByMobile(mobile).orElseThrow();

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login Success");
        response.put("role", user.getRole());
        response.put("email", user.getEmail());
        response.put("userId", user.getId());
        response.put("name", user.getName());
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
