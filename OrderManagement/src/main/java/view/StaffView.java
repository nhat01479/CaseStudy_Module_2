package view;

import utils.AppUtils;

import java.util.Scanner;

public class StaffView {
    private static Scanner scanner = new Scanner(System.in);
    public static FoodView foodView;
    public static OrderView orderView;

    public StaffView() {
    }

    public static void launcher(){
        foodView = new FoodView();
        orderView = new OrderView();

        boolean checkAction = false;
        do {
//            checkAction = false;
            System.out.println("                               ╔══════════════════════════════════════════════╗");
            System.out.println("                               ║              Giao diện nhân viên             ║");
            System.out.println("                               ╠══════════════════════════════════════════════╣");
            System.out.println("                               ║           [1] Quản lý thực đơn               ║");
            System.out.println("                               ║           [2] Quản lý order                  ║");
            System.out.println("                               ║           [0] Thoát khỏi chương trình        ║");
            System.out.println("                               ╚══════════════════════════════════════════════╝");
            System.out.println("Nhập lựa chọn");
            int choice = -1;
            do {
                choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        foodView.launcher();
                        break;
                    case 2:
                        orderView.launcher();
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Nhập sai, vui lòng nhập lại");
                }
            } while (choice < 0 || choice > 2);
            checkAction = AppUtils.checkContinue();
//            System.out.println("Nhập 'Y' để tiếp tục hoặc 'N' để thoát khỏi chương trình");
//            String choiceContinue = "";
//            do {
//                choiceContinue = scanner.nextLine().toUpperCase();
//                switch (choiceContinue) {
//                    case "Y":
//                        checkAction = true;
//                        break;
//                    case "N":
//                        checkAction = false;
//                        break;
//                    default:
//                        System.out.println("Lựa chọn không đúng, vui lòng nhập lại");
//
//                }
//            } while (!choiceContinue.equals("Y") && !choiceContinue.equals("N"));

        } while (checkAction);
    }
}
