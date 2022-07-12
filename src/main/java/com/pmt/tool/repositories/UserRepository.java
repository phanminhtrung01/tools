package com.pmt.tool.repositories;

import com.pmt.tool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String Email);

    @Query("select u from User u where u.lastName like %?1")
    List<User> findByLastNameContaining(String lastName);

}
