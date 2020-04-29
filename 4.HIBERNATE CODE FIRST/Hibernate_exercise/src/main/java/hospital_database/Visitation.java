package hospital_database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "visitations")
public class Visitation extends BaseEntity {
    @Column(nullable = false)
    private Date date;
    @Column(length = 300, nullable = false)
    private String comments;
    @ManyToOne(targetEntity = Patient.class)
    private Patient patient;

    public Visitation() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
