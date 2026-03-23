public class BookMyStayApp {
import java.util.LinkedList;
import java.util.Queue;

    class Reservation {
        String guestName;
        String roomType;

        Reservation(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }

        void display() {
            System.out.println("Guest: " + guestName + ", Requested Room: " + roomType);
        }
    }

    class BookingRequestQueue {
        private Queue<Reservation> queue;

        BookingRequestQueue() {
            queue = new LinkedList<>();
        }

        void addRequest(Reservation reservation) {
            queue.offer(reservation);
        }

        void displayQueue() {
            System.out.println("\n--- Booking Request Queue ---");
            for (Reservation r : queue) {
                r.display();
            }
        }
    }

    public class UseCase5BookingRequestQueue {

        public static void main(String[] args) {

            BookingRequestQueue requestQueue = new BookingRequestQueue();

            requestQueue.addRequest(new Reservation("Alice", "Single Room"));
            requestQueue.addRequest(new Reservation("Bob", "Double Room"));
            requestQueue.addRequest(new Reservation("Charlie", "Suite Room"));
            requestQueue.addRequest(new Reservation("Diana", "Single Room"));

            System.out.println("Book My Stay App - v5.0");

            requestQueue.displayQueue();
        }
    }

}

