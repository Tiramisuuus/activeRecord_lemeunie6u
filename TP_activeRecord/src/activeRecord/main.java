package activeRecord;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws SQLException {
        Personne.createTable();
        ArrayList<Personne> list = Personne.findAll();
        for (Personne p : list) {
            System.out.println(p);
        }
        Personne.deleteTable();
    }
}

