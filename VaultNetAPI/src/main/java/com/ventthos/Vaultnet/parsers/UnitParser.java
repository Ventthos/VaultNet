package com.ventthos.Vaultnet.parsers;

import com.ventthos.Vaultnet.domain.Unit;
import com.ventthos.Vaultnet.dto.unit.UnitResponseDto;
import org.springframework.stereotype.Component;

@Component
public class UnitParser {
    public UnitResponseDto toUnitResponseDto(Long id, Unit unit){
        return new UnitResponseDto(
                id,
                unit.getName(),
                unit.getSymbol(),
                unit.getBusiness().getBusinessId()
        );
    }
}
