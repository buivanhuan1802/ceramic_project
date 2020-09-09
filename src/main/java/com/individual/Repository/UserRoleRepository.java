package com.individual.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.individual.Entity.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	

}
