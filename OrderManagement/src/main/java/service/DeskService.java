package service;

import model.Desk;
import model.Order;
import model.User;
import utils.FileUtils;

import java.util.*;

public class DeskService {


//    private final String DESK_PATH = "D:\\CaseStudy_Module_2\\SalesManagement\\src\\main\\data\\desk.csv";
    private final String DESK_PATH = "./data/desk.csv";

    public List<Desk> findAllDesks() {
        return FileUtils.readFromFile(DESK_PATH, Desk.class);
    }

    public List<Desk> findAllEmptyDesk(List<Order> notPaidOrders) {

        List<Desk> desks = findAllDesks();

        List<Desk> emptyDesks = new ArrayList<>();

        for (Desk desk : desks) {
            boolean check = false;
            for (Order order : notPaidOrders) {
                if (order.getIdDesk() == desk.getIdDesk()) {
                    check = true;
                    break;
                }
            }
            if (!check){
                emptyDesks.add(desk);
            }
        }
        return emptyDesks;

    }

    public void showEmptyDesk(List<Desk> emptyDesks) {
        System.out.println("Danh sách bàn đang trống");
        System.out.println("-------------------------");
        for (Desk desk : emptyDesks) {
            System.out.println(desk.getName());
        }
        System.out.println("-------------------------");
    }

}
