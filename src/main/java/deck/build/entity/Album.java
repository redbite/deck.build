package deck.build.entity;

import java.util.HashMap;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "album")
public class Album {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idAlbum;

    private String nameAlbum;
    private HashMap<String, Integer> cards = new HashMap<>();
    private Integer pages;
    private Integer sheetSize;
    
    
}
