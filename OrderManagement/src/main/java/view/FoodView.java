package view;

import model.ECategory;
import model.Food;
import service.FoodService;
import utils.AppUtils;
import utils.ValidateUtils;
import utils.FileUtils;

import java.util.*;

public class FoodView {
    private Scanner scanner = new Scanner(System.in);
    private FoodService foodService;

    public FoodView() {
        this.foodService = new FoodService();
    }

    public void launcher() {
        boolean checkAction = false;
        do {
            System.out.println("                               ╔══════════════════════════════════════════════╗");
            System.out.println("                               ║               Quản lý thực đơn               ║");
            System.out.println("                               ╠══════════════════════════════════════════════╣");
            System.out.println("                               ║             1. Xem thực đơn                  ║");
            System.out.println("                               ║             2. Thêm món ăn                   ║");
            System.out.println("                               ║             3. Xoá món ăn                    ║");
            System.out.println("                               ║             4. Sửa món ăn                    ║");
            System.out.println("                               ║             5. Sắp xếp món ăn                ║");
            System.out.println("                               ║             6. Tìm kiếm món ăn               ║");
            System.out.println("                               ║             0. Trở lại                       ║");
            System.out.println("                               ╚══════════════════════════════════════════════╝");


            int actionMenu = Integer.parseInt(scanner.nextLine());
            switch (actionMenu) {
                case 1:
                    showListFood(foodService.findAllFood());
                    break;
                case 2:
                    addFoodToMenu();
                    break;
                case 3:
                    foodService.removeFoodFromMenu();
                    break;
                case 4:
                    editFoodMenu();
                    break;
                case 5:
                    sortMenu(foodService.findAllFood());
                    break;
                case 6:
                    findFoodMenu();
                    break;
                case 0:
                    break;
//                    System.exit(0);
                default:
                    System.out.println("Lựa chọn không đúng, vui lòng nhập lại");
            }

            checkAction = AppUtils.checkContinue();
        } while (checkAction);
    }

    private void sortMenu(List<Food> lists) {
        boolean checkAction = true;
        do {
            System.out.println("                               ╔════════════════════════════════════════════════╗");
            System.out.println("                               ║                Menu sắp xếp                    ║");
            System.out.println("                               ╠════════════════════════════════════════════════╣");
            System.out.println("                               ║             1. Sắp xếp món theo ID             ║");
            System.out.println("                               ║             2. Sắp xếp món theo tên            ║");
            System.out.println("                               ║             3. Sắp xếp món theo giá            ║");
            System.out.println("                               ║             4. Sắp xếp món theo danh mục       ║");
            System.out.println("                               ║             0. Trở lại                         ║");
            System.out.println("                               ╚════════════════════════════════════════════════╝");
            System.out.println("Nhập lựa chọn: ");
            int choice = -1;
            do {
                choice = Integer.parseInt(ValidateUtils.inputChoice());
                switch (choice) {
                    case 1:
                        foodService.sortFoodByID(lists);
                        break;
                    case 2:
                        foodService.sortFoodByName(lists);
                        break;
                    case 3:
                        foodService.sortFoodByPrice(lists);
                        break;
                    case 4:
                        foodService.sortFoodByCategory(lists);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Nhập sai, vui lòng nhập lại");
                }
            } while (choice < 0 || choice > 4);
            FileUtils.writeToFile(foodService.getPath(),lists);
            showListFood(foodService.findAllFood());

            checkAction = AppUtils.checkContinue();

        } while (checkAction);
    }


    private void editFoodMenu() {
        Food food = findById();
        if (food == null) {
            System.out.println("Không tìm thấy");
        }
        if (food != null) {
            System.out.println("Thông tin món ăn: ");
            foodService.showFood(food);
            boolean checkAction;
            do {
                checkAction = false;
                System.out.println("Bạn muốn sửa thông tin gì:");
                System.out.println("1. Sửa tên");
                System.out.println("2. Sửa giá");
                System.out.println("3. Sửa danh mục");
                System.out.println("0. Trở lại");
                System.out.println("Nhập lựa chọn");
                int choice;
                do {
                    choice = Integer.parseInt(ValidateUtils.inputChoice());
                    switch (choice) {
                        case 1:
                            inputNameFood(food);
                            showFoodAfterEdit(food);
                            break;
                        case 2:
                            inputPriceFood(food);
                            showFoodAfterEdit(food);
                            break;
                        case 3:
                            editECategory(food);
                            showFoodAfterEdit(food);
                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Lựa chọn không đúng. Vui lòng nhập lại!");
                    }
                } while (choice < 0 || choice > 3);

                checkAction = AppUtils.checkContinue();

            } while (checkAction);
        }
    }
    private void showFoodAfterEdit(Food food){
        System.out.println("Thông tin món ăn: ");
        foodService.showFood(food);
    }

    private long inputIdFood() {
//        Food food = null;
//        boolean checkEditValid;
//
//        do {
//            try {
//                checkEditValid = false;
                System.out.println("Nhập ID muốn tìm:");
                long idFoodToFind = Long.parseLong(ValidateUtils.inputId());
//                food = foodService.findFoodById(idFood);
//                if (food == null) {
//                    System.out.println("ID không hợp lệ, vui lòng nhập lại");
//                    checkEditValid = true;
//                }
//            } catch (NumberFormatException numberFormatException) {
//                System.out.println("ID không đúng định dạng, vui lòng nhập lại");
//                checkEditValid = true;
//            }
//        } while (checkEditValid);
//        return food;
        return idFoodToFind;
    }

