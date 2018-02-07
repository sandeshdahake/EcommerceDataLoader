import categoryList.ProductCategoryListLoader;
import productList.ProductListLoader;

import java.util.ArrayList;
import java.util.List;


public class Application {
    ProductListLoader productListLoader = null;
    public static void main(String args[]) {
        Application application = new Application();
        ProductCategoryListLoader productCategoryListLoader  = new ProductCategoryListLoader();
        productCategoryListLoader.load();
        List<String> list = new ArrayList<String>();
        list.add("mobile");
        list.add("storage-and-memory");
        for(String category : list){
            application.loadProductListByCategory(category); 
            application.loadProductFiltersByCateory(category);
        }
    }
    private void loadProductListByCategory(String category){
        if(productListLoader == null){
            productListLoader  = new ProductListLoader();
        }

        productListLoader.load(category);
    }

    private void loadProductFiltersByCateory(String category) {
    }


}
