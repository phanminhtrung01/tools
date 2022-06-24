package component;

import com.pmt.tool.dto.UserDto;
import com.pmt.tool.entity.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {

    public UserDto entityToDto(@NotNull User user) {

        UserDto userDto = new UserDto();

        userDto.setId(user.getIdUser());
        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setGender(user.getGender());

        return userDto;
    }

    public List<UserDto> entityToDto(@NotNull List<User> users) {

        return users.stream().map(this::entityToDto).collect(Collectors.toList());
    }

    public User dtoToEntity(@NotNull UserDto userDto) {

        User user = new User();

        user.setIdUser(userDto.getId());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setGender(userDto.getGender());

        return user;
    }

    public List<User> dtoToEntity(@NotNull List<UserDto> usersDto) {
        return usersDto.stream().map(this::dtoToEntity).collect(Collectors.toList());
    }

}
