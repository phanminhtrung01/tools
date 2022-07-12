package com.pmt.tool.repositories;

import com.pmt.tool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserName(String Email);

    @Query("select u from User u where u.lastName like %?1")
    List<User> findByLastNameContaining(String lastName);

}
