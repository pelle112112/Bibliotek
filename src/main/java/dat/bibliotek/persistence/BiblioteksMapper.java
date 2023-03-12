package dat.bibliotek.persistence;

import dat.bibliotek.dtos.BogListeDTO;
import dat.bibliotek.dtos.UdlaanDTO;
import dat.bibliotek.entities.Bog;
import dat.bibliotek.entities.Laaner;
import dat.bibliotek.entities.Udlaan;
import dat.bibliotek.exceptions.DatabaseException;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.List;

public class BiblioteksMapper implements IBiblioteksMapper
{
    private ConnectionPool connectionPool;

    public BiblioteksMapper(ConnectionPool connectionPool)
    {
        this.connectionPool = connectionPool;
    }

    @Override
    public Laaner hentLaanerUdFraId(int laanerId) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "laanerId=" + laanerId);
        Laaner laaner = null;
        String sql = "select * from Laaner inner join postnummer using(postnr) " +
                "where laaner_id = ?";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, laanerId);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    laanerId = rs.getInt("laaner_id");
                    String navn = rs.getString("navn");
                    String adresse = rs.getString("adresse");
                    int postnummer = rs.getInt("postnr");
                    String by = rs.getString("by");
                    laaner = new Laaner(laanerId, navn, adresse, postnummer, by);
                } else
                {
                    throw new DatabaseException("Låner med laaner_id = " + laanerId + " findes ikke");
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl i databaseforespørgsel for laaner_id = " + laanerId);
        }
        return laaner;
    }

    @Override
    public List<Laaner> hentAlleLaanere() throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        List<Laaner> laanerList = new ArrayList<>();

        String sql = "SELECT * FROM laaner inner join postnummer p using(postnr);";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int laanerId = rs.getInt("laaner_id");
                    String navn = rs.getString("navn");
                    String adresse = rs.getString("adresse");
                    int postnr = rs.getInt("postnr");
                    String by = rs.getString("by");
                    Laaner newLaaner = new Laaner(laanerId, navn, adresse, postnr, by);
                    laanerList.add(newLaaner);
                }
            }
        } catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af lånere fra databasen");
        }
        return laanerList;
    }

    @Override
    public List<BogListeDTO> hentAlleBoegerOgDeresForfattere() throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");

        List<BogListeDTO> boglisteDTOList = new ArrayList<>();

        String sql = "SELECT bog.bog_id, bog.titel, bog.udgivelsesaar, f.forfatter_id, f.navn " +
                "FROM bog " +
                "INNER JOIN forfatter f " +
                "USING(forfatter_id)";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int bogId = rs.getInt("bog_id");
                    String titel = rs.getString("titel");
                    int udgivelsesaar = rs.getInt("udgivelsesaar");
                    int forfatterId = rs.getInt("forfatter_id");
                    String forfatterNavn = rs.getString("navn");
                    BogListeDTO dto = new BogListeDTO(bogId, titel, udgivelsesaar, forfatterId, forfatterNavn);
                    boglisteDTOList.add(dto);
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af bøger og forfattere fra databasen");
        }
        return boglisteDTOList;
    }

    @Override
    public List<UdlaanDTO> hentAlleUdlaan() throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        List<UdlaanDTO> hentAlleUdlaanDTOList = new ArrayList<>();

        String sql = "select forfatter_id, bog_id, laaner_id, l.navn as laaner_navn, " +
                "adresse, postnr, dato, titel, udgivelsesaar, f.navn as forfatter_navn " +
                "from laaner l " +
                "inner join udlaan using(laaner_id) inner join bog " +
                "using(bog_id) inner join forfatter f using(forfatter_id)";

        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ResultSet rs = ps.executeQuery();
                while (rs.next())
                {
                    int forfatter_id = rs.getInt("forfatter_id");
                    int bogId = rs.getInt("bog_id");
                    int laanerId = rs.getInt("laaner_id");
                    String laanerNavn = rs.getString("laaner_navn");
                    String adresse = rs.getString("adresse");
                    int postnr = rs.getInt("postnr");
                    Date dato = rs.getDate("dato");
                    String titel = rs.getString("titel");
                    int udgivelsesaar = rs.getInt("udgivelsesaar");
                    String forfatterNavn = rs.getString("forfatter_navn");

                    UdlaanDTO dto = new UdlaanDTO(forfatter_id, bogId, laanerId,
                            laanerNavn, adresse, postnr, dato, titel, udgivelsesaar, forfatterNavn);
                    hentAlleUdlaanDTOList.add(dto);
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Fejl under indlæsning af udlaan fra databasen");
        }
        return hentAlleUdlaanDTOList;
    }

    @Override
    public Laaner opretNyLaaner(Laaner laaner) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        boolean result = false;
        int newId = 0;
        String sql = "insert into laaner (navn, adresse,  postnr) values (?,?,?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
            {
                ps.setString(1, laaner.getNavn());
                ps.setString(2, laaner.getAdresse());
                ps.setInt(3, laaner.getPostnummer());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1)
                {
                    result = true;
                } else
                {
                    throw new DatabaseException("Låner med navn = " + laaner.getNavn() + " kunne ikke oprettes i databasen");
                }
                ResultSet idResultset = ps.getGeneratedKeys();
                if (idResultset.next())
                {
                    newId = idResultset.getInt(1);
                    laaner.setLaaner_id(newId);
                } else
                {
                    laaner = null;
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Kunne ikke indsætte låner i databasen");
        }
        return laaner;
    }

    @Override
    public boolean opretNytUdlaan(Udlaan udlaan) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        boolean result = false;
        int newId = 0;
        String sql = "insert into udlaan (bog_id, laaner_id, dato) values (?,?,?)";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, udlaan.getBog_id());
                ps.setInt(2, udlaan.getLaaner_id());
                ps.setDate(3, udlaan.getDato());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1)
                {
                    result = true;
                } else
                {
                    throw new DatabaseException("Udlån for låner med laaner_id = " +
                            udlaan.getLaaner_id() + " og bog_id = " + udlaan.getBog_id() +
                            " kunne ikke oprettes i databasen");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Udlån for låner med laaner_id = " +
                    udlaan.getLaaner_id() + " og bog_id = " + udlaan.getBog_id() +
                    " kunne ikke oprettes i databasen");
        }
        return result;
    }

    @Override
    public boolean fjernUdlaan(int laanerId, int bogId) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        boolean result = false;
        String sql = "delete from udlaan where laaner_id = ? and bog_id = ?";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setInt(1, laanerId);
                ps.setInt(2, bogId);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1)
                {
                    result = true;
                } else
                {
                    throw new DatabaseException("Udlån med laaner_id = " + laanerId + " og " +
                            "bog_id = " + bogId + " kunne ikke fjernes");
                }
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Udlån med laaner_id = " + laanerId + " og " +
                    "bog_id = " + bogId + " kunne ikke fjernes");
        }
        return result;
    }

    @Override
    public boolean opdaterBog(Bog bog) throws DatabaseException
    {
        Logger.getLogger("web").log(Level.INFO, "");
        boolean result = false;
        String sql = "UPDATE bog SET titel = ?, udgivelsesaar = ?, forfatter_id = ? " +
                "WHERE bog_id = ?";
        try (Connection connection = connectionPool.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, bog.getTitel());
                ps.setInt(2, bog.getUdgivelsesaar());
                ps.setInt(3, bog.getForfatter_id());
                ps.setInt(4, bog.getBog_id());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected == 1){
                    result = true;
                } else {
                    throw new DatabaseException("Kunne ikke opdatere bog med bog_id = " + bog.getBog_id());
                }
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Kunne ikke opdatere bog med bog_id = " + bog.getBog_id());
        }
        return result;
    }
}
