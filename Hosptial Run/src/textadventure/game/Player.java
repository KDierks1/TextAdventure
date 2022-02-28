package textadventure.game;

import java.util.HashMap;
import java.util.Set;

public class Player {
	private HashMap <String, Item> inventory;
	private int stamina;
	private int health;
    
    public Player () {
        inventory = new HashMap<>();
    }
    
    public int getHealth (){
		return health;
    }
    public void adjustHealth () {
		health --; //when a room is entered
    }
    public void getStamina () {
    stamina = 20;
     }
     public  void adjustStamina () {
     stamina --; //when any action is performed
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
}
