package com.khrd.spring_boot_mini_project.repository;

import com.khrd.spring_boot_mini_project.model.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookMarkRepository extends JpaRepository<Bookmark,Integer> {
}
