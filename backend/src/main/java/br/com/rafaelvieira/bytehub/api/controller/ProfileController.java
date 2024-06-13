package br.com.rafaelvieira.bytehub.api.controller;

import br.com.rafaelvieira.bytehub.api.mapper.ProfileMapper;
import br.com.rafaelvieira.bytehub.api.model.profile.ProfileResponse;
import br.com.rafaelvieira.bytehub.api.security.AuthUtils;
import br.com.rafaelvieira.bytehub.api.security.authorization.CheckSecurity;
import br.com.rafaelvieira.bytehub.domain.service.ProfileService;
import br.com.rafaelvieira.bytehub.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private final UserService userService;
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;
    private final AuthUtils authUtils;

    @GetMapping("/{username}")
    @CheckSecurity.Public.canRead
    public ProfileResponse getByUsername(@PathVariable String username, WebRequest request) {
        if (authUtils.isAuthenticated()) {
            var currentUser = userService.getCurrentUser().getProfile();
            return profileMapper.toResponse(currentUser, profileService.getByUsername(username));
        }

        return profileMapper.toResponse(profileService.getByUsername(username));
    }

    @PostMapping("/{username}/follow")
    @CheckSecurity.Protected.canManage
    public ProfileResponse followProfile(@PathVariable String username) {
        var toFollow = profileService.getByUsername(username);
        var current = userService.getCurrentUser().getProfile();

        profileService.follow(current, toFollow);
        return profileMapper.toResponse(current, toFollow);
    }

    @DeleteMapping("/{username}/follow")
    @CheckSecurity.Protected.canManage
    public ProfileResponse unfollowProfile(@PathVariable String username) {
        var toFollow = profileService.getByUsername(username);
        var current = userService.getCurrentUser().getProfile();

        profileService.unfollow(current, toFollow);
        return profileMapper.toResponse(current, toFollow);
    }
}
