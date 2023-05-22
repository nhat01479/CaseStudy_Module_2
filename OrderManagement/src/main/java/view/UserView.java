package view;

import model.ERole;
import model.User;
import service.UserService;
import utils.AppUtils;
import utils.ValidateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView {
    public static Scanner scanner = new Scanner(System.in);
    private UserService userService;

    public UserView() {
        this.userService = new UserService();
    }

    public void launcher() {
        boolean checkAction = false;
        do {

            System.out.println("                               ╔════════════════════════════════════════════╗");
            System.out.println("                               ║                 Quản lý user               ║");
            System.out.println("                               ╠════════════════════════════════════════════╣");
            System.out.println("                               ║           1. Xem danh sách user            ║");
            System.out.println("                               ║           2. Thêm user                     ║");
            System.out.println("                               ║           3. Xoá user                      ║");
            System.out.println("                               ║           4. Sửa user                      ║");
            System.out.println("                               ║           5. Tìm kiếm user                 ║");
            System.out.println("                               ║           0. Trở lại                       ║");
            System.out.println("                               ╚════════════════════════════════════════════╝");
            int choice = -1;
            do {
                System.out.println("Nhập lựa chọn của bạn");
                choice = Integer.parseInt(ValidateUtils.inputChoice());
                switch (choice) {
                    case 1:
                        showListStaff(userService.findAllUsers());
                        break;
                    case 2:
                        createUser();
                        break;
                    case 3:
                        userService.removeUser();
                        break;
                    case 4:
                        editStaffMenu();
                        break;
                    case 5:
                        findUserMenu();
                        break;
                    case 0:
                        break;
//                    System.exit(0);
                    default:
                        System.out.println("Lựa chọn không đúng, vui lòng nhập lại");
                }
            } while (choice < 0 || choice > 5);
            checkAction = AppUtils.checkExit();
//            System.out.println("Nhập 'Y' để tiếp tục hoặc 'N' để trở lại");
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

    private void editStaffMenu() {
        User user = inputIdStaff();
        if (user != null) {
            boolean checkAction;
            do {
                checkAction = false;
                System.out.println("Menu chỉnh sửa user");
                System.out.println("1. Sửa tên");
                System.out.println("2. Sửa username");
                System.out.println("3. Sửa password");
//                System.out.println("4. Sửa danh mục");
                System.out.println("0. Trở lại");
                int actionEdit = -1;
                do {
                    System.out.println("Nhập lựa chọn của bạn");
                    actionEdit = Integer.parseInt(ValidateUtils.inputChoice());
                    switch (actionEdit) {
                        case 1:
                            inputNameStaff(user);
                            userService.showUser(user);
                            break;
                        case 2:
                            inputUserName(user);
                            break;
                        case 3:
                            inputPassword(user);
                            break;
//                    case 4:
//                        inputECategory(staff);
//                        break;
                        case 0:
                            break;
                        default:
                            System.out.println("Lựa chọn không đúng. Vui lòng nhập lại!");
                    }
                } while (actionEdit < 0 || actionEdit > 3);
                checkAction = AppUtils.checkContinue();

//                System.out.println("Nhập 'Y' để tiếp tục hoặc 'N' để trở lại");
//                String choiceContinue = "";
//                do {
//                    choiceContinue = scanner.nextLine().toUpperCase();
//                    switch (choiceContinue) {
//                        case "Y":
//                            checkActionEdit = true;
//                            break;
//                        case "N":
//                            break;
//                        default:
//                            System.out.println("Lựa chọn không đúng, vui lòng nhập lại");
//
//                    }
//                } while (!choiceContinue.equals("Y") && !choiceContinue.equals("N"));

            } while (checkAction);
        }
    }

    private void inputPassword(User user) {
        String password, confirmPassword;
        do {
            System.out.println("Nhập password");
            password = scanner.nextLine();
            System.out.println("Xác nhận password");
            confirmPassword = scanner.nextLine();
            if (!password.equals(confirmPassword)) {
                System.out.println("Password không đúng, vui lòng nhập lại");
            }
        } while (!password.equals(confirmPassword));

        user.setUsername(password);
        userService.editStaffById(user.getIdStaff(), user);
    }

    private void inputUserName(User user) {
        System.out.println("Nhập username mới");
        String username = scanner.nextLine();

        user.setUsername(username);
        userService.editStaffById(user.getIdStaff(), user);
    }

    private void inputNameStaff(User user) {
        System.out.println("Nhập tên mới");
        String name = scanner.nextLine();

        user.setFullName(name);
        userService.editStaffById(user.getIdStaff(), user);
    }

    private void findUserMenu() {
        boolean checkAction = false;
        do {
            checkAction = false;
            System.out.println("Menu tìm kiếm");
            System.out.println("1. Tìm theo ID");
            System.out.println("2. Tìm theo tên");
            System.out.println("0. Trở lại");
            int choiceToFind = -1;
            do {
                System.out.println("Nhập lựa chọn của bạn");
                choiceToFind = Integer.parseInt(scanner.nextLine());
                switch (choiceToFind) {
                    case 1:
                        inputIdStaff();
                        break;
                    case 2:
                        showListStaff(findByName());
                        break;
                    case 0:
                        break;
                    default:
//                        checkAction = true;
                        System.out.println("Nhập sai, vui lòng nhập lại");
                }
            } while (choiceToFind < 0 || choiceToFind > 2);
            checkAction = AppUtils.checkContinue();


        } while (checkAction);
    }

    private List<User> findByName() {
        System.out.println("Nhập tên nhân viên cần tìm");
        String name = scanner.nextLine();

        List<User> list = userService.findAllUsers();
        List<User> listByName = new ArrayList<>();

        for (User user : list) {
            if (user.getFullName().toUpperCase().contains(name.toUpperCase())) {
                listByName.add(user);
            }
        }
        return listByName;
    }

    private User inputIdStaff() {
        User user;
        boolean checkEditValid = false;

        do {
            System.out.println("Nhập ID muốn tìm:");
            long idUser = Long.parseLong(ValidateUtils.inputId());
            user = userService.findUserById(idUser);
            if (user == null) {
                System.out.println("ID không hợp lệ");
                System.out.println("1. Nhập lại\n2. Trở lại");
                int choice;
                do {
                    choice = Integer.parseInt(ValidateUtils.inputChoice());
                    switch (choice) {
                        case 1:
                            checkEditValid = true;
                            break;
                        case 2:
                            checkEditValid = false;
                            break;
                        default:
                            System.out.println("Lựa chọn không đúng, vui lòng nhập lại");
                    }
                } while (choice < 1 || choice > 2);
            } else {
                checkEditValid = false;
            }

        } while (checkEditValid);
        return user;
    }


    private void createUser() {
        System.out.println("Tạo user");
        System.out.println("Nhập ID nhân viên");
        long idUser = Long.parseLong(ValidateUtils.inputId());
        System.out.println("Nhập tên nhân viên");
        String name = scanner.nextLine();
        System.out.println("Nhập tên đăng nhập");
        String username = ValidateUtils.inputUsername();
        String password, confirmPassword;
        do {
            System.out.println("Nhập mật khẩu");
            password = ValidateUtils.inputPassword();
            System.out.println("Xác nhận mật khẩu");
            confirmPassword = ValidateUtils.inputPassword();
            if (!password.equals(confirmPassword)) {
                System.out.println("Mật khẩu phải trùng nhau, vui lòng nhập lại");
            }
        } while (!password.equals(confirmPassword));

        System.out.println("Nhập chức vụ");
        System.out.println("1. MANAGER\n2. STAFF");
        ERole eRole = null;
        int choice = -1;
        do {
            choice = Integer.parseInt(ValidateUtils.inputChoice());
            switch (choice){
                case 1:
                    eRole = ERole.MANAGER;
                    break;
                case 2:
                    eRole = ERole.STAFF;
                    break;
                default:
                    System.out.println("Nhập sai, vui lòng nhập lại");
            }
        } while (choice < 1 || choice > 2);



        User newUser = new User(idUser, name, username, password, eRole);
        userService.addUser(newUser);


        System.out.println("Tạo user thành công");
        userService.showUser(newUser);
    }

    private void showListStaff(List<User> allUsers) {
        System.out.printf("%-5s %-20s %-10s %-10s %-10s\n", "ID", "Tên", "Tên đăng nhập", "Mật khẩu", "Chức vụ");
        for (User user : allUsers) {
            System.out.printf("%-5s %-20s %-10s %-10s %-10s\n", user.getIdStaff(), user.getFullName(), user.getUsername(), user.getPassword(), user.getERole());
        }
    }

}
