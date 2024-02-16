import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ProductTest {

    @Test
    public void testAddProduct() {
        // Create a Product object
        Product product = new Product();

        // Get the initial number of products
        int initialProductCount = product.products.size();

        // Add a new product
        product.addProduct("Laptop", "ABC Electronics", 123, "Electronics", "Office");

        // Get the updated number of products
        int updatedProductCount = product.products.size();

        // Check if the number of products increased after adding a new product
        assertEquals(initialProductCount + 1, updatedProductCount);

        // Check if the added product details are correct
        Object[] addedProduct = product.products.get(updatedProductCount - 1);
        assertEquals("Laptop", addedProduct[0]);
        assertEquals("ABC Electronics", addedProduct[1]);
        assertEquals(123, (int) addedProduct[2]);
        assertEquals("Electronics", addedProduct[3]);
        assertEquals("Office", addedProduct[4]);
    }

    @Test
    public void testProductInitialization() {
        // Create a Product object
        Product product = new Product();

        // Check if the products list is initialized
        assertTrue(product.products != null);
        assertEquals(0, product.products.size());
    }

}