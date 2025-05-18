import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BusDAO {

    public List<String> getAllBusNumbers() {
        List<String> busNumbers = new ArrayList<>();
        String query = "SELECT bus_no FROM buses";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                busNumbers.add(rs.getString("bus_no"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return busNumbers;
    }
}
