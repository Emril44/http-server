package org.example;

import com.sun.net.httpserver.*;
import org.example.handlers.GoodHandler;
import org.example.handlers.GoodsHandler;
import org.example.handlers.LoginHandler;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(9999), 0);
        server.createContext("/login", new LoginHandler());
        server.createContext("/api/good", new GoodsHandler()).setAuthenticator(new Auth());
        server.createContext("/api/good/", new GoodHandler()).setAuthenticator(new Auth());
        server.setExecutor(null);
        System.out.println("Server started on port 9999");
        server.start();
    }
}