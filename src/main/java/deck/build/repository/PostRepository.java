package com.example.pocketMonsterCollector.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.pocketMonsterCollector.entity.Post;
import com.example.pocketMonsterCollector.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	//the filter is passed by the homepage with a drop menu with all the possibilities
	@Query("SELECT p FROM Post p ORDER BY :filter")
	User findAllByFilter(String filter);
}