    private void inputNameFood(Food food) {
//        System.out.println("Thông tin sản phẩm hiện tại");
//        System.out.println(food);

        System.out.println("Nhập tên sản phẩm");
        String name = scanner.nextLine();

        food.setNameFood(name);
        foodService.editFoodByID(food.getIdFood(), food);
    }

    private void inputPriceFood(Food food) {
//        System.out.println("Thông tin sản phẩm hiện tại");
//        System.out.println(food);

        System.out.println("Nhập giá mới");
        float price = Float.parseFloat(ValidateUtils.inputPrice());

        food.setPrice(price);
        foodService.editFoodByID(food.getIdFood(), food);
    }

    private ECategory inputECategory() {
//        System.out.println("Thông tin sản phẩm hiện tại");
//        System.out.println(food);
//        boolean check = false;
//        do {
            System.out.println("Nhập danh mục");
            System.out.println("1. Đồ ăn");
            System.out.println("2. Đồ uống");
            ECategory eCategory = null;
            int choiceECategory = -1;
            do {
                choiceECategory = Integer.parseInt(ValidateUtils.inputChoice());
                switch (choiceECategory) {
                    case 1:
                        eCategory = ECategory.getECategoryById(1);
                        break;
                    case 2:
                        eCategory = ECategory.getECategoryById(2);
                        break;
                    default:
                        System.out.println("Nhập không đúng, vui lòng nhập lại");
                }
            } while (choiceECategory != 1 && choiceECategory != 2);
//        }while (check);
        return eCategory;

    }

    private void editECategory(Food food){
        ECategory eCategory = inputECategory();

        food.setECategory(eCategory);
        foodService.editFoodByID(food.getIdFood(), food);
    }


    private void addFoodToMenu() {
        System.out.println("Thêm món ăn");
        System.out.println("Nhập tên");
        String name = scanner.nextLine();
        System.out.println("Nhập giá");
        float price = Float.parseFloat(ValidateUtils.inputPrice());
        ECategory eCategory = inputECategory();
//        System.out.println("Chọn danh mục");
//        for (ECategory eCategory : ECategory.values()) {
//            System.out.printf("Chọn %-5s %-10s\n", eCategory.getId(), eCategory.getName());
//        }
//        int idCategory = Integer.parseInt(scanner.nextLine());
//        ECategory eCategory = ECategory.getECategoryById(idCategory);

        Food food = new Food(System.currentTimeMillis() % 100000, name, price, eCategory);
        foodService.addFood(food);

        System.out.println("Món ăn vừa được thêm vào menu");
        foodService.showFood(food);
//        showMenu(foodService.findAllFood());
    }


    private void findFoodMenu() {
        boolean checkAction = false;

        do {
            checkAction = false;
            System.out.println("Menu tìm kiếm");
            System.out.println("1. Tìm theo ID");
            System.out.println("2. Tìm theo tên");
            System.out.println("3. Tìm theo giá");
            System.out.println("4. Tìm danh mục");
            System.out.println("0. Trở lại");
            System.out.println("Nhập lựa chọn");
            int choice;
            do {
                List<Food> foods;
                choice = Integer.parseInt(ValidateUtils.inputChoice());
                switch (choice){
                    case 1:
                        Food food = findById();
                        if (food == null)
                            System.out.println("Không tìm thấy");
                        else
                            foodService.showFood(food);
                        break;
                    case 2:
                        foods = findByName();
                        if (foods.isEmpty())
                            System.out.println("Không tìm thấy");
                        else
                            showListFood(foods);
                        break;
                    case 3:
                        foods = findByPrice();
                        if (foods.isEmpty())
                            System.out.println("Không tìm thấy");
                        else
                            showListFood(foods);
                        break;
                    case 4:
                        foods = findByCategory();
                        if (foods.isEmpty())
                            System.out.println("Không tìm thấy");
                        else
                            showListFood(foods);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Lựa chọn không đúng, vui lòng nhập lại");
                }
            }while (choice < 0 || choice > 4);

            checkAction = AppUtils.checkContinue();
        } while (checkAction);

    }
    private Food findById() {
        long id = inputIdFood();
        Food food = foodService.findFoodById(id);
        return food;
    }
    private List<Food> findByCategory() {
        ECategory eCategory = inputECategory();

        List<Food> list = foodService.findAllFood();
        List<Food> listByECategory = new ArrayList<>();

        for (Food food: list){
            if (eCategory == (food.getECategory())){
                listByECategory.add(food);
            }
        }

        return listByECategory;
    }

    private List<Food> findByPrice() {
        System.out.println("Nhập giá cần tìm");
        float price = Float.parseFloat(ValidateUtils.inputPrice());

        List<Food> list = foodService.findAllFood();
        List<Food> listByPrice = new ArrayList<>();

        for (Food food: list){
            if (food.getPrice() >= price){
                listByPrice.add(food);
            }
        }

        return listByPrice;
    }

    private List<Food> findByName() {
        System.out.println("Nhập tên món ăn cần tìm");
        String nameFood = scanner.nextLine();

        List<Food> list = foodService.findAllFood();
        List<Food> listByName = new ArrayList<>();

        for (Food food: list){
            if (food.getNameFood().toUpperCase().contains(nameFood.toUpperCase())){
                listByName.add(food);
            }
        }

        return listByName;
    }

    public static void showListFood(List<Food> foods) {
        System.out.printf("%-10s %-25s %10s    %-15s\n", "ID", "Tên món", "Giá", "Phân loại");
        System.out.println("---------------------------------------------------------------");
        for (Food food : foods) {
            System.out.printf("%-10s %-25s %10s    %-15s\n", food.getIdFood(), food.getNameFood(),  AppUtils.currencyFormat(food.getPrice()), food.getECategory().getName());
        }
        System.out.println("---------------------------------------------------------------");

    }

}
