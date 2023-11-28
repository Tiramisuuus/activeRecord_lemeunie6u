package activeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne( String nom, String prenom){
        this.id = -1;
        this.nom = nom;
        this.prenom = prenom;
    }

    public static ArrayList<Personne> findAll() throws SQLException {
        String SQLPrep = "SELECT * FROM Personne;";
        Connection connect = DBConnection.getInstance().getConnection();
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        ArrayList<Personne> list = new ArrayList<Personne>();
        while(rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
            Personne p = new Personne(nom, prenom);
            p.id = id;
            list.add(p);
        }
        for (Personne p : list) {
            System.out.println(p.toString());        }
        return list;
    }

    public String toString(){
        return "Personne("+this.id+") "+this.nom+", "+this.prenom;
    }
    public static void createTable() throws SQLException {
        String createString = "CREATE TABLE Personne ( " + "ID INTEGER  AUTO_INCREMENT, " + "NOM varchar(40) NOT NULL, " + "PRENOM varchar(40) NOT NULL, " + "PRIMARY KEY (ID))";
        Connection connect = DBConnection.getInstance().getConnection();
        Statement stmt = connect.createStatement();
        stmt.executeUpdate(createString);
    }

    public static void deleteTable() throws SQLException {
        String drop = "DROP TABLE Personne";
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
        String SQLprep = "update Personne set nom=?, prenom=? where id=?;";
        PreparedStatement prep = connect.prepareStatement(SQLprep);
        prep.setString(1, nom);
        prep.setString(2, prenom);
        prep.setInt(3, id);
        prep.execute();

    }
    private void saveNew() throws SQLException {
        String SQLPrep = "INSERT INTO Personne (nom, prenom) VALUES (?,?);";
        Connection connect = DBConnection.getInstance().getConnection();
        PreparedStatement prep = connect.prepareStatement(SQLPrep, Statement.RETURN_GENERATED_KEYS);
        prep.setString(1, nom);
        prep.setString(2, prenom);
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

    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public int getId() {
        return id;
    }
    public static ArrayList<Personne> findByName(String nom) throws SQLException {
        String SQLPrep = "SELECT * FROM Personne WHERE nom = ?;";
        Connection connect = DBConnection.getInstance().getConnection();
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setString(1, nom);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        ArrayList<Personne> list = new ArrayList<Personne>();
        while(rs.next()) {
            String nomn = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int id = rs.getInt("id");
            System.out.println("  -> (" + id + ") " + nom + ", " + prenom);
            Personne p = new Personne(nomn, prenom);
            p.id = id;
            list.add(p);
        }
        for (Personne p : list) {
            System.out.println(p.toString());        }
        return list;
    }
    public static Personne findById(int id) throws SQLException {
        String SQLPrep = "SELECT * FROM Personne WHERE id=?;";
        Personne p = null;
        Connection connect = DBConnection.getInstance().getConnection();
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.execute();
        ResultSet rs = prep1.getResultSet();
        // s'il y a un resultat
        if (rs.next()) {
            String nom = rs.getString("nom");
            String prenom = rs.getString("prenom");
            int idget = rs.getInt("id");
            p = new Personne(nom, prenom);
            p.id = idget;
        }
        return p;




    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    public void delete() throws SQLException {
        String SQLPrep = "DELETE FROM Personne WHERE id=?;";
        Connection connect = DBConnection.getInstance().getConnection();
        PreparedStatement prep1 = connect.prepareStatement(SQLPrep);
        prep1.setInt(1, id);
        prep1.execute();
        id = -1;
    }
}
