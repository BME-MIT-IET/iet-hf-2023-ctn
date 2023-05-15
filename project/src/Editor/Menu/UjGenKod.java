package Editor.Menu;

import Editor.SzituacioEpitoMenu;
import Modell.Agensek.Agens;
import Modell.Anyagok.Aminosav;
import Modell.Anyagok.Anyag;
import Modell.Anyagok.Nukleotid;
import Modell.Genetika.GenetikaiKod;
import Modell.Palya.Labor;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class UjGenKod implements MainMenuItem{
    @Override
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception {
        if(!(state.getCurrent() instanceof Labor))
            throw new Exception("Genetikai kod csak laborban helyezheto el.");

        var res = 0;
        Scanner sc = new Scanner(System.in);
        while (res == 0){
            try{
                System.out.println("Nukleotid szukseglet:");
                int nuk = sc.nextInt();
                System.out.println("Aminosav szukseglet:");
                int amino = sc.nextInt();
                if(sc.hasNextLine())
                    sc.nextLine();
                System.out.println("Milyen agenset allit elo?");
                Agens a = SzituacioEpitoMenu.agensGeneralasaNevAlapjan(sc.nextLine(), -1);

                var anyagok = new HashSet<Anyag>();
                anyagok.add(new Nukleotid(nuk));
                anyagok.add(new Aminosav(amino));

                ((Labor)state.getCurrent()).setGenKod(new GenetikaiKod(anyagok, a));
                System.out.println("gen. kod hozzaadva.");
                res = 1;

            }catch (Exception e){
                System.out.println("Hibas input, megint az egesz...");
            }

        }
    }

    @Override
    public String getName() {
        return "Genetikai kod hozzaadasa.";
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }
}
