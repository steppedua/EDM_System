package greenatom.service;

import greenatom.model.User;
import greenatom.repository.RoleRepository;
import greenatom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> getUserList() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> createUser(User user) {
        Optional<User> userFromDB = userRepository.findByUsername(user.getUsername());

        if (userFromDB.isPresent()) {
            return Optional.empty();
        }

        User newUser = new User();
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setPassword(user.getPassword());
        newUser.setRoles(Collections.singleton(roleRepository.findByName("ROLE_USER")));

        return Optional.of(userRepository.saveAndFlush(newUser));
    }

    @Override
    public boolean removeUser(Long id) {
        return userRepository.findById(id).map(user -> {
            if (userRepository.findById(user.getId()).isPresent()) {
                userRepository.deleteById(user.getId());
                return true;
            }
            return false;
        }).orElse(false);
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userRepository.findByUsernameAndPassword(login, password);
    }
}
