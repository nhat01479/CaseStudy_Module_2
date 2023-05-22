package model;

import utils.DateUtils;

import java.util.Date;
import java.util.List;

import static model.EStatus.getStatusByName;

public class Order implements IModel<Order>{
    private long idOrder;
    private long idStaff;
    private int idDesk;

//    private Desk desk;
    private Date createAt;
    private float total;
    private EStatus eStatus;

    private List<OrderItem> orderItems;

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Order() {
    }

    public Order(long idOrder, long idStaff, int idDesk, Date createAt, float total, EStatus eStatus) {
        this.idOrder = idOrder;
        this.idStaff = idStaff;
        this.idDesk = idDesk;
        this.createAt = createAt;
        this.total = total;
        this.eStatus = eStatus;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,%s", this.idOrder, this.idStaff, this.idDesk, DateUtils.format(this.createAt), this.total, this.eStatus);
    }

    @Override
    public void parseData(String line) {
        String[] items = line.split(",");
        this.idOrder = Long.parseLong(items[0]);
        this.idStaff = Long.parseLong(items[1]);
        this.idDesk = Integer.parseInt(items[2]);
        this.createAt = DateUtils.parse(items[3]);
        this.total = Float.parseFloat(items[4]);
        this.eStatus = getStatusByName(items[5]);
    }
    public void updateTotal() {
        this.total = 0;
        for (OrderItem orderItem : orderItems) {
            this.total += orderItem.getPrice() * orderItem.getQuantity();
        }
    }

    public float updateTotal(List<OrderItem> orderItems) {
        float total = 0;
        for (OrderItem orderItem : orderItems) {
            total += orderItem.getPrice() * orderItem.getQuantity();
        }
        System.err.println(total);
        return total;
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public long getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(long idStaff) {
        this.idStaff = idStaff;
    }

    public int getIdDesk() {
        return idDesk;
    }

    public void setIdDesk(int idDesk) {
        this.idDesk = idDesk;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public EStatus geteStatus() {
        return eStatus;
    }

    public void seteStatus(EStatus eStatus) {
        this.eStatus = eStatus;
    }
}
