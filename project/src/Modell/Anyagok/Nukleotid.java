package Modell.Anyagok;

/**
 * Gyűjthető tárgy/anyag, amit a játékos korlátozott mennyiségben vehet magához a
 * raktárakban. Ágens készítéséhez szükséges.
 */
public class Nukleotid extends Anyag{

    public Nukleotid() {}
    public Nukleotid(int mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    /**
     * Ezen metódussal az adott Nukleotid más nukleotidokkal képes egyesülni.
     * @param other
     * @return
     */
    @Override
    public Anyag merge(Anyag other) {
        if(!(other instanceof Nukleotid))
            return null;
        this.mennyiseg+=other.mennyiseg;
        return this;
    }

    @Override
    public Anyag clone() {
        return new Nukleotid(this.mennyiseg);
    }
}
