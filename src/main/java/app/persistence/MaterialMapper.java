package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MaterialMapper
{

    public List<IMaterials> getMaterialOfType(ConnectionPool connectionPool ,String type) throws DatabaseException
    {
        List<IMaterials> materialsList = new ArrayList<>();
        String sql = "SELECT m.material_id, m.material_name, m.width, m.height, m.length, f.description FROM carport_material AS m" +
                " INNER JOIN carport_material_function ON m.material_id = carport_material_function.material_id" +
                " INNER JOIN material_function AS f ON carport_material_function.function_id=f.function_id" +
                " WHERE f.description = ?";

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, type);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int height = rs.getInt("height");
                    int width = rs.getInt("width");
                    int length = rs.getInt("length");
                    int materialId = rs.getInt("material_id");
                    String materialName = rs.getString("material_name");
                    String description = rs.getString("description");
                    materialsList.add(new ConstructionWood(height, width, length, "stk", materialName, description, 0, materialId));
                }
            } catch (SQLException e) {
                throw new DatabaseException("Could not get materials of type: " + type, e);
            }

        return null;
    }

    public List<IMaterials> getMaterialOfTypeAndLength(ConnectionPool connectionPool, String type, int minLength)
    {
        List<IMaterials> materialsList = new ArrayList<>();
        String sql = "SELECT m.material_id, m.material_name, m.width, m.height, m.length, f.description FROM carport_material AS m" +
                " INNER JOIN carport_material_function ON m.material_id = carport_material_function.material_id" +
                " INNER JOIN material_function AS f ON carport_material_function.function_id=f.function_id" +
                " WHERE f.description = ? AND m.length >= ?";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, type);
            ps.setInt(2, minLength);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int height = rs.getInt("height");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                int materialId = rs.getInt("material_id");
                String materialName = rs.getString("material_name");
                String description = rs.getString("description");
                materialsList.add(new ConstructionWood(height, width, length, "stk", materialName, description, 0, materialId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return materialsList;
    }

}
