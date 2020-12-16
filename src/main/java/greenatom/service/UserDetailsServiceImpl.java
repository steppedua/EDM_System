package greenatom.service;

import greenatom.mappers.UserMapper;
import greenatom.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserServiceImpl userServiceImpl;
    private final UserMapper userMapper;

    @Autowired
    public UserDetailsServiceImpl(UserServiceImpl userServiceImpl, UserMapper userMapper) {
        this.userServiceImpl = userServiceImpl;
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userServiceImpl.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User " + username + " doesn't exists"));

        return new UserDetailsImpl(userMapper.toUserByUsernameDto(user.getUsername()));
    }
}
