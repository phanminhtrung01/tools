package com.pmt.tool.services;

import com.pmt.tool.dto.TUserDto;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<TUserDto> getAllUser();

    Optional<TUserDto> findById(Long id);

    List<TUserDto> findUserByLastName(String lastName);

    Optional<TUserDto> insertUser(@NotNull TUserDto userDto);

    Optional<TUserDto> updateUser(@NotNull TUserDto userDto);

    Optional<TUserDto> deleteUser(String userName);

}
