package textadventure.game;

	public class Game {
	    private Parser parser;
	    private Room currentRoom;
	    private Player player;
	    private CLS cls_var;

	    public Game() {
	        parser = new Parser();
	        player = new Player();
	    }

	    public void printTitle() {
	        System.out.println ("_    _   ______   ______   ______  _____ _______  ______   _            ______   _    _   ______  ");
	        System.out.println ("| |  | | / |  | \\ / |      | |  | \\  | |    | |   | |  | | | |          | |  | \\ | |  | | | |  \\ \\ ");
	        System.out.println ("| |--| | | |  | | '------. | |__|_/  | |    | |   | |__| | | |   _      | |__| | | |  | | | |  | | ");
	        System.out.println ("|_|  |_| \\_|__|_/  ____|_/ |_|      _|_|_   |_|   |_|  |_| |_|__|_|     |_|  \\_\\ \\_|__|_| |_|  |_| ");
	        System.out.println ("");
	        System.out.println ();
	        System.out.println ();
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
	        System.out.println ("***************************************************************************************************************");
	    }

	    public void setUpGame() {
	        Room startingLobby = new Room ("Lobby" , "Starting room that contains a key", "The starting point of the game. This room will only lead to Room 107, and key can be picked up and used to unlock something...");
	        Room Room107 = new Room ("Room 107" , "A room in the hospital which can only access the laboratory", "Room 107 is the second accessible room in the game. Has no special actions in this room either. The southernmost point of all the rooms. This room only leads to the Laboratory");
	        Room Lab = new Room ("Laboratory" , "Middle room of the hospital with a table of medicine", "The middle of the game with a table of medicine. One gives a health deduction and one gives a health increase. This leads into the operating room, waiting room and room 107");
	        Room OperatingRoom = new Room ("Operating Room" , "Room that leads to a closet and back to the waiting room", "The room contains a knife that grants the player an attack increase. This knife does not have to be picked up. Accessed by walking through Laboratory");
	        Room Closet = new Room ("Closet" , "Last room that is available in the game", "This room is the final room in the game and it gives access back to the laboratory and operatingRoom");

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
	            cls_var.main(); 
	        }catch(Exception e) {
	            System.out.println(e); 
	        }

	        printTitle();
	        System.out.println ("Type any word to start");
	        play();
	        
	        printInformation();
	    }

	    public void play() {
	        while(true) {            
	            Command command = parser.getCommand();
	            try {
	                cls_var.main(); 
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
	            case "help":
	                System.out.println ("Commands: Drink, Speak, Go, Grab, Drop, Inspect");
	            case "inspect":
	                inspect(command);
	                break;
	            case "drink":
	                drink(command);
	                break;
	        }
	     }
	    
	    public void drink (Command command) {
	        String thingToDrink;
	        if (!command.hasLine()) {
	            thingToDrink = command.getSecondWord() + command.getLine();   
	        }
	        else if (command.hasLine()) {
	            thingToDrink = command.getSecondWord() + command.getLine();       
	        }
	        //player.adjustHealth(thingToDrink.getHealth);
	    }

	    public void inspect(Command command) {
	        String printString = "Looking at ";
	        String thingToLook = "";
	        if (!command.hasSecondWord()) {
	            System.out.println ("Inspect whAat?"); 
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
	    }

	    public void grab (Command command) {
	        String item;
	        if (!command.hasSecondWord()) {
	            System.out.println ("Grab what?"); 
	            return;
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
	    }

	    public void goRoom(Command command) {
	        String direction = "";
	        if (!command.hasSecondWord()) {
	            System.out.print ("Go where?");
	        }
	        if (!command.hasLine()) {
	            direction = command.getSecondWord();
	        }
	        else if (command.hasLine()) {
	            direction = command.getSecondWord() + command.getLine();
	        }
	        Room nextRoom = currentRoom.getExit(direction);
	        /*if (player.setInventory ("key" , key)
	          nextRoom = currentRoom;
	        else nextRoom == null;
	        */
	        if (nextRoom == null) {
	            System.out.print ("Can not enter");
	        }
	        else {
	            currentRoom = nextRoom;
	        }
	        currentRoom = nextRoom;
	    }
	}
