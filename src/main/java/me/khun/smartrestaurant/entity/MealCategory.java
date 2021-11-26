package me.khun.smartrestaurant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "meal_category")
@Getter
@Setter
public class MealCategory extends BaseEntity <Long> {

    public enum MealCategoryStatus {
        ALL, ACTIVE, INACTIVE
    }


    public static final int MAX_NAME_LENGTH = 40;
    public static final int MAX_SHORT_NAME_LENGTH = 10;

    public static final boolean NAME_UNIQUE = true;

    public static final boolean NAME_NULLABLE = false;
    public static final boolean SHORT_NAME_NULLABLE = true;
    public static final boolean STATUS_NULLABLE = false;
    public static final boolean IMAGE_PATH_NULLABLE = true;
    public static final boolean ADDED_USER_NULLABLE = true;


    @Column(name = "name", length = MAX_NAME_LENGTH, nullable = NAME_NULLABLE, unique = NAME_UNIQUE)
    private String name;

    @Column(name = "short_name", length = MAX_SHORT_NAME_LENGTH, nullable = SHORT_NAME_NULLABLE)
    private String shortName;

    @Column(name = "status", nullable = STATUS_NULLABLE)
    @Enumerated(EnumType.STRING)
    private MealCategoryStatus status;

    @Column(name = "image_path", nullable = IMAGE_PATH_NULLABLE)
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "added_by_id", nullable = ADDED_USER_NULLABLE)
    private User addedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt;

    public void setAddedBy(User user) {
        this.addedBy = null == user ? null : user.clone();
    }

    public User getAddedBy() {
        return null == addedBy ? null : addedBy.clone();
    }

    public void setCreatedAt(Date date) {
        this.createdAt = null == date ? null : (Date) date.clone();
    }

    public Date getCreatedAt() {
        return null == createdAt ? null : (Date) createdAt.clone();
    }

    public MealCategory clone() {
        MealCategory mealCategoryClone = new MealCategory();

        mealCategoryClone.setId(getId());
        mealCategoryClone.setName(getName());
        mealCategoryClone.setShortName(getShortName());
        mealCategoryClone.setStatus(getStatus());
        mealCategoryClone.setImagePath(getImagePath());
        mealCategoryClone.setAddedBy(getAddedBy());
        mealCategoryClone.setCreatedAt(getCreatedAt());

        return mealCategoryClone;
    }


}
