package model;

public enum ECategory {
    FOOD(1, "Đồ ăn"), DRINKS(2, "Đồ uống");

    private int id;
    private String name;

    ECategory(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public static ECategory getECategoryById(int idCategory) {
        for (ECategory eCategory : ECategory.values()) {
            if (eCategory.getId() == idCategory) {
                return eCategory;
            }
        }
        return null;
    }

    public static ECategory getECategoryByName(String name) {
        for (ECategory eCategory : ECategory.values()) {
            if (eCategory.toString().equals(name)) {
                return eCategory;
            }
        }
        return null;
    }
}
