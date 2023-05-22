package view;

import model.*;
import service.FoodService;
import service.OrderItemService;
import service.OrderService;
import service.UserService;
import utils.AppUtils;
import utils.ValidateUtils;
import utils.DateUtils;

import java.util.List;
import java.util.PropertyPermission;
import java.util.Scanner;

public class OrderView {
    private Scanner scanner = new Scanner(System.in);
    private OrderService oderService;
    private OrderItemService orderItemService;
    private FoodService foodService;
    private UserService userService;
    public OrderView() {
        orderItemService = new OrderItemService();
        oderService = new OrderService();
        foodService = new FoodService();
        userService = new UserService();
    }
    public void launcher() {
        boolean actionMenu;
        do{
            actionMenu = false;
            System.out.println("                               ╔══════════════════════════════════════════════╗");
            System.out.println("                               ║               Quản lý order                  ║");
            System.out.println("                               ╠══════════════════════════════════════════════╣");
            System.out.println("                               ║           1. Xem thực đơn                    ║");
            System.out.println("                               ║           2. Xem danh sách order             ║");
            System.out.println("                               ║           3. Xem chi tiết order              ║");
            System.out.println("                               ║           4. Tạo order mới                   ║");
            System.out.println("                               ║           5. Xoá order                       ║");
            System.out.println("                               ║           6. Sửa thông tin order             ║");
            System.out.println("                               ║           7. Xem doanh thu                   ║");
            System.out.println("                               ║           0. Trở lại                         ║");
            System.out.println("                               ╚══════════════════════════════════════════════╝");
            int choice;
            do {
                System.out.println("Nhập lựa chọn");
                choice = Integer.parseInt(ValidateUtils.inputChoice());
                switch (choice) {
                    case 1:
                        FoodView.showListFood(foodService.findAllFood());
                        break;
                    case 2:
                      showOrders(oderService.findAllOrder());
                        break;
                    case 3:
                        showOrderDetail();
                        break;
                    case 4:
                        FoodView.showListFood(foodService.findAllFood());
                        System.out.println("Tạo order");
                        showOrderDetail(oderService.createOrder());
                        break;
                    case 5:
                        System.out.println("Danh sách hoá đơn chưa thanh toán: ");
                        showOrders(oderService.getNotPaidOrderList());
                        oderService.removeOrder();
                        break;
                    case 6:
                        editOrderMenu();
                        break;
                    case 7:
                        showRevenueMenu();
//                    showRevenue(oderService.getPaidOrderList(), oderService.totalRevenue());
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Lựa chọn không đúng, vui lòng chọn lại");
                }
            } while (choice < 0 || choice > 7);

            actionMenu = AppUtils.checkContinue();
        }while (actionMenu);

    }

    private void showRevenueMenu() {
        boolean checkAction = false;
        do {
        System.out.println("Quản lý doanh thu");
        System.out.println("1. Xem doanh thu theo ngày");
        System.out.println("2. Xem doanh thu theo khoảng thời gian");
        System.out.println("0. Trở lại");
        System.out.println("Nhập lựa chọn: ");
        int choice = -1;
        do {
            choice = Integer.parseInt(ValidateUtils.inputChoice());
            switch (choice){
                case 1:
                    List<Order> listByDate = oderService.findOrderByDate();
                    showRevenue(listByDate, oderService.getRevenue(listByDate));
                    break;
                case 2:
                    List<Order> list = oderService.findOrderBetweenTwoDay();
                    showRevenue(list, oderService.getRevenue(list));
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Nhập sai, vui lòng nhập lại");
            }
        } while (choice < 0 || choice > 2);
        checkAction = AppUtils.checkContinue();

    } while (checkAction);

    }

