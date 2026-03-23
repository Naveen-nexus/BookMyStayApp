public class BookMyStayApp {
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

    public class UseCase2RoomInitialization {

        public static void main(String[] args) {

            Room single = new SingleRoom();
            Room doub = new DoubleRoom();
            Room suite = new SuiteRoom();

            int singleAvailable = 5;
            int doubleAvailable = 3;
            int suiteAvailable = 2;

            System.out.println("Book My Stay App - v2.1");

            System.out.println("\n--- Room Details ---");

            single.display();
            System.out.println("Available: " + singleAvailable + "\n");

            doub.display();
            System.out.println("Available: " + doubleAvailable + "\n");

            suite.display();
            System.out.println("Available: " + suiteAvailable + "\n");
        }
    }

}

