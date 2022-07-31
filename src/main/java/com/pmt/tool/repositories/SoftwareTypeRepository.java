package com.pmt.tool.repositories;

import com.pmt.tool.entity.TSoftwareType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoftwareTypeRepository extends JpaRepository<TSoftwareType, Long> {
}
