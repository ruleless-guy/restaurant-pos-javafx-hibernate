package me.khun.smartrestaurant.bo.custom.impl;

import me.khun.smartrestaurant.application.ApplicationSettings;
import me.khun.smartrestaurant.application.exception.DuplicateEntryInsertionException;
import me.khun.smartrestaurant.bo.BoType;
import me.khun.smartrestaurant.bo.custom.MealBo;
import me.khun.smartrestaurant.bo.custom.MealCategoryBo;
import me.khun.smartrestaurant.bo.custom.UserBo;
import me.khun.smartrestaurant.dao.custom.MealDao;
import me.khun.smartrestaurant.dao.custom.impl.DaoFactory;
import me.khun.smartrestaurant.dto.custom.MealCategoryDto;
import me.khun.smartrestaurant.dto.custom.MealDto;
import me.khun.smartrestaurant.dto.custom.UserDto;
import me.khun.smartrestaurant.dao.DaoType;
import me.khun.smartrestaurant.entity.Meal;
import me.khun.smartrestaurant.entity.validator.MealValidator;
import me.khun.smartrestaurant.util.DecimalUtil;

import java.util.ArrayList;
import java.util.List;

class MealBoImpl implements MealBo {

    private final MealDao mealDao;

    MealBoImpl() {
        mealDao = (MealDao) DaoFactory.getInstance().getDao(DaoType.MEAL);
    }

    @Override
    public boolean save(MealDto dto) {
        Meal entity = MealValidator.validate(dto.getEntity());
        return mealDao.save(entity);
    }

    @Override
    public boolean update(MealDto dto) {
        Meal entity = MealValidator.validate(dto.getEntity());
        return mealDao.update(entity);
    }

    @Override
    public boolean saveOrUpdate(MealDto dto) {
        Meal entity = MealValidator.validate(dto.getEntity());
        return mealDao.saveOrUpdate(entity);
    }

    @Override
    public boolean delete(MealDto dto) {
        Meal entity = MealValidator.validate(dto.getEntity());
        return mealDao.delete(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return mealDao.deleteById(id);
    }

    @Override
    public MealDto findById(Long id) {
        Meal meal = mealDao.findById(id);
        return null == meal ? null : new MealDto(meal);
    }

    @Override
    public List<MealDto> findAll() {
        List<Meal> mealList = mealDao.findAll();
        return parseDtoList(mealList);
    }

    @Override
    public List<MealDto> findByKeyword(String keyword) {
        List<Meal> mealList = mealDao.findByKeyWord(keyword);
        return parseDtoList(mealList);
    }

    private List<MealDto> parseDtoList(List<Meal> mealList) {
        List<MealDto> dtoList = new ArrayList<>(mealList.size());


        for (Meal meal : mealList)
            dtoList.add(new MealDto(meal));

        return dtoList;
    }

    public static void main(String[] args) {
        MealBo mealBo = (MealBo) BoFactory.getInstance().getBo(BoType.MEAL);
        UserBo userBo = (UserBo) BoFactory.getInstance().getBo(BoType.USER);
        MealCategoryBo mealCategoryBo = (MealCategoryBo) BoFactory.getInstance().getBo(BoType.MEAL_CATEGORY);

        UserDto userDto = userBo.findByLoginIdAndPassword("kkk", "123");
        MealCategoryDto mealCategoryDto = mealCategoryBo.findById(1L);

        MealDto mealDto = new MealDto();
        mealDto.setName("Ice-coffee");
        mealDto.setShortName("IC");
        mealDto.setUnitPrice(1000d);
        mealDto.setSize(Meal.MealSize.MEDIUM);
        mealDto.setStatus(Meal.MealStatus.ACTIVE);
        mealDto.setAddedBy(userDto);
        mealDto.setCategory(mealCategoryDto);

        try {
            mealBo.save(mealDto);
        } catch (DuplicateEntryInsertionException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getEntryValue());
        }

    }
}
