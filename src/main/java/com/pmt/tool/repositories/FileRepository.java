package com.pmt.tool.repositories;

import com.pmt.tool.entity.TFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<TFile, String> {

    List<TFile> findByTypeFile(String typeFile);

    List<TFile> findByNameFileAndTypeFile(String nameFile, String typeFile);
}
