package me.khun.smartrestaurant.dto.custom;

import lombok.Getter;
import lombok.Setter;
import me.khun.smartrestaurant.dto.Dto;
import me.khun.smartrestaurant.entity.Meal;

import java.util.Date;

@Getter
@Setter
public class MealDto implements Dto<Meal> {

    private Long id;
    private String name;
    private String shortName;
    private Double unitPrice;
    private Meal.MealStatus status;
    private Meal.MealSize size;
    private MealCategoryDto category;
    private String imagePath;
    private UserDto addedBy;
    private Date createdAt;

    public MealDto(){}

    public MealDto(Meal meal) {
        this.id = meal.getId();
        this.name = meal.getName();
        this.shortName = meal.getShortName();
        this.unitPrice = meal.getUnitPrice();
        this.status = meal.getStatus();
        this.size = meal.getSize();
        this.category = new MealCategoryDto(meal.getCategory());
        this.imagePath = meal.getImagePath();
        this.addedBy = null == meal.getAddedBy() ? null : new UserDto(meal.getAddedBy());
        this.createdAt = null == meal.getCreatedAt() ? null : (Date) meal.getCreatedAt().clone();
    }

    public void setCreatedAt(Date date) {
        this.createdAt = null == date ? null : (Date) date.clone();
    }

    public Date getCreatedAt() {
        return null == this.createdAt ? null : (Date) this.createdAt.clone();
    }

    public void setCategory(MealCategoryDto mealCategoryDto) {
        this.category = null == mealCategoryDto ? null : mealCategoryDto.clone();
    }

    public MealCategoryDto getCategory() {
        return null == this.category ? null : this.category.clone();
    }

    @Override
    public Meal getEntity() {

        Meal meal = new Meal();

        meal.setId(getId());
        meal.setName(getName());
        meal.setShortName(getShortName());
        meal.setUnitPrice(getUnitPrice());
        meal.setStatus(getStatus());
        meal.setSize(getSize());
        meal.setCategory(null == getCategory() ? null : getCategory().getEntity());
        meal.setImagePath(getImagePath());
        meal.setAddedBy(null == getAddedBy() ? null : getAddedBy().getEntity());
        meal.setCreatedAt(getCreatedAt());

        return meal;
    }
}
