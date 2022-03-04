package textadventure.game;

public class Item {
    private String name;
    private String description;

    int health;
    int stamina;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public String getName() {
        return name;
    }   
    public String getDescription() {
        return description;
    }   
    public void setHealth(int value) {
        health = 15;
    }
    public int getHealth() {
        return health;
    }
    public void setStamina (int value) { 
        stamina = 10;
    }
    public int getStamina () {
        return stamina;
    }
}