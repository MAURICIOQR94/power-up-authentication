package co.com.pragma.model.security.gateways;

import co.com.pragma.model.user.User;

import java.util.UUID;

public interface JwtUtilService {

    String generateToken(User user);
    UUID extractUserId(String token);
    String extractRole(String token);
    boolean validateToken(String token);

}
