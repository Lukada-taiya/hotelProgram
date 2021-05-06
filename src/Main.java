/*Hotel Deluxe Program by Adam Lukman Tungtaiya
*/
import java.util.*;

public class Main {


    private static final int EXECUTIVE_AMOUNT = 2500;
    private static final double EXECUTIVE_AMOUNT_DISABLED = 2500 - (0.1 * 2500);
    private static final int CHALET_AMOUNT = 1500;
    private static final double CHALET_AMOUNT_DISABLED = 1500 - (0.1 * 1500);
    private static final int ORDINARY_AMOUNT = 500;
    private static final double ORDINARY_AMOUNT_DISABLED = 500 - (0.1 * 500);
    private static String customerName = "";
    private static int customerTel = 0;
    private static Map<String, Room> executive = new LinkedHashMap<>();
    private static Map<String, Room> chalet = new LinkedHashMap<>();
    private static Map<String, Room> ordinary = new LinkedHashMap<>();
    private static String roomName = "";
    private static int roomType = 0; //Indicates the category of room wanted by the user, i.e 1 = Executive, 2 = Chalet, 3 = Ordinary
    private static String[] availableRoomList = new String[10];
    static Scanner input = new Scanner(System.in);
    private static Room room;

    public static void main(String[] args) {
        initializeHotel();
        mainThread();
    }

    private static void initializeHotel() {
        Room e1 = new Room();
        Room e2 = new Room();
        Room e3 = new Room();
        Room e4 = new Room();
        Room e5 = new Room();
        Room e6 = new Room();
        Room e7 = new Room();
        Room e8 = new Room();
        Room e9 = new Room();
        Room e10 = new Room();

        executive.put("E-01", e1);
        executive.put("E-02", e2);
        executive.put("E-03", e3);
        executive.put("E-04", e4);
        executive.put("E-05", e5);
        executive.put("E-06", e6);
        executive.put("E-07", e7);
        executive.put("E-08", e8);
        executive.put("E-09", e9);
        executive.put("E-10", e10);

        Room c1 = new Room();
        Room c2 = new Room();
        Room c3 = new Room();
        Room c4 = new Room();
        Room c5 = new Room();
        Room c6 = new Room();
        Room c7 = new Room();

        chalet.put("C-01", c1);
        chalet.put("C-02", c2);
        chalet.put("C-03", c3);
        chalet.put("C-04", c4);
        chalet.put("C-05", c5);
        chalet.put("C-06", c6);
        chalet.put("C-07", c7);

        Room o1 = new Room();
        Room o2 = new Room();
        Room o3 = new Room();
        Room o4 = new Room();
        Room o5 = new Room();
        Room o6 = new Room();
        Room o7 = new Room();

        ordinary.put("O-01", o1);
        ordinary.put("O-02", o2);
        ordinary.put("O-03", o3);
        ordinary.put("O-04", o4);
        ordinary.put("O-05", o5);
        ordinary.put("O-06", o6);
        ordinary.put("O-07", o7);
    }

