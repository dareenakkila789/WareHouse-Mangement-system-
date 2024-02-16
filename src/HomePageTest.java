import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.event.ActionEvent;

public class HomePageTest {

    @Test
    public void testActionPerformed_ViewProductsButton() {
        // Create a HomePage object
        HomePage homePage = new HomePage();

        // Perform action for the "View Products" button
        homePage.actionPerformed(new ActionEvent(homePage.viewProductsButton, ActionEvent.ACTION_PERFORMED, null));

        // Check if a new ViewProductsPage is visible
        assertTrue(homePage.viewProductsButton.isVisible());
    }

    @Test
    public void testActionPerformed_AddSaleButton() {
        // Create a HomePage object
        HomePage homePage = new HomePage();

        // Perform action for the "Add Sale" button
        homePage.actionPerformed(new ActionEvent(homePage.addSaleButton, ActionEvent.ACTION_PERFORMED, null));

        // Check if a new AddSalePage is visible
        assertTrue(homePage.addSaleButton.isVisible());
    }

    @Test
    public void testActionPerformed_LogoutButton() {
        // Create a HomePage object
        HomePage homePage = new HomePage();

        // Perform action for the "Logout" button
        homePage.actionPerformed(new ActionEvent(homePage.logoutButton, ActionEvent.ACTION_PERFORMED, null));

        // Check if a new Login page is visible and the current frame is disposed
        assertTrue(homePage.logoutButton.isVisible());
        assertEquals(false, homePage.isVisible());
    }

    // Add more test cases as needed based on your specific requirements
}