package deck.build.entity;

import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "post")
public class Post {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int idPost;
	
	String cardImage;
	
	String username;
	
	public Post() {
	}
	
	public int getIdPost() {
		return idPost;
	}

	public void setIdPost(int idPost) {
		this.idPost = idPost;
	}

	public String getCardImage() {
		return cardImage;
	}

	public void setCardImage(String cardImage) {
		this.cardImage = cardImage;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
//	@Column(name="card")
//	Card card;
//	@Column(name="user")
//	User user;
	
	//ArrayList<User> likes = new ArrayList<>();
	//ArrayList<String> comments = new ArrayList<>();
	
//	public Post(int idPost, Card card, User user) {
//		super();
//		this.idPost = idPost;
//		this.card = card;
//		this.user = user;
//	}
//
//

//
//	public Card getCard() {
//		return card;
//	}
//
//	public void setCard(Card card) {
//		this.card = card;
//	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

//	public ArrayList<User> getLikes() {
//		return likes;
//	}
//
//	public void setLikes(ArrayList<User> likes) {
//		this.likes = likes;
//	}
//
//	public ArrayList<String> getComments() {
//		return comments;
//	}
//
//	public void setComments(ArrayList<String> comments) {
//		this.comments = comments;
//	}
}
