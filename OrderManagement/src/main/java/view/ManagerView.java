package view;

import utils.AppUtils;
import utils.ValidateUtils;

import java.util.Scanner;

public class ManagerView {
    public static Scanner scanner = new Scanner(System.in);

    public static FoodView foodView;
    public static OrderView orderView;
    public static UserView userView;

    public ManagerView() {
    }

    public static void launcher() {
        foodView = new FoodView();
        orderView = new OrderView();
        userView = new UserView();

        boolean checkAction = false;
        do {
//            checkAction = false;
            System.out.println("                               ╔══════════════════════════════════════════════╗");
            System.out.println("                               ║               Giao diện Quản lý              ║");
            System.out.println("                               ╠══════════════════════════════════════════════╣");
            System.out.println("                               ║           [1] Quản lý thực đơn               ║");
            System.out.println("                               ║           [2] Quản lý nhân viên              ║");
            System.out.println("                               ║           [3] Quản lý order                  ║");
            System.out.println("                               ║           [0] Thoát khỏi chương trình        ║");
            System.out.println("                               ╚══════════════════════════════════════════════╝");
            System.out.println("Nhập lựa chọn");
            int choice = -1;
            do {
                choice = Integer.parseInt(ValidateUtils.inputChoice());
                switch (choice) {
                    case 1:
                        foodView.launcher();
                        break;
                    case 2:
                        userView.launcher();
                        break;
                    case 3:
                        orderView.launcher();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Nhập sai, vui lòng nhập lại");
                }
            } while (choice < 0 || choice > 3);
            checkAction = AppUtils.checkExit();


        } while (checkAction);
    }
}
