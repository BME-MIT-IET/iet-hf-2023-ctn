package Editor.ViroMenu;

import Editor.SzituacioEpitoMenu;
import Logika.Virologus;

public class AktivFelszerelesHozzaadasa implements ViroMenuItem {
    @Override
    public String getName() {
        return "Aktiv vedofelszereles hozzaadasa";
    }

    @Override
    public int exec(String args, Virologus viro) throws Exception {
        var felszereles = SzituacioEpitoMenu.felszerelesGeneralasaNevAlapjan(args);
        if(felszereles == null)
            throw new Exception("Nem sikerult a megadott nevvel felszerelest generalni.");
        viro.getAktivVedofelszerelesek().add(felszereles);
        viro.getAktivModositok().add(felszereles);
        return 0;
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[]{"balta","kopeny","kesztyu","zsak"};
    }

}
