package Modell.Palya;

import Logika.Virologus;
import Modell.Agensek.Agens;
import Modell.Anyagok.Anyag;
import Modell.Genetika.GenetikaiKod;
import Modell.Vedofelszereles.Vedofelszereles;

import java.io.Serializable;
import java.util.*;

//KB UML HELYES!

/**
 *A virológus kísérletére amennyiben rendelkezésre
 * áll, az anyag paraméterben megfogalmazott igénynek megfelelően anyagot ad a
 * virológusnak.
 * */
public class TeruletiElem implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    int id;
    /**
     * A virológus kísérletére amennyiben rendelkezésre
     * áll, az anyag paraméterben megfogalmazott igénynek megfelelően anyagot ad a
     * virológusnak.
     */
    private Set<Virologus> ittJartak;

    /**
     *A virológus kísérletére amennyiben rendelkezésre
     * áll, az anyag paraméterben megfogalmazott igénynek megfelelően anyagot ad a
     * virológusnak.
     */
    private Set<Virologus> ittLevok;

    public void AnyagokElhelyezese(Anyag anyag){}

    /**
     * A virológus kísérletére amennyiben rendelkezésre
     * áll, az anyag paraméterben megfogalmazott igénynek megfelelően anyagot ad a
     * virológusnak.
     */
    Set<TeruletiElem> szomszedok;

    public TeruletiElem(){
        szomszedok = new HashSet<TeruletiElem>();
        ittLevok = new HashSet<Virologus>();
        ittJartak = new HashSet<Virologus>();
    }

    /**
     * A virológus kísérletére amennyiben rendelkezésre
     * áll, az anyag paraméterben megfogalmazott igénynek megfelelően anyagot ad a
     * virológusnak.
     * @param o
     */
    public void vedofelszerelesFelvetele(Vedofelszereles o, Virologus ki) {
        return;
    }

    /**
     * A virológus kísérletére amennyiben rendelkezésre
     * áll, az anyag paraméterben megfogalmazott igénynek megfelelően anyagot ad a
     * virológusnak.
     * @param anyag
     */
    public void anyagokFelvetele(Anyag anyag, Virologus akiAkarja) {
        return;
    }

    /**
     * A virológus kísérletére amennyiben rendelkezésre
     * áll, az anyag paraméterben megfogalmazott igénynek megfelelően anyagot ad a
     * virológusnak.
     * @return
     */
    public GenetikaiKod letapogatas() {
        return null;
    }

    /**
     * az adott virológust elhelyezi saját magán [a terület],
     * tehát bekerül az ittLevok és az ittJartak listába
     * @param belepo
     * @return
     */
    public TeruletiElem belepes(Virologus belepo) {
        ittLevok.add(belepo);
        ittJartak.add(belepo);
        return this;
    }

    /**
     * az adott virológust törli az ittLevok listájából.
     * @param kilepo
     */
    public void kilepes(Virologus kilepo) {
        ittLevok.remove(kilepo);
    }


    public Set<TeruletiElem> getSzomszedok() {
        return szomszedok;
    }
    public Set<Virologus> getErinthetok(){return ittLevok; }

    /**
     * szomszédot ad hozzá az aktuális területi elemhez (a szomszédok listájához)
     * @param uj
     */
    public void addSzomszed(TeruletiElem uj){
        szomszedok.add(uj);
    }

    public void mindenkiFertozese(Agens agens){
        for (var it: ittLevok)
        {
            it.agensKapasa(null, agens);
        }
    }

    // megmondja h jart-e mar itt az adott illeto
    public boolean jartEMarItt(Virologus v){
        if(ittJartak.contains(v)) return true;
        return false;
    }

    @Override
    public String toString(){
        return "Szabadterulet\tSzomszedok: " + szomszedok.size() + " db.";
    }

    public String getTipus() { return "Szabad Terület"; }
}
