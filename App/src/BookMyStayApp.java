public class BookMyStayApp {
    class InvalidBookingException extends Exception {
        InvalidBookingException(String message) {
            super(message);
        }
    }

    class RoomInventory {
        private Map<String, Integer> inventory = new HashMap<>();

        RoomInventory() {
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);
            inventory.put("Suite Room", 1);
        }

        int getAvailability(String type) throws InvalidBookingException {
            if (!inventory.containsKey(type)) {
                throw new InvalidBookingException("Invalid Room Type: " + type);
            }
            return inventory.get(type);
        }

        void reduceAvailability(String type) throws InvalidBookingException {
            int available = getAvailability(type);
            if (available <= 0) {
                throw new InvalidBookingException("No rooms available for: " + type);
            }
            inventory.put(type, available - 1);
        }
    }

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

    class BookingService {
        private int counter = 1;
        private Map<String, Set<String>> allocatedRooms = new HashMap<>();

        Reservation allocateRoom(String guestName, String roomType, RoomInventory inventory) throws InvalidBookingException {
            inventory.reduceAvailability(roomType);

            String roomId = roomType.replace(" ", "").toUpperCase() + counter++;
            allocatedRooms.putIfAbsent(roomType, new HashSet<>());
            if (allocatedRooms.get(roomType).contains(roomId)) {
                throw new InvalidBookingException("Duplicate Room ID: " + roomId);
            }

            allocatedRooms.get(roomType).add(roomId);
            return new Reservation(guestName, roomType, roomId);
        }
    }

    public class UseCase9ErrorHandlingValidation {

        public static void main(String[] args) {

            RoomInventory inventory = new RoomInventory();
            BookingService service = new BookingService();

            String[][] bookingRequests = {
                    {"Alice", "Single Room"},
                    {"Bob", "Double Room"},
                    {"Charlie", "Triple Room"},
                    {"Diana", "Suite Room"},
                    {"Eve", "Suite Room"}
            };

            System.out.println("Book My Stay App - v9.0");

            for (String[] request : bookingRequests) {
                String guest = request[0];
                String roomType = request[1];

                try {
                    Reservation r = service.allocateRoom(guest, roomType, inventory);
                    System.out.println("Booking Confirmed:");
                    r.display();
                } catch (InvalidBookingException e) {
                    System.out.println("Booking Failed for " + guest + ": " + e.getMessage());
                }
            }
        }
    }
}