    private static void mainThread() {
        System.out.print("\n\nHello Adventurer! Nice to Meet You.\nHow may I help you?\n1. Book A Room\n2. Check Out \n3. Check Room Details\n4. Exit\n=> ");
        int step = input.nextInt();
        if(step == 1) {
            double amountToPay = bookRoom();
            System.out.print("Amount Due: Ghc " + amountToPay);
            double balance = payAmount(amountToPay);
            System.out.print("\n\nPayment has been paid successfully. Here is your balance: Ghc " + balance + "\n1. Check In\n2. Print Receipt\n3. Go to Main Menu\n=> ");
            step = input.nextInt();
            if(step == 1) {
                checkIn();
                System.out.print("\nYou are now checked into Room " + roomName + "\n1. Check out of Room \n2. Go to Main Menu\n=> ");
                step = input.nextInt();
                if (step == 1) {
                    checkOut(roomName);
                }else if(step == 2) {
                    Arrays.fill(availableRoomList, null);
                    mainThread();
                }
            }else if (step == 2) {
                printReceipt(amountToPay);
                System.out.print("\n1. Go to Main Menu\n=> ");
                step = input.nextInt();
                if (step == 1) {
                    Arrays.fill(availableRoomList, null);
                    mainThread();
                }
            }else if(step == 3) {
                Arrays.fill(availableRoomList, null);
                mainThread();
            }
        }else if(step == 2) {
            System.out.print("\nEnter Room Number to check out from\n=> ");
            String roomname = input.next();
            checkOut(roomname);
        }else if(step == 3) {
            checkForRoomDetails();
        }else if(step == 4) {
            System.out.println("Thank you for patronizing our services. Please come back later.");
            System.exit(1);
        }else {
            System.out.println("Something went wrong. Please try again later.");
            Arrays.fill(availableRoomList, null);
            mainThread();
        }
    }

    private static double bookRoom() {
        System.out.print("\n\nWe have 24 rooms and three category of rooms. Which category of room are you interested in?\n1. Executive\n2. Chalet\n3. Ordinary\n=> ");
        int step = input.nextInt();
        roomType = step;
        System.out.print("\n\nWe have rooms "+ getAvailableRoomCount() + " available. Please pick a room of your choice\n" + getAvailableRoomsList() + "\n=> ");
        step = input.nextInt();
        step -= 1;
        roomName = availableRoomList[step];
        System.out.print("\n\n" + getRoomDetails() + "\n1. Book Room\n=> ");
        step = input.nextInt();
        double amountToPay = 0;
        if(step == 1) {
            System.out.print("\n\nRoom " + roomName + " Booking Sheet\n");
            registerCustomer();
            System.out.print("How many nights do you want the room for? \n=> ");
            int numberOfNights = input.nextInt();
            amountToPay = getAmountDue(numberOfNights);
            if (roomType == 1) {
                room = executive.get(roomName);
                room.put("Number of Nights", numberOfNights);
            }
        }
        return amountToPay;
    }

    private static int getAvailableRoomCount() {
        int roomNum = 0;
        if(roomType == 1) {
            Set set = executive.entrySet();
            for (Object o : set) {
                Map.Entry entry = (Map.Entry) o;
                room = (Room) entry.getValue();
                roomNum += room.getAttributeValue("Availability");
            }
        }else if(roomType == 2) {
            Set set = chalet.entrySet();
            for (Object o : set) {
                Map.Entry entry = (Map.Entry) o;
                room = (Room) entry.getValue();
                roomNum += room.getAttributeValue("Availability");
            }
        }else if(roomType == 3) {
            Set set = ordinary.entrySet();
            for (Object o : set) {
                Map.Entry entry = (Map.Entry) o;
                room = (Room) entry.getValue();
                roomNum += room.getAttributeValue("Availability");
            }
        }
        return roomNum;
    }

    private static String getAvailableRoomsList() {
        StringBuilder list = new StringBuilder();
        int count = 0;
        if(roomType == 1) {
            Set set = executive.entrySet();
            for (Object o : set) {
                Map.Entry entry = (Map.Entry) o;
                room = (Room) entry.getValue();
                if (room.getAttributeValue("Availability") == 1) {
                    availableRoomList[count] = (String) entry.getKey();
                    count++;
                }
            }
        }else if(roomType == 2) {
            Set set = chalet.entrySet();
            for (Object o : set) {
                Map.Entry entry = (Map.Entry) o;
                room = (Room) entry.getValue();
                if (room.getAttributeValue("Availability") == 1) {
                    availableRoomList[count] = (String) entry.getKey();
                    count++;
                }
            }
        }else if(roomType == 3) {
            Set set = ordinary.entrySet();
            for (Object o : set) {
                Map.Entry entry = (Map.Entry) o;
                room = (Room) entry.getValue();
                if (room.getAttributeValue("Availability") == 1) {
                    availableRoomList[count] = (String) entry.getKey();
                    count++;
                }
            }
        }
        count = 1;
        for(String room: availableRoomList) {
            if(room != null) {
                list.append(count).append(". ").append(room).append("\n");
                count++;
            }
        }
        return list.toString();
    }

