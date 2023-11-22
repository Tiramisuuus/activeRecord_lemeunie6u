package activeRecord;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class DBConnectionTest{
    @Test
    public void testGetConnection() throws SQLException {
        Connection c1 = DBConnection.getInstance().getConnection();
        Connection c2 = DBConnection.getInstance().getConnection();
        assertEquals("ça devrait être la même conncetion", c1, c2);
        
    }
}