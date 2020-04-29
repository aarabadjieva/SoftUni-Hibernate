package hospital_database;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "diagnoses")
public class Diagnose extends BaseEntity{

    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 300, nullable = false)
    private String comments;
    @ManyToMany(mappedBy = "diagnoses")
    private Set<Patient> patients;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "diagnoses_medicaments",
    joinColumns = @JoinColumn(name = "disgnose_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "meicament_id", referencedColumnName = "id"))
    private Set<Medicament> medicaments;

    public Diagnose() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }

    public Set<Medicament> getMedicaments() {
        return medicaments;
    }

    public void setMedicaments(Set<Medicament> medicaments) {
        this.medicaments = medicaments;
    }
}
