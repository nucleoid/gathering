package statz.model.survey;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "response")
public class Response {
	
	@Id @GeneratedValue
    @Column(name = "id")
    private Integer id;
	
	@OneToMany
	private List<Answer> answers;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date", nullable = false, updatable = true)
    private Date date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Response ID: ");
		sb.append(this.getId());
		for(Answer answer : getAnswers()) {
			sb.append("Answer ID: ");
			sb.append(answer.getId());
			sb.append("Answer content: ");
			sb.append(answer.getContent());
			sb.append("\n");
			if(answer.getComment() != null) {
				sb.append("Answer comment: ");
				sb.append(answer.getComment());
				sb.append("\n");
			}		
		}
		sb.append("Answer Date: ");
		sb.append(this.getDate());
		return sb.toString();
	}
}
