package org.example.gestorapi.security.user;


import org.example.gestorapi.security.user.LoginDTO.LogInDTO;
import org.example.gestorapi.security.user.LoginDTO.TokenDTO;
import org.example.gestorapi.security.user.gateway.UserGateway;
import reactor.core.publisher.Mono;

public class LogInUseCase {


    private final UserGateway userGateway;

    public LogInUseCase(UserGateway userGateway) {
        this.userGateway = userGateway;
    }

    public Mono<TokenDTO> login(LogInDTO dto) {
        return userGateway.login(dto);
    }
}