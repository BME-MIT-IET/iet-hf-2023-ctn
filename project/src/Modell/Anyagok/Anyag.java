package Modell.Anyagok;

import java.io.Serializable;

/**
 * Aminosav és Nukleotid őszosztálya, ezzel lehet majd ágenseket készíteni. Megadott
 * mennyiséget képes a játékos cipelni magával.
 */
public abstract class Anyag implements Cloneable, Serializable {

    public int getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(int mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    /**
     * megadja, hogy az adott anyagból mennyi van
     */
    protected int mennyiseg;

    /**
     * Ezen metódussal az adott anyag más anyagokkal képes
     * egyesülni. Két anyagból egy anyag keletkezik aminek a mennyisége a kettő összege lesz.
     * @param other
     * @return
     */
    public Anyag merge(Anyag other) {
        return null;
    }

    @Override
    public abstract Anyag clone();

    @Override
    public String toString() {
        return "Anyag{" + "mennyiseg=" + mennyiseg + '}';
    }
}
