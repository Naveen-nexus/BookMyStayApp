public class BookMyStayApp {
    import java.util.HashMap;

    abstract class Room {
        String type;
        int beds;
        double price;

        Room(String type, int beds, double price) {
            this.type = type;
            this.beds = beds;
            this.price = price;
        }

        void display() {
            System.out.println("Room Type: " + type);
            System.out.println("Beds: " + beds);
            System.out.println("Price: " + price);
        }
    }

    class SingleRoom extends Room {
        SingleRoom() {
            super("Single Room", 1, 1000);
        }
    }

    class DoubleRoom extends Room {
        DoubleRoom() {
            super("Double Room", 2, 1800);
        }
    }

    class SuiteRoom extends Room {
        SuiteRoom() {
            super("Suite Room", 3, 3000);
        }
    }

    class RoomInventory {
        private HashMap<String, Integer> inventory;

        RoomInventory() {
            inventory = new HashMap<>();
            inventory.put("Single Room", 5);
            inventory.put("Double Room", 3);
            inventory.put("Suite Room", 0);
        }

        int getAvailability(String roomType) {
            return inventory.getOrDefault(roomType, 0);
        }
    }

    class RoomSearchService {

        void searchRooms(Room[] rooms, RoomInventory inventory) {
            System.out.println("\n--- Available Rooms ---");

            for (Room room : rooms) {
                int available = inventory.getAvailability(room.type);

                if (available > 0) {
                    room.display();
                    System.out.println("Available: " + available + "\n");
                }
            }
        }
    }

    public class UseCase4RoomSearch {

        public static void main(String[] args) {

            Room[] rooms = {
                    new SingleRoom(),
                    new DoubleRoom(),
                    new SuiteRoom()
            };

            RoomInventory inventory = new RoomInventory();
            RoomSearchService searchService = new RoomSearchService();

            System.out.println("Book My Stay App - v4.0");

            searchService.searchRooms(rooms, inventory);
        }
    }
}

