package org.example.gestorapi.security.user.mongodb;

import org.example.gestorapi.security.JwtProvider;
import org.example.gestorapi.security.user.LoginDTO.LogInDTO;
import org.example.gestorapi.security.user.LoginDTO.SignUpDTO;
import org.example.gestorapi.security.user.LoginDTO.TokenDTO;
import org.example.gestorapi.security.user.User;
import org.example.gestorapi.security.user.gateway.UserGateway;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class UserMongoAdapter implements UserGateway {

    private final UserReactiveMongoRepository userReactiveMongoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    public UserMongoAdapter(UserReactiveMongoRepository userReactiveMongoRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userReactiveMongoRepository = userReactiveMongoRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }


    @Override
    public Mono<User> signUp(SignUpDTO dto) {
        var userDocument = getUserDocument(dto);

        Mono<Boolean> userExists = userReactiveMongoRepository.findByUsername(userDocument.getUsername()).hasElement();
        return userExists
                .flatMap(exists -> exists ?
                        Mono.error(new Throwable("email already in use"))
                        : userReactiveMongoRepository.save(userDocument))
                .map(UserMapper::mapToModel);
    }

    private UserDocument getUserDocument(SignUpDTO dto) {
        return new UserDocument(
                null,
                dto.username(),
                passwordEncoder.encode(dto.password()),
                "ADMIN",
                true);
    }

    @Override
    public Mono<TokenDTO> login(LogInDTO dto) {
        return userReactiveMongoRepository.findByUsername(dto.username())
                .filter(userDocument -> passwordEncoder.matches(dto.password(), userDocument.getPassword()))
                .map(userDocument -> new TokenDTO(jwtProvider.generateToken(userDocument)))
                .switchIfEmpty(Mono.error(new Throwable("bad credentials")));
    }
}