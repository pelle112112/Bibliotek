package dat.bibliotek.persistence;

import dat.bibliotek.entities.Bruger;
import dat.bibliotek.exceptions.DatabaseException;

public interface IBrugerMapper
{
    public Bruger login(String email, String kodeord) throws DatabaseException;
    public Bruger opretBruger(String email, String kodeord, String rolle) throws DatabaseException;
}
