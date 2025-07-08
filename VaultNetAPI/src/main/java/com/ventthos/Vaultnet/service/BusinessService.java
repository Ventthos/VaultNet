package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.domain.UserBusiness;
import com.ventthos.Vaultnet.dto.business.BusinessResponseDto;
import com.ventthos.Vaultnet.dto.business.CreateBusinessDto;
import com.ventthos.Vaultnet.dto.user.UserResponseDto;
import com.ventthos.Vaultnet.parsers.UserParser;
import com.ventthos.Vaultnet.repository.BusinessRepository;
import com.ventthos.Vaultnet.repository.UserBusinessRepository;
import com.ventthos.Vaultnet.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class BusinessService {
    private final BusinessRepository businessRepository;
    private final UserRepository userRepository;
    private final UserBusinessRepository userBusinessRepository;
    private final UserParser userParser;

    public BusinessService(BusinessRepository businessRepository, UserRepository userRepository,
                           UserBusinessRepository userBusinessRepository, UserParser userParser){
        this.businessRepository = businessRepository;
        this.userRepository = userRepository;
        this.userBusinessRepository = userBusinessRepository;
        this.userParser = userParser;
    }

    public BusinessResponseDto CreateBusiness(CreateBusinessDto newBusiness, Long userId) throws IllegalArgumentException{
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new IllegalArgumentException("Usuario no encontrado");
        }

        User user = userOptional.get();

        // Crear instancia de negocio sin users todavía
        Business business = new Business();
        business.setName(newBusiness.name());
        business.setLogoUrl(newBusiness.logoUrl());
        business.setOwner(user);

        // Guardar primero el negocio para que tenga ID
        business = businessRepository.save(business);

        // Crear la conexión entre el business y el usuario
        UserBusiness userBusiness = UserBusiness.builder()
                .user(user)
                .business(business)
                .joinedAt(LocalDate.now())
                .active(true)
                .build();

        // Guardar UserBusiness
        Long id = userBusinessRepository.save(userBusiness).getId();

        user.getBusinesses().size();
        business.getUsers().size();

        // Agregar la relación a ambas entidades
        user.getBusinesses().add(userBusiness);
        business.getUsers().add(userBusiness);

        return new BusinessResponseDto(
            id, business.getName(),business.getLogoUrl(),
                userParser.toUserResponseDto(user.getUserId(), user),
                business.getUsers().stream()
                        .map( userInList -> userParser.toUserResponseDto(userInList.getId(), userInList.getUser())).toList()
        );
    }
}
