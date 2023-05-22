package service;

import model.Order;
import model.OrderItem;
import utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderItemService {


//    private final String ORDER_ITEM_PATH = "D:\\CaseStudy_Module_2\\SalesManagement\\src\\main\\data\\orderItem.csv";
    private final String ORDER_ITEM_PATH = "./data/orderItem.csv";
    public String getPath(){
        return this.ORDER_ITEM_PATH;
    }
    public List<OrderItem> findAllOrderItems(){
        return FileUtils.readFromFile(ORDER_ITEM_PATH, OrderItem.class);
    }
    public  OrderItem findOrderItemById(Order order, long idOrderItem){
        List<OrderItem> orderItems = order.getOrderItems();
        for (OrderItem orderItem: orderItems){
            if (orderItem.getIdOrderItem() == idOrderItem){
                return orderItem;
            }
        }
        return null;
    }

    public List<OrderItem> findAllOrderItemsByOrderId(long idOrder){
        List<OrderItem> list = findAllOrderItems();

        List<OrderItem> result = new ArrayList<>();
        for (OrderItem item : list) {
            if (item.getIdOrder() == idOrder) {
                result.add(item);
            }
        }
        return result;
    }
    public List<OrderItem> removeOrderItemByIdOrder(List<OrderItem> orderItems, long idOrder){

        List<OrderItem> result = new ArrayList<>();
        for (OrderItem item : orderItems) {
            if (item.getIdOrder() != idOrder) {
                result.add(item);
            }
        }
        return result;
    }
    public List<OrderItem> removeOrderItem(OrderItem orderItem){
        List<OrderItem> orderItems = findAllOrderItems();


        List<OrderItem> resultSave = new ArrayList<>();
        for (OrderItem item : orderItems) {
            if (item.getIdOrderItem() != orderItem.getIdOrderItem()) {
                resultSave.add(item);
            }
        }

        List<OrderItem> resultOrders = new ArrayList<>();
        for (OrderItem item : orderItems) {
            if (item.getIdOrder() == orderItem.getIdOrder()) {
                resultOrders.add(item);
            }
        }

        List<OrderItem> result = new ArrayList<>();
        for (OrderItem item : resultOrders) {
            if (item.getIdOrderItem() != orderItem.getIdOrderItem()) {
                result.add(item);
            }
        }


        FileUtils.writeToFile(ORDER_ITEM_PATH, resultSave);
        return result;
    }



    public void saveOrderItemByOrder(Order order) {
        List<OrderItem> orderItems = findAllOrderItems();
        orderItems.addAll(order.getOrderItems());

        FileUtils.writeToFile(ORDER_ITEM_PATH, orderItems);
    }


/*
* //        List<Order> orders = orderService.findAllOrder();
//        for (Order item: orders){
//            for (OrderItem odItem: orderItems){
//                item.setTotal(odItem.getQuantity()*odItem.getPrice());
//            }
//        }
//        FileUtils.writeToFile(orderService.getPath(), orders);*/

}
