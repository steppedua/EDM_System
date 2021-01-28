package greenatom.controller;

import greenatom.dto.UserDto;
import greenatom.mappers.UserMapper;
import greenatom.model.User;
import greenatom.service.UserServiceImpl;
import greenatom.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserDto>> getUserList() {
        List<User> userList = userServiceImpl.getUserList();

        return ResponseEntity.ok(userMapper.toUsersListDto(userList));
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        Optional<User> userById = userServiceImpl.getUserById(id);

        return ResponseEntity.ok(userMapper.toUserByIdDto(userById.get()));
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
        userServiceImpl.createUser(userMapper.toCreateUser(userDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeUser(@PathVariable("id") Long id) {
        return ResponseUtils.responseEntityOf(userServiceImpl.removeUser(id));
    }
}