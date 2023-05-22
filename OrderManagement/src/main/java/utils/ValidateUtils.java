package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    private static String REGEX;
    public static Scanner scanner = new Scanner(System.in);

    public static String inputPassword() {
        while (true) {
            try {
                System.out.println("Mật khẩu có độ dài từ 6 - 20 ký tự");
                String password = scanner.nextLine();
                if (password != null && !password.trim().isEmpty() && password.matches("^[a-zA-Z0-9]{6,20}$")) {
                    return password;
                } else {
                    System.out.println("Sai định dạng, vui lòng nhập lại!");
                }
            } catch (Exception e) {
                System.out.println("Sai định dạng, vui lòng nhập lại!");
            }
        }
    }

    public static String inputUsername() {
        while (true) {
            try {
                System.out.println("Tên đăng nhập bắt đầu bằng chữ cái, độ dài từ 6 - 20 ký tự");
                String username = scanner.nextLine();
                if (username != null && !username.trim().isEmpty() && username.matches("^[a-zA-Z][a-zA-Z0-9_-]{5,20}$")) {
                    return username;
                } else {
                    System.out.println("Sai định dạng, vui lòng nhập lại!");
                }
            } catch (Exception e) {
                System.out.println("Sai định dạng, vui lòng nhập lại!");
            }
        }
    }

    public static String inputId() {
        while (true) {
            try {
                String id = scanner.nextLine();
//                if (id != null && !id.trim().isEmpty() && id.matches("\\d*")) {
                if (id != null && !id.trim().isEmpty() && numberValidate(id)) {
                    int number = Integer.parseInt(id);
                    if (number > 0) {
                        return id;
                    } else {
                        System.out.println("Số nhập vào phải lớn hơn 0, vui lòng nhập lại!");
                    }
                } else {
                    System.out.println("Sai định dạng, vui lòng nhập lại!");
                }
            } catch (Exception e) {
                System.out.println("Sai định dạng, vui lòng nhập lại!");
            }
        }
    }

    public static String inputQuantity() {
        while (true) {
            try {
                String number = scanner.nextLine();
//                if (id != null && !id.trim().isEmpty() && id.matches("\\d*")) {
                if (number != null && !number.trim().isEmpty() && numberValidate(number)) {
                    int quantity = Integer.parseInt(number);
                    if (quantity > 0) {
                        return number;
                    } else {
                        System.out.println("Số lượng phải lớn hơn 0, vui lòng nhập lại!");
                    }
                } else {
                    System.out.println("Sai định dạng, vui lòng nhập lại!");
                }
            } catch (Exception e) {
                System.out.println("Sai định dạng, vui lòng nhập lại!");
            }
        }
    }

    public static String inputPrice() {
        while (true) {
            try {
                String price = scanner.nextLine();
                if (price != null && !price.trim().isEmpty() && numberValidate(price)) {
                    float number = Float.parseFloat(price);
                    if (number > 0) {
                        return price;
                    } else {
                        System.out.println("Giá phải lớn hơn 0, vui lòng nhập lại!");
                    }
                } else {
                    System.out.println("Sai định dạng, vui lòng nhập lại!");
                }
            } catch (Exception e) {
                System.out.println("Sai định dạng, vui lòng nhập lại!");
            }
        }
    }
    public static String inputChoice() {
        while (true) {
            try {
                String number = scanner.nextLine();
                if (number != null && !number.trim().isEmpty() && numberValidate(number)) {
                        return number;
                } else {
                    System.out.println("Sai định dạng, vui lòng nhập lại!");
                }
            } catch (Exception e) {
                System.out.println("Sai định dạng, vui lòng nhập lại!");
            }
        }
    }


    public static String checkDate() {
        while (true) {
            try {
                System.out.println("Ví dụ: 18-05-2023");
                String date = scanner.nextLine();
                if (date != null && !date.trim().isEmpty()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate dateCheck = LocalDate.parse(date, formatter);
                    LocalDate now = LocalDate.now();
                    return date;
                } else {
                    System.out.println("Sai định dạng, vui lòng nhập lại!");
                }
            } catch (Exception e) {
                System.out.println("Sai định dạng, vui lòng nhập lại!");
            }
        }
    }

    //    public static boolean checkDuplicateId(){}
    public static boolean numberValidate(String number) {
        REGEX = "\\d+";
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }



}

