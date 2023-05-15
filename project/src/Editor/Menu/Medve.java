package Editor.Menu;

import Modell.Palya.Labor;
import Editor.SzituacioEpitoMenu;

public class Medve implements MainMenuItem {
    @Override
    public String getName() {
        return "MEDVE!";
    }

    @Override
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception {
        if(!(state.getCurrent() instanceof Labor)){
            throw new Exception("Csak labor lehet fertozo!");
        }
        var labor = (Labor)state.getCurrent();
        //TODO: EZ ITT SZAR
        labor.bFertozo = true;
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }
}
