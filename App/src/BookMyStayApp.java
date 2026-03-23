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

        synchronized boolean allocateRoom(String type) {
            int available = inventory.getOrDefault(type, 0);
            if (available <= 0) return false;
            inventory.put(type, available - 1);
            return true;
        }

        synchronized void releaseRoom(String type) {
            inventory.put(type, inventory.getOrDefault(type, 0) + 1);
        }

        synchronized int getAvailability(String type) {
            return inventory.getOrDefault(type, 0);
        }
    }

    class BookingService {
        private int counter = 1;
        private Map<String, Set<String>> allocatedRooms = new HashMap<>();
        private RoomInventory inventory;

        BookingService(RoomInventory inventory) {
            this.inventory = inventory;
        }

        Reservation tryAllocate(String guestName, String roomType) {
            synchronized (this) {
                if (!inventory.allocateRoom(roomType)) return null;
                String roomId = roomType.replace(" ", "").toUpperCase() + counter++;
                allocatedRooms.putIfAbsent(roomType, new HashSet<>());
                allocatedRooms.get(roomType).add(roomId);
                return new Reservation(guestName, roomType, roomId);
            }
        }
    }

    class BookingTask implements Runnable {
        private String guestName;
        private String roomType;
        private BookingService service;

        BookingTask(String guestName, String roomType, BookingService service) {
            this.guestName = guestName;
            this.roomType = roomType;
            this.service = service;
        }

        public void run() {
            Reservation r = service.tryAllocate(guestName, roomType);
            if (r != null) {
                System.out.println("Booking Confirmed:");
                r.display();
            } else {
                System.out.println("Booking Failed for " + guestName + " (Room not available: " + roomType + ")");
            }
        }
    }

    public class UseCase11ConcurrentBookingSimulation {

        public static void main(String[] args) throws InterruptedException {

            RoomInventory inventory = new RoomInventory();
            BookingService service = new BookingService(inventory);

            System.out.println("Book My Stay App - v11.0\n");

            String[][] requests = {
                    {"Alice", "Single Room"},
                    {"Bob", "Double Room"},
                    {"Charlie", "Single Room"},
                    {"Diana", "Suite Room"},
                    {"Eve", "Suite Room"},
                    {"Frank", "Single Room"}
            };

            ExecutorService executor = Executors.newFixedThreadPool(3);

            for (String[] req : requests) {
                executor.submit(new BookingTask(req[0], req[1], service));
            }

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.SECONDS);

            System.out.println("\nFinal Inventory:");
            System.out.println("Single Room: " + inventory.getAvailability("Single Room"));
            System.out.println("Double Room: " + inventory.getAvailability("Double Room"));
            System.out.println("Suite Room: " + inventory.getAvailability("Suite Room"));
        }
    }
}

