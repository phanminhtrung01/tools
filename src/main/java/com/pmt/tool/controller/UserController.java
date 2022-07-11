package com.pmt.tool.controller;

import com.pmt.tool.component.Converter;
import com.pmt.tool.dto.UserDto;
import com.pmt.tool.entity.ResponseObject;
import com.pmt.tool.entity.User;
import com.pmt.tool.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/pmt/users/")
public class UserController {

    private final UserRepository userRepository;
    private Converter<User, UserDto> userConverter;

    @GetMapping("home")
    public String greeting() {
        return "index";
    }

    @GetMapping("")
    List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        return userConverter.entityToDto(users, UserDto.class);
    }

    @ResponseBody
    @GetMapping("find/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<User> foundUser = userRepository.findById(id);

        return foundUser.map(user -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Query user successfully",
                        userConverter.entityToDto(user, UserDto.class)
                )
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(
                        404,
                        "Cannot find user with idUser: " + id,
                        ""
                )
        ));
    }

    @ResponseBody
    @GetMapping("find/{lastName}")
    ResponseEntity<ResponseObject> findUserByLastName(@PathVariable("lastName") String lastName) {
        List<User> foundUser = userRepository.findByLastNameContaining(lastName);

        return foundUser.size() > 0 ?
                ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(
                                200,
                                "Query user successfully",
                                userConverter.entityToDto(foundUser, UserDto.class)
                        )
                ) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject(
                                500,
                                "Cannot find user with lastName: " + lastName,
                                ""
                        )
                );
    }

    @PostMapping("insert")
    ResponseEntity<ResponseObject> insertUser(@RequestBody @NotNull UserDto userDto) {

        //TODO: check foundUser.Size --> error :.trim()
        List<User> foundUser = userRepository.findByUserName(userConverter.dtoToEntity(userDto, User.class).getUserName().trim());

        if (foundUser.size() > 0) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            501,
                            "Email of user already taken",
                            ""
                    )
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Insert user successfully",
                        userConverter.entityToDto(
                                userRepository.save(userConverter.dtoToEntity(userDto, User.class)),
                                UserDto.class
                        )
                )
        );
    }

    @PutMapping("{id}")
    ResponseEntity<ResponseObject> updateUser(@RequestBody UserDto userDto, @PathVariable Long id) {
        Optional<User> updateUser = userRepository.findById(id);
        if (updateUser.isPresent()) {

            updateUser.map(user1 -> {
                user1.setFirstName(userConverter.dtoToEntity(userDto, User.class).getFirstName());
                user1.setLastName(userConverter.dtoToEntity(userDto, User.class).getLastName());
                user1.setUserName(userConverter.dtoToEntity(userDto, User.class).getUserName());

                return userRepository.save(user1);
            });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            201,
                            "Update user successfully",
                            userConverter.entityToDto(updateUser.get(), UserDto.class)
                    )
            );
        } else {
            userConverter.dtoToEntity(userDto, User.class).setIdUser(id);
            return insertUser(userDto);
        }
    }

    @DeleteMapping("delete/{id}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable Long id) {
        boolean exists = userRepository.existsById(id);

        if (exists) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Delete user successfully",
                            ""
                    )
            );

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(
                        404,
                        "Cannot not User to delete",
                        ""
                )
        );
    }
}
