package me.khun.smartrestaurant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "meal", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "size"}))
@Getter
@Setter
public class Meal extends BaseEntity<Long> {

    public enum MealSize {
        SMALL, MEDIUM, LARGE
    }

    public enum MealStatus {
        ALL, ACTIVE, INACTIVE
    }

    public static final int MAX_NAME_LENGTH = 40;
    public static final int MAX_SHORT_NAME_LENGTH = 10;

    public static final boolean NAME_NULLABLE = false;
    public static final boolean SHORT_NAME_NULLABLE = true;
    public static final boolean UNIT_PRICE_NULLABLE = false;
    public static final boolean IMAGE_PATH_NULLABLE = true;
    public static final boolean SIZE_NULLABLE = false;
    public static final boolean STATUS_NULLABLE = false;
    public static final boolean CATEGORY_NULLABLE = false;
    public static final boolean ADDED_USER_NULLABLE = true;

    @Column(name = "name", length = MAX_NAME_LENGTH, nullable = NAME_NULLABLE)
    private String name;

    @Column(name = "short_name", length = MAX_SHORT_NAME_LENGTH, nullable = SHORT_NAME_NULLABLE)
    private String shortName;

    @Column(name = "unit_price", nullable = UNIT_PRICE_NULLABLE)
    private Double unitPrice;

    @Column(name = "image_path", columnDefinition = "TEXT", nullable = IMAGE_PATH_NULLABLE)
    private String imagePath;

    @Column(name = "size", nullable = SIZE_NULLABLE)
    @Enumerated(EnumType.STRING)
    private MealSize size;

    @ManyToOne
    @JoinColumn(name = "category", nullable = CATEGORY_NULLABLE)
    private MealCategory category;

    @Column(name = "status", nullable = STATUS_NULLABLE)
    @Enumerated(EnumType.STRING)
    private MealStatus status;

    @ManyToOne
    @JoinColumn(name = "added_by_id", nullable = ADDED_USER_NULLABLE)
    private User addedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition = "TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt;



    public void setCategory(MealCategory mealCategory) {
        this.category = null == mealCategory ? null : mealCategory.clone();
    }

    public MealCategory getCategory() {
        return null == category ? null : category.clone();
    }

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

    public Meal clone() {
        Meal mealClone = new Meal();

        mealClone.setId(getId());
        mealClone.setName(getName());
        mealClone.setShortName(getShortName());
        mealClone.setUnitPrice(getUnitPrice());
        mealClone.setImagePath(getImagePath());
        mealClone.setSize(getSize());
        mealClone.setCategory(getCategory());
        mealClone.setStatus(getStatus());
        mealClone.setAddedBy(getAddedBy());
        mealClone.setCreatedAt(getCreatedAt());

        return mealClone;
    }


}
