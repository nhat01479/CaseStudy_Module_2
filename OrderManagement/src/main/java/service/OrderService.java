package service;

import com.sun.security.jgss.GSSUtil;
import model.*;
import utils.AppUtils;
import utils.ValidateUtils;
import utils.DateUtils;
import utils.FileUtils;

import java.util.*;

public class OrderService {
    public static Scanner scanner = new Scanner(System.in);
    private final String ORDER_PATH = "./data/order.csv";
    private final String PAID_ORDER_PATH = "./data/paidorder.csv";

    public String getPath() {
        return this.ORDER_PATH;
    }

    private DeskService deskService;

    private OrderItemService orderItemService;
    private FoodService foodService;
    private UserService userService;


    public OrderService() {
        orderItemService = new OrderItemService();
        foodService = new FoodService();
        deskService = new DeskService();
        userService = new UserService();
    }

    public List<Order> findAllOrder() {
        List<Order> orders = FileUtils.readFromFile(ORDER_PATH, Order.class);
        for (Order order : orders) {
            List<OrderItem> orderItems = orderItemService.findAllOrderItemsByOrderId(order.getIdOrder());
            order.setOrderItems(orderItems);
        }
        return orders;
    }

    public void inputQuantity(long idOrderItem, OrderItem orderItem) {
        System.out.println("Nhập số lượng");
        int quantity = Integer.parseInt(ValidateUtils.inputQuantity());

        orderItem.setQuantity(quantity);
        editOrderItemById(idOrderItem, orderItem);


    }

    public void editOrderItemById(long idOrderItem, OrderItem orderItem) {
        List<OrderItem> orderItems = orderItemService.findAllOrderItems();
        for (OrderItem odItem : orderItems) {
            if (odItem.getIdOrderItem() == idOrderItem) {
                odItem.setQuantity(orderItem.getQuantity());

            }
        }

        FileUtils.writeToFile(orderItemService.getPath(), orderItems);
    }

    public void editOrderByID(long idOrder, Order order) {
        List<Order> orders = findAllOrder();

        for (Order o : orders) {
            if (o.getIdOrder() == idOrder) {
                o.setIdDesk(order.getIdDesk());
                o.seteStatus(order.geteStatus());
            }
        }
        FileUtils.writeToFile(ORDER_PATH, orders);
    }

    public void removeOrder() {

        System.out.println("Xoá order");
        System.out.println("Nhập ID order");
        long idOrder = Long.parseLong(ValidateUtils.inputId());
        List<Order> orders = findAllOrder();

        Order od = findOrder(idOrder);
        if (od == null) {
            System.out.println("Không tìm thấy order");
        } else {
            System.out.println("Thông tin order cần xoá: ");
            showOrderDetail(od);
            List<OrderItem> orderItems = orderItemService.findAllOrderItems();
            for (Order order : orders) {
                if (idOrder == order.getIdOrder()) {
                    if (order.geteStatus() == EStatus.UNPAID) {
                        List<OrderItem> result = orderItemService.removeOrderItemByIdOrder(orderItems, idOrder);
                        FileUtils.writeToFile(orderItemService.getPath(), result);
                        orders.remove(order);
                        break;
                    } else {
                        System.out.println("Hoá đơn đã thanh toán, không thể xoá");
                    }
                }
            }
        }


        FileUtils.writeToFile(ORDER_PATH, orders);
    }

    public void createOrder(Order order) {
        List<Order> orders = findAllOrder();

        orderItemService.saveOrderItemByOrder(order);
        orders.add(order);
        FileUtils.writeToFile(ORDER_PATH, orders);
    }

    public Order findOrder(long idOrder) {
        List<Order> list = findAllOrder();
        for (Order order : list) {
            if (order.getIdOrder() == idOrder) {
                return order;
            }
        }
        return null;
    }

    public void updateOrder(Order order) {
        List<Order> orders = findAllOrder();
        for (Order o : orders) {
            if (order.getIdOrder() == o.getIdOrder()) {
                o.setOrderItems(order.getOrderItems());
                o.setTotal(order.getTotal());
//                o.seteStatus(order.geteStatus());
            }
        }


        FileUtils.writeToFile(ORDER_PATH, orders);
    }

    private Food inputIdFood() {
        Food food;
        boolean checkEditValid;
        do {
            checkEditValid = false;
            System.out.println("Nhập ID món ăn:");
            long idFood = Long.parseLong(ValidateUtils.inputId());
            food = foodService.findFoodById(idFood);
            if (food == null) {
                System.out.println("Không tìm thấy");
                checkEditValid = true;
            }
        } while (checkEditValid);
        return food;
    }

