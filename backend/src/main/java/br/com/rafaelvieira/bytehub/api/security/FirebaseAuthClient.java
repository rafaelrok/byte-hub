package br.com.rafaelvieira.bytehub.api.security;

import br.com.rafaelvieira.bytehub.api.exception.InvalidLoginCredentialsException;
import br.com.rafaelvieira.bytehub.api.exception.InvalidRefreshTokenException;
import br.com.rafaelvieira.bytehub.domain.model.User;
import br.com.rafaelvieira.bytehub.domain.service.UserService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;


@Slf4j
@Component
public class FirebaseAuthClient {

//    private static final Logger log = LoggerFactory.getLogger(FirebaseAuthClient.class);

    private static final String API_KEY_PARAM = "key";
    private static final String REFRESH_TOKEN_GRANT_TYPE = "refresh_token";

    private static final String UNIDENTIFIED_USER_LOGIN = "unidentified user";
    private static final String INVALID_CREDENTIALS_ERROR = "INVALID_LOGIN_CREDENTIALS";
    private static final String INVALID_REFRESH_TOKEN_ERROR = "INVALID_REFRESH_TOKEN";

    private static final String SIGN_IN_BASE_URL = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword";
    private static final String REFRESH_TOKEN_BASE_URL = "https://securetoken.googleapis.com/v1/token";

    private final String webApiKey;
    private final UserService userService;
    private final FirebaseAuth firebaseAuth;

    public FirebaseAuthClient(@Value("${br.com.rafaelvieira.bytehub.firebase.web-api-key}") String webApiKey,
                              UserService userService,
                              FirebaseAuth firebaseAuth) {
        this.webApiKey = webApiKey;
        this.userService = userService;
        this.firebaseAuth = firebaseAuth;
    }

    public FirebaseSignInResponse login(String emailId, String password) throws FirebaseAuthException {
        final User userSocial = userService.getByEmail(emailId);
        final UserRecord userRecord = firebaseAuth.getUserByEmail(emailId);

        if(userSocial.getEmail().equals(userRecord.getEmail())){
            FirebaseSignInRequest requestBody = new FirebaseSignInRequest(emailId, password, true);
            return sendSignInRequest(requestBody);
        } else {
            throw new InvalidLoginCredentialsException(UNIDENTIFIED_USER_LOGIN);
        }
    }

    public RefreshTokenResponse exchangeRefreshToken(String refreshToken) {
        RefreshTokenRequest requestBody = new RefreshTokenRequest(REFRESH_TOKEN_GRANT_TYPE, refreshToken);
        return sendRefreshTokenRequest(requestBody);
    }

    private FirebaseSignInResponse sendSignInRequest(FirebaseSignInRequest firebaseSignInRequest) {
        try {
            return RestClient.create(SIGN_IN_BASE_URL)
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam(API_KEY_PARAM, webApiKey)
                            .build())
                    .body(firebaseSignInRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(FirebaseSignInResponse.class);
        } catch (HttpClientErrorException exception) {
            if (exception.getResponseBodyAsString().contains(INVALID_CREDENTIALS_ERROR)) {
                throw new InvalidLoginCredentialsException("Invalid login credentials provided");
            }
            throw exception;
        }
    }

    private RefreshTokenResponse sendRefreshTokenRequest(RefreshTokenRequest refreshTokenRequest) {
        try {
            return RestClient.create(REFRESH_TOKEN_BASE_URL)
                    .post()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam(API_KEY_PARAM, webApiKey)
                            .build())
                    .body(refreshTokenRequest)
                    .contentType(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(RefreshTokenResponse.class);
        } catch (HttpClientErrorException exception) {
            if (exception.getResponseBodyAsString().contains(INVALID_REFRESH_TOKEN_ERROR)) {
                throw new InvalidRefreshTokenException("Invalid refresh token provided");
            }
            throw exception;
        }
    }

    public record FirebaseSignInRequest(String email, String password, boolean returnSecureToken) {
    }

    public record FirebaseSignInResponse(String idToken, String refreshToken) {
    }

    public record RefreshTokenRequest(String grant_type, String refresh_token) {
    }

    public record RefreshTokenResponse(String id_token) {
    }

}
