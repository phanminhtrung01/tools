package com.pmt.tool.controller;

import com.pmt.tool.dto.TUserDto;
import com.pmt.tool.entity.ResponseObject;
import com.pmt.tool.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/pmt/user/")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    List<TUserDto> getAllUser() {
        return userService.getAllUser();
    }

    @ResponseBody
    @GetMapping("/find")
    ResponseEntity<ResponseObject> findById(@RequestParam Long id) {
        Optional<TUserDto> foundUser = userService.findById(id);

        return foundUser.map(user -> ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        HttpStatus.OK.value(),
                        "Query user successfully",
                        user
                )
        )).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(
                        HttpStatus.NO_CONTENT.value(),
                        "Cannot find user with idUser: " + id,
                        foundUser
                )
        ));
    }

    @ResponseBody
    @GetMapping("find/{lastName}")
    ResponseEntity<ResponseObject> findUserByLastName(@PathVariable("lastName") String lastName) {
        List<TUserDto> foundUsers = userService.findUserByLastName(lastName);

        return foundUsers.size() > 0
                ? ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        200,
                        "Query user successfully!",
                        foundUsers
                )
        )
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(
                        HttpStatus.NO_CONTENT.value(),
                        "Cannot find user with lastName: " + lastName,
                        foundUsers//Optional.empty()
                )
        );
    }

    @PostMapping("insert")
    ResponseEntity<ResponseObject> insertUser(@RequestBody TUserDto userDto) {

        Optional<TUserDto> foundUser = userService.insertUser(userDto);

        if (foundUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(
                    new ResponseObject(
                            HttpStatus.ALREADY_REPORTED.value(),
                            "Email of user already taken",
                            Optional.empty()
                    )
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(
                        HttpStatus.OK.value(),
                        "Insert user successfully",
                        foundUser
                )
        );
    }

    @PutMapping("update")
    ResponseEntity<ResponseObject> updateUser(@RequestBody TUserDto userDto) {
        Optional<TUserDto> updateUser = userService.updateUser(userDto);

        if (updateUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            HttpStatus.OK.value(),
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

        Optional<TUserDto> deleteUser = userService.deleteUser(userName);

        if (deleteUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(
                            HttpStatus.OK.value(),
                            "Delete user successfully",
                            deleteUser
                    )
            );

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject(
                        HttpStatus.NO_CONTENT.value(),
                        "Cannot not User to delete",
                        deleteUser
                )
        );
    }
}
