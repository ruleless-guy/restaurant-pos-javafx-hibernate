package me.khun.smartrestaurant.dto.custom;

import lombok.Getter;
import lombok.Setter;
import me.khun.smartrestaurant.dto.Dto;
import me.khun.smartrestaurant.entity.RestaurantTable;

import java.util.Date;

@Getter
@Setter
public class RestaurantTableDto implements Dto<RestaurantTable> {

    private Long id;
    private String name;
    private Integer seatCount;
    private RestaurantTable.TableStatus status;
    private String imagePath;
    private Date createdAt;
    private UserDto addedBy;

    public RestaurantTableDto() {}

    public RestaurantTableDto(RestaurantTable table) {
        this.id = table.getId();
        this.name = table.getName();
        this.seatCount = table.getSeatCount();
        this.status = table.getStatus();
        this.imagePath = table.getImagePath();
        this.createdAt = null == table.getCreatedAt() ? null : (Date) table.getCreatedAt().clone();
        this.addedBy = null == table.getAddedBy() ? null : new UserDto(table.getAddedBy());
    }


    public void setCreatedAt(Date date) {
        this.createdAt = null == date ? null : (Date) date.clone();
    }

    public Date getCreatedAt() {
        return null == this.createdAt ? null : (Date) this.createdAt.clone();
    }

    public void setAddedBy(UserDto userDto) {
        this.addedBy = null == userDto ? null : userDto.clone();
    }

    public UserDto getAddedBy() {
        return null == this.addedBy ? null : this.addedBy.clone();
    }


    @Override
    public RestaurantTable getEntity() {
        RestaurantTable table = new RestaurantTable();

        table.setId(getId());
        table.setName(getName());
        table.setSeatCount(getSeatCount());
        table.setImagePath(getImagePath());
        table.setStatus(getStatus());
        table.setCreatedAt(null == getCreatedAt() ? null : (Date) getCreatedAt().clone());
        table.setAddedBy(null == getAddedBy() ? null : getAddedBy().getEntity());
        return table;
    }
}
