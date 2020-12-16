package greenatom.mappers;

import greenatom.dto.UserDto;
import greenatom.model.User;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper
public interface UserMapper {

    UserDto toUserByIdDto(User user);

    List<UserDto> toUsersListDto(List<User> products);

    UserDto toUserByUsernameDto(String username);

    User toCreateUser(UserDto userDto);
}