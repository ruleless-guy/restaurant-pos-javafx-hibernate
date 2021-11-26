package me.khun.smartrestaurant.dto.custom;

import me.khun.smartrestaurant.dto.Dto;
import lombok.Getter;
import lombok.Setter;
import me.khun.smartrestaurant.entity.Gender;
import me.khun.smartrestaurant.entity.User;
import me.khun.smartrestaurant.entity.UserRole;

import java.util.Date;

@Getter
@Setter
public class UserDto implements Dto <User> {

    private Long id;
    private String firstName;
    private String lastName;
    private String loginId;
    private String password;
    private String email;
    private String phone;
    private String address;
    private String profileImagePath;
    private Date dateOfBirth;
    private Gender gender;
    private Date createdAt;
    private UserDto addedBy;
    private UserRole role;
    private User.UserStatus status;
    private String note;

    public UserDto() {}

    public UserDto(User user) {
        this.id = user.getId();
        this.firstName = user.getUserName().getFirstName();
        this.lastName = user.getUserName().getLastName();
        this.loginId = user.getLoginId();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.profileImagePath = user.getProfileImagePath();
        this.dateOfBirth = null == user.getDateOfBirth() ? null : (Date) user.getDateOfBirth().clone();
        this.gender = user.getGender();
        this.createdAt = null == user.getCreatedAt() ? null : (Date) user.getCreatedAt().clone();
        this.addedBy = null == user.getAddedBy() ? null : new UserDto(user.getAddedBy());
        this.role = user.getRole();
        this.status = user.getStatus();
        this.note = user.getNote();
    }

    public Date getDateOfBirth() {
        return null == this.dateOfBirth ? null : (Date) this.dateOfBirth.clone();
    }

    public Date getCreatedAt() {
        return null == this.createdAt ? null : (Date) this.createdAt.clone();
    }

    public void setAddedBy(UserDto userDto) {
        this.addedBy = null == userDto ? null : userDto.clone();
    }

    public UserDto getAddedBy() {
        return  null == this.addedBy ? null : this.addedBy.clone();
    }

    @Override
    public User getEntity() {

        User user = new User();
        user.setId(getId());
        user.setUserName(new User.UserName(getFirstName(), getLastName()));
        user.setLoginId(getLoginId());
        user.setPassword(getPassword());
        user.setEmail(getEmail());
        user.setPhone(getPhone());
        user.setAddress(getAddress());
        user.setProfileImagePath(getProfileImagePath());
        user.setDateOfBirth(getDateOfBirth());
        user.setGender(getGender());
        user.setRole(getRole());
        user.setCreatedAt(getCreatedAt());
        user.setAddedBy(null == getAddedBy() ? null : getAddedBy().getEntity());
        user.setStatus(getStatus());
        user.setNote(getNote());

        return user;
    }

    public User getEntity(Class<? extends User> tClass) {

        return null;
    }

    public UserDto clone() {
        UserDto userDto = new UserDto();

        userDto.setId(this.id);
        userDto.setFirstName(this.firstName);
        userDto.setLastName(this.lastName);
        userDto.setGender(this.getGender());
        userDto.setEmail(this.email);
        userDto.setPhone(this.phone);
        userDto.setLoginId(this.loginId);
        userDto.setPassword(this.password);
        userDto.setStatus(this.status);
        userDto.setRole(this.role);
        userDto.setProfileImagePath(this.profileImagePath);
        userDto.setAddress(this.address);
        userDto.setNote(this.note);
        userDto.setDateOfBirth(this.dateOfBirth);
        userDto.setCreatedAt(this.createdAt);
        userDto.setAddedBy(this.addedBy);

        return userDto;
    }

}
