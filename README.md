# -_sistema_de_consulta_de_cascos_F-26_oracle_19C_-_javafx : .
# Sistema de Consulta de Cascos F-26 (Oracle 19c + JavaFX):

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/39b3eb9b-a48e-4a82-bd51-b152178b59ec" />  

**Proyecto para IntelliJ IDEA** — Permite consultar registros de cascos tipo **Kasas Bombarderos F-26**, con sus provisiones y documentos asociados. Incluye el SQL para crear la tabla y un método para generar automáticamente **9.658** INSERTs de ejemplo (archivo `cascos_data.sql`) .

---

## 🚀 Descripción general:

- **Nombre del programa:** Sistema de Consulta de Cascos F-26
- **Objetivo:** Permitir al usuario buscar y consultar la información de los cascos bombarderos F-26, incluyendo sus provisiones y documentos.
- **Tecnologías:** Oracle 19c, Java 17+, JavaFX (interfaz), JDBC
- **Componentes:**
  - Base de datos Oracle 19c
  - Clases Java: `model.Casco`, `dao.CascoDAO`, `db.ConexionOracle`, `ui.CascosApp` (JavaFX)
  - Archivo para datos de ejemplo: `resources/cascos_data.sql` (9.658 INSERTs, generable automáticamente)


## 🧩 Estructura del proyecto:

```
CascosF26/
├─ src/
│  ├─ dao/
│  │  └─ CascoDAO.java
│  ├─ db/
│  │  └─ ConexionOracle.java
│  ├─ model/
│  │  └─ Casco.java
│  └─ ui/
│     └─ CascosApp.java
├─ resources/
│  └─ cascos_data.sql
└─ README.md
```

---

## 🗃️ 1. SQL para Oracle 19c:

Archivo: `resources/cascos_schema.sql`

```sql
-- Creación de la tabla CASCOS_F26
CREATE TABLE CASCOS_F26 (
    ID_CASCO NUMBER PRIMARY KEY,
    NOMBRE_CASCO VARCHAR2(50),
    MODELO VARCHAR2(20),
    ANIO_FABRICACION NUMBER,
    PROVISIONES VARCHAR2(100),
    DOCUMENTOS VARCHAR2(100)
);

-- Índice ejemplo (opcional)
CREATE INDEX IDX_CASCOS_MODELO ON CASCOS_F26(MODELO);
```

### 🔹 Ejemplo de INSERTs (dos primeros registros)

```sql
INSERT INTO CASCOS_F26 (ID_CASCO, NOMBRE_CASCO, MODELO, ANIO_FABRICACION, PROVISIONES, DOCUMENTOS)
VALUES (1, 'Casco F-26 Serie 0001', 'KASA-F26', 2020, 'Kit emergencia - radar - oxígeno', 'Manual, Certificación 2020');

INSERT INTO CASCOS_F26 (ID_CASCO, NOMBRE_CASCO, MODELO, ANIO_FABRICACION, PROVISIONES, DOCUMENTOS)
VALUES (2, 'Casco F-26 Serie 0002', 'KASA-F26', 2020, 'Kit emergencia - radar - oxígeno', 'Manual, Certificación 2020');

-- ... hasta 9658
```

> **Nota:** en lugar de pegar 9.658 líneas en este README, incluyo más abajo un *script de generación* (Python y SQL) que crea `resources/cascos_data.sql` con los 9.658 INSERTs listos para importar en Oracle.

---

## 💾 2. Clase de conexión: `ConexionOracle.java`:

Archivo: `src/db/ConexionOracle.java`

```java
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionOracle {
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:XE"; // ajustar según su SID/servicio
    private static final String USUARIO = "system";
    private static final String PASSWORD = "oracle";

    public static Connection conectar() {
        try {
            return DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error al conectar con Oracle: " + e.getMessage());
            return null;
        }
    }
}
```

---

## 🧠 3. Modelo: `Casco.java`:

Archivo: `src/model/Casco.java`

```java
package model;

public class Casco {
    private int idCasco;
    private String nombreCasco;
    private String modelo;
    private int anioFabricacion;
    private String provisiones;
    private String documentos;

    public Casco(int idCasco, String nombreCasco, String modelo, int anioFabricacion, String provisiones, String documentos) {
        this.idCasco = idCasco;
        this.nombreCasco = nombreCasco;
        this.modelo = modelo;
        this.anioFabricacion = anioFabricacion;
        this.provisiones = provisiones;
        this.documentos = documentos;
    }

    // Getters y Setters
    public int getIdCasco() { return idCasco; }
    public String getNombreCasco() { return nombreCasco; }
    public String getModelo() { return modelo; }
    public int getAnioFabricacion() { return anioFabricacion; }
    public String getProvisiones() { return provisiones; }
    public String getDocumentos() { return documentos; }

    public void setIdCasco(int idCasco) { this.idCasco = idCasco; }
    public void setNombreCasco(String nombreCasco) { this.nombreCasco = nombreCasco; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setAnioFabricacion(int anioFabricacion) { this.anioFabricacion = anioFabricacion; }
    public void setProvisiones(String provisiones) { this.provisiones = provisiones; }
    public void setDocumentos(String documentos) { this.documentos = documentos; }
}
```

---

## 🔍 4. DAO: `CascoDAO.java`:

Archivo: `src/dao/CascoDAO.java`

```java
package dao;

import db.ConexionOracle;
import model.Casco;

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
```

---

## 🖥️ 5. Interfaz gráfica: `CascosApp.java` (JavaFX):

