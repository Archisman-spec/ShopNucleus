package com.archi.Ecomm_Backend.repository;

import com.archi.Ecomm_Backend.models.Role;

import com.archi.Ecomm_Backend.models.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long> {
   Role findByRoleName(String roleName);

   default Role findByRoleType(RoleType roleType){
       return findByRoleName(roleType.name());
   }

}
