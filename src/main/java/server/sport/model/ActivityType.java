package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activity_type")
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name ="activity_type_id")
    private long activityTypeId;

    @Column(name = "activity_type_name", nullable = false)
    private String activityTypeName;

    @OneToMany(mappedBy="activity",
    fetch=FetchType.EAGER,
    cascade = CascadeType.ALL)
    private List<Activity> activities = new ArrayList<>();

}
