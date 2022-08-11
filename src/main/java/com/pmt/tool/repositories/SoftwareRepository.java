package com.pmt.tool.repositories;

import com.pmt.tool.entity.TSoftware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SoftwareRepository extends JpaRepository<TSoftware, Long> {
    Optional<TSoftware> findByNameSoftware(String nameSoftware);
}
