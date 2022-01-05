package com.example.projectbackend.repository.crawling;

import com.example.projectbackend.entity.crawling.IndieNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IndieNewsRepository extends JpaRepository<IndieNews, Long> {
}
