package com.pmt.tool.repositories;

import com.pmt.tool.entity.DetailType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailTypeRepository extends JpaRepository<DetailType, Long> {

}
