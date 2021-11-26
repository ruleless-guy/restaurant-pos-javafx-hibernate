package me.khun.smartrestaurant.application;

import me.khun.smartrestaurant.bo.BoType;
import me.khun.smartrestaurant.bo.custom.MealBo;
import me.khun.smartrestaurant.bo.custom.MealCategoryBo;
import me.khun.smartrestaurant.bo.custom.RestaurantTableBo;
import me.khun.smartrestaurant.bo.custom.UserBo;
import me.khun.smartrestaurant.bo.custom.impl.BoFactory;
import me.khun.smartrestaurant.dto.custom.MealCategoryDto;
import me.khun.smartrestaurant.dto.custom.MealDto;
import me.khun.smartrestaurant.dto.custom.RestaurantTableDto;
import me.khun.smartrestaurant.dto.custom.UserDto;
import me.khun.smartrestaurant.entity.*;
import me.khun.smartrestaurant.util.DateTimeUtil;

import java.util.Date;

public class DateInsert {

    public static void main(String[] args) {

    insertTable();

    }

    private static void insertData() {
        UserBo userBo = (UserBo) BoFactory.getInstance().getBo(BoType.USER);

        UserDto admin = new UserDto();
        admin.setFirstName("Khun");
        admin.setLastName("Pyae Phyo Aung");
        admin.setRole(UserRole.ADMIN);
        admin.setLoginId("kkk");
        admin.setPassword("kkk");
        admin.setStatus(User.UserStatus.ACTIVE);
        admin.setPhone("09254575210");
        admin.setAddress("Toungoo");
        admin.setGender(Gender.MALE);
        admin.setProfileImagePath("");
        admin.setDateOfBirth(new Date());

        UserDto manager = new UserDto();
        manager.setFirstName("Khun");
        manager.setLastName("Pyi Thar Hein");
        manager.setRole(UserRole.MANAGER);
        manager.setLoginId("ppp");
        manager.setPassword("ppp");
        manager.setStatus(User.UserStatus.ACTIVE);
        manager.setPhone("0925457521");
        manager.setAddress("Zayatkyi");
        manager.setGender(Gender.MALE);
        manager.setProfileImagePath("");
        manager.setDateOfBirth(new Date());

        UserDto cashier = new UserDto();
        cashier.setFirstName("Khun");
        cashier.setLastName("Tar Too");
        cashier.setRole(UserRole.CASHIER);
        cashier.setLoginId("aaa");
        cashier.setPassword("aaa");
        cashier.setStatus(User.UserStatus.ACTIVE);
        cashier.setPhone("092545752");
        cashier.setAddress("Yangon");
        cashier.setGender(Gender.MALE);
        cashier.setProfileImagePath("");
        cashier.setDateOfBirth(new Date());


        userBo.save(admin);
        userBo.save(manager);
        userBo.save(cashier);

        MealCategoryBo mealCategoryBo = (MealCategoryBo) BoFactory.getInstance().getBo(BoType.MEAL_CATEGORY);

        MealCategoryDto mealCategoryDto1 = new MealCategoryDto();
        mealCategoryDto1.setName("Cold Drink");
        mealCategoryDto1.setShortName("CD");
        mealCategoryDto1.setStatus(MealCategory.MealCategoryStatus.ACTIVE);
        mealCategoryDto1.setAddedBy(userBo.findById(1L));

        MealCategoryDto mealCategoryDto2 = new MealCategoryDto();
        mealCategoryDto2.setName("Hot Drink");
        mealCategoryDto2.setShortName("HD");
        mealCategoryDto2.setStatus(MealCategory.MealCategoryStatus.ACTIVE);
        mealCategoryDto2.setAddedBy(userBo.findById(2L));

        mealCategoryBo.save(mealCategoryDto1);
        mealCategoryBo.saveOrUpdate(mealCategoryDto2);


        MealBo mealBo = (MealBo) BoFactory.getInstance().getBo(BoType.MEAL);

        MealDto mealDto1 = new MealDto();
        mealDto1.setName("Ice Coffee");
        mealDto1.setUnitPrice(1000d);
        mealDto1.setSize(Meal.MealSize.MEDIUM);
        mealDto1.setStatus(Meal.MealStatus.ACTIVE);
        mealDto1.setCategory(mealCategoryBo.findById(1L));
        mealDto1.setAddedBy(userBo.findById(1L));

        MealDto mealDto2 = new MealDto();
        mealDto2.setName("Super Coffee");
        mealDto2.setUnitPrice(400d);
        mealDto2.setSize(Meal.MealSize.SMALL);
        mealDto2.setStatus(Meal.MealStatus.ACTIVE);
        mealDto2.setCategory(mealCategoryBo.findById(2L));
        mealDto2.setAddedBy(userBo.findById(2L));

        mealBo.save(mealDto1);
        mealBo.save(mealDto2);




    }

    private static void insertTable() {
        RestaurantTableBo bo = (RestaurantTableBo) BoFactory.getInstance().getBo(BoType.RESTAURANT_TABLE);

        RestaurantTableDto table1 = new RestaurantTableDto();
        table1.setName("Table 1");
        table1.setSeatCount(5);
        table1.setStatus(RestaurantTable.TableStatus.AVAILABLE);

        RestaurantTableDto table2 = new RestaurantTableDto();
        table2.setName("Table 2");
        table2.setSeatCount(5);
        table2.setStatus(RestaurantTable.TableStatus.AVAILABLE);

        RestaurantTableDto table3 = new RestaurantTableDto();
        table3.setName("Table 3");
        table3.setSeatCount(5);
        table3.setStatus(RestaurantTable.TableStatus.AVAILABLE);

        RestaurantTableDto table4 = new RestaurantTableDto();
        table4.setName("Table 4");
        table4.setSeatCount(5);
        table4.setStatus(RestaurantTable.TableStatus.AVAILABLE);

        bo.saveOrUpdate(table1);
        bo.saveOrUpdate(table2);
        bo.saveOrUpdate(table3);
        bo.saveOrUpdate(table4);
    }
}
