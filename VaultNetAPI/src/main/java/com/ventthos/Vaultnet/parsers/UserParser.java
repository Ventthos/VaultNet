package com.ventthos.Vaultnet.parsers;

import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.user.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UserParser {
    public UserResponseDto toUserResponseDto(Long id, User user){
        return new UserResponseDto(id, user.getName(), user.getPaternalLastName(),
                user.getMaternalLastName(), user.getEmail(), user.getImage());
    }
}
