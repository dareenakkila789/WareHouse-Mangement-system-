import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

public class ProductDataPrinter {

    private static final String FILE_NAME = "products.ser";

    public static void printProductData() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            List<Product> productList = (List<Product>) ois.readObject();

            System.out.println("Product data in the file:");

            if (productList.isEmpty()) {
                System.out.println("No products found.");
            } else {
                for (Product product : productList) {
                    System.out.println(product);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        printProductData();
    }
}
