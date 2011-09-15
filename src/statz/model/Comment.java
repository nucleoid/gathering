package statz.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "comment")
public class Comment implements Serializable {

	private static final long serialVersionUID = -4191718508192538157L;

		@Id @GeneratedValue
	    @Column(name = "id")
	    private Integer id;

	    @Column(name = "name", length = 50, nullable = false)
	    private String name;

	    @Column(name = "comment", length = 255, nullable = false)
	    private String comment;
	    
	    @Temporal(TemporalType.TIMESTAMP)
		@Column(name="postdate", nullable = false, updatable = true)
	    private Date postDate;

	    public Comment() {}

	    public Comment(String name, String comment) {
	        this.name = name;
	        this.comment = comment;
	    }

		public String getComment() {
			return comment;
		}

		public void setComment(String comment) {
			this.comment = comment;
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
	    
		public Date getPostDate() {
			return postDate;
		}

		public void setPostDate(Date postDate) {
			this.postDate = postDate;
		}

		@Override
		public int hashCode() {
			final int PRIME = 31;
			int result = 1;
			result = PRIME * result + ((comment == null) ? 0 : comment.hashCode());
			result = PRIME * result + ((name == null) ? 0 : name.hashCode());
			result = PRIME * result + ((postDate == null) ? 0 : postDate.hashCode());
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
			final Comment other = (Comment) obj;
			if (comment == null) {
				if (other.comment != null)
					return false;
			} else if (!comment.equals(other.comment))
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			if (postDate == null) {
				if (other.postDate != null)
					return false;
			} else if (!postDate.equals(other.postDate))
				return false;
			return true;
		}

		public String toString() {
		      return  "Name: '" +getName() +"' Comment: '" +getComment();
		}	
}
