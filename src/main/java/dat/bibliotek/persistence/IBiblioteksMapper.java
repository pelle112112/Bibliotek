package dat.bibliotek.persistence;

import dat.bibliotek.dtos.BogListeDTO;
import dat.bibliotek.dtos.UdlaanDTO;
import dat.bibliotek.entities.Bog;
import dat.bibliotek.entities.Laaner;
import dat.bibliotek.entities.Udlaan;
import dat.bibliotek.exceptions.DatabaseException;


import java.util.List;

public interface IBiblioteksMapper
{
    // Find en låner ud fra et specifikt laaner_id.
    public Laaner hentLaanerUdFraId(int laaner_id) throws DatabaseException;

    // Find alle lånere, og vis deres data inklusive postnummer og by.
    public List<Laaner> hentAlleLaanere() throws DatabaseException;

    // Find alle bøger, og deres forfattere
    public List<BogListeDTO> hentAlleBoegerOgDeresForfattere()  throws DatabaseException;

    // Find alle lånere og de bøger de har lånt. Medtag også bogtitler og evt. forfatter
    public List<UdlaanDTO> hentAlleUdlaan() throws DatabaseException;

    // Indsæt en nyt låner (insert)
    public Laaner opretNyLaaner(Laaner laaner) throws DatabaseException;

    // Opret et nyt udlån af en bog (insert)
    public boolean opretNytUdlaan(Udlaan udlaan) throws DatabaseException;

    // Fjern et udlån (delete)
    public boolean fjernUdlaan(int laaner_id, int bog_id) throws DatabaseException;

    // Rediger en bogtitel (update)
    public boolean opdaterBog(Bog bog) throws DatabaseException;
}
