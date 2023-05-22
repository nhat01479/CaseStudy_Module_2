package utils;


import model.ERole;

import java.text.DecimalFormat;
import java.util.Scanner;

import static model.ERole.MANAGER;

public class AppUtils {
    public static Scanner scanner = new Scanner(System.in);

    public static boolean checkContinue() {

        do {
            System.out.println("Nhập 'Y' để tiếp tục hoặc 'N' để trở lại menu trước");
            String choiceContinue = scanner.nextLine().toUpperCase();
            switch (choiceContinue) {
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Lựa chọn không đúng, vui lòng nhập lại");
            }
        } while (true);
    }
    public static boolean checkExit() {

        do {
            System.out.println("Nhập 'Y' để tiếp tục hoặc 'N' để thoát khỏi chương trình");
            String choiceContinue = scanner.nextLine().toUpperCase();
            switch (choiceContinue) {
                case "Y":
                    return true;
                case "N":
                    return false;
                default:
                    System.out.println("Lựa chọn không đúng, vui lòng nhập lại");
            }
        } while (true);
    }
    public static String currencyFormat(float number){
        DecimalFormat decimalFormat = new DecimalFormat();
        String currency = decimalFormat.format(number);
        return String.format("%10s %-2s",currency, "đ");
    }
}
