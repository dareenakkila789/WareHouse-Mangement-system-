import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

	public class Product implements Serializable {
	    List<Object[]> products;

	    public Product() {
	        products = new ArrayList<>();
	    }

	    public void addProduct(String name, String company, int number, String category, String location) {
	        Object[] newProduct = {name, company, number, category, location};
	        products.add(newProduct);
	    }

		
	}