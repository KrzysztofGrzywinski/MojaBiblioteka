package utilities;


import database.models.Category;
import modelfx.CategoryFx;

public class CategoryConverter {
    public static CategoryFx convertToCategoryFX(Category category){
        CategoryFx categoryFx = new CategoryFx();
        categoryFx.setId(category.getId());
        categoryFx.setName(category.getName());
        return categoryFx;
    }
}