    private void editOrderMenu() {
        System.out.println("Danh sách hoá đơn chưa thanh toán: ");
        showOrders(oderService.getNotPaidOrderList());

        Order order = findOrderById();
        if (order == null) System.out.println("Không tìm thấy hoá đơn");
        if (order != null) {
            showOrderDetail(order);

            boolean check = isPaid(order);
            if (check) {
                System.out.println("Hoá đơn đã thanh toán, không thể chỉnh sửa");
            } else {
                boolean checkContinue;
                do {
                    checkContinue = false;
                    System.out.println("Sửa Order");
                    System.out.println("1. Sửa chi tiết order");
                    System.out.println("2. Thêm món vào order");
                    System.out.println("3. Sửa trạng thái thanh toán");
                    System.out.println("0. Trở lại");
                    int choice = -1;
                    do {
                        System.out.println("Nhập lựa chọn");
                        choice = Integer.parseInt(ValidateUtils.inputChoice());
                        switch (choice){
                            case 1:
                                editOrderItemMenu(order);
//                            order.updateTotal();
//                            oderService.updateOrders(order);
                                break;
                            case 2:
//                            order = oderService.inputOrderItem(order);
                                order = oderService.inputOrderItem(order);
                                oderService.updateOrder(order);
                                break;
                            case 3:
                                inputeStatus(order);
                                oderService.updateOrder(order);
                                break;
                            case 0:
                                break;
                            default:
                                System.out.println("Nhập sai, vui lòng nhập lại");
                                checkContinue = true;
                        }
                    } while (choice < 0 || choice > 3);
                } while (checkContinue);
                System.out.println("Order sau khi sửa: ");
                showOrderDetail(order);

            }
        }
    }
    private void editOrderItemMenu(Order order){
        List<OrderItem> odItems = orderItemService.findAllOrderItemsByOrderId(order.getIdOrder());
        System.out.println("Danh sách các orderItem: ");
        for (OrderItem orderItem: odItems){
            System.out.println("idOrderItem: " + orderItem.getIdOrderItem() + " - Tên món: " + foodService.findFoodById(orderItem.getIdFood()).getNameFood() + " - Số lượng: " + orderItem.getQuantity() + " - Giá: " + orderItem.getPrice());
        }
        System.out.println("Nhập IdOrderItem");
        long idOrderItem = Long.parseLong(ValidateUtils.inputId());
        OrderItem orderItem = orderItemService.findOrderItemById(order, idOrderItem);
        if (orderItem != null){
            boolean check = false;
            do {
                check = false;
                System.out.println("Menu edit OrderItem");
                System.out.println("1. Sửa số lượng");
                System.out.println("2. Xoá món ăn khỏi order");
                System.out.println("0. Trở lại");
                System.out.println("Nhập lựa chọn");
                int choice = -1;
                do {
                    choice = Integer.parseInt(ValidateUtils.inputChoice());
                    switch (choice){
                        case 1:
                            oderService.inputQuantity(idOrderItem, orderItem);
                            order.updateTotal();
                            oderService.updateOrder(order);
                            break;
                        case 2:
                            List<OrderItem> orderItems = orderItemService.removeOrderItem(orderItem);
                            order.setOrderItems(orderItems);
                            order.updateTotal();
                            oderService.updateOrder(order);
//                            List<Order> orders = oderService.findAllOrder();
//                            for (Order od: orders){
//                                if (od.getIdOrder() == order.getIdOrder()){
//                                    od.setOrderItems(order.getOrderItems());
//                                    od.setTotal(order.getTotal());
//                                }
//                            }
//
//                            FileUtils.writeToFile(oderService.getPath(), orders);

                            break;
                        case 0:
                            break;
                        default:
                            System.out.println("Nhập sai, vui lòng nhập lại");
                            check = true;
                    }
                }while (choice < 0 || choice > 2);

            } while (check);
        }

    }

    private void inputIdDesk(Order order) {
        System.out.println("Nhập mã số bàn");
        int idDesk = Integer.parseInt(ValidateUtils.inputId());

        order.setIdDesk(idDesk);
        oderService.editOrderByID(order.getIdOrder(), order);
    }
    private void inputeStatus(Order order) {
        System.out.println("Nhập trạng thái thanh toán");
        EStatus eStatus = null;
        boolean check;
        do {
            check = false;

            int choice = -1;
            do {
                System.out.println("1. Đã thanh toán\n2. Chưa thanh toán");
                choice = Integer.parseInt(ValidateUtils.inputChoice());
                switch (choice){
                    case 1:
                        eStatus = EStatus.PAID;
                        break;
                    case 2:
                        eStatus = EStatus.UNPAID;
                        break;
                    default:
                        System.out.println("Nhập không đúng, vui lòng nhập lại");
//                        check = true;
                }
            } while (choice < 1 || choice > 2);
        }while (check);

        order.seteStatus(eStatus);
        oderService.editOrderByID(order.getIdOrder(), order);
    }

