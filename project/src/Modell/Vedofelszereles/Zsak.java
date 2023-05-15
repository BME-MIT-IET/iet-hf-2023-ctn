package Modell.Vedofelszereles;

import Modell.Anyagok.AnyagTarolo;

import java.util.Set;

//KB UML HELYES

/**
 * Megnöveli az maximum összegyűjthető anyagok számát.
 */
public class Zsak extends Vedofelszereles{

    public AnyagTarolo getTar() {
        return tar;
    }

    /**
     * a zsák anyagtárolója, a hordozójának maximum felvehető anyagain túl felvett anyagaihoz
     */
    AnyagTarolo tar = new AnyagTarolo();

    /**
     * Metódus, melyben a zsák hozzájárulhat a virológus tárolóképességéhez. Felüldefiniálatlanul,
     * érintetlenül visszaadja ‘tarolok’ paraméterét.
     * @param tarolok
     * @return
     */
    public Set<AnyagTarolo> anyagTarolasa(Set<AnyagTarolo> tarolok) {
        tarolok.add(tar);
        return tarolok;
    }

    @Override
    public String getNeve(){
        return "Zsak";
    }
}
