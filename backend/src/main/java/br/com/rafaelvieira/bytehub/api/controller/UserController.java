package br.com.rafaelvieira.bytehub.api.controller;

import br.com.rafaelvieira.bytehub.api.mapper.UserMapper;
import br.com.rafaelvieira.bytehub.api.model.user.UserResponse;
import br.com.rafaelvieira.bytehub.api.model.user.UserUpdate;
import br.com.rafaelvieira.bytehub.api.security.authorization.CheckSecurity;
import br.com.rafaelvieira.bytehub.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    @CheckSecurity.Protected.canManage
    public UserResponse getCurrentUser() {
        var user = userService.getCurrentUser();
        return userMapper.toResponse(user);
    }

    @PutMapping
    @CheckSecurity.Protected.canManage
    public UserResponse updateCurrentUser(@RequestBody UserUpdate userUpdate) {
        var currentUser = userService.getCurrentUser();
        userMapper.copyToEntity(userUpdate, currentUser);
        return userMapper.toResponse(userService.save(currentUser, currentUser.getProfile()));
    }

}
