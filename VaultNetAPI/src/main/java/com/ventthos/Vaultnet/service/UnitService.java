package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.Unit;
import com.ventthos.Vaultnet.dto.unit.CreateUnitDto;
import com.ventthos.Vaultnet.dto.unit.UnitResponseDto;
import com.ventthos.Vaultnet.exceptions.ApiException;
import com.ventthos.Vaultnet.exceptions.Code;
import com.ventthos.Vaultnet.parsers.UnitParser;
import com.ventthos.Vaultnet.repository.UnitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnitService {
    private final UnitRepository unitRepository;
    private final BusinessService businessService;
    private final UnitParser unitParser;

    public UnitService(UnitRepository unitRepository, BusinessService businessService, UnitParser unitParser){
        this.businessService = businessService;
        this.unitRepository = unitRepository;
        this.unitParser = unitParser;
    }

    public UnitResponseDto createUnit(CreateUnitDto newUnitDto, Long businessId){
        // Se obtiene el business
        Business business = businessService.getBusinessOrTrow(businessId);

        // Se crea el objeto
        Unit newUnit = new Unit();
        newUnit.setName(newUnitDto.name());
        newUnit.setSymbol(newUnitDto.symbol());
        newUnit.setBusiness(business);

        // Se guarda
        Long id = unitRepository.save(newUnit).getUnitId();

        // Se actualiza la lista del negocio
        business.getUnits().add(newUnit);

        // Se crea la respuesta
        return unitParser.toUnitResponseDto(id, newUnit);
    }

    public Unit getUnitOrTrow(Long unitId){
        Optional<Unit> optionalUnit = unitRepository.findById(unitId);
        if(optionalUnit.isEmpty()){
            throw new ApiException(Code.UNIT_NOT_FOUND);
        }
        return optionalUnit.get();
    }

    public void confirmUnitIsFromBusinessOrTrow(Long businessId, Long unitId) throws ApiException {
        Unit unit = getUnitOrTrow(unitId);

        if(!unit.getBusiness().getBusinessId().equals(businessId)){
            throw new ApiException(Code.ACCESS_DENIED);
        }
    }

    public List<UnitResponseDto> getUnitsFromBusiness(Long businessId){
        Business business = businessService.getBusinessOrTrow(businessId);

        return business.getUnits().stream().map(unit -> unitParser.toUnitResponseDto(unit.getUnitId(), unit)
        ).toList();
    }

    public UnitResponseDto getUnit(Long unitId){
        Unit unit = getUnitOrTrow(unitId);

        return unitParser.toUnitResponseDto(unitId, unit);
    }

}
