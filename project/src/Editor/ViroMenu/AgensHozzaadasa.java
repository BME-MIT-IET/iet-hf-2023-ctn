package Editor.ViroMenu;

import Editor.SzituacioEpitoMenu;
import Logika.Virologus;

public class AgensHozzaadasa implements ViroMenuItem{
    @Override
    public String getName() {
        return "Agens hozzaadasa";
    }

    @Override
    public int exec(String args, Virologus viro) throws Exception {

        var components = args.split(",");
        var hatas = components.length > 1 ? Integer.parseInt(components[1]) : 1;

        var agens = SzituacioEpitoMenu.agensGeneralasaNevAlapjan(components[0], hatas);
        if(agens == null)
            throw new Exception("Nem sikerult a megadott nevvel agenst generalni.");
        viro.getAktivModositok().add(agens);
        return 0;
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[]{
            "benulas","felejtes","medve","sebezhetetlenseg","vitustanc"
        };
    }
}
