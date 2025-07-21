package com.ventthos.Vaultnet.parsers;

import com.ventthos.Vaultnet.domain.Change;
import com.ventthos.Vaultnet.dto.change.ChangeResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ChangeParser {
    private final UserParser userParser;
    public ChangeParser(UserParser userParser){
        this.userParser = userParser;
    }

    public ChangeResponseDto toChangeResponseDto(Change change){
        return new ChangeResponseDto(
                change.getChangeId(),
            change.getProduct().getProductId(),
            userParser.toUserResponseDto(change.getUser().getUserId(), change.getUser()),
                change.getOldValuesJson(),
           change.getNewValuesJson()

        );
    }
}
