package server.sport.model;

import javax.persistence.*;

@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private int user_id;

    @Column(name="name")
    private String name;

    @Column(name="surname")
    private String surname;

    @Column(name="email")
    private String email;

    @Column(name="gender")
    private String gender;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name="user_type_id")
    private UserType userType;



    public User(String name, String surname, String email, String gender, Team team, UserType userType){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.gender = gender;
        this.team = team;
        this.userType = userType;
    }


}
