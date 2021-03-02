package net.minestom.codegen.entitytypes;

public class BurgerEntity {
    int id;
    String name;
    double width;
    double height;

    public BurgerEntity() {
    }

    public BurgerEntity(int id, String name, double width, double height) {
        this.id = id;
        this.name = name;
        this.width = width;
        this.height = height;
    }
}
