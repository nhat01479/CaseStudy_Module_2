package model;

public enum ERole {
    MANAGER, STAFF;
    public static ERole getERoleByName(String name) {
        for (ERole eRole : ERole.values()) {
            if (eRole.toString().equals(name)) {
                return eRole;
            }
        }
        return null;
    }
}
