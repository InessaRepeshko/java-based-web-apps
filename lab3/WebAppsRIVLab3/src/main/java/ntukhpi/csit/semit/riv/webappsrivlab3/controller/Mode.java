package ntukhpi.csit.semit.riv.webappsrivlab3.controller;

public enum Mode {
    ADD("Add"),
    VIEW_FORM("View Form"),
    VIEW_TABLE("View Table"),
    EDIT("Edit"),
    DELETE("Delete");
    private final String value;

    private Mode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
