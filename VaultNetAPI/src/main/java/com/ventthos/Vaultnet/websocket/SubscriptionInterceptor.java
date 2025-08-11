package com.ventthos.Vaultnet.websocket;

import com.ventthos.Vaultnet.service.BusinessService;
import com.ventthos.Vaultnet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubscriptionInterceptor implements ChannelInterceptor {

    private final UserService userService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())) {
            String destination = accessor.getDestination();
            Long requestedBusinessId = extractBusinessId(destination);

            // Obtener usuario autenticado
            Long sessionUserId = (Long) accessor.getSessionAttributes().get("userId");

            // Validar en base de datos
            userService.validateUserBelongsToBusiness(sessionUserId, requestedBusinessId);
        }

        return message;
    }

    private Long extractBusinessId(String destination) {
        // Ejemplo: /topic/categories/5 → extraemos 5
        String[] parts = destination.split("/");
        return Long.valueOf(parts[parts.length - 1]);
    }
}
