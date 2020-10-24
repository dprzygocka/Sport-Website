package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "responsibility")
public class Responsibility {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name ="responsibility_id")
    private long responsibilityId;

    @Column(name = "responsibility_name", nullable = false)
    private String responsibilityName;

    public Responsibility() {
    }

    public Responsibility(String responsibilityName) {
        this.responsibilityName = responsibilityName;
    }

    public long getResponsibilityId() {
        return responsibilityId;
    }

    public void setResponsibilityId(long responsibilityId) {
        this.responsibilityId = responsibilityId;
    }

    public String getResponsibilityName() {
        return responsibilityName;
    }

    public void setResponsibilityName(String responsibilityName) {
        this.responsibilityName = responsibilityName;
    }

    @Override
    public String toString() {
        return "Responsibility{" +
                "responsibilityId=" + responsibilityId +
                ", responsibilityName='" + responsibilityName + '\'' +
                '}';
    }
}
