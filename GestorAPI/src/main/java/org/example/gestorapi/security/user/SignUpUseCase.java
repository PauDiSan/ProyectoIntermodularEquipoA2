package org.example.gestorapi.security.user;


import org.example.gestorapi.security.user.LoginDTO.SignUpDTO;
import org.example.gestorapi.security.user.gateway.UserGateway;
import reactor.core.publisher.Mono;

public class SignUpUseCase {

    private final UserGateway userGateway;


    public SignUpUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public Mono<User> signUp(SignUpDTO dto) {
        return userGateway.signUp(dto);
    }
}
