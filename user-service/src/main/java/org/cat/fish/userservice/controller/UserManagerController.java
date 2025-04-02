package org.cat.fish.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.cat.fish.userservice.exception.wrapper.TokenErrorOrAccessTimeOut;
import org.cat.fish.userservice.exception.wrapper.UserNotFoundException;
import org.cat.fish.userservice.http.HeaderGenerator;
import org.cat.fish.userservice.model.dto.request.ChangePasswordRequest;
import org.cat.fish.userservice.model.dto.request.SignUp;
import org.cat.fish.userservice.model.dto.request.UserDto;
import org.cat.fish.userservice.model.dto.response.ResponseMessage;
import org.cat.fish.userservice.security.jwt.JwtProvider;
import org.cat.fish.userservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/manager")
public class UserManagerController {
    private final ModelMapper modelMapper;

    private final UserService userService;
    private final HeaderGenerator headerGenerator;
    private final JwtProvider jwtProvider;

    @Autowired
    public UserManagerController(UserService userService, HeaderGenerator headerGenerator, JwtProvider jwtProvider,
                       ModelMapper modelMapper) {
        this.userService = userService;
        this.headerGenerator = headerGenerator;
        this.jwtProvider = jwtProvider;
        this.modelMapper = modelMapper;
    }

    @PutMapping("update/{id}")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public Mono<ResponseEntity<ResponseMessage>> update(@PathVariable("id") Long id, @RequestBody SignUp updateDTO) {
        return userService.update(id, updateDTO)
                .flatMap(user -> Mono.just(new ResponseEntity<>(
                        new ResponseMessage("Update user: " + updateDTO.getEmail() + " successfully."),
                        HttpStatus.OK))
                )
                .onErrorResume(
                        error -> Mono.just(new ResponseEntity<>(
                                new ResponseMessage("Update user: " + updateDTO.getEmail() + " failed " + error.getMessage()),
                                HttpStatus.BAD_REQUEST)
                        )
                );
    }

    @PutMapping("/change-password")
    @PreAuthorize("isAuthenticated() and hasAuthority('USER')")
    public Mono<String> changePassword(@RequestBody ChangePasswordRequest request) {
        return userService.changePassword(request);
    }

    @DeleteMapping("delete/{id}")
    @PreAuthorize("isAuthenticated() and (hasAuthority('USER') or hasAuthority('ADMIN'))")
    public String delete(@PathVariable("id") Long id) {
        return userService.delete(id);
    }

    @GetMapping("/user")
    @PreAuthorize("(isAuthenticated() and (hasAuthority('USER') and principal.username == #username) or hasAuthority('ADMIN'))")
    public ResponseEntity<?> getUserByUsername(@RequestParam(value = "username") String username) {
        Optional<UserDto> user = Optional.ofNullable(userService.findByEmail(username)
                .map((element) -> modelMapper.map(element, UserDto.class))
                .orElseThrow(() -> new UserNotFoundException("User not found with: " + username)));
        return user.map(u -> new ResponseEntity<>(u,
                        headerGenerator.getHeadersForSuccessGetMethod(),
                        HttpStatus.OK)
                )
                .orElseGet(() -> new ResponseEntity<>(null,
                        headerGenerator.getHeadersForError(),
                        HttpStatus.NOT_FOUND)
                );
    }

    @GetMapping("/user/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER') and principal.id == #id")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id) {
        Optional<UserDto> userDTO = Optional.ofNullable(userService.findById(id)
                .map((element) -> modelMapper.map(element, UserDto.class))
                .orElseThrow(() -> new UserNotFoundException("User not found with: " + id)));
        return (userDTO.isPresent())
                ? new ResponseEntity<>(userDTO.get(), headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK)
                : new ResponseEntity<>(null, headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Page<UserDto>> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size,
                                                     @RequestParam(defaultValue = "id") String sortBy,
                                                     @RequestParam(defaultValue = "ASC") String sortOrder) {

        Page<UserDto> usersPage = userService.findAllUsers(page, size, sortBy, sortOrder);
        return new ResponseEntity<>(usersPage, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(@RequestHeader("Authorization") String token) {
        String username = jwtProvider.getEmailFromToken(token);
        UserDto user = userService.findByEmail(username)
                .map((element) -> modelMapper.map(element, UserDto.class))
                .orElseThrow(() -> new TokenErrorOrAccessTimeOut("Token error or access timeout"));

        return (user != null)
                ? new ResponseEntity<>(user, headerGenerator.getHeadersForSuccessGetMethod(), HttpStatus.OK)
                : new ResponseEntity<>(null, headerGenerator.getHeadersForError(), HttpStatus.NOT_FOUND);
    }
}
