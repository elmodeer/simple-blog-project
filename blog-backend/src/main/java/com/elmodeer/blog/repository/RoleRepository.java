package com.elmodeer.blog.repository;

import com.elmodeer.blog.models.ERole;
import com.elmodeer.blog.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);

}