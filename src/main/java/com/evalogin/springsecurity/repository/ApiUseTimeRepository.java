package com.evalogin.springsecurity.repository;


import com.evalogin.springsecurity.entity.ApiUseTime;
import com.evalogin.springsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
    Optional<ApiUseTime> findByUser(User user);
}