    private static String getRoomDetails() {
        String roomStr = "Room " + roomName + " Details\n";
        if(roomType == 1) {
            room = executive.get(roomName);
            if(room.getAttributeValue("Availability") == 1) {
                roomStr += "Availability: Yes\n";
            }else {
                roomStr += "Availability: No\n";
                if(room.getAttributeValue("Checked-In") == 0) {
                    roomStr += "Checked In: Not Yet\n";
                }else {
                    roomStr += "Checked In: Yes\n";
                }
                if(room.getAttributeValue("Fully Paid") == 2) {
                    roomStr += "Payment: Part Payment\n";
                }else if(room.getAttributeValue("Fully Paid") == 1) {
                    roomStr += "Payment: Paid in full\n";
                }
            }
            if(room.getAttributeValue("Disability") == 0) {
                roomStr += "Disability Friendly: No\n";
                roomStr += "Amount Per Night: Ghc " + EXECUTIVE_AMOUNT;
            }else {
                roomStr += "Disability Friendly: Yes\n";
                roomStr += "Amount Per Night: Ghc " + EXECUTIVE_AMOUNT_DISABLED;
            }
        }else if(roomType == 2) {
            System.out.println("Room Name: " + roomName);
            room = chalet.get(roomName);
            System.out.println("Room " + room);
            if(room.getAttributeValue("Availability") == 1) {
                roomStr += "Availability: Yes\n";
            }else {
                roomStr += "Availability: No\n";
                if(room.getAttributeValue("Checked-In") == 0) {
                    roomStr += "Checked In: Not Yet\n";
                }else {
                    roomStr += "Checked In: Yes\n";
                }
                if(room.getAttributeValue("Fully Paid") == 2) {
                    roomStr += "Payment: Part Payment\n";
                }else if(room.getAttributeValue("Fully Paid") == 1) {
                    roomStr += "Payment: Paid in full\n";
                }
            }
            if(room.getAttributeValue("Disability") == 0) {
                roomStr += "Disability Friendly: No\n";
                roomStr += "Amount Per Night: Ghc " + CHALET_AMOUNT;
            }else {
                roomStr += "Disability Friendly: Yes\n";
                roomStr += "Amount Per Night: Ghc " + CHALET_AMOUNT_DISABLED;
            }
        }else if(roomType == 3) {
            room = ordinary.get(roomName);
            if(room.getAttributeValue("Availability") == 1) {
                roomStr += "Availability: Yes\n";
            }else {
                roomStr += "Availability: No\n";
                if(room.getAttributeValue("Checked-In") == 0) {
                    roomStr += "Checked In: Not Yet\n";
                }else {
                    roomStr += "Checked In: Yes\n";
                }
                if(room.getAttributeValue("Fully Paid") == 2) {
                    roomStr += "Payment: Part Payment\n";
                }else if(room.getAttributeValue("Fully Paid") == 1) {
                    roomStr += "Payment: Paid in full\n";
                }
            }
            if(room.getAttributeValue("Disability") == 0) {
                roomStr += "Disability Friendly: No\n";
                roomStr += "Amount Per Night: Ghc " + ORDINARY_AMOUNT;
            }else {
                roomStr += "Disability Friendly: Yes\n";
                roomStr += "Amount Per Night: Ghc " + ORDINARY_AMOUNT_DISABLED;
            }
        }
        return roomStr;

    }

    private static void registerCustomer() {
        System.out.print("Enter First Name: ");
        customerName = input.next();
        System.out.print("Enter Telephone Number: ");
        customerTel = input.nextInt();
    }

