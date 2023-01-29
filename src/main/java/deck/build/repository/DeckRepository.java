package deck.build.repository;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import deck.build.entity.Deck;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long>{
	@Query("SELECT d FROM Deck d WHERE name=:name ORDER BY idDeck DESC")
	ArrayList<Deck> findAllByName(String name);

	@Query("SELECT name FROM Deck")
	ArrayList<String> findAllNames();
	
	@Modifying
	@Query("DELETE FROM Deck WHERE name=:name ")
	void delete(String name);
	
}
