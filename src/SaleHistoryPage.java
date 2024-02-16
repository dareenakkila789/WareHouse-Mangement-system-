import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class SaleHistoryPage extends JFrame {

    public SaleHistoryPage(List<SaleHistory> saleHistoryList) {
        setTitle("Sale History");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        String[] columnNames = {"Category", "Sale Percentage", "Start Date", "End Date"};
        Object[][] data = new Object[saleHistoryList.size()][4];

        for (int i = 0; i < saleHistoryList.size(); i++) {
            SaleHistory saleHistory = saleHistoryList.get(i);
            data[i][0] = saleHistory.getCategory();
            data[i][1] = saleHistory.getSalePercentage();
            data[i][2] = saleHistory.getStartDate();
            data[i][3] = saleHistory.getEndDate();
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            new AddSalePage().setVisible(true);
            dispose();
        });
        add(backButton, BorderLayout.SOUTH);

        setSize(600, 400);
        setLocation(400, 200);
        setVisible(true);
    }

    public static void main(String[] args) {
        // Example usage to test the SaleHistoryPage
        List<SaleHistory> loadedHistory = SaleHistoryPage.deserializeHistory();
        SwingUtilities.invokeLater(() -> {
            new SaleHistoryPage(loadedHistory);
        });
    }

    public static void serializeHistory(SaleHistory saleHistory) {
        List<SaleHistory> historyList = deserializeHistory();

        if (historyList == null) {
            historyList = new ArrayList<>();
        }

        historyList.add(saleHistory);

        try (ObjectOutputStream outputStream = new ObjectOutputStream(
                new FileOutputStream("history.ser"))) {
            outputStream.writeObject(historyList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<SaleHistory> deserializeHistory() {
        List<SaleHistory> historyList;

        try (ObjectInputStream inputStream = new ObjectInputStream(
                new FileInputStream("history.ser"))) {
            historyList = (List<SaleHistory>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // If the file is not found or there is an issue deserializing, return an empty list
            e.printStackTrace();
            historyList = new ArrayList<>();
        }

        return historyList;
    }
}
