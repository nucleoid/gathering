package statz.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "object")
public class Thing implements Serializable {

	private static final long serialVersionUID = 7057518658741474164L;

	@Id @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "owner", length = 50, nullable = false)
    private String owner;

    @Column(name = "quantity")
    private Integer quantity = 0;

	@OneToOne(optional = false, fetch = FetchType.LAZY)
	private Type type;

    public Thing() {}

    public Thing(String name, String owner, int quantity, Type type) {
        this.name = name;
        this.owner = owner;
        this.quantity = quantity;
        this.type = type;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + quantity;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		final Thing other = (Thing) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (quantity != other.quantity)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public String toString() {
	      return  "Name: '" + getName() + "', " +
	              "Owner: '" + getOwner() + "', " +
	              "Quantity: '" + getQuantity() + "'" +
	              "Type: '" +getType() +"'";
	}
	
}
