package com.hm.takeout.boot.dto;


import com.hm.takeout.boot.entity.Setmeal;
import com.hm.takeout.boot.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
