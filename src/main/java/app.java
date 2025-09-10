
import Clases.Admin.presentation.AdminController;
import Clases.Admin.presentation.AdminModel;
import Clases.Admin.presentation.AdminView;
import Clases.Admin.logic.Admin;

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
