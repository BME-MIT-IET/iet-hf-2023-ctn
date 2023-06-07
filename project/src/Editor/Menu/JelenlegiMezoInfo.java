package Editor.Menu;

import Editor.SzituacioEpitoMenu;
import Logika.Virologus;
import Modell.Agensek.*;
import Modell.Genetika.GenetikaiKod;
import Modell.TulajdonsagModosito;
import Modell.Vedofelszereles.Vedofelszereles;

public class JelenlegiMezoInfo implements MainMenuItem {
    @Override
    public String getName() {
        return "Jelenlegi mezo adatainak megjelenitese";
    }

    @Override
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception {
        System.out.println("~~~~~~~~~~~| STATISZTIKA |~~~~~~~~~~~~");
        // Jelenlegi mezo informaciok mutatasa
        System.out.println("JELENLEGI MEZO TULAJDONSAGAI");
        System.out.println("\t* " + state.getCurrent().toString());

        System.out.println("A MEZON ALLO JATEKOSOK");
        for(Virologus v : state.getCurrent().getErinthetok()){
            System.out.println("\t* Stats for: " + v.getNev());

            // Genetikia kodok listazasa
            System.out.println("MEGTANULT GENETIKAI KODOK");
            for(GenetikaiKod g : v.getIsmertKodok()){
                System.out.println("\t* " + g.getKodNeve());
            }

            System.out.println("\t+ AKTIV FELSZERELESEK");
            for(Vedofelszereles f : v.getAktivVedofelszerelesek()){
                System.out.println("\t\t* " + f.getNeve());
            }
            System.out.println("\t+ HORDOZOTT FELSZERELESEK");
            for(Vedofelszereles f : v.getHordozottVedofelszerelesek()){
                System.out.println("\t\t* " + f.getNeve());
            }

            // Anyagainak mutatasa
            System.out.println("SAJAT ANYAGOK");
            System.out.println("\t* " + v.getAnyagok().toString());

            // Rahato agensek mutatasa
            System.out.println("RAHATO AGENSEK");
            for(TulajdonsagModosito a : v.getAktivModositok()){
                if (a instanceof Agens) System.out.println(((Agens) a).getNev());
                /*if(a instanceof Benulas)                    System.out.println("\t* Benulas");
                else if(a instanceof Felejtes)              System.out.println("\t* Felejtes");
                else if(a instanceof MedveVirus)            System.out.println("\t* MedveVirus");
                else if(a instanceof Sebezhetetlenseg)      System.out.println("\t* Sebezhetetlenseg");
                else if(a instanceof Vitustanc)             System.out.println("\t* Vitustanc");*/
            }

            // Agensei mutatasa
            System.out.println("BIRTOKOLT/ELKESZITETT AGENSEK");
            for (Agens a : v.getFelhasznalhatoAgensek()){
                System.out.println("\t* " + a.getNev());
            }
        }


    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }
}
