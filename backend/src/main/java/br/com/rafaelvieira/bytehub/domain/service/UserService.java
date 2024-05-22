package br.com.rafaelvieira.bytehub.domain.service;

import br.com.rafaelvieira.bytehub.api.security.AuthUtils;
import br.com.rafaelvieira.bytehub.domain.exception.EmailNotFoundException;
import br.com.rafaelvieira.bytehub.domain.exception.EmailTakenException;
import br.com.rafaelvieira.bytehub.domain.exception.UsernameTakenException;
import br.com.rafaelvieira.bytehub.domain.model.Profile;
import br.com.rafaelvieira.bytehub.domain.model.User;
import br.com.rafaelvieira.bytehub.domain.repository.ProfileRepository;
import br.com.rafaelvieira.bytehub.domain.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthUtils authUtils;
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final ProfileService profileService;
    private final PasswordEncoder passwordEncoder;
    private final ProfileRepository profileRepository;

    public User getCurrentUser() {
        return getByEmail(authUtils.getCurrentUserEmail());
    }

    @Transactional(readOnly = true)
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(EmailNotFoundException::new);
    }

    @Transactional
    public User save(User user, Profile profile) {
        //so that the profile repository doesn't throw a duplicate row exception when trying to find by profile.username
        entityManager.detach(user);

        checkUserAvailable(user, profile);

        if (user.getId() == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setProfile(profile);
        }

        user = userRepository.save(user);
        profileService.save(profile);
        return user;
    }

    private void checkUserAvailable(User user, Profile profile) {
        if (usernameTaken(profile)) {
            throw new UsernameTakenException();
        }

        if (emailTaken(user)) {
            throw new EmailTakenException();
        }
    }

    private boolean usernameTaken(Profile profile) {
        var existingProfile = profileRepository.findByUsername(profile.getUsername());
        return existingProfile.isPresent() && !existingProfile.get().equals(profile);
    }

    private boolean emailTaken(User user) {
        var existingUser = userRepository.findByEmail(user.getEmail());
        return existingUser.isPresent() && !existingUser.get().equals(user);
    }

    @Transactional
    public void setToken(User user, String token) {
        user.setToken(token);
        userRepository.save(user);
    }
}
