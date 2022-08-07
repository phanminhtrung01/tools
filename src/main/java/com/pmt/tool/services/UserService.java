package com.pmt.tool.services;

import com.pmt.tool.component.Converter;
import com.pmt.tool.dto.TUserDto;
import com.pmt.tool.entity.TUser;
import com.pmt.tool.repositories.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Converter<TUser, TUserDto> userConverter;

    @Autowired
    public UserService(
            UserRepository userRepository,
            Converter<TUser, TUserDto> userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public List<TUserDto> getAllUser() {
        List<TUser> users = userRepository.findAll();

        return userConverter.entityToDto(users, TUserDto.class);
    }

    public Optional<TUserDto> findById(Long id) {
        Optional<TUser> foundUser = userRepository.findById(id);

        return userConverter.entityToDto(foundUser, TUserDto.class);
    }

    public List<TUserDto> findUserByLastName(String lastName) {
        List<TUser> users = userRepository.findByLastNameContaining(lastName);

        return userConverter.entityToDto(users, TUserDto.class);
    }

    public Optional<TUserDto> insertUser(@NotNull TUserDto userDto) {
        Optional<TUser> foundUser = Optional.empty();

        if (!userDto.getUserName().trim().isEmpty()) {
            foundUser = userRepository.findByUserName(userDto.getUserName());
        }

        if (foundUser.isEmpty()) {
            userRepository.save(userConverter.dtoToEntity(userDto, TUser.class));
        }

        return userConverter.entityToDto(foundUser, TUserDto.class);
    }

    public Optional<TUserDto> updateUser(@NotNull TUserDto userDto) {
        Optional<TUser> foundUser = userRepository.findByUserName(userDto.getUserName());
        if (foundUser.isPresent()) {
            return foundUser.map(user -> userConverter.entityToDto(user, TUserDto.class));
        } else {
            return insertUser(userDto);
        }
    }

    public Optional<TUserDto> deleteUser(String userName) {
        Optional<TUser> foundUser = userRepository.findByUserName(userName);

        foundUser.map(user -> {
            userRepository.delete(foundUser.get());

            return user;
        });

        return userConverter.entityToDto(foundUser, TUserDto.class);

    }

}
