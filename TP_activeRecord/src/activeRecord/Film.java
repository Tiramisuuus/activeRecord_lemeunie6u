package activeRecord;

import java.sql.*;

public class Film {
    private String titre;
    private int id;
    private int id_real;

    public Film(String titre, Personne real){
        this.titre = titre;
        this.id = -1;
        this.id_real = real.getId();
    }

    private Film(int id, int id_real, String titre){
        this.id = id;
        this.id_real = id_real;
        this.titre = titre;
    }

    public static void createTable() throws SQLException {
        String createString = "CREATE TABLE Film ( " + "ID INTEGER  AUTO_INCREMENT, " + "TITRE varchar(40) NOT NULL, " + "ID_REAL INTEGER NOT NULL, " + "PRIMARY KEY (ID))";
        Connection connect = DBConnection.getInstance().getConnection();
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        String drop = "DROP TABLE Film";
        Connection connect = DBConnection.getInstance().getConnection();
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(drop);
    }

    public void save() throws SQLException {
        if(id > -1){
            update();
        }
        else{
            saveNew();
        }
    }

    private void  update() throws SQLException {
        Connection connect = DBConnection.getInstance().getConnection();
        String SQLprep = "update Film set id=?, titre=?, id_real=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        prep.setInt(1, id);
        prep.setString(2, titre);
        prep.setInt(3, id_real);
        prep.execute();

    }

    private void saveNew() throws SQLException {
        String SQLPrep = "INSERT INTO Film (id, titre, id_real) VALUES (?,?,?);";
        Connection connect = DBConnection.getInstance().getConnection();
        PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setInt(1, id);
        prep.setString(2, titre);
        prep.setInt(3, id_real);
        prep.executeUpdate();

        // recuperation de la derniere ligne ajoutee (auto increment)
        // recupere le nouvel id
        int autoInc = -1;
        ResultSet rs = prep.getGeneratedKeys();
        if (rs.next()) {
            autoInc = rs.getInt(1);
        }
        this.id = autoInc;
    }

    public static Film findById(int id) throws SQLException {
        String SQLPrep = "SELECT * FROM Film WHERE id=?;";
        Connection connect = DBConnection.getInstance().getConnection();
        PreparedStatement prep = connect.prepareStatement(SQLPrep);
        prep.setInt(1, id);
        ResultSet rs = prep.executeQuery();
        if(rs.next()){
            return new Film(rs.getInt("id"), rs.getInt("id_real"), rs.getString("titre"));
        }
        return null;
    }

    public Personne getRealisateur() throws SQLException {
        return Personne.findById(id_real);

    }

    public String toString(){
        return "Film("+this.id+") "+this.titre+", "+this.id_real;
    }

    public String getTitre() {
        return titre;
    }

    public int getId() {
        return id;
    }

    public int getId_real() {
        return id_real;
    }

}
