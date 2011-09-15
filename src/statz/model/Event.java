package statz.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "event")
public class Event implements Serializable {

	private static final long serialVersionUID = -3208145418093306783L;

	@Id @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
	@Column(name="whentime", nullable = false, updatable = true)
    private Date whentime;

    @Column(name = "duration")
    private int duration;

    @Column(name = "facilitator", length = 50, nullable = false)
    private String facilitator;

	@Column(name = "location", length = 50, nullable = false)
    private String location;

    @Column(name = "name", length = 50, nullable = false)
    private String name;


    public Event() {}

    public Event(Date whentime, int duration, String facilitator, String location, String name) {
        this.whentime = whentime;
        this.duration = duration;
        this.facilitator = facilitator;
        this.location = location;
        this.name = name;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getWhentime() {
		return whentime;
	}

	public void setWhentime(Date whentime) {
		this.whentime = whentime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public int getHours() {
		BigDecimal hourRound = new BigDecimal(duration/60, new MathContext(0, RoundingMode.FLOOR));
		return hourRound.intValue();
	}

	public String getFacilitator() {
		return facilitator;
	}

	public void setFacilitator(String facilitator) {
		this.facilitator = facilitator;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + duration;
		result = prime * result
				+ ((facilitator == null) ? 0 : facilitator.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((whentime == null) ? 0 : whentime.hashCode());
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
		final Event other = (Event) obj;
		if (duration != other.duration)
			return false;
		if (facilitator == null) {
			if (other.facilitator != null)
				return false;
		} else if (!facilitator.equals(other.facilitator))
			return false;
		if (id != other.id)
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (whentime == null) {
			if (other.whentime != null)
				return false;
		} else if (!whentime.equals(other.whentime))
			return false;
		return true;
	}
    
	public String toString() {
      return  "When: '" + getWhentime() + "', " +
              "Duration: '" + getDuration() + "', " +
              "Facilitator: '" + getFacilitator() + "'" +
              "Location: '" +getLocation() +"'" +
              "Name: '" +getName() +"'";
  }
}
