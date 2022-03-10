package textadventure.game;

public class Game {
    private Parser parser;
    private Room currentRoom;
    private Player player;
    private CLS cls_var;
    private Room startingLobby;
    private Room OperatingRoom;
    private Room Closet;
    private Room Room107;
    private Room Lab;
    private Room nextRoom;
    private int health = 5;
    private int stamina = 5;
    private int lose = 1; 

    public Game() {
        parser = new Parser();
        player = new Player();
    }

    public void printTitle() {
        System.out.println (" _    _   ______   ______   ______  _____ _______  ______   _            ______   _    _   ______  ");
        System.out.println ("| |  | | / |  | \\ / |      | |  | \\  | |    | |   | |  | | | |          | |  | \\ | |  | | | |  \\ \\ ");
        System.out.println ("| |--| | | |  | | '------. | |__|_/  | |    | |   | |__| | | |   _      | |__| | | |  | | | |  | | ");
        System.out.println ("|_|  |_| \\_|__|_/  ____|_/ |_|      _|_|_   |_|   |_|  |_| |_|__|_|     |_|  \\_\\ \\_|__|_| |_|  |_| ");
        System.out.println ("");
        System.out.println ();
        System.out.println ();
        System.out.println ("Type anything to start");
    }

    public static void main (String[] args) {
        Game game = new Game();
        game.setUpGame();
        game.play();
    }

    public void printInformation() {
        System.out.println (currentRoom.getShortDescription());
        System.out.println (currentRoom.getExitString());
        System.out.println (currentRoom.getInventoryString());
        System.out.println (player.getInventoryString());
        System.out.println ("Health: " + health);
        System.out.println ("Stamina: " + stamina);
    }

    public void setUpGame() {
        startingLobby = new Room ("Lobby" , "Starting room that contains a key", "The starting point of the game. This room will only lead to Room 107, and key can be picked up and used to unlock something...");
        Room107 = new Room ("Room 107" , "A room in the hospital which can only access the laboratory", "Room 107 is the second accessible room in the game. Has no special actions in this room either. The southernmost point of all the rooms. This room only leads to the Laboratory");
        Lab = new Room ("Laboratory" , "Middle room of the hospital with a table of medicine", "The middle of the game with a table of medicine. One gives a health deduction and one gives a health increase. This leads into the operating room, waiting room and room 107");
        OperatingRoom = new Room ("Operating Room" , "Room that leads to a closet and back to the waiting room", "The room contains a knife that grants the player an attack increase. This knife does not have to be picked up. Accessed by walking through Laboratory");
        Closet = new Room ("Closet" , "Last room that is available in the game", "This room is the final room in the game and it gives access back to the laboratory and operatingRoom");

        startingLobby.setExit("Room107", Room107);
        Room107.setExit("Lab", Lab); 
        Lab.setExit("WaitingRoom" , OperatingRoom); 
        Lab.setExit("OperatingRoom" , OperatingRoom); 
        OperatingRoom.setExit("Closet", Closet);
        OperatingRoom.setExit("WaitingRoom", startingLobby);
        Closet.setExit("Laboratory" , Lab);
        Closet.setExit("OperatingRoom" , OperatingRoom);

        Item key = new Item ("key" , "This key gives access to the next room of the game.");
        Item medicine = new Item ("medicine" , "grants +7 health to the player");
        Item food = new Item ("food" , "grants +10 stamina to the player");

        startingLobby.setItem ("key", key);
        Lab.setItem ("medicine", medicine);
        Closet.setItem ("food", food);

        currentRoom = startingLobby;

        try {
            //cls_var.main(); 
        }catch(Exception e) {
            System.out.println(e); 
        }

        printTitle();
        play();
        
        printInformation();
    }

    public void play() {
        while(true && lose == 1) {            
            Command command = parser.getCommand();
            try {
                //cls_var.main(); 
            }catch(Exception e) {
                System.out.println(e); 
            }
            processCommand(command);
            printInformation();   
        }
    }

