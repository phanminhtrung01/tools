package com.pmt.tool.repositories;

import com.pmt.tool.entity.TStatusFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusFileRepository extends JpaRepository<TStatusFile, Long> {

    Optional<TStatusFile> findByNameStatusFile(String nameSf);
}
