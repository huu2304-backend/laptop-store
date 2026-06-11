package com.laptoppstore;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Run with: ./gradlew bootRun --args="--gen-hash"
// Or just run this main directly
public class GenHash {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String raw = "admin123";
        String hash = encoder.encode(raw);
        System.out.println("Password: " + raw);
        System.out.println("BCrypt Hash: " + hash);
        // Verify
        System.out.println("Verify: " + encoder.matches(raw, hash));
    }
}
