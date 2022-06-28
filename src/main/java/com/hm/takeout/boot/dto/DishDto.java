package com.hm.takeout.boot.dto;

import com.hm.takeout.boot.entity.Dish;
import com.hm.takeout.boot.entity.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
