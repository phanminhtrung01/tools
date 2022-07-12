package com.pmt.tool.controller;

import com.pmt.tool.dto.UserDto;
import com.pmt.tool.entity.ResponseObject;
import com.pmt.tool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/pmt/users/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("home")
    public String greeting() {
        return "index";
    }

    @GetMapping("")
    List<UserDto> getAllUser() {
        return userService.getAllUser();
    }

    @ResponseBody
    @GetMapping("find/{id}")
    ResponseEntity<ResponseObject> findById(@PathVariable Long id) {
        Optional<UserDto> foundUser = userService.findById(id);

        return foundUser.map(user -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Query user successfully",
                        user
                )
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(
                        404,
                        "Cannot find user with idUser: " + id,
                        foundUser//Optional.empty()
                )
        ));
    }

    @ResponseBody
    @GetMapping("find/{lastName}")
    ResponseEntity<ResponseObject> findUserByLastName(@PathVariable("lastName") String lastName) {
        List<UserDto> foundUsers = userService.findUserByLastName(lastName);

        return foundUsers.size() > 0
                ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Query user successfully",
                        foundUsers
                )
        )
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(
                        500,
                        "Cannot find user with lastName: " + lastName,
                        foundUsers//Optional.empty()
                )
        );
    }

    @PostMapping("insert")
    ResponseEntity<ResponseObject> insertUser(@RequestBody UserDto userDto) {

        Optional<UserDto> foundUser = userService.insertUser(userDto);

        if (foundUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject(
                            501,
                            "Email of user already taken",
                            foundUser //Optional.empty()
                    )
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Insert user successfully",
                        foundUser
                )
        );
    }

    @PutMapping("update")
    ResponseEntity<ResponseObject> updateUser(@RequestBody UserDto userDto) {
        Optional<UserDto> updateUser = userService.updateUser(userDto);

        if (updateUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            201,
                            "Update user successfully",
                            updateUser
                    )
            );
        } else {
            return insertUser(userDto);
        }
    }

    @DeleteMapping("delete/{userName}")
    ResponseEntity<ResponseObject> deleteUser(@PathVariable String userName) {

        Optional<UserDto> deleteUser = userService.deleteUser(userName);

        if (deleteUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            200,
                            "Delete user successfully",
                            deleteUser
                    )
            );

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(
                        404,
                        "Cannot not User to delete",
                        deleteUser
                )
        );
    }
}
