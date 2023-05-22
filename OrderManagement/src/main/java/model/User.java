package model;

public class User implements IModel<User>{
    private long idStaff;
    private String fullName;
    private String username;
    private String password;
    private ERole eRole;

    public User() {
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s", this.idStaff, this.fullName, this.username, this.password, this.eRole);
    }

    @Override
    public void parseData(String line) {
        String[] items = line.split(",");

        this.setIdStaff(Long.parseLong(items[0]));
        this.setFullName(items[1]);
        this.setUsername(items[2]);
        this.setPassword(items[3]);
        this.setERole(ERole.getERoleByName(items[4]));
    }

    public User(long idStaff, String fullName, String username, String password, ERole eRole) {
        this.idStaff = idStaff;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.eRole = eRole;
    }

    public long getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(long idStaff) {
        this.idStaff = idStaff;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ERole getERole() {
        return eRole;
    }

    public void setERole(ERole eRole) {
        this.eRole = eRole;
    }
}
