package Editor;

import Logika.Jatek;
import Modell.Agensek.*;
import Modell.Palya.*;
import Modell.Vedofelszereles.*;
import Editor.Menu.*;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import static Editor.TestUtils.nev;

public class SzituacioEpitoMenu {

    public SzituacioEpitoMenu() {
        menu.add(new AnyagHozzaadasa());
        menu.add(new JelenlegiMezoInfo());
        menu.add(new LepesSzomszedra());
        menu.add(new VirologusTorlese());
        menu.add(new Mentes());
        menu.add(new Betoltes());
        menu.add(new MezoHozzaadasa());
        menu.add(new SzomszedsagLetrehozasa());
        menu.add(new UjViro());
        menu.add(new UjGenKod());
        menu.add(new VedofelszerelesElhelyezese());
    }

    public static TeruletiElem elemOlvasasa(Scanner sr){
        TeruletiElem te =  null;
        while(te == null){
            te = elemGeneralasaNevAlapjan(sr.nextLine());
            if(te == null)
                System.out.println("Ilyen mezo nem letezik, kerlek adj meg egy letezo tipusnevet:");
        }
        return te;
    }

    public static TeruletiElem elemGeneralasaNevAlapjan(String nev){
        nev = nev.trim().toLowerCase(Locale.ROOT);
        if ("ures".startsWith(nev))
            return new TeruletiElem();
        if("raktar".startsWith(nev))
            return new Raktar();
        if("ovohely".startsWith(nev))
            return new Ovohely();
        if("labor".startsWith(nev))
            return new Labor();
        if("fertozo".startsWith(nev))
            return new FertozoLabor();
        return null;
    }


    public static Vedofelszereles felszerelesGeneralasaNevAlapjan(String nev){
        nev = nev.trim().toLowerCase(Locale.ROOT);
        if ("balta".startsWith(nev))
            return new Balta();
        if("kopeny".startsWith(nev))
            return new Kopeny();
        if("kesztyu".startsWith(nev))
            return new Kesztyu();
        if("zsak".startsWith(nev))
            return new Zsak();
        return null;
    }
    public static Agens agensGeneralasaNevAlapjan(String nev, int hatas){
        nev = nev.trim().toLowerCase(Locale.ROOT);
        if ("benulas".startsWith(nev))
            return new Benulas(hatas);
        if("felejtes".startsWith(nev))
            return new Felejtes(hatas);
        if("medve".startsWith(nev))
            return new MedveVirus();
        if("sebezhetetlenseg".startsWith(nev))
            return new Sebezhetetlenseg(hatas);
        if("vitustanc".startsWith(nev))
            return new Vitustanc(hatas);
        return null;
    }




    public static class GenState {

        public String getSaveName() {
            return saveName;
        }

        public void setSaveName(String saveName) {
            this.saveName = saveName;
        }

        private String saveName = null;
        public TeruletiElem getCurrent() {
            return current;
        }
        public void setCurrent(TeruletiElem current) {
            this.current = current;
        }
        TeruletiElem current;
        public GenState(TeruletiElem kezdo){
            current = kezdo;
            Jatek.getInstance().getPalya().add(kezdo);
        }
        private void printMezo(){
            System.out.println("Jelenleg "+ nev(current) + " mezon allsz.");
            System.out.print("\tSzomszedok: ");
            for(var it : current.getSzomszedok()){
                System.out.print(nev(it) + " (" + it.getId() + ") ,");
            }
            System.out.println();
        }
        public void printState(){
            printMezo();

        }
    }

    private static ArrayList<MainMenuItem> menu = new ArrayList<>();

    public static String mainMode(GenState state, Scanner scanner){
        scanner.useDelimiter("");
        while(state.getSaveName() == null){
            state.printState();
            System.out.println("A lehetosegeid:");
            for (var it : menu){
                System.out.println(it.printDescription());
            }
            try{
                System.out.print(">");
                    var line = scanner.nextLine().toLowerCase(Locale.ROOT);

                    var potentialArgs = "";
                    String finalIn = line;
                    if(line.contains(" ")) {
                        potentialArgs = line.substring(line.lastIndexOf(" ") + 1);
                        finalIn = line.substring(0, line.lastIndexOf(" "));
                    }

                    String finalIn1 = finalIn;
                    var itemsPossible = menu.stream().filter(p->p.getName().toLowerCase(Locale.ROOT).startsWith(finalIn1)).collect(Collectors.toList());
                    System.out.print("\r");
                    if(itemsPossible.isEmpty())
                    {
                        if(itemsPossible.size() == 1){
                            if(itemsPossible.get(0).getName().contains(potentialArgs))
                                potentialArgs = "";
                            System.out.println("Menu item matched: " + itemsPossible.get(0).getName() + " with arguments: " + potentialArgs);
                            try{
                                itemsPossible.get(0).exec(potentialArgs, state);
                            }catch (Exception e){
                                System.out.println(e.getMessage());
                            }
                        }
                        else
                        {
                            System.out.println("Nem sikerult egyetlen elemet azonositani a bemenet alapjan.");
                        }
                        System.out.println(itemsPossible);
                    }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return state.getSaveName();
    }


    public static void main(String[] Args){
        var scanner = new Scanner(System.in);
        System.out.println("Udv T.N.C.F.S. palya-es szituacio generator programjaban!");
        System.out.println("Mi legyen az első mező? (<ures|raktar|ovohely|labor|fertozo>)");
        GenState state = new GenState(elemOlvasasa(scanner));
        var fname = mainMode(state, scanner);

        System.out.println("Kilepes, mentes...");
        Jatek.getInstance().mentes(fname);

    }
}
