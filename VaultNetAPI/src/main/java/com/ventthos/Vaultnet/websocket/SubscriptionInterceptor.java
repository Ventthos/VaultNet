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
            System.out.println("Intentando conectar");
            String destination = accessor.getDestination();
            System.out.println("Destination " + destination);
            Long requestedBusinessId = extractBusinessId(destination);

            Long sessionUserId = (Long) accessor.getSessionAttributes().get("userId");
            System.out.println("User id " + sessionUserId);

            userService.validateUserBelongsToBusiness(sessionUserId, requestedBusinessId);
            System.out.println("Logró validarlos");
        }

        return message;
    }

    private Long extractBusinessId(String destination) {
        String[] parts = destination.split("/");
        for (String part : parts) {
            if (part.matches("\\d+")) { // Busca algo que sea solo números
                return Long.valueOf(part);
            }
        }
        throw new IllegalArgumentException("No se encontró un ID en: " + destination);
    }
}
