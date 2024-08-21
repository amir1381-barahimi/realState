package com.realstate.demo.ws.repository;

import com.realstate.demo.ws.model.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Advertisement, Long> {
    Advertisement findAdById(long id);
}
