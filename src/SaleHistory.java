import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SaleHistory implements Serializable {
    private static final long serialVersionUID = 1L;

    private String category;
    private int salePercentage;
    private String startDate;
    private String endDate;

    public SaleHistory(String category, int salePercentage, Date startDate, Date endDate) {
        this.category = category;
        this.salePercentage = salePercentage;

        // Format dates as strings
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.startDate = dateFormat.format(startDate);
        this.endDate = dateFormat.format(endDate);
    }

    // Add getters and setters as needed
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSalePercentage() {
        return salePercentage;
    }

    public void setSalePercentage(int salePercentage) {
        this.salePercentage = salePercentage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
