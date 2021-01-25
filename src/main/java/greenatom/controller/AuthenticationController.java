package greenatom.controller;

import greenatom.config.jwt.JwtProvider;
import greenatom.dto.AuthenticationRequestDto;
import greenatom.dto.AuthenticationResponseDto;
import greenatom.model.User;
import greenatom.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {

    private final JwtProvider jwtProvider;

    private final UserServiceImpl userServiceImpl;

    @Autowired
    public AuthenticationController(UserServiceImpl userServiceImpl, JwtProvider jwtProvider) {
        this.userServiceImpl = userServiceImpl;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseDto> auth(@RequestBody AuthenticationRequestDto request) {
        Optional<User> user = userServiceImpl.findByLoginAndPassword(request.getUsername(), request.getPassword());

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with username: " + user.get().getUsername() + " not found");
        }

        String token = jwtProvider.generateToken(user.get().getUsername());

        return ResponseEntity.ok(new AuthenticationResponseDto(token));
    }
}