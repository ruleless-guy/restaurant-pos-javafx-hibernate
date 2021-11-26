package me.khun.smartrestaurant.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "restaurant_table")
@Getter
@Setter
public class RestaurantTable extends BaseEntity<Long> {

    public enum TableStatus {
        ALL, AVAILABLE, OCCUPIED, REMOVED
    }

    public static final int MAX_NAME_LENGTH = 20;

    public static final boolean NAME_UNIQUE = true;

    public static final boolean NAME_NULLABLE = false;
    public static final boolean SEAT_COUNT_NULLABLE = false;
    public static final boolean STATUS_NULLABLE = false;
    public static final boolean IMAGE_PATH_NULLABLE = true;
    public static final boolean ADDED_BY_NULLABLE = true;


    @Column(name = "name", length = MAX_NAME_LENGTH, nullable = NAME_NULLABLE, unique = NAME_UNIQUE)
    private String name;

    @Column(name = "seat_count", nullable = SEAT_COUNT_NULLABLE)
    private Integer seatCount;

    @Column(name = "status", nullable = STATUS_NULLABLE)
    @Enumerated(EnumType.STRING)
    private TableStatus status;

    @Column(name = "image_path", columnDefinition = "TEXT",nullable = IMAGE_PATH_NULLABLE)
    private String imagePath;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", columnDefinition="TIMESTAMP default CURRENT_TIMESTAMP")
    private Date createdAt;

    @Column(name = "added_by", nullable = ADDED_BY_NULLABLE)
    private User addedBy;

    public void setCreatedAt(Date date) {
        this.createdAt = null == date ? null : (Date) date.clone();
    }

    public Date getCreatedAt() {
        return null == this.createdAt ? null : (Date) this.createdAt.clone();
    }

    public void setAddedBy(User user) {
        this.addedBy = null == user ? null : user.clone();
    }

    public User getAddedBy() {
        return null == this.addedBy ? null : this.addedBy.clone();
    }


    public RestaurantTable clone() {
        RestaurantTable table = new RestaurantTable();

        table.setId(getId());
        table.setName(getName());
        table.setSeatCount(getSeatCount());
        table.setStatus(getStatus());
        table.setImagePath(getImagePath());
        table.setCreatedAt(null == getCreatedAt() ? null : (Date) getCreatedAt().clone());
        table.setAddedBy(null == getAddedBy() ? null : getAddedBy().clone());

        return table;
    }
}
