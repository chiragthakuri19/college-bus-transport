public class Main {
    public static void main(String[] args) {
        BusDAO busDAO = new BusDAO();
        System.out.println("Bus numbers in DB:");
        for (String busNo : busDAO.getAllBusNumbers()) {
            System.out.println(busNo);
        }
    }
}