Archivo: `src/ui/CascosApp.java`

```java
package ui;

import dao.CascoDAO;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Casco;

public class CascosApp extends Application {

    private TableView<Casco> tabla = new TableView<>();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Consulta de Cascos KASA F-26");

        TableColumn<Casco, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(f -> new javafx.beans.property.SimpleIntegerProperty(f.getValue().getIdCasco()).asObject());

        TableColumn<Casco, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getNombreCasco()));

        TableColumn<Casco, String> colModelo = new TableColumn<>("Modelo");
        colModelo.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getModelo()));

        TableColumn<Casco, String> colProv = new TableColumn<>("Provisiones");
        colProv.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getProvisiones()));

        TableColumn<Casco, String> colDocs = new TableColumn<>("Documentos");
        colDocs.setCellValueFactory(f -> new javafx.beans.property.SimpleStringProperty(f.getValue().getDocumentos()));

        tabla.getColumns().addAll(colId, colNombre, colModelo, colProv, colDocs);

        Button btnCargar = new Button("Consultar cascos");
        btnCargar.setOnAction(e -> {
            CascoDAO dao = new CascoDAO();
            tabla.getItems().setAll(dao.consultarCascos());
        });

        VBox root = new VBox(10, btnCargar, tabla);
        root.setPadding(new javafx.geometry.Insets(10));
        stage.setScene(new Scene(root, 900, 600));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
```

---

## ⚙️ 6. Cómo generar `cascos_data.sql` con 9.658 INSERTs (script sugerido):

A continuación hay dos opciones: **a)** script Python local que genera el archivo `resources/cascos_data.sql`, o **b)** bloque PL/SQL que inserta los registros con un `FOR` en el servidor (útil para no transferir un archivo grande).

### Opción A — Generar archivo SQL con Python (recomendado local)

Archivo: `tools/generar_cascos_sql.py`

```python
# generar_cascos_sql.py
# Ejecuta: python generar_cascos_sql.py > resources/cascos_data.sql

N = 9658

def format_provisiones(i):
    return "Kit emergencia - radar - oxígeno"

def format_documentos(i):
    return "Manual, Certificación " + ("2020" if i % 2 == 0 else "2021")

print("-- Archivo generado con {} INSERTs para la tabla CASCOS_F26".format(N))
for i in range(1, N+1):
    nombre = f"Casco F-26 Serie {i:04d}"
    modelo = "KASA-F26"
    anio = 2020 + (i % 6)  # ejemplo: 2020..2025
    prov = format_provisiones(i)
    docs = format_documentos(i)
    sql = ("INSERT INTO CASCOS_F26 (ID_CASCO, NOMBRE_CASCO, MODELO, ANIO_FABRICACION, PROVISIONES, DOCUMENTOS) VALUES ("
           f"{i}, ''{nombre}'', ''{modelo}'', {anio}, ''{prov}'', ''{docs}'' );")
    # Nota: usamos '' para escapar ' en SQL; cuando redirija a un archivo, ajuste según su herramienta
    print(sql)
```

> Ejecuta `python generar_cascos_sql.py > resources/cascos_data.sql` para crear el archivo listo para importar. (Si usas Windows PowerShell, redirecciona igual).


### Opción B — Generación desde PL/SQL (ejecutar en Oracle SQL*Plus o SQL Developer)

```sql
DECLARE
  N PLS_INTEGER := 9658;
  i PLS_INTEGER;
BEGIN
  FOR i IN 1..N LOOP
    INSERT INTO CASCOS_F26 (ID_CASCO, NOMBRE_CASCO, MODELO, ANIO_FABRICACION, PROVISIONES, DOCUMENTOS)
    VALUES (i,
            'Casco F-26 Serie ' || TO_CHAR(i,'FM0000'),
            'KASA-F26',
            2020 + MOD(i,6),
            'Kit emergencia - radar - oxígeno',
            'Manual, Certificación ' || CASE WHEN MOD(i,2)=0 THEN '2020' ELSE '2021' END);
  END LOOP;
  COMMIT;
END;
/
```

> **Advertencia:** ejecutar el bloque PL/SQL insertará todos los registros directamente en la base de datos. Asegúrate de tener espacio y permisos.

---

## 🔧 7. Instrucciones de ejecución (resumen):

1. Crear la tabla en Oracle ejecutando `resources/cascos_schema.sql`.
2. Generar y/o importar `resources/cascos_data.sql` con 9.658 INSERTs (ver script Python o ejecutar PL/SQL).
3. Abrir el proyecto en IntelliJ IDEA.
4. Configurar SDK a Java 17+ y asegurarse de tener JavaFX en el classpath/module-path (dependencia o SDK con JavaFX).
5. Ajustar los parámetros de `ConexionOracle.java` (URL, usuario, password) a su entorno.
6. Ejecutar `src/ui/CascosApp.java` → aparecerá la ventana JavaFX y podrá consultar la tabla con el botón "Consultar cascos".

---

## 🔐 8. Notas y recomendaciones:

- Para producción, no use `system` como usuario de la aplicación. Cree un usuario/schema dedicado y otorgue permisos mínimos.
- Ajuste `VARCHAR2(100)` si espera textos más largos (o use CLOB para documentación extensa).
- Para mejorar rendimiento con 9.658 registros: paginación en el GUI, consultas con `LIMIT`/`ROWNUM`, y uso de índices.
- Si usa módulos Java (module-info.java) añada los `requires` para `java.sql` y `javafx.*`.

--- :  .  
