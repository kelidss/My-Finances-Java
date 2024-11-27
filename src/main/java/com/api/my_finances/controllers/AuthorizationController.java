package com.api.my_finances.controllers;

import com.api.my_finances.infra.dto.LoginDTO;
import com.api.my_finances.infra.dto.LoginResponseDTO;
import com.api.my_finances.infra.dto.RegisterDTO;
import com.api.my_finances.infra.security.TokenService;
import com.api.my_finances.models.User;
import com.api.my_finances.repositorys.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthorizationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @SuppressWarnings("rawtypes")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated LoginDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        // adiciona o cookie na resposta com token
        ResponseCookie responseCookie = ResponseCookie.from("Authorization", token)
            .httpOnly(true)
            .secure(true)
            .sameSite("Lax")
            .path("/")
            .maxAge(3600)
            .build();
        return ResponseEntity.ok().header("Set-Cookie", responseCookie.toString()).body(new LoginResponseDTO(token));
    }

    @SuppressWarnings("rawtypes")
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Validated RegisterDTO data){
        if(this.userRepository.findByUsername(data.username()) != null){
            ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        User newUser = new User(data.username(), encryptedPassword, data.role());
        
        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