    private static double getAmountDue(int nights) {
        if (roomType == 1) {
            room = executive.get(roomName);
        }else if (roomType == 2) {
            room = chalet.get(roomName);
        }else if (roomType == 3) {
            room = ordinary.get(roomName);
        }
        double amount = 0;
        if(roomType == 1) {
            if(room.getAttributeValue("Disability") == 1) {
                amount = nights * EXECUTIVE_AMOUNT_DISABLED;
            }else {
                amount = nights * EXECUTIVE_AMOUNT;
            }
        }else if(roomType == 2) {
            if(room.getAttributeValue("Disability") == 1) {
                amount = nights * CHALET_AMOUNT_DISABLED;
            }else {
                amount = nights * CHALET_AMOUNT;
            }
        }else if(roomType == 3) {
            if(room.getAttributeValue("Disability") == 1) {
                amount = nights * ORDINARY_AMOUNT_DISABLED;
            }else {
                amount = nights * ORDINARY_AMOUNT;
            }
        }

        return amount;
    }

    private static double payAmount(double amount) {
        if(roomType == 1) {
            room = executive.get(roomName);
        }else if(roomType == 2) {
            room = chalet.get(roomName);
        }else if(roomType == 3) {
            room = ordinary.get(roomName);
        }
        double balance = 0;
        System.out.print("\nEnter Payment: Ghc ");
        double payment = input.nextDouble();
        if(payment >= amount) {
            balance = payment - amount;
            room.put("Fully Paid", 1);
        }else if (payment < amount) {
            double arrears = amount - payment;
            room.put("Fully Paid", 2);
            System.out.print("\n\nArrears: Ghc " + arrears + "\nYou must pay all the amount in order to be able to check-in\n1. Pay Arrears\n2. Go to Main Menu\n=> ");
            int step = input.nextInt();
            if(step == 1) {
                payAmount(arrears);
            }else if (step == 2) {
                Arrays.fill(availableRoomList, null);
                mainThread();
            }
        }
        return balance;
    }

    private static void checkIn() {
        if(roomType == 1) {
            room = executive.get(roomName);
        }else if(roomType == 2) {
            room = chalet.get(roomName);
        }else if(roomType == 3) {
            room = ordinary.get(roomName);
        }
        room.put("Availability", 0);
        room.put("Checked-In", 1);
    }

    private static void checkOut(String roomname) {
        String roomChar = roomname.substring(0,1);
        switch (roomChar) {
            case "E" -> room = executive.get(roomname);
            case "C" -> room = chalet.get(roomname);
            case "O" -> room = ordinary.get(roomname);
            default -> {
                System.out.println("Sorry. You have entered a wrong input value");
                Arrays.fill(availableRoomList, null);
                mainThread();
            }
        }
        int step;
        if(room.getAttributeValue("Checked-In") == 1) {
            room.put("Checked-In", 0);
            room.put("Availability", 1);
            room.put("Fully Paid", 0);
            room.put("Number of Nights", 0);
            System.out.print("You have successfully checked out of Room " + roomName +"\n1. Main Menu\n=> ");
        }else {
            System.out.print("Sorry. You are not checked into this room\n1. Main Menu\n=> ");
        }
        step = input.nextInt();
        if (step == 1) {
            Arrays.fill(availableRoomList, null);
            mainThread();
        }
    }

    private static void printReceipt(double amount) {
        if(roomType == 1) {
            room = executive.get(roomName);
        }else if(roomType == 2) {
            room = chalet.get(roomName);
        }else if(roomType == 3) {
            room = ordinary.get(roomName);
        }
        String receipt = "===============================================\n\t\tHOTEL DELUXE\n===============================================\n"+
                "Name of Customer: " + customerName + "\nTelephone Number: "+ customerTel + "\nRoom Booked: " + roomName;
        receipt += "\nCategory of Room: Executive\n";
        if(room.getAttributeValue("Disability") == 1) {
            receipt += "Disability Friendly: Yes\n";
        }else {
            receipt += "Disability Friendly: No\n";
        }
        receipt += "Amount Paid: " + amount;
        System.out.println(receipt);
    }

