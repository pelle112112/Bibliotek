package dat.bibliotek.entities;

public class Bog
{
    private int bog_id;
    private String titel;
    private int udgivelsesaar;
    private int forfatter_id;

    public Bog(String titel, int udgivelsesaar, int forfatter_id)
    {
        this.titel = titel;
        this.udgivelsesaar = udgivelsesaar;
        this.forfatter_id = forfatter_id;
    }

    public Bog(int bog_id, String titel, int udgivelsesaar, int forfatter_id)
    {
        this.bog_id = bog_id;
        this.titel = titel;
        this.udgivelsesaar = udgivelsesaar;
        this.forfatter_id = forfatter_id;
    }

    public int getBog_id()
    {
        return bog_id;
    }

    public void setBog_id(int bog_id)
    {
        this.bog_id = bog_id;
    }

    public String getTitel()
    {
        return titel;
    }

    public void setTitel(String titel)
    {
        this.titel = titel;
    }

    public int getUdgivelsesaar()
    {
        return udgivelsesaar;
    }

    public void setUdgivelsesaar(int udgivelsesaar)
    {
        this.udgivelsesaar = udgivelsesaar;
    }

    public int getForfatter_id()
    {
        return forfatter_id;
    }

    public void setForfatter_id(int forfatter_id)
    {
        this.forfatter_id = forfatter_id;
    }

    @Override
    public String toString()
    {
        return "Bog{" +
                "bog_id=" + bog_id +
                ", titel='" + titel + '\'' +
                ", udgivelsesaar=" + udgivelsesaar +
                ", forfatter_id=" + forfatter_id +
                '}';
    }
}
