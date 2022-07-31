package com.pmt.tool.repositories;

import com.pmt.tool.entity.TTypeWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeWorkRepository extends JpaRepository<TTypeWork, Long> {
}
