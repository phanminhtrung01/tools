package com.pmt.tool.repositories;

import com.pmt.tool.entity.TSoftware;
import com.pmt.tool.entity.TSoftwareType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoftwareTypeRepository extends JpaRepository<TSoftwareType, Long> {
    List<TSoftwareType> findAllByExtensionType(String extensionName);

    List<TSoftwareType> findAllBySoftware(TSoftware software);

}
