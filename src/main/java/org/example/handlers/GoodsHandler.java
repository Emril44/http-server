package org.example.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.Good;
import org.example.GoodService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class GoodsHandler implements HttpHandler {
    private final GoodService goodService = new GoodService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if("PUT".equals(exchange.getRequestMethod())) {
            try {
                handlePut(exchange);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            exchange.sendResponseHeaders(405, -1); // not allowed
        }
    }

    private void handlePut(HttpExchange exchange) throws IOException, SQLException {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        ObjectMapper mapper = new ObjectMapper();
        Good good = mapper.readValue(body, Good.class);

        if(isValidGood(good)) {
            goodService.createGood(good);
            String res = String.valueOf(good.getId());
            exchange.sendResponseHeaders(201, res.length());
            OutputStream os = exchange.getResponseBody();
            os.write(res.getBytes());
            os.close();
        } else {
            exchange.sendResponseHeaders(409, -1);
        }
    }

    private boolean isValidGood(Good good) {
        return good.getPrice() >= 0;
    }
}
