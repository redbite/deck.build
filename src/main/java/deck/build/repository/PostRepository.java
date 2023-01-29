package deck.build.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import deck.build.entity.Post;
import deck.build.entity.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
	//the filter is passed by the homepage with a drop menu with all the possibilities
	@Query("SELECT p FROM Post p ORDER BY :filter")
	User findAllByFilter(String filter);
}
