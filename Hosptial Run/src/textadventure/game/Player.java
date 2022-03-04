package textadventure.game;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class Player {
    private HashMap <String, Item> inventory;
    private int stamina;
    private int health;
    
    public Player () {
        inventory = new HashMap<>();
    }
    public int getHealth (){
        int health = 15;
        return health;
    }
    
    public void adjustHealth () {
		//if (.equals("go"))
        health --;
    }
    
     public int getStamina () {
        int stamina = 10;
        return stamina;
    }
    
    public  void adjustStamina () {
        //if (!inputLine.equals("go"))
        stamina --;
    }
  
    public void setItem (String name, Item item) {
        inventory.put (name, item);
    }
    public Item getItem (String key) {
        return inventory.get(key);
    }
    public Item removeItem(String key) {
        return inventory.remove(key);   
    }
    public String getInventoryString() {
        String returnString = "Player Inventory:";
        Set <String> keys = inventory.keySet();
        for (String item: keys) {
            returnString += " " + item;
        }
        return returnString;
    }
	public void setStamina(int stamina) {
		this.stamina = stamina;
	}
}
