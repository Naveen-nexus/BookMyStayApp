public class BookMyStayApp {
import java.util.*;

    class AddOnService {
        String name;
        double cost;

        AddOnService(String name, double cost) {
            this.name = name;
            this.cost = cost;
        }
    }

    class AddOnServiceManager {
        private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

        void addService(String reservationId, AddOnService service) {
            serviceMap.putIfAbsent(reservationId, new ArrayList<>());
            serviceMap.get(reservationId).add(service);
        }

        double calculateTotalCost(String reservationId) {
            double total = 0;
            List<AddOnService> services = serviceMap.get(reservationId);

            if (services != null) {
                for (AddOnService s : services) {
                    total += s.cost;
                }
            }

            return total;
        }

        void displayServices(String reservationId) {
            List<AddOnService> services = serviceMap.get(reservationId);

            System.out.println("\nServices for Reservation ID: " + reservationId);

            if (services != null && !services.isEmpty()) {
                for (AddOnService s : services) {
                    System.out.println(s.name + " - " + s.cost);
                }
                System.out.println("Total Add-On Cost: " + calculateTotalCost(reservationId));
            } else {
                System.out.println("No services selected.");
            }
        }
    }

    public class UseCase7AddOnServiceSelection {

        public static void main(String[] args) {

            AddOnServiceManager manager = new AddOnServiceManager();

            String reservation1 = "SINGLEROOM1";
            String reservation2 = "DOUBLEROOM2";

            manager.addService(reservation1, new AddOnService("Breakfast", 200));
            manager.addService(reservation1, new AddOnService("WiFi", 100));

            manager.addService(reservation2, new AddOnService("Airport Pickup", 500));

            System.out.println("Book My Stay App - v7.0");

            manager.displayServices(reservation1);
            manager.displayServices(reservation2);
        }
    }
}

