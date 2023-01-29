package deck.build.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Comparator;

import javax.persistence.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "card")
public class Card{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idCard;

    private String id;
    private String name;
    private int nationalPokedexNumber;
    private String imageSmall;
    private String imageLarge;
    private String supertype;
    private String subtype;
    private String evolvesFrom;
    private String hp;
    private Integer hpInt;
    
    private String number;
    private String artist;
    private String rarity;
    private String series;
    private String setName;
    
    public int getIdCard() {
		return idCard;
	}

	public void setIdCard(int idCard) {
		this.idCard = idCard;
	}

	public String getImageSmall() {
		return imageSmall;
	}

	public void setImageSmall(String imageSmall) {
		this.imageSmall = imageSmall;
	}

	public String getImageLarge() {
		return imageLarge;
	}

	public void setImageLarge(String imageLarge) {
		this.imageLarge = imageLarge;
	}

	public String getSetName() {
		return setName;
	}

	public void setSetName(String setName) {
		this.setName = setName;
	}

	public String getFlavorText() {
		return flavorText;
	}

	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}

	private String setCode;
	private String flavorText;
    
    public Card() {
    }

    public Card(String id, String name, int nationalPokedexNumber, String imageUrl) {
        this.id = id;
        this.name = name;
        this.nationalPokedexNumber = nationalPokedexNumber;
        this.imageLarge = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNationalPokedexNumber() {
        return nationalPokedexNumber;
    }

    public void setNationalPokedexNumber(int nationalPokedexNumber) {
        this.nationalPokedexNumber = nationalPokedexNumber;
    }

    public String getImageUrl() {
        return imageSmall;
    }

    public void setImageUrl(String imageUrl) {
        this.imageSmall = imageUrl;
    }

    public String getImageUrlHiRes() {
        return imageLarge;
    }

    public void setImageUrlHiRes(String imageUrlHiRes) {
        this.imageLarge = imageUrlHiRes;
    }

    public String getSupertype() {
        return supertype;
    }

    public void setSupertype(String supertype) {
        this.supertype = supertype;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getEvolvesFrom() {
        return evolvesFrom;
    }

    public void setEvolvesFrom(String evolvesFrom) {
        this.evolvesFrom = evolvesFrom;
    }

    public String getHp() {
        return hp;
    }

    public void setHp(String hp) {
        this.hp = hp;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getSet() {
        return setName;
    }

    public void setSet(String set) {
        this.setName = set;
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }


    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", nationalPokedexNumber=" + nationalPokedexNumber +
                ", imageUrl='" + imageSmall + '\'' +
                ", imageUrlHiRes='" + imageLarge + '\'' +
                ", supertype='" + supertype + '\'' +
                ", subtype='" + subtype + '\'' +
                ", evolvesFrom='" + evolvesFrom + '\'' +
                ", hp='" + hp + '\'' +
                ", number='" + number + '\'' +
                ", artist='" + artist + '\'' +
                ", rarity='" + rarity + '\'' +
                ", series='" + series + '\'' +
                ", set='" + setName + '\'' +
                ", setCode='" + setCode + '\'' +
                '}';
    }
    
    
	public static Comparator<Card> compareHp = new Comparator<Card>() {
		@Override
		public int compare(Card c1, Card c2) {
			int result = c1.getHpInt().compareTo(c2.getHpInt());
			if(result ==0) result = c1.getHpInt().compareTo(c2.getHpInt());
			return result;
		}
	};

	public Integer getHpInt() {
		return hpInt;
	}

	public void setHpInt(String hp) {
		try {
            this.hpInt = Integer.parseInt(hp);
        }catch(Exception e) {
        	System.out.println("Error parsing string hp ["+hp+"] to int - setting 0 "+e);
        	hpInt=0;
        }
	}    
	
}