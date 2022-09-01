package com.pmt.tool.repositories;

import com.pmt.tool.entity.TRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<TRole, Long> {
}
