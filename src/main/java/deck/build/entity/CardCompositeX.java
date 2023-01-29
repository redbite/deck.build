package deck.build.entity;

public class CardCompositeX {
	
	private Card card;
	private Integer quantity;
	private Boolean pkmnType; //true pkmnType, false trainer or energy
	
	
	public CardCompositeX(Card card, Integer quantity, Boolean pkmnType) {
		this.card = card;
		this.quantity = quantity;
		this.pkmnType = pkmnType;
	}


	public CardCompositeX() {
	}


	public Card getCard() {
		return card;
	}


	public void setCard(Card card) {
		this.card = card;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Boolean getPkmnType() {
		return pkmnType;
	}


	public void setPkmnType(Boolean pkmnType) {
		this.pkmnType = pkmnType;
	}
	
	
}
