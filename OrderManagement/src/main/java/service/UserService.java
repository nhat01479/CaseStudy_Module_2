package service;

import model.Food;
import model.User;
import utils.FileUtils;
import utils.ValidateUtils;

import java.util.Iterator;
import java.util.List;

import static view.UserView.scanner;

public class UserService {
//    private final String USER_PATH = "D:\\CaseStudy_Module_2\\SalesManagement\\src\\main\\data\\user.csv";
    private final String USER_PATH = "./data/user.csv";

    public List<User> findAllUsers() {
        return FileUtils.readFromFile(USER_PATH, User.class);
    }
    public void addUser(User user) {
        List<User> list = findAllUsers();
        list.add(user);
        FileUtils.writeToFile(USER_PATH, list);
    }
    public void removeUser() {
        List<User> list = findAllUsers();

        System.out.println("Nhập ID cần xoá: ");
        long idRemove = Long.parseLong(ValidateUtils.inputId());

        User userRemove = findUserById(idRemove);

        if (userRemove == null){
            System.out.println("Không tìm thấy");
        } else {
            System.out.println("User cần xoá");
            showUser(userRemove);
            for (User user : list) {
                if (idRemove == user.getIdStaff()) {
                    list.remove(user);
                    break;
                }
            }
            System.out.println("Xoá thành công");
        }
//
//
//
//
//
//
//
//        Iterator<User> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            if (idRemove == iterator.next().getIdStaff()) {
//                iterator.remove();
//            }
//        }
//        System.out.println("Xoá thành công");
        FileUtils.writeToFile(USER_PATH, list);
    }
    public void showUser(User user){
        System.out.printf("%-5s %-20s %-10s %-10s %-10s\n", "ID", "Name", "Username", "Password", "Chức vụ");
        System.out.printf("%-5s %-20s %-10s %-10s %-10s\n", user.getIdStaff(), user.getFullName(), user.getUsername(), user.getPassword(), user.getERole());
    }

    public User findUserById(long idStaff) {
        List<User> list = findAllUsers();
        for (User user : list) {
            if (user.getIdStaff() == idStaff) {
//                showUser(user);
                return user;
            }
        }
        return null;
    }

    public void editStaffById(long idStaff, User user) {
        List<User> list = findAllUsers();

        for (User s: list){
            if (s.getIdStaff() == idStaff){
                s.setFullName(user.getFullName());
                s.setUsername(user.getUsername());
                s.setPassword(user.getPassword());
                s.setERole(user.getERole());
            }
        }
        FileUtils.writeToFile(USER_PATH, list);
    }
}
