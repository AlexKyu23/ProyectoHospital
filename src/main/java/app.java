
import Clases.Admin.*;
import Clases.Admin.AdminView;

public class app {
    public static void main(String[] args) {
        Admin admin = new Admin("001", "SuperAdmin", "1234");

        AdminModel adminModel = new AdminModel();
        adminModel.setCurrent(admin);

        AdminView adminView = new AdminView();
        new AdminController(adminModel, adminView);

        adminView.setVisible(true);
    }
}
