package me.khun.smartrestaurant.dto.custom;

import lombok.Getter;
import lombok.Setter;
import me.khun.smartrestaurant.dto.Dto;
import me.khun.smartrestaurant.entity.MealCategory;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
public class MealCategoryDto implements Dto<MealCategory> {

    private Long id;
    private String name;
    private String shortName;
    private MealCategory.MealCategoryStatus status;
    private String imagePath;
    private Date createdAt;
    private UserDto addedBy;

    public MealCategoryDto(MealCategory mealCategory) {
        this.id = mealCategory.getId();
        this.name = mealCategory.getName();
        this.shortName = mealCategory.getShortName();
        this.status = mealCategory.getStatus();
        this.imagePath = mealCategory.getImagePath();
        this.createdAt = null == mealCategory.getCreatedAt() ? null : (Date) mealCategory.getCreatedAt().clone();
        this.addedBy = null == mealCategory.getAddedBy() ? null : new UserDto(mealCategory.getAddedBy());
    }

    public MealCategoryDto() {

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
    public MealCategory getEntity() {

        MealCategory mealCategory = new MealCategory();

        mealCategory.setId(getId());
        mealCategory.setName(getName());
        mealCategory.setShortName(getShortName());
        mealCategory.setStatus(getStatus());
        mealCategory.setImagePath(getImagePath());
        mealCategory.setCreatedAt(getCreatedAt());
        mealCategory.setAddedBy(null == getAddedBy() ? null : getAddedBy().getEntity());

        return mealCategory;
    }

    public MealCategoryDto clone() {
        MealCategoryDto dto = new MealCategoryDto();
        dto.setId(this.id);
        dto.setName(this.name);
        dto.setShortName(this.shortName);
        dto.setStatus(this.status);
        dto.setImagePath(this.imagePath);
        dto.setCreatedAt(this.createdAt);
        dto.setAddedBy(this.addedBy);
        return dto;
    }

}
