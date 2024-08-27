package com.khrd.spring_boot_mini_project.repository;

import com.khrd.spring_boot_mini_project.repository.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article , Integer> {


}
