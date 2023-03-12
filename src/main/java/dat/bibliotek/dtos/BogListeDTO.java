package dat.bibliotek.dtos;

import java.util.Objects;

public class BogListeDTO
{
    private int bogId;
    private String titel;
    private int udgivelsesaar;
    private int forfatterId;
    private String forfatterNavn;

    public BogListeDTO(int bogId, String titel, int udgivelsesaar, int forfatterId, String forfatterNavn)
    {
        this.bogId = bogId;
        this.titel = titel;
        this.udgivelsesaar = udgivelsesaar;
        this.forfatterId = forfatterId;
        this.forfatterNavn = forfatterNavn;
    }

    public int getBogId()
    {
        return bogId;
    }

    public String getTitel()
    {
        return titel;
    }

    public int getUdgivelsesaar()
    {
        return udgivelsesaar;
    }

    public int getForfatterId()
    {
        return forfatterId;
    }

    public String getForfatterNavn()
    {
        return forfatterNavn;
    }

    @Override
    public String toString()
    {
        return "BogListeDTO{" +
                "bogId=" + bogId +
                ", titel='" + titel + '\'' +
                ", udgivelsesaar=" + udgivelsesaar +
                ", forfatterId=" + forfatterId +
                ", forfatterNavn=" + forfatterNavn +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BogListeDTO)) return false;
        BogListeDTO that = (BogListeDTO) o;
        return getBogId() == that.getBogId() && getUdgivelsesaar() == that.getUdgivelsesaar() && getForfatterId() == that.getForfatterId() && getTitel().equals(that.getTitel()) && getForfatterNavn().equals(that.getForfatterNavn());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getBogId(), getTitel(), getUdgivelsesaar(), getForfatterId(), getForfatterNavn());
    }
}
