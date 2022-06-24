package com.pmt.tool.repositories;

import com.pmt.tool.entity.FileUpload;
import com.pmt.tool.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {


    List<FileUpload> findByNameFile(String nameFile);

    @Query("select u from FileUpload u where u.typeFile like %?1")
    List<User> findAllByTypeFile(String typeFile);

}
