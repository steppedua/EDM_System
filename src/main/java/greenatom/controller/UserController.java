package greenatom.controller;

import greenatom.dto.UserDto;
import greenatom.mappers.UserMapper;
import greenatom.model.User;
import greenatom.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    private final UserMapper userMapper;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl, UserMapper userMapper) {
        this.userServiceImpl = userServiceImpl;
        this.userMapper = userMapper;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
    public ResponseEntity<List<UserDto>> getUserList() {
        List<User> userList = userServiceImpl.getUserList();

        return ResponseEntity.ok(userMapper.toUsersListDto(userList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        Optional<User> userById = userServiceImpl.getUserById(id);

        return ResponseEntity.ok(userMapper.toUserByIdDto(userById.get()));
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(
            @RequestBody UserDto userDto
    ) {
        userServiceImpl.createUser(userMapper.toCreateUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeUser(@PathVariable("id") Long id) {
        return responseEntityOf(userServiceImpl.removeUser(id));
    }

    private ResponseEntity<?> responseEntityOf(boolean value) {
        if (value) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}