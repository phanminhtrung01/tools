package com.pmt.tool.services.serviceIml;

import com.pmt.tool.component.Converter;
import com.pmt.tool.dto.TUserDto;
import com.pmt.tool.entity.TUser;
import com.pmt.tool.repositories.UserRepository;
import com.pmt.tool.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceIml implements UserService {
    private final UserRepository userRepository;
    private final Converter<TUser, TUserDto> userConverter;

    @Override
    public List<TUserDto> getAllUser() {
        List<TUser> users = userRepository.findAll();

        return userConverter.entityToDto(users, TUserDto.class);
    }

    @Override
    public Optional<TUserDto> findById(Long id) {
        Optional<TUser> foundUser = userRepository.findById(id);

        return userConverter.entityToDto(foundUser, TUserDto.class);
    }

    @Override
    public List<TUserDto> findUserByLastName(String lastName) {
        List<TUser> users = userRepository.findByLastNameContaining(lastName);

        return userConverter.entityToDto(users, TUserDto.class);
    }

    @Override
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

    @Override
    public Optional<TUserDto> updateUser(@NotNull TUserDto userDto) {
        Optional<TUser> foundUser = userRepository.findByUserName(userDto.getUserName());
        if (foundUser.isPresent()) {
            return foundUser.map(user -> userConverter.entityToDto(user, TUserDto.class));
        } else {
            return insertUser(userDto);
        }
    }

    @Override
    public Optional<TUserDto> deleteUser(String userName) {
        Optional<TUser> foundUser = userRepository.findByUserName(userName);

        foundUser.map(user -> {
            userRepository.delete(foundUser.get());

            return user;
        });

        return userConverter.entityToDto(foundUser, TUserDto.class);

    }
}