    public Order inputOrderItem(Order order) {
        List<OrderItem> orderItems = orderItemService.findAllOrderItems();
        boolean checkAction;
        do {
            Food food = inputIdFood();
            System.out.println("Nhập số lượng: ");
            int quantity = Integer.parseInt(ValidateUtils.inputQuantity());

            OrderItem orderItem = new OrderItem(System.currentTimeMillis() % 1000,
                    order.getIdOrder(), food.getIdFood(), quantity, food.getPrice());
            order.getOrderItems().add(orderItem);
            orderItems.add(orderItem);

            System.out.println("Bạn có muốn thêm món không?");
            System.out.println("Nhập 1: Yes");
            System.out.println("Nhập 2: No");
            int actionAddOrderItem = Integer.parseInt(scanner.nextLine());
            switch (actionAddOrderItem) {
                case 1:
                    checkAction = true;
                    break;
                case 2:
                    checkAction = false;
                    break;
                default:
                    System.out.println("Nhập không đúng, vui lòng nhập lại");
                    checkAction = true;
            }
        } while (checkAction);


//        if (order.getOrderItems() == null) {
//            List<OrderItem> odItems = new ArrayList<>();
//            OrderItem orderItem = new OrderItem(System.currentTimeMillis() % 1000,
//                    order.getIdOrder(), food.getIdFood(), quantity, food.getPrice());
//            odItems.add(orderItem);
//
//            order.setOrderItems(odItems);
//        } else {
//            if (checkFoodExistOrder(order, food)) { // san pham da co trong order
//                for (OrderItem orderItem : order.getOrderItems()) {
//                    if (orderItem.getIdFood() == food.getIdFood()) {
//                        orderItem.setQuantity(quantity); //orderItem.getQuantity()+ quantity
//                    }
//                }
//            } else {


//            }
        order.updateTotal();
//        }
        FileUtils.writeToFile(orderItemService.getPath(), orderItems);
        return order;

    }

    public Order createOrder() {
        Order order = new Order();
        order.setIdOrder(System.currentTimeMillis() % 100000);

        boolean checkAction;
        do {
            checkAction = false;
            Food food = inputIdFood();
            System.out.println("Nhập số lượng: ");
            int quantity = Integer.parseInt(ValidateUtils.inputQuantity());

            if (order.getOrderItems() == null) {
                List<OrderItem> orderItems = new ArrayList<>();
                OrderItem orderItem = new OrderItem(System.currentTimeMillis() % 1000,
                        order.getIdOrder(), food.getIdFood(), quantity, food.getPrice());
                orderItems.add(orderItem);

                order.setOrderItems(orderItems);
            } else {
                if (checkFoodExistOrder(order, food)) { // san pham da co trong order
                    for (OrderItem orderItem : order.getOrderItems()) {
                        if (orderItem.getIdFood() == food.getIdFood()) {
                            orderItem.setQuantity(quantity);
                        }
                    }
                } else {
                    OrderItem orderItem = new OrderItem(System.currentTimeMillis() % 1000,
                            order.getIdOrder(), food.getIdFood(), quantity, food.getPrice());
                    order.getOrderItems().add(orderItem);
                }
            }
            int choice;
            do {
                System.out.println("Bạn có muốn thêm món không?");
                System.out.println("Nhập 1: Yes");
                System.out.println("Nhập 2: No");
                choice = Integer.parseInt(ValidateUtils.inputChoice());
                switch (choice) {
                    case 1:
                        checkAction = true;
                        break;
                    case 2:
                        checkAction = false;
                        break;
                    default:
                        System.out.println("Nhập không đúng, vui lòng nhập lại");
                        checkAction = true;
                }
            } while (choice < 1 || choice > 2);
        } while (checkAction);

        order.updateTotal();

        System.out.println("Nhập ID user: ");
        long idUser;
        boolean checkId = true;
        do {
            idUser = Long.parseLong(ValidateUtils.inputId());
            List<User> list = userService.findAllUsers();
            for (User user : list) {
                if (idUser == user.getIdStaff()) {
                    order.setIdStaff(idUser);
                    checkId = false;
                    break;
                }
            }
            if (order.getIdStaff() == 0){
                System.out.println("ID user không đúng");
            }

        } while (checkId);

//        order.setIdStaff(idUser);

        List<Order> orders = getNotPaidOrderList();
        List<Desk> emptyDesks = deskService.findAllEmptyDesk(orders);
        deskService.showEmptyDesk(emptyDesks);
        int idDesk;
        boolean check = true;
        do {
            System.out.println("Nhập id bàn: ");
            idDesk = Integer.parseInt(ValidateUtils.inputId());
            for (Desk desk : emptyDesks) {
                if (idDesk == desk.getIdDesk()) {
                    check = false;
                    break;
                }
            }
            System.out.println("Bàn đang được sử dụng hoặc không có, vui lòng nhập lại");

        } while (check);
        order.setIdDesk(idDesk);
        order.setCreateAt(new Date());

//        System.out.println("Chọn trạng thái thanh toán");
//        for (EStatus eStatus : EStatus.values()) {
//            System.out.printf("Chọn %-5s %-10s\n", eStatus.getId(), eStatus.getName());
//        }
//        int idEStatus = Integer.parseInt(scanner.nextLine());
//        EStatus eStatus = EStatus.getStatusById(idEStatus);
//        order.seteStatus(eStatus);
        EStatus eStatus = inputEStatus();
        order.seteStatus(eStatus);

        createOrder(order);
        System.out.println("Tạo order thành công");
        return order;

    }

