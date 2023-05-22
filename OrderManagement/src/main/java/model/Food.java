package model;

public class Food implements IModel<Food> {
    private long idFood;
    private String nameFood;
//    private int quantity;
    private float price;
    private ECategory eCategory;

    public Food() {
    }

    public Food(long idFood, String nameFood, float price, ECategory eCategory) {
        this.idFood = idFood;
        this.nameFood = nameFood;
        this.price = price;
        this.eCategory = eCategory;
    }

    public long getIdFood() {
        return idFood;
    }

    public void setIdFood(long idFood) {
        this.idFood = idFood;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }



    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ECategory getECategory() {
        return eCategory;
    }

    public void setECategory(ECategory eCategory) {
        this.eCategory = eCategory;
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s", this.idFood, this.nameFood, this.price, this.eCategory);
    }

    @Override
    public void parseData(String line) {
        String[] items = line.split(",");
        long idFood = Long.parseLong(items[0]);
        float price = Float.parseFloat(items[2]);
        ECategory eCategory = ECategory.getECategoryByName(items[3]);
        Food food = new Food(idFood, items[1], price, eCategory);
        this.setIdFood(idFood);
        this.setNameFood(items[1]);
        this.setPrice(price);
        this.setECategory(eCategory);

    }

}
