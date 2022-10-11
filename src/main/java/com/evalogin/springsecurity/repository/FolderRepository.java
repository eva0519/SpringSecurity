package com.evalogin.springsecurity.repository;


import com.evalogin.springsecurity.entity.Folder;
import com.evalogin.springsecurity.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
    List<Folder> findAllByUserAndNameIn(User user, List<String> names);
    boolean existsByUserAndName(User user, String name);
}