package hospital_database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "medicaments")
public class Medicament extends BaseEntity {
    @Column(length = 50, nullable = false)
    private String name;
    @ManyToMany(mappedBy = "medicaments")
    private Set<Diagnose> diagnoses;
    @ManyToMany(mappedBy = "prescriptions")
    private Set<Patient> patients;

    public Medicament() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Diagnose> getDiagnoses() {
        return diagnoses;
    }

    public void setDiagnoses(Set<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public Set<Patient> getPatients() {
        return patients;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
    }
}
