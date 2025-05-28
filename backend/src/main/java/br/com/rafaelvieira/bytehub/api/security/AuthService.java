package br.com.rafaelvieira.bytehub.api.security;

import br.com.rafaelvieira.bytehub.api.exception.AccountAlreadyExistsException;
import br.com.rafaelvieira.bytehub.api.mapper.UserMapper;
import br.com.rafaelvieira.bytehub.api.model.user.UserAuthenticate;
import br.com.rafaelvieira.bytehub.api.model.user.UserResponse;
import br.com.rafaelvieira.bytehub.api.model.user.UserToken;
import br.com.rafaelvieira.bytehub.domain.enums.RegistrationMethod;
import br.com.rafaelvieira.bytehub.domain.model.Profile;
import br.com.rafaelvieira.bytehub.domain.model.User;
import br.com.rafaelvieira.bytehub.domain.service.ProfileService;
import br.com.rafaelvieira.bytehub.domain.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String DUPLICATE_ACCOUNT_ERROR = "EMAIL_EXISTS";

    private final UserService userService;
    private final TokenService tokenService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final ProfileService profileService;
    private final FirebaseAuth firebaseAuth;
    private final AuthenticatedUserIdProvider authenticatedUserIdProvider;

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

    public void create(final String emailId, final String password) throws FirebaseAuthException {
        final CreateRequest request = new CreateRequest();
        request.setEmail(emailId);
        request.setPassword(password);
        request.setEmailVerified(Boolean.TRUE);

        try {
            firebaseAuth.createUser(request);
            final UserRecord userRecord = firebaseAuth.getUserByEmail(emailId);

            final Profile newProfile = new Profile();
            newProfile.setUsername(userRecord.getDisplayName());
            newProfile.setImage(userRecord.getPhotoUrl());

            final User newUser = new User();
            newUser.setUserProvideUid(userRecord.getUid());
            newUser.setEmail(emailId);
            newUser.setPassword(password);
            newUser.setEmailVerified(true);
            newUser.setMethod(RegistrationMethod.SOCIAL);
            newUser.setProvider(userRecord.getProviderId());
            newUser.setProfile(newProfile);

            userService.save(newUser, newProfile);
            profileService.save(newProfile);
        } catch (FirebaseAuthException exception) {
            if (exception.getMessage().contains(DUPLICATE_ACCOUNT_ERROR)) {
                throw new AccountAlreadyExistsException("Account with given email-id already exists");
            }
            throw exception;
        }
    }


    public void logout() throws FirebaseAuthException {
        String userId = authenticatedUserIdProvider.getUserId();
        firebaseAuth.revokeRefreshTokens(userId);
    }

    public UserRecord retrieve() throws FirebaseAuthException {
        String userId = authenticatedUserIdProvider.getUserId();
        return firebaseAuth.getUser(userId);
    }
}

