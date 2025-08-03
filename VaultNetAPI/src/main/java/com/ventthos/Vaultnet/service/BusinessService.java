package com.ventthos.Vaultnet.service;

import com.ventthos.Vaultnet.config.FileRoutes;
import com.ventthos.Vaultnet.config.FileStorageService;
import com.ventthos.Vaultnet.domain.Business;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.domain.UserBusiness;
import com.ventthos.Vaultnet.dto.business.AddUserToBusinessDto;
import com.ventthos.Vaultnet.dto.business.BusinessResponseDto;
import com.ventthos.Vaultnet.dto.business.CreateBusinessDto;
import com.ventthos.Vaultnet.dto.business.UsersInBusinessDto;
import com.ventthos.Vaultnet.exceptions.ApiException;
import com.ventthos.Vaultnet.exceptions.Code;
import com.ventthos.Vaultnet.parsers.BusinessParser;
import com.ventthos.Vaultnet.parsers.UserParser;
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
    private final UserParser userParser;

    public BusinessService(BusinessRepository businessRepository, UserService userService,
                           UserBusinessRepository userBusinessRepository, BusinessParser businessParser,
                           FileStorageService fileStorageService, UserParser userParser){
        this.businessRepository = businessRepository;
        this.userService = userService;
        this.userBusinessRepository = userBusinessRepository;
        this.businessParser = businessParser;
        this.fileStorageService = fileStorageService;
        this.userParser = userParser;
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
        Business savedBusiness = businessRepository.save(business);

        // Crear la conexión entre el business y el usuario
        UserBusiness userBusiness = UserBusiness.builder()
                .user(user)
                .business(savedBusiness)
                .joinedAt(LocalDate.now())
                .active(true)
                .build();

        // Guardar UserBusiness
        Long id = userBusinessRepository.save(userBusiness).getId();

        user.getBusinesses().size();
        savedBusiness.getUsers().size();

        // Agregar la relación a ambas entidades
        user.getBusinesses().add(userBusiness);
        savedBusiness.getUsers().add(userBusiness);


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

    public UserBusiness link(User user, Business business) {
        boolean alreadyLinked = business.getUsers().stream()
                .anyMatch(ub -> ub.getUser().getUserId().equals(user.getUserId()));
        if (alreadyLinked) return null;

        UserBusiness ub = UserBusiness.builder()
                .user(user)
                .business(business)
                .joinedAt(LocalDate.now())
                .active(true)
                .build();

        userBusinessRepository.save(ub);

        // Mantener relación actualizada en memoria para la respuesta
        business.getUsers().add(ub);

        return ub;
    }

    public UsersInBusinessDto addUsersToBusiness(AddUserToBusinessDto newMembers, Long businessId){
        // Hallamos el negocio
        Business business = getBusinessOrTrow(businessId);

        // Vemos que cada uno de los usuarios exista, solo para que si uno no existe, lo rechace sin guardar nada
        newMembers.usersEmails().forEach(newMemberEmail-> {
            User newMember = userService.getUserOrTrow(newMemberEmail);
        });

        // unimos a los demás usuarios
        if(newMembers.usersEmails() != null){
            newMembers.usersEmails().forEach(newMemberEmail->{
                User newMember = userService.getUserOrTrow(newMemberEmail);
                link(newMember, business);
            });
        }
        businessRepository.save(business);
        return new UsersInBusinessDto(business.getUsers().stream().map(userInList ->
                userParser.toUserResponseDto(userInList.getUser().getUserId(), userInList.getUser())).toList());
    }
}
