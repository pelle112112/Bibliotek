package dat.bibliotek.persistence;

import dat.bibliotek.dtos.BogListeDTO;
import dat.bibliotek.dtos.UdlaanDTO;
import dat.bibliotek.entities.Bog;
import dat.bibliotek.entities.Laaner;
import dat.bibliotek.entities.Udlaan;
import dat.bibliotek.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class BiblioteksMapperTest
{
    private final static String USER = "root";
    private final static String PASSWORD = "root";
    private final static String URL = "jdbc:mysql://localhost:3306/bibliotek_test?serverTimezone=CET&allowPublicKeyRetrieval=true&useSSL=false";

    private static ConnectionPool connectionPool;
    private static BiblioteksMapper biblioteksMapper;

    @BeforeAll
    public static void setUpClass() {
        connectionPool = new ConnectionPool(USER, PASSWORD, URL);
        biblioteksMapper = new BiblioteksMapper(connectionPool);
    }

    @BeforeEach
    void setUp()
    {
        try (Connection testConnection = connectionPool.getConnection()) {
            try (Statement stmt = testConnection.createStatement() ) {
                // Remove all rows from all tables
                stmt.execute("delete from udlaan");
                stmt.execute("delete from laaner");
                stmt.execute("delete from postnummer");
                stmt.execute("delete from bog");
                stmt.execute("delete from forfatter");
                // Insert a well known number of members into the zip and member tables

                stmt.execute("ALTER TABLE `laaner` DISABLE KEYS");
                stmt.execute("ALTER TABLE `laaner` AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE `bog` DISABLE KEYS");
                stmt.execute("ALTER TABLE `bog` AUTO_INCREMENT = 1");
                stmt.execute("ALTER TABLE `forfatter` DISABLE KEYS");
                stmt.execute("ALTER TABLE `forfatter` AUTO_INCREMENT = 1");

                stmt.execute("insert into forfatter (forfatter_id, navn) " +
                        "values (1, 'Karen Blixen'),(2,'Johannes V. Jensen'), (3,'Jostein Gaader') ");
                stmt.execute("insert into bog (bog_id, titel, udgivelsesaar, forfatter_id)\n" +
                        "values (1,'Den lange rejse',1977,2), (2,'Vintereventyr',1964,1), (3, 'Sofies Verden', 1981,3)");
                stmt.execute("insert into postnummer (postnr, `by`)\n" +
                        "values (7490,'Aulum'), (7500,'Hobro'),(8520,'Lystrup')");
                stmt.execute("insert into laaner (laaner_id, navn, adresse, postnr)\n" +
                        "values (1,'Peter Jensen','Ringgaden 10',7500),\n" +
                        "(2,'Mathilde Nielsen','Østervej 22',7500),\n" +
                        "(3,'Mattias Bruun','Ellevang 12',7490)\n;");
                stmt.execute("insert into udlaan (bog_id, laaner_id, dato)\n" +
                        "values (1,1,'2000-11-21'),\n" +
                        "(1,2,'2000-11-24'),\n" +
                        "(2,1,'2000-11-21'),\n" +
                        "(2,3,'2000-11-25'),\n" +
                        "(3,1,'2000-11-21'),\n" +
                        "(3,2,'2000-11-24')\n");
                stmt.execute("ALTER TABLE `laaner` ENABLE KEYS");
                stmt.execute("ALTER TABLE `bog` ENABLE KEYS");
                stmt.execute("ALTER TABLE `forfatter` ENABLE KEYS");
            }
        } catch (SQLException throwables) {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @Test
    void testConnection() throws SQLException
    {
        Connection connection = connectionPool.getConnection();
        assertNotNull(connection);
        if (connection != null)
        {
            connection.close();
        }
    }

    @Test
    void hentLaanerUdFraId() throws DatabaseException
    {
        Laaner forventetLaaner = new Laaner(1,"Peter Jensen","Ringgaden 10",7500,"Hobro");
        Laaner faktiskLaaner = biblioteksMapper.hentLaanerUdFraId(1);
        assertEquals(forventetLaaner, faktiskLaaner);
    }

    @Test
    void hentForkertLaanerUdFraId() throws DatabaseException
    {
        Laaner forventetLaaner = new Laaner(1,"Peter Jensen","Ringgaden 10",7500,"Hobro");
        Laaner faktiskLaaner = biblioteksMapper.hentLaanerUdFraId(2);
        assertNotEquals(forventetLaaner, faktiskLaaner);
    }

    @Test
    void hentUgyldigLaanerUdFraId() throws DatabaseException
    {
        assertThrows(DatabaseException.class, ()-> biblioteksMapper.hentLaanerUdFraId(12));
    }

    @Test
    void hentAlleLaanere() throws DatabaseException
    {
        List<Laaner> forventetLaanerList = new ArrayList<>();
        forventetLaanerList.add(new Laaner(1,"Peter Jensen","Ringgaden 10",7500,"Hobro"));
        forventetLaanerList.add(new Laaner(2,"Mathilde Nielsen","Østervej 22",7500,"Hobro"));
        forventetLaanerList.add(new Laaner(3,"Mattias Bruun","Ellevang 12",7490, "Aulum"));
        List<Laaner> laanerList = biblioteksMapper.hentAlleLaanere();
        assertEquals(3, laanerList.size());
        assertThat(laanerList, containsInAnyOrder(forventetLaanerList.toArray()));  // bruger Hamcrest matcher
    }

    @Test
    void hentAlleBoegerOgDeresForfattere() throws DatabaseException
    {
        List<BogListeDTO> bogListeDTOList = biblioteksMapper.hentAlleBoegerOgDeresForfattere();
        assertEquals(3, bogListeDTOList.size());
        assertEquals("Den lange rejse", bogListeDTOList.get(0).getTitel());
        assertEquals("Vintereventyr", bogListeDTOList.get(1).getTitel());
        assertEquals("Sofies Verden", bogListeDTOList.get(2).getTitel());
    }

    @Test
    void hentAlleUdlaan() throws DatabaseException
    {
        List<UdlaanDTO> udlaanDTOList = biblioteksMapper.hentAlleUdlaan();
        assertEquals(6, udlaanDTOList.size());

        List<UdlaanDTO> forventetUdlaanDTOList = new ArrayList<>();
        forventetUdlaanDTOList.add(new UdlaanDTO(1, 2, 3,"Mattias Bruun","Ellevang 12", 7490,Date.valueOf("2000-11-25"),"Vintereventyr", 1964,"Karen Blixen"));
        forventetUdlaanDTOList.add(new UdlaanDTO(3, 3, 1,"Peter Jensen","Ringgaden 10", 7500,Date.valueOf("2000-11-21"),"Sofies Verden", 1981,"Jostein Gaader"));
        forventetUdlaanDTOList.add(new UdlaanDTO(3, 3, 2,"Mathilde Nielsen","Østervej 22", 7500,Date.valueOf("2000-11-24"),"Sofies Verden", 1981,"Jostein Gaader"));
        forventetUdlaanDTOList.add(new UdlaanDTO(2, 1, 1,"Peter Jensen","Ringgaden 10", 7500, Date.valueOf("2000-11-21"),"Den lange rejse", 1977,"Johannes V. Jensen"));
        forventetUdlaanDTOList.add(new UdlaanDTO(2, 1, 2,"Mathilde Nielsen","Østervej 22", 7500,Date.valueOf("2000-11-24"),"Den lange rejse", 1977,"Johannes V. Jensen"));
        forventetUdlaanDTOList.add(new UdlaanDTO(1, 2, 1,"Peter Jensen","Ringgaden 10", 7500,Date.valueOf("2000-11-21"),"Vintereventyr", 1964,"Karen Blixen"));

        assertThat(udlaanDTOList, containsInAnyOrder(forventetUdlaanDTOList.toArray()) );

    }

    @Test
    void opretNyLaaner() throws DatabaseException
    {
        Laaner laaner = new Laaner("Jens Hansen", "Bygaden 32", 7500, "Hobro");
        Laaner nyLaaner = biblioteksMapper.opretNyLaaner(laaner);
        List<Laaner> laanerList = biblioteksMapper.hentAlleLaanere();
        assertEquals(4, laanerList.size());
        assertEquals(nyLaaner, biblioteksMapper.hentLaanerUdFraId(nyLaaner.getLaaner_id()));
    }

    @Test
    void opretNytUdlaan() throws DatabaseException
    {
        // bog_id, laaner_id, dato
        Udlaan udlaan = new Udlaan(1,3,Date.valueOf("2022-03-18"));
        boolean result = biblioteksMapper.opretNytUdlaan(udlaan);
        assertTrue(result);
        List<UdlaanDTO> udlaanDTOList = biblioteksMapper.hentAlleUdlaan();
        assertEquals(7, udlaanDTOList.size());
    }

    @Test
    void fjernUdlaan() throws DatabaseException
    {
        UdlaanDTO udlaanDTO = new UdlaanDTO(2,1,1,"Peter Jensen", "Ringgaden 10", 7500,
                Date.valueOf("2000-11-21"), "Den lange rejse", 1977, "Johannes V. Jensen");
        boolean result = biblioteksMapper.fjernUdlaan(1,1);
        assertTrue(result);
        List<UdlaanDTO> udlaanDTOList = biblioteksMapper.hentAlleUdlaan();
        assertEquals(5, udlaanDTOList.size());
        assertThat(udlaanDTOList, not(hasItem(udlaanDTO)));
    }

    @Test
    void opdaterBog() throws DatabaseException
    {
        Bog bog = new Bog(1, "Den lange rejse", 1978, 2);
        boolean result = biblioteksMapper.opdaterBog(bog);
        assertTrue(result);
        List<BogListeDTO> bogListeDTOList = biblioteksMapper.hentAlleBoegerOgDeresForfattere();
        BogListeDTO opdateretBogListDTO = new BogListeDTO(1, "Den lange rejse", 1978, 2, "Johannes V. Jensen");
        assertThat(bogListeDTOList, hasItem(opdateretBogListDTO));

    }
}