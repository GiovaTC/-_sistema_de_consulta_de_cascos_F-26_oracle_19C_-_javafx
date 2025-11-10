package com.f26.cascosf26_2.dao;

import com.f26.cascosf26_2.db.ConexionOracle;
import com.f26.cascosf26_2.model.Casco;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CascoDAO {

    public List<Casco> consultarCascos() {
        List<Casco> lista = new ArrayList<>();
        String sql = "SELECT * FROM CASCOS_F26";

        try (Connection conn = ConexionOracle.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Casco(
                        rs.getInt("ID_CASCO"),
                        rs.getString("NOMBRE_CASCO"),
                        rs.getString("MODELO"),
                        rs.getInt("ANIO_FABRICACION"),
                        rs.getString("PROVISIONES"),
                        rs.getString("DOCUMENTOS")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error consultando cascos: " + e.getMessage());
        }

        return lista;
    }
}