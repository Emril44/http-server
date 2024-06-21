package org.example.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.JwtUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public class LoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            System.out.println("Received request: " + exchange.getRequestMethod());
            if ("POST".equals(exchange.getRequestMethod())) {
                String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, String> params = objectMapper.readValue(body, Map.class);
                String login = params.get("login");
                String password = params.get("password");

                if (login != null && password != null && validateUser(login, password)) {
                    String token = JwtUtil.generateToken(login);
                    exchange.sendResponseHeaders(200, token.length());
                    OutputStream os = exchange.getResponseBody();
                    os.write(token.getBytes());
                    os.close();
                } else {
                    System.out.println("Invalid login or password");
                    exchange.sendResponseHeaders(401, -1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(500, -1);
        }
    }

    private boolean validateUser(String login, String password) {
        // Example validation logic, should be replaced with actual user validation
        String hashedPassword = hashPassword(password);
        return "user".equals(login) && "5f4dcc3b5aa765d61d8327deb882cf99".equals(hashedPassword); // password is "password" in MD5
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            for(byte b : hashInBytes) {
                stringBuilder.append(String.format("%02x", b));
            }
            return stringBuilder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
