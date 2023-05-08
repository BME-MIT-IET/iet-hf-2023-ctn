package Editor.Menu;

import Modell.Palya.Ovohely;
import Modell.Vedofelszereles.Vedofelszereles;
import Editor.SzituacioEpitoMenu;

public class VedofelszerelesElhelyezese implements MainMenuItem {
    @Override
    public String getName() {
        return "Vedofelszereles elhelyezese";
    }

    @Override
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception {
        if(!(state.getCurrent() instanceof Ovohely))
            throw new Exception("Csak ovohelyen helyezheto el vedofelszereles!");

        var vfsz = SzituacioEpitoMenu.felszerelesGeneralasaNevAlapjan(args);
        if(vfsz == null)
            throw new Exception("Nem sikerült az argumentum alapjan vedofelszerelest kesziteni.");
        ((Ovohely) state.getCurrent()).vedofelszerelesElhelyezese((Vedofelszereles) vfsz);
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[]{"balta","kopeny","kesztyu","zsak"};
    }
}
