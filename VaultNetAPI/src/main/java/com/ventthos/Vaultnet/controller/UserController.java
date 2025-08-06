package com.ventthos.Vaultnet.controller;

import com.ventthos.Vaultnet.config.JwtUtil;
import com.ventthos.Vaultnet.domain.User;
import com.ventthos.Vaultnet.dto.responses.ApiResponse;
import com.ventthos.Vaultnet.dto.responses.ApiResponseWithToken;
import com.ventthos.Vaultnet.dto.user.LoginUserDto;
import com.ventthos.Vaultnet.dto.user.RegisterUserDto;
import com.ventthos.Vaultnet.dto.user.UserResponseDto;
import com.ventthos.Vaultnet.exceptions.Code;
import com.ventthos.Vaultnet.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil){
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> RegisterUser(
            @ModelAttribute @Valid RegisterUserDto newUser,
            @RequestPart(value = "image", required = false)MultipartFile imageFile
            ){
        UserResponseDto userResponseDto = userService.registerUser(newUser, imageFile);

        // Construir la URI del nuevo recurso
        URI location = URI.create("/user/" + userResponseDto.id());

        return ResponseEntity
                .created(location)
                .body(new ApiResponse<>("Success", Code.USER_SAVED.name(), Code.USER_SAVED.getDefaultMessage(),
                        userResponseDto));
    }

    @PostMapping("auth/login")
    public ResponseEntity<ApiResponseWithToken<UserResponseDto>> Login(@RequestBody @Valid LoginUserDto loginUser){
        UserResponseDto user = userService.loginUser(loginUser);
        return ResponseEntity.ok(
                new ApiResponseWithToken<>("Success", Code.LOGGED_IN.name(),Code.LOGGED_IN.getDefaultMessage(), user,
                        jwtUtil.generateToken(user.email(), user.id()))
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponseDto>> FindUserById(@PathVariable Long id){
        UserResponseDto user = userService.GetUserById(id);
        return ResponseEntity.ok(
                new ApiResponse<>("Success", Code.ELEMENT_GET_SUCCESSFUL.name(),
                        Code.ELEMENT_GET_SUCCESSFUL.getDefaultMessage(), user)
        );
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<UserResponseDto>>> findUsersByEmail(@RequestParam String email){
        List<UserResponseDto> users = userService.findUsersWithEmailLike(email);
        return ResponseEntity.ok(
                new ApiResponse<>("Success",  Code.ELEMENT_GET_SUCCESSFUL.name(),
                        Code.ELEMENT_GET_SUCCESSFUL.getDefaultMessage(), users)
        );
    }
}
