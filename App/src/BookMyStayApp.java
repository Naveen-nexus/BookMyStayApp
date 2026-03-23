public class BookMyStayApp {
    class Reservation {
        String guestName;
        String roomType;
        String roomId;

        Reservation(String guestName, String roomType, String roomId) {
            this.guestName = guestName;
            this.roomType = roomType;
            this.roomId = roomId;
        }

        void display() {
            System.out.println("Guest: " + guestName + ", Room Type: " + roomType + ", Room ID: " + roomId);
        }
    }

    class RoomInventory {
        private Map<String, Integer> inventory = new HashMap<>();

        RoomInventory() {
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);
        }

        int getAvailability(String type) {
            return inventory.getOrDefault(type, 0);
        }

        void reduceAvailability(String type) {
            inventory.put(type, getAvailability(type) - 1);
        }

        void increaseAvailability(String type) {
            inventory.put(type, getAvailability(type) + 1);
        }
    }

    class BookingService {
        private int counter = 1;
        private Map<String, Set<String>> allocatedRooms = new HashMap<>();

        Reservation allocateRoom(String guestName, String roomType, RoomInventory inventory) throws Exception {
            if (inventory.getAvailability(roomType) <= 0) {
                throw new Exception("No rooms available for: " + roomType);
            }
            inventory.reduceAvailability(roomType);

            String roomId = roomType.replace(" ", "").toUpperCase() + counter++;
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());
            allocatedRooms.get(roomType).add(roomId);

            return new Reservation(guestName, roomType, roomId);
        }

        boolean cancelReservation(Reservation r, RoomInventory inventory, Stack<String> rollbackStack) {
            String roomType = r.roomType;
            String roomId = r.roomId;

            if (!allocatedRooms.containsKey(roomType) || !allocatedRooms.get(roomType).contains(roomId)) {
                return false;
            }

            allocatedRooms.get(roomType).remove(roomId);
            inventory.increaseAvailability(roomType);
            rollbackStack.push(roomId);

            return true;
        }
    }

    public class UseCase10BookingCancellation {

        public static void main(String[] args) throws Exception {

            RoomInventory inventory = new RoomInventory();
            BookingService service = new BookingService();
            Stack<String> rollbackStack = new Stack<>();

            Reservation r1 = service.allocateRoom("Alice", "Single Room", inventory);
            Reservation r2 = service.allocateRoom("Bob", "Double Room", inventory);
            Reservation r3 = service.allocateRoom("Charlie", "Single Room", inventory);

            System.out.println("Book My Stay App - v10.0\n");

            System.out.println("Initial Bookings:");
            r1.display();
            r2.display();
            r3.display();

            System.out.println("\nProcessing Cancellations:");

            if (service.cancelReservation(r2, inventory, rollbackStack)) {
                System.out.println("Cancellation Successful for: " + r2.guestName);
            } else {
                System.out.println("Cancellation Failed for: " + r2.guestName);
            }

            if (service.cancelReservation(r3, inventory, rollbackStack)) {
                System.out.println("Cancellation Successful for: " + r3.guestName);
            } else {
                System.out.println("Cancellation Failed for: " + r3.guestName);
            }

            System.out.println("\nRollback Stack:");
            while (!rollbackStack.isEmpty()) {
                System.out.println("Released Room ID: " + rollbackStack.pop());
            }

            System.out.println("\nInventory After Cancellations:");
            System.out.println("Single Room: " + inventory.getAvailability("Single Room"));
            System.out.println("Double Room: " + inventory.getAvailability("Double Room"));
            System.out.println("Suite Room: " + inventory.getAvailability("Suite Room"));
        }
    }
}

