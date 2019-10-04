package reviewsite;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Review {
	@Id
	@GeneratedValue
	private long id;

	private String name;
	
	@ManyToMany(mappedBy = "reviews")
	private Collection<Category> categories;

	public long getId() {
		return id;
	}

	public Review(String name) {
		this.name = name;
	}


	public String getName() {
		return name;
	}
	// default no args constructor required by jpa
	public Review() {
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	

}
