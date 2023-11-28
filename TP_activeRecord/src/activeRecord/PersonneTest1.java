package activeRecord;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class PersonneTest1 {
    @BeforeEach
    void avant() throws SQLException {
        Personne.deleteTable();
        Personne.createTable();
        Personne p1 = new Personne("Dupont", "Jean");
        p1.save();
        Personne p2 = new Personne("Durand", "Paul");
        p2.save();


    }
    //@AfterEach
    //void apres() throws SQLException {
      //  Personne.deleteTable();
    //}
    @Test
    public void testFindAll() throws Exception {
        ArrayList<Personne> personnes;
        personnes = Personne.findAll();
        assertEquals(2, personnes.size());

    }
}