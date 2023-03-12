package dat.bibliotek.dtos;

import java.sql.Date;
import java.util.Objects;

public class UdlaanDTO
{
    private int forfatter_id;
    private int bogId;
    private int laanerId;
    private String laanerNavn;
    private String adresse;
    private int postnr;
    private Date dato;
    private String titel;
    private int udgivelsesaar;
    private String forfatterNavn;

    public UdlaanDTO(int forfatter_id, int bogId, int laanerId, String laanerNavn, String adresse, int postnr, Date dato, String titel, int udgivelsesaar, String forfatterNavn)
    {
        this.forfatter_id = forfatter_id;
        this.bogId = bogId;
        this.laanerId = laanerId;
        this.laanerNavn = laanerNavn;
        this.adresse = adresse;
        this.postnr = postnr;
        this.dato = dato;
        this.titel = titel;
        this.udgivelsesaar = udgivelsesaar;
        this.forfatterNavn = forfatterNavn;
    }

    @Override
    public String toString()
    {
        return "UdlaanDTO{" +
                "forfatter_id='" + forfatter_id + '\'' +
                ", bogId=" + bogId +
                ", laanerId=" + laanerId +
                ", laanerNavn='" + laanerNavn + '\'' +
                ", adresse='" + adresse + '\'' +
                ", postnr=" + postnr +
                ", dato=" + dato +
                ", titel='" + titel + '\'' +
                ", udgivelsesaar=" + udgivelsesaar +
                ", forfatterNavn='" + forfatterNavn + '\'' +
                '}';
    }

    public int getForfatter_id()
    {
        return forfatter_id;
    }

    public int getBogId()
    {
        return bogId;
    }

    public int getLaanerId()
    {
        return laanerId;
    }

    public String getLaanerNavn()
    {
        return laanerNavn;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public int getPostnr()
    {
        return postnr;
    }

    public Date getDato()
    {
        return dato;
    }

    public String getTitel()
    {
        return titel;
    }

    public int getUdgivelsesaar()
    {
        return udgivelsesaar;
    }

    public String getForfatterNavn()
    {
        return forfatterNavn;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof UdlaanDTO)) return false;
        UdlaanDTO udlaanDTO = (UdlaanDTO) o;
        return getForfatter_id() == udlaanDTO.getForfatter_id() && getBogId() == udlaanDTO.getBogId() && getLaanerId() == udlaanDTO.getLaanerId() && getPostnr() == udlaanDTO.getPostnr() && getUdgivelsesaar() == udlaanDTO.getUdgivelsesaar() && getLaanerNavn().equals(udlaanDTO.getLaanerNavn()) && getAdresse().equals(udlaanDTO.getAdresse()) && getDato().equals(udlaanDTO.getDato()) && getTitel().equals(udlaanDTO.getTitel()) && getForfatterNavn().equals(udlaanDTO.getForfatterNavn());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getForfatter_id(), getBogId(), getLaanerId(), getLaanerNavn(), getAdresse(), getPostnr(), getDato(), getTitel(), getUdgivelsesaar(), getForfatterNavn());
    }
}
