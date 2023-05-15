package Editor.ViroMenu;

import Editor.SzituacioEpitoMenu;
import Logika.Virologus;

public class VethetoAgensHozzaadasa  implements ViroMenuItem{
    @Override
    public String getName() {
        return "Vetheto agens hozzaadasa";
    }

    @Override
    public int exec(String args, Virologus viro) throws Exception {
        var agens = SzituacioEpitoMenu.agensGeneralasaNevAlapjan(args, -1);
        if(agens == null)
            throw new Exception("Nem sikerult a megadott nevvel agenst generalni.");
        viro.getFelhasznalhatoAgensek().add(agens);
        return 0;
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[]{
                "benulas","felejtes","medve","sebezhetetlenseg","vitustanc"
        };
    }
}