    private boolean isPaid(Order order){        //check đã thanh toán chưa
        return order.geteStatus() == EStatus.PAID;
    }
    private Order findOrderById(){
        List<Order> orders = oderService.findAllOrder();
        System.out.println("Nhập ID Order: ");
        long idOrder = Long.parseLong(ValidateUtils.inputId());
        for (Order order: orders){
            if (order.getIdOrder() == idOrder){
                return order;
            }
        }
        return null;
    }








    private void showOrderDetail() {
        System.out.println("Nhập mã hóa đơn cần xem:");
        long idOrder = Long.parseLong(ValidateUtils.inputId());
        Order order = oderService.findOrder(idOrder);

        if (order != null) {
            System.out.printf("%-25s - %-20s\n", "Mã hoá đơn: " + order.getIdOrder(), "Mã số bàn: " + order.getIdDesk());
            System.out.printf("%-25s - %-20s\n", "Mã nhân viên: " + order.getIdStaff(), "Tên nhân viên: " + userService.findUserById(order.getIdStaff()).getFullName());

            System.out.printf("%-25s%-10s%-15s%-15s\n","Tên món", "Số lượng", "Đơn giá","Thành tiền");
            System.out.println("-----------------------------------------------------------");

            for (OrderItem orderItem : order.getOrderItems()) {
                Food f = foodService.findFoodById(orderItem.getIdFood());
                System.out.printf("%-25s%-10s%-15s%-15s\n", f.getNameFood(), orderItem.getQuantity(), orderItem.getPrice(),  AppUtils.currencyFormat(orderItem.getQuantity()*orderItem.getPrice()));
            }
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%-50s%-15s\n", "Tổng tiền: ", AppUtils.currencyFormat(order.getTotal()));

        }else{
            System.out.println("Không tìm thấy order");
        }
    }
    private void showOrderDetail(Order order) {


            System.out.printf("Mã hoá đơn: %-20s - Mã số bàn: %-20s\n", order.getIdOrder(), order.getIdDesk());
            System.out.printf("Mã nhân viên: %-18s - Tên nhân viên: %-20s\n", order.getIdStaff(), userService.findUserById(order.getIdStaff()).getFullName());

            System.out.printf("%-25s%-10s%-15s%-15s\n","Tên món", "Số lượng", "Đơn giá","Thành tiền");
            System.out.println("-----------------------------------------------------------");

            for (OrderItem orderItem : order.getOrderItems()) {
                Food f = foodService.findFoodById(orderItem.getIdFood());
                System.out.printf("%-25s%-10s%-15s%-15s\n", f.getNameFood(), orderItem.getQuantity(), orderItem.getPrice(), AppUtils.currencyFormat(orderItem.getQuantity()*orderItem.getPrice()));
            }
            System.out.println("-----------------------------------------------------------");
            System.out.printf("%-50s%-15s\n", "Tổng tiền: ", AppUtils.currencyFormat(order.getTotal()));


    }

    private void showOrders(List<Order> allOrder) {
        System.out.printf("%-10s  %-20s  %-20s  %-20s  %-20s  %-20s\n", "ID Order", "ID Staff", "ID Desk", "TOTAL", "CREATE AT", "PAID STATUS");
        System.out.println("----------------------------------------------------------------------------------------------");

        for (Order order : allOrder) {
            System.out.printf("%-10s  %-20s  %-20s  %-20s  %-20s  %-20s\n",
                    order.getIdOrder(), order.getIdStaff(), order.getIdDesk(), AppUtils.currencyFormat(order.getTotal()), DateUtils.format(order.getCreateAt()), order.geteStatus().getName());
        }
        System.out.println("----------------------------------------------------------------------------------------------");

    }
    private void showRevenue(List<Order> orders, Float total) {
        System.out.printf("%-10s  %-15s  %-15s  %-20s  %-25s\n", "Mã hoá đơn", "Mã nhân viên", "Mã số bàn", "  Tổng cộng", "  Ngày tạo");
        System.out.println("----------------------------------------------------------------------------------------------");
        for (Order order : orders) {
            System.out.printf("%-10s  %-15s  %-15s  %-20s  %-25s\n",
                   "  " + order.getIdOrder(), "  " + order.getIdStaff(), "      " + order.getIdDesk(), AppUtils.currencyFormat(order.getTotal()), DateUtils.format(order.getCreateAt()));
        }
        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf("%-44s  %-25s\n", "TỔNG CỘNG: ", AppUtils.currencyFormat(total));

    }

}
