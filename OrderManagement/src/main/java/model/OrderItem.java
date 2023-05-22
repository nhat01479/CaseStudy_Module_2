package model;

public class OrderItem implements IModel<OrderItem>{
    private long idOrderItem;
    private long idOrder;
    private long idFood;
    private int quantity;
    private float price;

    public OrderItem() {
    }

    public OrderItem(long idOrderItem, long idOrder, long idFood, int quantity, float price) {
        this.idOrderItem = idOrderItem;
        this.idOrder = idOrder;
        this.idFood = idFood;
        this.quantity = quantity;
        this.price = price;
    }
    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s",
                this.idOrderItem, this.idOrder, this.idFood, this.quantity, this.price);
    }
    @Override
    public void parseData(String line) {
        String[] items = line.split(",");

        this.idOrderItem = Long.parseLong(items[0]);
        this.idOrder = Long.parseLong(items[1]);
        this.idFood = Long.parseLong(items[2]);
        this.quantity = Integer.parseInt(items[3]);
        this.price = Float.parseFloat(items[4]);
    }

    public long getIdOrderItem() {
        return idOrderItem;
    }

    public void setIdOrderItem(long idOrderItem) {
        this.idOrderItem = idOrderItem;
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public long getIdFood() {
        return idFood;
    }

    public void setIdFood(long idFood) {
        this.idFood = idFood;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
