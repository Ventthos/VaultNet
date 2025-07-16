package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.config.FileRoutes;
import com.ventthos.Vaultnet.config.FileStorageService;
import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.domain.UserBusiness;
import com.ventthos.Vaultnet.dto.business.BusinessResponseDto;
import com.ventthos.Vaultnet.dto.business.CreateBusinessDto;
import com.ventthos.Vaultnet.exceptions.ApiException;
import com.ventthos.Vaultnet.exceptions.Code;
import com.ventthos.Vaultnet.parsers.BusinessParser;
import com.ventthos.Vaultnet.repository.BusinessRepository;
import com.ventthos.Vaultnet.repository.UserBusinessRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.*;

@Service
public class BusinessService {
    private final BusinessRepository businessRepository;
    private final UserService userService;
    private final UserBusinessRepository userBusinessRepository;
    private final BusinessParser businessParser;
    private final FileStorageService fileStorageService;

    public BusinessService(BusinessRepository businessRepository, UserService userService,
                           UserBusinessRepository userBusinessRepository, BusinessParser businessParser,
                           FileStorageService fileStorageService){
        this.businessRepository = businessRepository;
        this.userService = userService;
        this.userBusinessRepository = userBusinessRepository;
        this.businessParser = businessParser;
        this.fileStorageService = fileStorageService;
    }

    public BusinessResponseDto CreateBusiness(CreateBusinessDto newBusiness, Long userId, MultipartFile imageFile) throws ApiException{

        User user = userService.getUserOrTrow(userId);

        String imagePath = null;

        if (imageFile != null && !imageFile.isEmpty()) {
            // Guardar la imagen
            imagePath = fileStorageService.save(imageFile, FileRoutes.BUSINESS);
        }

        // Crear instancia de negocio sin users todavía
        Business business = new Business();
        business.setName(newBusiness.name());
        business.setLogoUrl(imagePath);
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

        return businessParser.toBusinessDto(id, business);
    }

    public BusinessResponseDto GetBussiness(Long id){
        Business business = getBusinessOrTrow(id);
        return  businessParser.toBusinessDto(id, business);
    }

    public Business getBusinessOrTrow(Long id){
        Optional<Business> businessInRepo = businessRepository.findById(id);

        if(businessInRepo.isEmpty()){
            throw new ApiException(Code.BUSINESS_NOT_FOUND);
        }
        return businessInRepo.get();
    }
}
