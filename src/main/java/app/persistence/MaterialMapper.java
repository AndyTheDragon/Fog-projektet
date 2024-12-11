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

    public static List<IMaterials> getMaterialOfType(String descriptionType, ConnectionPool connectionPool) throws DatabaseException
    {
        List<IMaterials> materialsList = new ArrayList<>();
        String sql = "SELECT m.material_id, m.material_name, m.width, m.height, m.length,m.unit, f.description, f.material_type FROM carport_material AS m" +
                " INNER JOIN carport_material_function ON m.material_id = carport_material_function.material_id" +
                " INNER JOIN material_function AS f ON carport_material_function.function_id=f.function_id" +
                " WHERE f.description LIKE ?" +
                " ORDER BY m.length ASC";

        try (Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, "%" + descriptionType + "%");
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    int height = rs.getInt("height");
                    int width = rs.getInt("width");
                    int length = rs.getInt("length");
                    int materialId = rs.getInt("material_id");
                    String unit = rs.getString("unit");
                    String materialName = rs.getString("material_name");
                    String description = rs.getString("description");
                    String materialType = rs.getString("material_type");
                    if(materialType.equals("wood"))
                    {
                        materialsList.add(new ConstructionWood(height, width, length, unit, materialName, description, 0, materialId));
                    }
                    if(materialType.equals("screw"))
                    {
                        materialsList.add(new BoltsScrewsBrackets(width, length, materialName,0, unit,  description,  materialId));
                    }
                    if(materialType.equals("roof"))
                    {
                        materialsList.add(new RoofCovering(length, width,0,  materialName, unit, description, materialId));
                    }
                }
            } catch (SQLException e) {
                throw new DatabaseException("Message "+ e.getMessage());
            }

        return materialsList;
    }

    public static List<IMaterials> getMaterialOfTypeAndLength(String descriptionType, int minLength, ConnectionPool connectionPool ) throws DatabaseException
    {
        List<IMaterials> materialsList = new ArrayList<>();
        String sql = "SELECT m.material_id, m.material_name, m.width, m.height, m.length,m.unit, f.description, f.material_type FROM carport_material AS m" +
                " INNER JOIN carport_material_function ON m.material_id = carport_material_function.material_id" +
                " INNER JOIN material_function AS f ON carport_material_function.function_id=f.function_id" +
                " WHERE f.description = ? AND m.length >= ?"+
                " ORDER BY m.length DESC";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, descriptionType);
            ps.setInt(2, minLength);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int height = rs.getInt("height");
                int width = rs.getInt("width");
                int length = rs.getInt("length");
                int materialId = rs.getInt("material_id");
                String unit = rs.getString("unit");
                String materialName = rs.getString("material_name");
                String description = rs.getString("description");
                String materialType = rs.getString("material_type");
                if(materialType.equals("wood"))
                {
                    materialsList.add(new ConstructionWood(height, width, length, unit, materialName, description, 0, materialId));
                }
                if(materialType.equals("screw"))
                {
                    materialsList.add(new BoltsScrewsBrackets(width, length, materialName,0, unit,  description,  materialId));
                }
                if(materialType.equals("roof"))
                {
                    materialsList.add(new RoofCovering(length, width,0,  materialName, unit, description, materialId));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Message "+ e.getMessage());
        }

        return materialsList;
    }

}