    public void processCommand (Command command) {
        String commandWord = command.getCommandWord().toLowerCase();
        switch (commandWord) {
            case "speak":
                System.out.println ("You wanted to speak this word, " + command.getSecondWord());
                break;
            case "go":
                goRoom(command);
                break;
            case "grab":
                grab(command);
                break;
            case "drop":
                drop(command);
                break;
            case "inspect":
                inspect(command);
                break;
            case "eat":
                eat(command);
                break;
                /*
            case "help speak":
            	System.out.println ("Gives the word you wanted to speak");
            	break;
            case "help go":
            	System.out.println ("Type in an exit after go to travel to a different room");
            	break;
            case "help grab":
            	System.out.println ("Type in grab followed by the item on the floor that you want to pick up");
            	break;
            case "help drop":
            	System.out.println ("Type in drop followed by the item on the floor you want to drop up");
            	break;
            case "help inspect":
            	System.out.println ("Type in inspect followed by the item you want to inspect");
            	break;
            case "help eat":
            	System.out.println ("Type in eat followed by the item you want to eat");
            	break;
            	*/
            case "help":
                System.out.println ("Commands: Speak, Go, Grab, Drop, Inspect and Eat");
                System.out.println ("For extra help, type in help followed by the command you are struggling with");
                break;
           
        }
     }

    public void eat (Command command) {
        String thingToEat;
        String itemToEat;
        if (!command.hasLine()) {
            thingToEat = command.getSecondWord() + command.getLine();   
        }
        else if (command.hasLine()) {
            thingToEat = command.getSecondWord() + command.getLine();       
        }
        itemToEat = command.getSecondWord();
        Item itemGrab = currentRoom.removeItem(itemToEat);
        health--;
        System.out.println (health);
    }

    public void inspect(Command command) {
        String printString = "Looking at ";
        String thingToLook = "";
        if (!command.hasSecondWord()) {
            System.out.println ("Inspect what?"); 
            return;
        }
        if (!command.hasLine()) {
            thingToLook = command.getSecondWord();   
        }
        else if (command.hasLine()) {
            thingToLook = command.getSecondWord() + command.getLine();       
        }
        
        if (thingToLook.equals(currentRoom.getName())) {
            printString += "the room: " + currentRoom.getName() + "\n" + currentRoom.getLongDescription();
        }
        else if (currentRoom.getItem(thingToLook) != null) {
            printString += "the item: " + currentRoom.getItem(thingToLook).getName() + "\n" + currentRoom.getItem(thingToLook).getDescription();
        }
        else if (player.getItem(thingToLook) != null) {
            printString += "the item: " + player.getItem(thingToLook).getName() + "\n" + player.getItem(thingToLook).getDescription();
        }
        else {
            printString += "\nYou can't look at that";
        }
        System.out.println (printString);
        health--;

    }

    public void grab (Command command) {
        String item;
        if (!command.hasSecondWord()) {
            System.out.println ("Grab what?"); 
            return;
        }
        else {
        	nextRoom = currentRoom;
        }
        if (!command.hasLine()) {
            item = command.getSecondWord();
        }
        else if (command.hasLine()) {
            item = command.getSecondWord() + command.getLine();
        }
        item = command.getSecondWord();
        Item itemGrab = currentRoom.removeItem(item);
        if (itemGrab == null) {
            System.out.println ("Can't grab this");
        }
        else {
            player.setItem(item, itemGrab);
            health--;
        }
    }

    public void drop (Command command) {
        String item;
        if (!command.hasSecondWord()) {
            System.out.println ("Drop what?"); 
            return;
        }
        if (!command.hasLine()) {
            item = command.getSecondWord();
        }
        else if (command.hasLine()) {
            item = command.getSecondWord() + command.getLine();
        }
        item = command.getSecondWord();
        Item itemDrop = player.removeItem(item);
        if (itemDrop == null) {
            System.out.println ("Can't drop this");
            return;
        }
        else {
            currentRoom.setItem(item, itemDrop);
        }
        health--;
    }
    
    public void goRoom(Command command) {
        String direction = "";
        if (player.getInventoryString().equals("Player Inventory: key") && currentRoom == startingLobby) {
        	nextRoom = Room107;
       }
        else if (!player.getInventoryString().equals("key") && currentRoom == startingLobby) {
         System.out.println ("This door needs a key to be unlocked");
         currentRoom = startingLobby;
         return;
       }
        if (!command.hasSecondWord()) {
            System.out.println ("Go where?");
            printInformation();
            play();
        }
         if (!command.hasLine()) {
            direction = command.getSecondWord();
        }
        else if (command.hasLine()) {
            direction = command.getSecondWord() + command.getLine();
        }
        Room nextRoom = currentRoom.getExit(direction);
        
        if (nextRoom == null) {
            System.out.print ("Can not enter");
        }
        else {
            currentRoom = nextRoom;
            stamina --;
        }
        currentRoom = nextRoom;
    }
    
    public void loseGame () {
    	if (health == 0)
    		lose = 0;
    	if (stamina == 0)
    		lose = 0;
    }
}
