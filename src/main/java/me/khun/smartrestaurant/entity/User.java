package me.khun.smartrestaurant.entity;

import lombok.Getter;
import lombok.Setter;
import me.khun.smartrestaurant.auth.Permission;

import javax.persistence.*;
import java.util.*;


@Entity
@Table(name = "user")
@Getter
@Setter
public class User extends BaseEntity<Long> {


    /*public enum UserRole {
        ADMIN, MANAGER , CASHIER

    }*/

    public enum UserStatus {
        ACTIVE, INACTIVE, RETIRED
    }

    public static final int MAX_FIRST_NAME_LENGTH = 20;
    public static final int MAX_LAST_NAME_LENGTH = 30;
    public static final int MAX_LOGIN_ID_LENGTH = 30;
    public static final int MAX_PASSWORD_LENGTH = 50;
    public static final int MAX_EMAIL_LENGTH = 100;
    public static final int MAX_PHONE_LENGTH = 15;
    public static final int MAX_ADDRESS_LENGTH = 255;
    public static final int MAX_NOTE_LENGTH = 255;

    public static final boolean FIRST_NAME_NULLABLE = false;
    public static final boolean LAST_NAME_NULLABLE = false;
    public static final boolean LOGIN_ID_NULLABLE = false;
    public static final boolean PASSWORD_NULLABLE = false;
    public static final boolean EMAIL_NULLABLE = true;
    public static final boolean PHONE_NULLABLE = false;
    public static final boolean ADDRESS_NULLABLE = false;
    public static final boolean PROFILE_IMAGE_PATH_NULLABLE = false;
    public static final boolean DATE_OF_BIRTH_NULLABLE = false;
    public static final boolean GENDER_NULLABLE = false;
    public static final boolean USER_ROLE_NULLABLE = false;
    public static final boolean ADDED_BY_NULLABLE = true;
    public static final boolean STATUS_NULLABLE = false;
    public static final boolean NOTE_NULLABLE = true;

    @Embedded
    private UserName userName;

    @Column(name = "login_id", length = MAX_LOGIN_ID_LENGTH, nullable = LOGIN_ID_NULLABLE, unique = true)
    private String loginId;

    @Column(name = "password", length = MAX_PASSWORD_LENGTH, nullable = PASSWORD_NULLABLE)
    private String password;

    @Column(name = "email", length = MAX_EMAIL_LENGTH, nullable = EMAIL_NULLABLE, unique = true)
    private String email;

    @Column(name = "phone", length = MAX_PHONE_LENGTH, nullable = PHONE_NULLABLE, unique = true)
    private String phone;

    @Column(name = "address", length = MAX_ADDRESS_LENGTH, nullable = ADDRESS_NULLABLE)
    private String address;

    @Column(name = "profile_image_path", columnDefinition = "TEXT", nullable = PROFILE_IMAGE_PATH_NULLABLE)
    private String profileImagePath;

    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_birth", nullable = DATE_OF_BIRTH_NULLABLE)
    private Date dateOfBirth;

    @Column(name = "gender", nullable = GENDER_NULLABLE)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt;

    @Column(name = "added_by", nullable = ADDED_BY_NULLABLE)
    private User addedBy;

    @Column(name = "role", nullable = USER_ROLE_NULLABLE)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "status", nullable = STATUS_NULLABLE)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "note", length = MAX_NOTE_LENGTH, nullable = NOTE_NULLABLE)
    private String note;


    public void setUserName(UserName userName) {
        this.userName = null == userName ? null : new UserName(userName.getFirstName(), userName.getLastName());
    }

    public UserName getUserName() {
        if (this.userName == null) return null;
        return new UserName(this.userName.getFirstName(), this.userName.getLastName());
    }

    public void setDateOfBirth(Date date) {
        this.dateOfBirth = null == date ? null : (Date) date.clone();
    }

    public Date getDateOfBirth() {
        return null == dateOfBirth ? null : (Date) dateOfBirth.clone();
    }

    public void setCreatedAt(Date date) {
        this.createdAt = null == date ? null : (Date) date.clone();
    }

    public Date getCreatedAt() {
        return null == createdAt ? null : (Date) createdAt.clone();
    }


    public boolean hasAccessForAll(Permission... permissions) {
        return getRole().hasAccessForAll(permissions);
    }

    public User clone() {

        User userClone = new User();

        userClone.setId(getId());
        userClone.setUserName(getUserName());
        userClone.setGender(getGender());
        userClone.setDateOfBirth(getDateOfBirth());
        userClone.setLoginId(getLoginId());
        userClone.setPassword(getPassword());
        userClone.setProfileImagePath(getProfileImagePath());
        userClone.setPhone(getPhone());
        userClone.setEmail(getEmail());
        userClone.setAddress(getAddress());
        userClone.setCreatedAt(getCreatedAt());
        userClone.setNote(getNote());
        userClone.setStatus(getStatus());
        userClone.setRole(getRole());

        return userClone;
    }

    @Getter
    @Setter
    @Embeddable
    public static class UserName {

        @Column(name = "first_name", length = MAX_FIRST_NAME_LENGTH, nullable = FIRST_NAME_NULLABLE)
        private String firstName;

        @Column(name = "last_name", length = MAX_LAST_NAME_LENGTH, nullable = LAST_NAME_NULLABLE)
        private String lastName;

        public UserName() {}

        public UserName(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName;
        }
    }
}
