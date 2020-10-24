package server.sport.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="user_type")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_type_id")
    private int userTypeId;

    @Column(name="user_name")
    private String userName;

    @OneToMany(mappedBy= "userType",
              fetch = FetchType.EAGER,
              cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public UserType(String userName){
        this.userName = userName;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "userTypeId=" + userTypeId +
                ", userName='" + userName + '\'' +
                ", users=" + users.toString() +
                '}';
    }
}
