package org.cat.fish.userservice.service;

import org.cat.fish.userservice.model.dto.request.ChangePasswordRequest;
import org.cat.fish.userservice.model.dto.request.Login;
import org.cat.fish.userservice.model.dto.request.SignUp;
import org.cat.fish.userservice.model.dto.request.UserDto;
import org.cat.fish.userservice.model.dto.response.JwtResponseMessage;
import org.cat.fish.userservice.model.entity.User;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface UserService {
    Mono<User> register(SignUp signUp);
    Mono<JwtResponseMessage> login(Login signInForm);
    Mono<Void> logout();
    Mono<User> update(Long userId, SignUp update);
    Mono<String> changePassword(ChangePasswordRequest request);
    String delete(Long id);
    Optional<User> findById(Long userId);
    Optional<User> findByEmail(String email);
    Page<UserDto> findAllUsers(int page, int size, String sortBy, String sortOrder);

}
