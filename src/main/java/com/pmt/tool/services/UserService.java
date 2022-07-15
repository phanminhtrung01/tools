package com.pmt.tool.services;

import com.pmt.tool.component.Converter;
import com.pmt.tool.dto.UserDto;
import com.pmt.tool.entity.User;
import com.pmt.tool.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Converter<User, UserDto> userConverter;

    @Autowired
    public UserService(
            UserRepository userRepository,
            Converter<User, UserDto> userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();

        return userConverter.entityToDto(users, UserDto.class);
    }

    public Optional<UserDto> findById(Long id) {
        Optional<User> foundUser = userRepository.findById(id);

        return userConverter.entityToDto(foundUser, UserDto.class);
    }

    public List<UserDto> findUserByLastName(String lastName) {
        List<User> users = userRepository.findByLastNameContaining(lastName);

        return userConverter.entityToDto(users, UserDto.class);
    }

    public Optional<UserDto> insertUser(@NotNull UserDto userDto) {
        Optional<User> foundUser = Optional.empty();

        if (!userDto.getUserName().trim().isEmpty()) {
            foundUser = userRepository.findByUserName(userDto.getUserName());
        }

        if (foundUser.isPresent()) {
            foundUser.ifPresent(userRepository::save);
        }

        return userConverter.entityToDto(foundUser, UserDto.class);
    }

    public Optional<UserDto> updateUser(@NotNull UserDto userDto) {
        Optional<User> foundUser = userRepository.findByUserName(userDto.getUserName());
        if (foundUser.isPresent()) {
            return foundUser.map(user -> userConverter.entityToDto(user, UserDto.class));
        } else {
            return insertUser(userDto);
        }
    }

    public Optional<UserDto> deleteUser(String userName) {
        Optional<User> foundUser = userRepository.findByUserName(userName);

        foundUser.map(user -> {
            userRepository.delete(foundUser.get());

            return user;
        });

        return userConverter.entityToDto(foundUser, UserDto.class);

    }

}
