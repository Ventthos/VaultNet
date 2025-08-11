package com.ventthos.Vaultnet.websocket;

import com.ventthos.Vaultnet.config.JwtUtil;
import com.ventthos.Vaultnet.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.net.URI;
import java.util.Map;

public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtUtil jwtUtil;


    public AuthHandshakeInterceptor(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
                                   ServerHttpResponse response,
                                   WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        URI uri = request.getURI();
        String query = uri.getQuery();

        if (query == null) {
            return false; // No hay query string, rechaza
        }

        String token = null;
        Long businessId = null;

        for (String param : query.split("&")) {
            String[] pair = param.split("=");
            if (pair.length == 2) {
                if ("token".equals(pair[0])) token = pair[1];
                else if ("businessId".equals(pair[0])) businessId = Long.valueOf(pair[1]);
            }
        }

        if (token == null || !jwtUtil.validateToken(token)) {
            return false; // Token inválido o ausente
        }

        Long userId = jwtUtil.extractUserId(token);

        // Guardamos en los atributos de sesión para usar después
        attributes.put("userId", userId);
        attributes.put("businessId", businessId);

        return true; // Permitir handshake
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
                               ServerHttpResponse response,
                               WebSocketHandler wsHandler,
                               Exception exception) {
        // No hacemos nada acá
    }
}
