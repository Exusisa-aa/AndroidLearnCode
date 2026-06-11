package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.restaurant.common.Result;
import com.restaurant.entity.Category;
import com.restaurant.entity.Dish;
import com.restaurant.entity.DishFlavor;
import com.restaurant.service.CategoryService;
import com.restaurant.service.DishFlavorService;
import com.restaurant.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/common")
public class SeedController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @PostMapping("/seed")
    public Result<String> seed() {
        // Clear existing data (optional, but good for testing)
        // dishFlavorService.remove(new LambdaQueryWrapper<>());
        // dishService.remove(new LambdaQueryWrapper<>());
        // categoryService.remove(new LambdaQueryWrapper<>());

        // Only seed if empty
        if (categoryService.count() > 0) {
            return Result.success("Data already exists");
        }

        // 1. Create Categories
        List<Category> categories = new ArrayList<>();
        categories.add(createCategory("Recommended", 1, 1));
        categories.add(createCategory("Main Course", 1, 2));
        categories.add(createCategory("Appetizer", 1, 3));
        categories.add(createCategory("Dessert", 1, 4));
        categories.add(createCategory("Beverage", 1, 5));
        categoryService.saveBatch(categories);

        // 2. Create Dishes
        List<Dish> dishes = new ArrayList<>();
        List<DishFlavor> flavors = new ArrayList<>();

        // Recommended Dishes
        Long catId = categories.get(0).getId();
        dishes.add(createDish("Kung Pao Chicken", catId, 38.0, "Classic spicy chicken with peanuts", "kung_pao_chicken.jpg"));
        dishes.add(createDish("Mapo Tofu", catId, 22.0, "Spicy tofu with minced meat", "mapo_tofu.jpg"));
        dishes.add(createDish("Beef Noodle Soup", catId, 28.0, "Rich beef broth with noodles", "beef_noodle.jpg"));

        // Main Course
        catId = categories.get(1).getId();
        dishes.add(createDish("Sweet and Sour Pork", catId, 42.0, "Crispy pork with sweet sauce", "sweet_sour_pork.jpg"));
        dishes.add(createDish("Peking Duck", catId, 88.0, "Roasted duck with pancakes", "peking_duck.jpg"));
        dishes.add(createDish("Braised Pork Belly", catId, 45.0, "Slow cooked pork belly", "braised_pork.jpg"));
        dishes.add(createDish("Spicy Crayfish", catId, 98.0, "Popular spicy seafood dish", "crayfish.jpg"));
        
        // Appetizer
        catId = categories.get(2).getId();
        dishes.add(createDish("Spring Rolls", catId, 12.0, "Crispy vegetable rolls", "spring_rolls.jpg"));
        dishes.add(createDish("Dumplings", catId, 18.0, "Pork and cabbage dumplings", "dumplings.jpg"));
        dishes.add(createDish("Edamame", catId, 8.0, "Steamed soybeans with salt", "edamame.jpg"));

        // Dessert
        catId = categories.get(3).getId();
        dishes.add(createDish("Mango Pudding", catId, 15.0, "Fresh mango dessert", "mango_pudding.jpg"));
        dishes.add(createDish("Egg Tart", catId, 10.0, "Traditional flaky pastry", "egg_tart.jpg"));
        
        // Beverage
        catId = categories.get(4).getId();
        dishes.add(createDish("Iced Lemon Tea", catId, 12.0, "Refreshing tea with lemon", "lemon_tea.jpg"));
        dishes.add(createDish("Cola", catId, 5.0, "Classic soda", "cola.jpg"));
        dishes.add(createDish("Orange Juice", catId, 15.0, "Freshly squeezed", "orange_juice.jpg"));

        dishService.saveBatch(dishes);

        // Add Flavors to some dishes
        for (Dish dish : dishes) {
            if (dish.getName().contains("Spicy") || dish.getName().contains("Kung Pao") || dish.getName().contains("Mapo")) {
                flavors.add(createFlavor(dish.getId(), "Spiciness", "[\"Mild\",\"Medium\",\"Hot\"]"));
            }
            if (dish.getName().contains("Tea") || dish.getName().contains("Juice") || dish.getName().contains("Cola")) {
                flavors.add(createFlavor(dish.getId(), "Ice Level", "[\"No Ice\",\"Less Ice\",\"Normal Ice\"]"));
                flavors.add(createFlavor(dish.getId(), "Sugar Level", "[\"No Sugar\",\"Half Sugar\",\"Full Sugar\"]"));
            }
        }
        dishFlavorService.saveBatch(flavors);

        return Result.success("Data seeded successfully");
    }

    private Category createCategory(String name, Integer type, Integer sort) {
        Category category = new Category();
        category.setName(name);
        category.setType(type); // 1: Dish, 2: Setmeal
        category.setSort(sort);
        return category;
    }

    private Dish createDish(String name, Long categoryId, Double price, String description, String image) {
        Dish dish = new Dish();
        dish.setName(name);
        dish.setCategoryId(categoryId);
        dish.setPrice(new BigDecimal(price));
        dish.setDescription(description);
        dish.setImage(image); // In real app, this would be a file name or URL
        dish.setStatus(1); // On sale
        dish.setSort(1);
        return dish;
    }

    private DishFlavor createFlavor(Long dishId, String name, String value) {
        DishFlavor flavor = new DishFlavor();
        flavor.setDishId(dishId);
        flavor.setName(name);
        flavor.setValue(value);
        return flavor;
    }
}
