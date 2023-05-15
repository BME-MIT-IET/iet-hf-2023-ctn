package Modell.Anyagok;


/**
 * Az egyik Anyag, amit a virológus gyüjthet korlátozott mennyiségben.
 * Ágens készítéséhez szükséges
 * */

public class Aminosav extends Anyag{
    public Aminosav() {}

    public Aminosav(int mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    /**
     * Megkísérli anyagok egyesítését. Azonos anyagok képesek egyesülni.
     * @param other
     * @return
     */
    @Override
    public Anyag merge(Anyag other) {
        if(!(other instanceof  Aminosav)) return null;
        this.mennyiseg+= other.mennyiseg;
        return this;
    }

    @Override
    public Anyag clone(){
        return new Aminosav(this.mennyiseg);
    }
}
