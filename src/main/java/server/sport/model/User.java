package server.sport.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "users", schema = "mydb", catalog = "")
public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer age;
    private String gender;
    private String phone;
    private Collection<Activity> activitiesByUserId;
    private Collection<ActivityStatus> activityStatusesByUserId;
    private Collection<Match> matchesByUserId;
    private Collection<UserResponsibility> userResponsibilitiesByUserId;
    private UserType userTypeByUserTypeId;
    private Team teamsByTeamId;

    @Id
    @Column(name = "user_id", nullable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "first_name", nullable = false, length = 45)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = false, length = 45)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 45)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "age", nullable = true)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Basic
    @Column(name = "gender", nullable = true, length = 6)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "phone", nullable = false, length = 20)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (userId != user.userId) return false;
        if (firstName != null ? !firstName.equals(user.firstName) : user.firstName != null) return false;
        if (lastName != null ? !lastName.equals(user.lastName) : user.lastName != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (age != null ? !age.equals(user.age) : user.age != null) return false;
        if (gender != null ? !gender.equals(user.gender) : user.gender != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (age != null ? age.hashCode() : 0);
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "usersByCreatorId")
    public Collection<Activity> getActivitiesByUserId() {
        return activitiesByUserId;
    }

    public void setActivitiesByUserId(Collection<Activity> activitiesByUserId) {
        this.activitiesByUserId = activitiesByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<ActivityStatus> getActivityStatusesByUserId() {
        return activityStatusesByUserId;
    }

    public void setActivityStatusesByUserId(Collection<ActivityStatus> activityStatusesByUserId) {
        this.activityStatusesByUserId = activityStatusesByUserId;
    }

    @OneToMany(mappedBy = "usersByPlayerOfTheMatches")
    public Collection<Match> getMatchesByUserId() {
        return matchesByUserId;
    }

    public void setMatchesByUserId(Collection<Match> matchesByUserId) {
        this.matchesByUserId = matchesByUserId;
    }

    @OneToMany(mappedBy = "usersByUserId")
    public Collection<UserResponsibility> getUserResponsibilitiesByUserId() {
        return userResponsibilitiesByUserId;
    }

    public void setUserResponsibilitiesByUserId(Collection<UserResponsibility> userResponsibilitiesByUserId) {
        this.userResponsibilitiesByUserId = userResponsibilitiesByUserId;
    }

    @ManyToOne
    @JoinColumn(name = "user_type_id", referencedColumnName = "user_type_id", nullable = false)
    public UserType getUserTypeByUserTypeId() {
        return userTypeByUserTypeId;
    }

    public void setUserTypeByUserTypeId(UserType userTypeByUserTypeId) {
        this.userTypeByUserTypeId = userTypeByUserTypeId;
    }

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "team_id", nullable = false)
    public Team getTeamsByTeamId() {
        return teamsByTeamId;
    }

    public void setTeamsByTeamId(Team teamsByTeamId) {
        this.teamsByTeamId = teamsByTeamId;
    }
}
