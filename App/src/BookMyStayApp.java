public class BookMyStayApp {
import java.util.*;

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

    class BookingHistory {
        private List<Reservation> history = new ArrayList<>();

        void addReservation(Reservation r) {
            history.add(r);
        }

        List<Reservation> getAllReservations() {
            return Collections.unmodifiableList(history);
        }
    }

    class BookingReportService {
        void generateReport(BookingHistory history) {
            System.out.println("\n--- Booking History Report ---");
            for (Reservation r : history.getAllReservations()) {
                r.display();
            }
            System.out.println("Total Bookings: " + history.getAllReservations().size());
        }
    }

    public class UseCase8BookingHistoryReport {

        public static void main(String[] args) {

            BookingHistory history = new BookingHistory();

            history.addReservation(new Reservation("Alice", "Single Room", "SINGLEROOM1"));
            history.addReservation(new Reservation("Bob", "Double Room", "DOUBLEROOM2"));
            history.addReservation(new Reservation("Charlie", "Single Room", "SINGLEROOM3"));
            history.addReservation(new Reservation("Diana", "Suite Room", "SUITEROOM4"));

            System.out.println("Book My Stay App - v8.0");

            BookingReportService reportService = new BookingReportService();
            reportService.generateReport(history);
        }
    }
}

