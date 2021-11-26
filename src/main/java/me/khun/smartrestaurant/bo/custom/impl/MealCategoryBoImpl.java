package me.khun.smartrestaurant.bo.custom.impl;

import me.khun.smartrestaurant.application.exception.DuplicateEntryInsertionException;
import me.khun.smartrestaurant.application.exception.InvalidFieldException;
import me.khun.smartrestaurant.bo.BoType;
import me.khun.smartrestaurant.bo.custom.MealCategoryBo;
import me.khun.smartrestaurant.bo.custom.UserBo;
import me.khun.smartrestaurant.dao.custom.MealCategoryDao;
import me.khun.smartrestaurant.dao.custom.impl.DaoFactory;
import me.khun.smartrestaurant.dto.custom.MealCategoryDto;
import me.khun.smartrestaurant.dto.custom.UserDto;
import me.khun.smartrestaurant.dao.DaoType;
import me.khun.smartrestaurant.entity.MealCategory;
import me.khun.smartrestaurant.entity.validator.MealCategoryValidator;

import java.util.ArrayList;
import java.util.List;

class MealCategoryBoImpl implements MealCategoryBo {

    private final MealCategoryDao mealCategoryDao;

    MealCategoryBoImpl() {
        mealCategoryDao = (MealCategoryDao) DaoFactory.getInstance().getDao(DaoType.MEAL_CATEGORY);
    }

    @Override
    public boolean save(MealCategoryDto dto) throws InvalidFieldException {
        MealCategory entity = MealCategoryValidator.validate(dto.getEntity());
        return mealCategoryDao.save(entity);
    }

    @Override
    public boolean update(MealCategoryDto dto) throws InvalidFieldException {
        MealCategory entity = MealCategoryValidator.validate(dto.getEntity());
        return mealCategoryDao.update(entity);
    }

    @Override
    public boolean saveOrUpdate(MealCategoryDto dto) throws InvalidFieldException {
        MealCategory entity = MealCategoryValidator.validate(dto.getEntity());
        return mealCategoryDao.saveOrUpdate(entity);
    }

    @Override
    public boolean delete(MealCategoryDto dto) throws InvalidFieldException {
        MealCategory entity = MealCategoryValidator.validate(dto.getEntity());
        return mealCategoryDao.delete(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return mealCategoryDao.deleteById(id);
    }

    @Override
    public MealCategoryDto findById(Long id) {
        MealCategory mealCategory = mealCategoryDao.findById(id);
        return null == mealCategory ? null : new MealCategoryDto(mealCategory);
    }

    @Override
    public List<MealCategoryDto> findAll() {
        List<MealCategory> mealCategoryList = mealCategoryDao.findAll();
        return parseDtoList(mealCategoryList);
    }

    @Override
    public List<MealCategoryDto> findByKeyword(String keyword) {
        List<MealCategory> mealCategoryList = mealCategoryDao.findByKeyWord(keyword);
        return parseDtoList(mealCategoryList);
    }

    private List<MealCategoryDto> parseDtoList(List<MealCategory> mealCategoryList) {
        List<MealCategoryDto> dtoList = new ArrayList<>(mealCategoryList.size());

        for (MealCategory mealCategory : mealCategoryList)
            dtoList.add(new MealCategoryDto(mealCategory));

        return dtoList;
    }


    public static void main(String[] args) {
        UserBo userBo = (UserBo) BoFactory.getInstance().getBo(BoType.USER);
        UserDto userDto = userBo.findByLoginIdAndPassword("kkk", "kkk");
        System.out.println("User : " + userDto.getFirstName());
        System.out.println();

        MealCategoryDto mealCategoryDto = new MealCategoryDto();
        mealCategoryDto.setName("Hot Drink");
        mealCategoryDto.setStatus(MealCategory.MealCategoryStatus.ACTIVE);
        mealCategoryDto.setShortName("Hot");
//        mealCategoryDto.setImagePath("/home/khun/Pictures/smart-restaurant-images/bbq.jpeg");
        mealCategoryDto.setImagePath("/home/khun/Pictures/smart-restaurant-images/no-name.png");
        mealCategoryDto.setAddedBy(userDto);

        MealCategoryBo mealCategoryBo = (MealCategoryBo) BoFactory.getInstance().getBo(BoType.MEAL_CATEGORY);

        try {
            mealCategoryBo.save(mealCategoryDto);
        } catch (DuplicateEntryInsertionException e) {
            System.out.println(e.getMessage());
            System.out.println("Entry : " + e.getEntryValue());
        }

    }


}
