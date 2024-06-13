package br.com.rafaelvieira.bytehub.api.security;

import br.com.rafaelvieira.bytehub.api.mapper.UserMapper;
import br.com.rafaelvieira.bytehub.api.model.user.UserAuthenticate;
import br.com.rafaelvieira.bytehub.api.model.user.UserRegister;
import br.com.rafaelvieira.bytehub.api.model.user.UserResponse;
import br.com.rafaelvieira.bytehub.domain.service.ProfileService;
import br.com.rafaelvieira.bytehub.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final ProfileService profileService;

    @PostMapping
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegister register) {
        var user = userMapper.toEntity(register);
        var profile = profileService.createNewProfile(user, register.getUsername());

        return ResponseEntity.ok(authService.register(userService.save(user, profile)));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> authenticate(@Valid @RequestBody UserAuthenticate authenticate) {
        return ResponseEntity.ok(authService.authenticate(authenticate));
    }
}
