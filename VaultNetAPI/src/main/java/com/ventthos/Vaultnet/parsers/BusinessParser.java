package com.ventthos.Vaultnet.parsers;

import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.dto.business.BusinessResponseDto;
import org.springframework.stereotype.Component;

@Component
public class BusinessParser {

    private final UserParser userParser;
    public BusinessParser(UserParser userParser){
        this.userParser = userParser;
    }

    public BusinessResponseDto toBusinessDto(Long id, Business business){
        return new BusinessResponseDto(
                id, business.getName(),business.getLogoUrl(),
                userParser.toUserResponseDto(business.getOwner().getUserId(), business.getOwner()),
                business.getUsers().stream()
                        .map( userInList -> userParser.toUserResponseDto(userInList.getUser().getUserId(), userInList.getUser())).toList()
        );
    }
}
