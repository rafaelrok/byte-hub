package br.com.rafaelvieira.bytehub.api.security;

import br.com.rafaelvieira.bytehub.api.mapper.UserMapper;
import br.com.rafaelvieira.bytehub.api.model.user.UserAuthenticate;
import br.com.rafaelvieira.bytehub.api.model.user.UserRegister;
import br.com.rafaelvieira.bytehub.api.model.user.UserResponse;
import br.com.rafaelvieira.bytehub.domain.model.Profile;
import br.com.rafaelvieira.bytehub.domain.service.ProfileService;
import br.com.rafaelvieira.bytehub.domain.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import br.com.rafaelvieira.bytehub.api.security.FirebaseAuthClient.FirebaseSignInResponse;
import br.com.rafaelvieira.bytehub.api.security.FirebaseAuthClient.RefreshTokenResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final ProfileService profileService;
    private final FirebaseAuthClient firebaseAuthClient;

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

    @GetMapping
    public ResponseEntity<UserRecord> getUser() throws FirebaseAuthException {
        UserRecord userRecord = authService.retrieve();
        return ResponseEntity.ok(userRecord);
    }

    @PostMapping("/auth/create")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) throws FirebaseAuthException {
        authService.create(
                request.emailId(),
                request.password());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/auth/login")
    public ResponseEntity<FirebaseSignInResponse> loginUser(@RequestBody LoginUserRequest request) throws FirebaseAuthException {
        FirebaseSignInResponse response = firebaseAuthClient.login(
                request.emailId(),
                request.password()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        RefreshTokenResponse response = firebaseAuthClient.exchangeRefreshToken(request.refreshToken());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logoutUser() throws FirebaseAuthException {
        authService.logout();
        return ResponseEntity.ok().build();
    }

    public record CreateUserRequest(String emailId, String password) {}

    public record LoginUserRequest(String userUid, String emailId, String password) {}

    public record RefreshTokenRequest(String refreshToken) {}
}
