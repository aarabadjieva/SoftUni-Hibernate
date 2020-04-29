package football_betting_database;

import javax.persistence.*;

@Entity
@Table(name = "result_predictions")
public class ResultPrediction extends BaseEntity{
    @Column(name = "prediction")
    @Enumerated(EnumType.STRING)
    private Prediction prediction;
}
