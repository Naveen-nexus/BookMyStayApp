public class BookMyStayApp {
import java.util.*;

    class Reservation {
        String guestName;
        String roomType;

        Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }

    class BookingRequestQueue {
        private Queue<Reservation> queue = new LinkedList<>();

        void addRequest(Reservation r) {
            queue.offer(r);
        }

        Reservation getNextRequest() {
            return queue.poll();
        }

        boolean isEmpty() {
            return queue.isEmpty();
        }
    }

    class RoomInventory {
        private HashMap<String, Integer> inventory = new HashMap<>();

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
    }

    class BookingService {
        private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();
        private int counter = 1;

        void processRequests(BookingRequestQueue queue, RoomInventory inventory) {

            while (!queue.isEmpty()) {
                Reservation r = queue.getNextRequest();
                String type = r.roomType;

                if (inventory.getAvailability(type) > 0) {

                    String roomId = type.replace(" ", "").toUpperCase() + counter++;
                    allocatedRooms.putIfAbsent(type, new HashSet<>());

                    if (!allocatedRooms.get(type).contains(roomId)) {
                        allocatedRooms.get(type).add(roomId);
                        inventory.reduceAvailability(type);

                        System.out.println("Booking Confirmed: " + r.guestName +
                                " | Room Type: " + type +
                                " | Room ID: " + roomId);
                    }

                } else {
                    System.out.println("Booking Failed (No Availability): " + r.guestName +
                            " | Room Type: " + type);
                }
            }
        }
    }

    public class UseCase6RoomAllocationService {

        public static void main(String[] args) {

            BookingRequestQueue queue = new BookingRequestQueue();

            queue.addRequest(new Reservation("Alice", "Single Room"));
            queue.addRequest(new Reservation("Bob", "Double Room"));
            queue.addRequest(new Reservation("Charlie", "Single Room"));
            queue.addRequest(new Reservation("Diana", "Suite Room"));
            queue.addRequest(new Reservation("Eve", "Suite Room"));

            RoomInventory inventory = new RoomInventory();
            BookingService service = new BookingService();

            System.out.println("Book My Stay App - v6.0\n");

            service.processRequests(queue, inventory);
        }
    }
}

