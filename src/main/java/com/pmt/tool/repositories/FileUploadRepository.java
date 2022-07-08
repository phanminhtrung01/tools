package com.pmt.tool.repositories;

import com.pmt.tool.entity.Files;
import com.pmt.tool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileUploadRepository extends JpaRepository<Files, Long> {


    List<Files> findByNameFile(String nameFile);

    @Query("select u from Files u where u.typeFile like %?1")
    List<User> findAllByTypeFile(String typeFile);

}