    private EStatus inputEStatus() {
        System.out.println("Chọn trạng thái thanh toán: ");
        System.out.println("1. Đã thanh toán");
        System.out.println("2. Chưa thanh toán");
        EStatus eStatus = null;
        int choice;
        do {
            choice = Integer.parseInt(ValidateUtils.inputChoice());
            switch (choice) {
                case 1:
                    eStatus = EStatus.getStatusById(1);
                    break;
                case 2:
                    eStatus = EStatus.getStatusById(2);
                    break;
                default:
                    System.out.println("Nhập không đúng, vui lòng nhập lại");
            }
        } while (choice < 1 || choice > 2);
        return eStatus;
    }
    private void showOrderDetail(Order order){
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
    }

    private boolean checkFoodExistOrder(Order order, Food food) {
        if (order.getOrderItems() == null) {
            return false;
        } else {
            for (OrderItem orderItem : order.getOrderItems()) {
                if (orderItem.getIdFood() == food.getIdFood()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Float totalRevenue() {
        List<Order> result = getPaidOrderList();
        float total = 0;
        for (Order order : result) {
            if (order.geteStatus() == EStatus.PAID) {
                total += order.getTotal();
            }
        }
        return total;
    }

    public Float getRevenue(List<Order> orders) {
        float total = 0;
        for (Order order : orders) {
            if (order.geteStatus() == EStatus.PAID) {
                total += order.getTotal();
            }
        }
        return total;
    }

    public List<Order> getPaidOrderList() {
        List<Order> orders = findAllOrder();
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.geteStatus() == EStatus.PAID) {
                result.add(order);
            }
        }
        FileUtils.writeToFile(PAID_ORDER_PATH, result);
        return result;
    }

    public List<Order> getNotPaidOrderList() {
        List<Order> orders = findAllOrder();
        List<Order> result = new ArrayList<>();
        for (Order order : orders) {
            if (order.geteStatus() == EStatus.UNPAID) {
                result.add(order);
            }
        }
//        FileUtils.writeToFile(PAID_ORDER_PATH, result);
        return result;
    }


    public List<Order> findOrderByDate() {
        System.out.println("Nhập ngày cần xem");
        String date = ValidateUtils.checkDate();

        List<Order> listPaidOrder = getPaidOrderList();
        List<Order> listPaidOrderByDate = new ArrayList<>();

        for (Order order : listPaidOrder) {
            if (DateUtils.format(order.getCreateAt()).contains(date)) {
                listPaidOrderByDate.add(order);
            }
        }
        return listPaidOrderByDate;
    }

    public List<Order> findOrderBetweenTwoDay() {
//        System.out.println("Nhập ngày cần xem");
        System.out.println("Nhập ngày bắt đầu");
        String dayStart = ValidateUtils.checkDate();
        System.out.println("Nhập ngày kết thúc");
        String dayEnd = ValidateUtils.checkDate();
        ;
        Date start = DateUtils.parseIgnoreTime(dayStart + " 00:00");
        Date end = DateUtils.parseIgnoreTime(dayEnd + " 23:59");

        List<Order> listPaidOrder = getPaidOrderList();
        List<Order> list = new ArrayList<>();

        for (Order order : listPaidOrder) {

            if (DateUtils.isDateInRangeIgnoreTime(order.getCreateAt(), start, end)) {
                list.add(order);
            }
        }
        return list;
    }


}
