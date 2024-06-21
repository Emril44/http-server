package org.example;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpPrincipal;

public class Auth extends Authenticator {
    @Override
    public Result authenticate(HttpExchange exchange) {
        String token = exchange.getRequestHeaders().getFirst("authorization");
        if(token == null || !JwtUtil.validateToken(token, JwtUtil.extractClaims(token).getSubject())) {
            return new Failure(403);
        }
        return new Success(new HttpPrincipal(JwtUtil.extractClaims(token).getSubject(), "realm"));
    }
}
