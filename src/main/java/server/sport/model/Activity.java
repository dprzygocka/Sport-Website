package server.sport.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "activities")
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name ="activity_id")
    private long activityId;

    @Column(name = "capacity", nullable = false)
    private long capacity;

    @Column(name = "description", nullable = false)
    private String description;

    @OneToOne(fetch = "user")
    @JoinColumn(name = "creator")
    private User creator;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="activity_status",
        joinColumns = {@JoinColumn(name = "activity_id")},
        inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users = new ArrayList<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="user_responsibilities",
            joinColumns = {@JoinColumn(name = "activity_id")},
            inverseJoinColumns = {@JoinColumn(name = "responsibility_id")})
    private List<Responsibility> responsibilities = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @OneToOne
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;

    @Column(name = "is_cancelled", nullable = false)
    private boolean isCancelled;
}
