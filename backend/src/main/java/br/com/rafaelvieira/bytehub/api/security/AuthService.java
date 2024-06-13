package br.com.rafaelvieira.bytehub.api.security;

import br.com.rafaelvieira.bytehub.api.mapper.UserMapper;
import br.com.rafaelvieira.bytehub.api.model.user.UserAuthenticate;
import br.com.rafaelvieira.bytehub.api.model.user.UserResponse;
import br.com.rafaelvieira.bytehub.api.model.user.UserToken;
import br.com.rafaelvieira.bytehub.domain.model.User;
import br.com.rafaelvieira.bytehub.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final TokenService tokenService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    public UserResponse register(User user) {
        var token = tokenService.generateToken(setDefaultClaims(user), user.getEmail());
        userService.setToken(user, token);
        return toUserResponse(user);
    }



    public UserResponse authenticate(UserAuthenticate authenticate) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticate.getEmail(), authenticate.getPassword())
        );

        var user = userService.getByEmail(authenticate.getEmail());
        var token = tokenService.generateToken(setDefaultClaims(user), user.getEmail());

        userService.setToken(user, token);
        return toUserResponse(user);
    }

    private UserResponse toUserResponse(User user) {
        return userMapper.toResponse(user);
    }

    private Map<String, Object> setDefaultClaims(User user) {
        var claims = new HashMap<String, Object>();
        var userToken = UserToken
                .builder()
                .id(user.getId())
                .build();

        claims.put("user", userToken);
        return claims;
    }
}

