package deck.build.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import deck.build.entity.Album;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long>{
	@Query("SELECT a FROM Album a WHERE albumName=:name")
	ArrayList<Album> findAllByName(String name);
	
	@Modifying
	@Query("DELETE FROM Album WHERE albumName=:name ")
	void delete(String name);
	
}