    private static void checkForRoomDetails() {
        System.out.print("Search For Details of Room Here\nEnter Room Number: ");
        String roomname = input.next();
        String roomChar = roomname.substring(0,1);
        switch (roomChar) {
            case "E" -> room = executive.get(roomname);
            case "C" -> room = chalet.get(roomname);
            case "O" -> room = ordinary.get(roomname);
            default -> {
                System.out.println("Sorry, you have entered an incorrect room. Try again");
                checkForRoomDetails();
            }
        }
        String roomStr = "\nRoom " + roomname + " Details\n";
        if (room.getAttributeValue("Availability") == 1) {
            roomStr += "Availability: Yes\n";
        }else {
            roomStr += "Availability: No\n";
            if(room.getAttributeValue("Checked-In") == 1) {
                roomStr += "Checked In: Yes\n";
            }else {
                roomStr += "Checked In: Not Yet\n";
            }
            if(room.getAttributeValue("Fully Paid") == 1) {
                roomStr += "Payment: Paid in Full for " + room.getAttributeValue("Number of Nights") + " Nights\n";
            }else if (room.getAttributeValue("Fully Paid") == 2) {
                roomStr += "Payment: Part Payment for " + room.getAttributeValue("Number of Nights") + " Nights\n";
            }
        }
        if (room.getAttributeValue("Disability") == 1) {
            roomStr += "Disability Friendly: Yes\n";
            switch (roomChar) {
                case "E" -> roomStr += "Amount Per Night: Ghc " + EXECUTIVE_AMOUNT_DISABLED + "\n";
                case "C" -> roomStr += "Amount Per Night: Ghc " + CHALET_AMOUNT_DISABLED + "\n";
                case "O" -> roomStr += "Amount Per Night: Ghc " + ORDINARY_AMOUNT_DISABLED + "\n";
            }
        }else {
            roomStr += "Disability Friendly: No\n";
            switch (roomChar) {
                case "E" -> roomStr += "Amount Per Night: Ghc " + EXECUTIVE_AMOUNT+ "\n";
                case "C" -> roomStr += "Amount Per Night: Ghc " + CHALET_AMOUNT + "\n";
                case "O" -> roomStr += "Amount Per Night: Ghc " + ORDINARY_AMOUNT + "\n";
            }
        }
        System.out.println(roomStr);
        System.out.print("\n1. Search Again\n2. Go to Main Menu\n=> ");
        int step = input.nextInt();
        if (step == 1) {
            checkForRoomDetails();
        }else if (step == 2) {
            Arrays.fill(availableRoomList, null);
            mainThread();
        }
    }

}

class Room implements Map{
    private Map<Object,Object> room;

    public Room() {
        this.room = new HashMap<>();
        this.room.put("Availability", 1);
        this.room.put("Checked-In", 0);
        this.room.put("Fully Paid", 0);
        //A random variable has been created to open the possibility of some rooms being disability friendly
        Random rand = new Random();
        this.room.put("Disability", rand.nextInt(2));
        this.room.put("Number of Nights", 0);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public Object get(Object key) {
        return room.get(key);
    }


    @Override
    public Object put(Object key, Object value) {
        return room.put(key,value);
    }

    @Override
    public Object remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set keySet() {
        return null;
    }

    @Override
    public Collection values() {
        return null;
    }

    @Override
    public Set<Entry> entrySet() {
        return null;
    }

    @Override
    public boolean replace(Object key, Object oldValue, Object newValue) {
        return false;
    }

    //Gets the value of a room attribute
    public int getAttributeValue(Object key) {
        return (int) room.get(key);
    }
}
