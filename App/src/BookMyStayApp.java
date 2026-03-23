public class BookMyStayApp {

    class Reservation implements Serializable {
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

    class RoomInventory implements Serializable {
        private Map<String, Integer> inventory = new HashMap<>();

        RoomInventory() {
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);
        }

        boolean allocateRoom(String type) {
            int available = inventory.getOrDefault(type, 0);
            if (available <= 0) return false;
            inventory.put(type, available - 1);
            return true;
        }

        void releaseRoom(String type) {
            inventory.put(type, inventory.getOrDefault(type, 0) + 1);
        }

        int getAvailability(String type) {
            return inventory.getOrDefault(type, 0);
        }

        void display() {
            System.out.println("Inventory:");
            for (String type : inventory.keySet()) {
                System.out.println(type + ": " + inventory.get(type));
            }
        }
    }

    class BookingSystem implements Serializable {
        private static final long serialVersionUID = 1L;
        List<Reservation> bookingHistory = new ArrayList<>();
        RoomInventory inventory = new RoomInventory();
        transient int counter = 1;

        Reservation bookRoom(String guestName, String roomType) {
            if (!inventory.allocateRoom(roomType)) return null;
            String roomId = roomType.replace(" ", "").toUpperCase() + counter++;
            Reservation r = new Reservation(guestName, roomType, roomId);
            bookingHistory.add(r);
            return r;
        }

        void cancelBooking(Reservation r) {
            if (bookingHistory.remove(r)) {
                inventory.releaseRoom(r.roomType);
            }
        }

        void displayBookings() {
            System.out.println("Booking History:");
            for (Reservation r : bookingHistory) r.display();
        }

        void displayInventory() {
            inventory.display();
        }
    }

    class PersistenceService {
        static void saveState(BookingSystem system, String filename) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(system);
                System.out.println("System state saved to " + filename);
            } catch (IOException e) {
                System.out.println("Error saving system state: " + e.getMessage());
            }
        }

        static BookingSystem loadState(String filename) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
                BookingSystem system = (BookingSystem) in.readObject();
                System.out.println("System state loaded from " + filename);
                return system;
            } catch (Exception e) {
                System.out.println("No previous state found. Starting fresh.");
                return new BookingSystem();
            }
        }
    }

    public class UseCase12DataPersistenceRecovery {

        public static void main(String[] args) {

            String filename = "bookingSystem.dat";

            BookingSystem system = PersistenceService.loadState(filename);

            System.out.println("\nBook My Stay App - v12.0\n");

            Reservation r1 = system.bookRoom("Alice", "Single Room");
            Reservation r2 = system.bookRoom("Bob", "Double Room");

            System.out.println("Current Bookings:");
            system.displayBookings();

            System.out.println("\nCurrent Inventory:");
            system.displayInventory();

            PersistenceService.saveState(system, filename);

            System.out.println("\nSimulating system restart...\n");

            BookingSystem restoredSystem = PersistenceService.loadState(filename);

            System.out.println("Restored Bookings:");
            restoredSystem.displayBookings();

            System.out.println("\nRestored Inventory:");
            restoredSystem.displayInventory();
        }
    }
}

