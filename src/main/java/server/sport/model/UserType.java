package server.sport.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "user_type", schema = "mydb", catalog = "")
public class UserType {
    private int userTypeId;
    private String userName;
    private Collection<User> usersByUserTypeId;

    @Id
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
        if (userName != null ? !userName.equals(userType.userName) : userType.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userTypeId;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "userTypeByUserTypeId")
    public Collection<User> getUsersByUserTypeId() {
        return usersByUserTypeId;
    }

    public void setUsersByUserTypeId(Collection<User> usersByUserTypeId) {
        this.usersByUserTypeId = usersByUserTypeId;
    }
}
