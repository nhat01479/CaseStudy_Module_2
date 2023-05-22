package service;

import model.Food;
import utils.AppUtils;
import utils.FileUtils;
import utils.ValidateUtils;

import java.text.Collator;
import java.util.*;

public class FoodService {
//    private final String FOOD_PATH = "D:\\CaseStudy_Module_2\\SalesManagement\\src\\main\\data\\food.csv";
    private final String FOOD_PATH = "./data/food.csv";

    public String getPath() {
        return this.FOOD_PATH;
    }

    private Scanner scanner = new Scanner(System.in);

    public List<Food> findAllFood() {
        return FileUtils.readFromFile(FOOD_PATH, Food.class);
    }

    public Food findFoodById(long idFood) {
        List<Food> list = findAllFood();
        for (Food food : list) {
            if (food.getIdFood() == idFood) {
//                showFood(food);
                return food;
            }
        }
        return null;
    }

    public void addFood(Food food) {
        List<Food> list = findAllFood();
        list.add(food);
        FileUtils.writeToFile(FOOD_PATH, list);
    }

    public void removeFoodFromMenu() {
        List<Food> list = findAllFood();

        System.out.println("Nhập ID cần xoá: ");
        long idRemove = Long.parseLong(ValidateUtils.inputId());

        Food foodRemove = findFoodById(idRemove);
//        Iterator<Food> iterator = list.iterator();
//        while (iterator.hasNext()) {
//            if (idRemove == iterator.next().getIdFood()) {
//                iterator.remove();
//            }
//        }
//        for (Food food: list){
//            if (idRemove == food.getIdFood()) {
//                System.out.println("Món ăn cần xoá");
//                showFood(food);
//                list.remove(food);
//                break;
//            }
//        }
        if (foodRemove == null) {
            System.out.println("Không tìm thấy");
        } else {
            System.out.println("Món ăn cần xoá");
            showFood(foodRemove);
            for (Food food : list) {
                if (idRemove == food.getIdFood()) {
                    System.out.println("Món ăn cần xoá");
                    list.remove(food);
                    break;
                }
            }
            System.out.println("Xoá thành công");
        }
        FileUtils.writeToFile(FOOD_PATH, list);

    }

    public void editFoodByID(long idFood, Food food) {
        List<Food> list = findAllFood();

        for (Food f : list) {
            if (f.getIdFood() == idFood) {
                f.setNameFood(food.getNameFood());
                f.setPrice(food.getPrice());
                f.setECategory(food.getECategory());
            }
        }
        FileUtils.writeToFile(FOOD_PATH, list);
    }

    public void sortFoodByID(List<Food> products) {
        products.sort(new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                return (int) (o1.getIdFood() - o2.getIdFood());
            }
        });
    }

    public void sortFoodByName(List<Food> products) {
        Collator collator = Collator.getInstance(Locale.forLanguageTag("vi-VN"));
//        Collator collator = Collator.getInstance(new Locale("vi", "VN"));
        products.sort(new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
//                return o1.getNameFood().compareTo(o2.getNameFood());
                return collator.compare(o1.getNameFood(), o2.getNameFood());
            }
        });
    }

    public void sortFoodByPrice(List<Food> products) {
        products.sort(new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                return (int) (o1.getPrice() - o2.getPrice());
            }
        });
    }

    //    private void sortProductByDate(List<Food> products) {
//        products.sort(new Comparator<Food>() {
//            @Override
//            public int compare(Food o1, Food o2) {
//                return (o1.getCreatAt().compareTo(o2.getCreatAt()));
//            }
//        });
//    }
    public void sortFoodByCategory(List<Food> products) {
        products.sort(new Comparator<Food>() {
            @Override
            public int compare(Food o1, Food o2) {
                return (o1.getECategory().compareTo(o2.getECategory()));
            }
        });
    }

    public void showFood(Food food) {
        System.out.printf("%-5s %-25s %-10s %-10s\n", "ID", "Name", "Giá", "Phân loại");
        System.out.println("------------------------------------------------------");
        System.out.printf("%-5s %-25s %-10s %-10s\n", food.getIdFood(), food.getNameFood(), food.getPrice(), food.getECategory().getName());
        System.out.println("------------------------------------------------------");

    }

}
