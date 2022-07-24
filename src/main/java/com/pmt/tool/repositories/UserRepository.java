package com.pmt.tool.repositories;

import com.pmt.tool.entity.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<TUser, Long> {

    Optional<TUser> findByUserName(String Email);

    @Query("select u from TUser u where u.lastName like %?1")
    List<TUser> findByLastNameContaining(String lastName);

}
