package view;

import model.ERole;
import model.User;
import service.UserService;

import java.util.List;
import java.util.Scanner;

public class LoginView {
    public static Scanner scanner = new Scanner(System.in);
    private UserService userService;


    public LoginView() {
        userService = new UserService();
    }

    public void launcher() {
        System.out.println("                               ╔══════════════════════════════════════════════════════════╗");
        System.out.println("                               ║                                                          ║");
        System.out.println("                               ║                                                          ║");
        System.out.println("                               ║                  ĐĂNG NHẬP VÀO HỆ THỐNG                  ║");
        System.out.println("                               ║                                                          ║");
        System.out.println("                               ║                                                          ║");
        System.out.println("                               ╚══════════════════════════════════════════════════════════╝");
        boolean actionLogin;
        do {
            actionLogin = false;
            System.out.println("Nhập tên tài khoản");
        String username = scanner.nextLine();
////            String username = "leo123";
//            String username = "admin";
            System.out.println("Nhập mật khẩu");
        String password = scanner.nextLine();
////        String password = "123123";
//            String password = "admin";

            User user = loginUser(username, password);
            if (user != null) {
                if (user.getERole() == ERole.MANAGER) {
                    ManagerView.launcher();
                } else {
                    StaffView.launcher();
                }
            } else {
                System.out.println("Tên đăng nhập hoặc mật khẩu không đúng, vui lòng nhập lại");
                actionLogin = true;
            }
        } while (actionLogin);
    }

    public User loginUser(String username, String password) {
        List<User> allUsers = userService.findAllUsers();
        for (User user : allUsers) {
            if (username.equals(user.getUsername())) {
                if (password.equals(user.getPassword())) {
                    return user;
                }
            }
        }
        return null;
    }
}
