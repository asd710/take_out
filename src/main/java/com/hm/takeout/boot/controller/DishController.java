package com.hm.takeout.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hm.takeout.boot.common.R;
import com.hm.takeout.boot.dto.DishDto;
import com.hm.takeout.boot.entity.Category;
import com.hm.takeout.boot.entity.Dish;
import com.hm.takeout.boot.entity.DishFlavor;
import com.hm.takeout.boot.service.CategoryService;
import com.hm.takeout.boot.service.DishFlavorService;
import com.hm.takeout.boot.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    DishFlavorService dishFlavorService;
    @Autowired
    DishService dishService;
    @Autowired
    CategoryService categoryService;

    /**
     * 分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page:{} pageSize:{}",page,pageSize);
        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,Dish::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);
        //执行分页查询
        dishService.page(pageInfo,queryWrapper);
        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item,dishDto);
            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);
            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(list);
        return R.success(dishDtoPage);
    }

    /**
     * 新增菜品
     * @param dishDto
     * @return
     * 只有接受到请求体中的json才加RequestBody注解
     */
    @PostMapping
    public R<String> insert(@RequestBody DishDto dishDto){
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);

        return R.success("新增菜品成功");
    }

    /**
     * 根据Id查询,修改时回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getById(@PathVariable Long id){
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * 修改菜品
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto){
        dishService.updateWithFlavor(dishDto);
        return R.success("修改成功");
    }

    /**
     * 批量停售
     * @param ids
     * @return
     */
    @PostMapping("/status/0")
    public R<String> updateStopStatusById(@RequestParam(value = "ids") List<Long> ids){
        for (Long id : ids) {
            System.out.println(id);
            dishService.updateStopStatusById(id);
        }

        return R.success("停售成功");
    }

    /**
     * 批量启售
     * @param ids
     * @return
     */
    @PostMapping("/status/1")
    public R<String> updateStartStatusById(@RequestParam(value = "ids") List<Long> ids){
        for (Long id : ids) {
            System.out.println(id);
            dishService.updateStartStatusById(id);
        }
        return R.success("启售成功");
    }

    /**
     * 批量删除菜品
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteById(@RequestParam(value = "ids") List<Long> ids){
        for (Long id : ids) {
            System.out.println(id);
            dishService.deleteById(id);
        }
        return R.success("删除成功");
    }

    /**
     * 在新增套餐时查询菜品
     * @param dish
     * @return
     */
//    @GetMapping("/list")
//    public R<List<Dish>> getcategoryById(Dish dish){
//        log.info(dish.toString());
//        Long categoryId = dish.getCategoryId();
//        List<Dish> dishList = dishService.getcategoryById(categoryId);
//        return R.success(dishList);
//    }
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        //构造查询条件
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null ,Dish::getCategoryId,dish.getCategoryId());
        //添加条件，查询状态为1（起售状态）的菜品
        queryWrapper.eq(Dish::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list = dishService.list(queryWrapper);

        List<DishDto> dishDtoList = list.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item,dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if(category != null){
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            //当前菜品的id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,dishId);
            //SQL:select * from dish_flavor where dish_id = ?
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());

        return R.success(dishDtoList);
    }

}
