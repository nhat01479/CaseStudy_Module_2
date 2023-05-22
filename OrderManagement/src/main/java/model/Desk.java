package model;

public class Desk implements IModel<Desk>{
    private long idDesk;
    private String name;

    @Override
    public String toString() {
        return String.format("%s,%s", this.idDesk, this.name);
    }

    @Override
    public void parseData(String line) {
        String[] items = line.split(",");
        this.setId(Long.parseLong(items[0]));
        this.setName(items[1]);
    }
    public long getIdDesk() {
        return idDesk;
    }

    public void setId(long idDesk) {
        this.idDesk = idDesk;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
