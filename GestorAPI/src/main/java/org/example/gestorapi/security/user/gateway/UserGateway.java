package org.example.gestorapi.security.user.gateway;


import org.example.gestorapi.security.user.LoginDTO.LogInDTO;
import org.example.gestorapi.security.user.LoginDTO.SignUpDTO;
import org.example.gestorapi.security.user.LoginDTO.TokenDTO;
import org.example.gestorapi.security.user.User;
import reactor.core.publisher.Mono;

public interface UserGateway {
    Mono<User> signUp(SignUpDTO dto);
    Mono<TokenDTO> login(LogInDTO dto);

}