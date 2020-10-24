package server.sport.model;

import javax.persistence.*;

@Entity
@Table(name="activity_status")
public class ActivityStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="activity_id")
    private int activityId;



}
