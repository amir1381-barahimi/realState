package com.realstate.demo.ws.repository;

import com.realstate.demo.ws.model.entity.UserEntity;
import com.realstate.demo.ws.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByPublicId(String publicId);

    UserEntity  findByUsername(String userName);
    List<UserEntity> findByRole(Role role);

    List<UserEntity> findAll();

    int deleteByPublicId(String publicId);

}