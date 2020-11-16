package server.sport.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user_types")
public class UserType {
    private int userTypeId;
    private String userName;
    private Collection<User> users;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    @Column(name = "user_type_id", nullable = false)
    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    @Basic
    @Column(name = "user_name", nullable = false, length = 45)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserType userType = (UserType) o;

        if (userTypeId != userType.userTypeId) return false;
        if (!Objects.equals(userName, userType.userName)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userTypeId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    @JsonBackReference
    @OneToMany(mappedBy = "userType")
    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public UserType(String userName, Collection<User> users) {
        this.userName = userName;
        this.users = users;
    }

    public UserType() {
    }
/*
    @Override
    public String toString() {
        return "UserType{" +
                "userTypeId=" + userTypeId +
                ", userName='" + userName + '\'' +
                ", users=" + users +
                '}';
    }

 */

    public UserType(int userTypeId, String userName, Collection<User> users) {
        this.userTypeId = userTypeId;
        this.userName = userName;
        this.users = users;
    }
}
