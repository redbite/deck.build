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
    private String creator;
    
	public Album() {
		super();
	}
	
	public Album(int idAlbum, String nameAlbum, HashMap<String, Integer> cards, Integer pages, Integer sheetSize,
			String creator) {
		super();
		this.idAlbum = idAlbum;
		this.nameAlbum = nameAlbum;
		this.cards = cards;
		this.pages = pages;
		this.sheetSize = sheetSize;
		this.creator = creator;
	}
	
	public int getIdAlbum() {
		return idAlbum;
	}
	public void setIdAlbum(int idAlbum) {
		this.idAlbum = idAlbum;
	}
	public String getNameAlbum() {
		return nameAlbum;
	}
	public void setNameAlbum(String nameAlbum) {
		this.nameAlbum = nameAlbum;
	}
	public HashMap<String, Integer> getCards() {
		return cards;
	}
	public void setCards(HashMap<String, Integer> cards) {
		this.cards = cards;
	}
	public Integer getPages() {
		return pages;
	}
	public void setPages(Integer pages) {
		this.pages = pages;
	}
	public Integer getSheetSize() {
		return sheetSize;
	}
	public void setSheetSize(Integer sheetSize) {
		this.sheetSize = sheetSize;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	
}
