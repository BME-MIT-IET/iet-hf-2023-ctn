package Editor.Menu;

import Editor.SzituacioEpitoMenu;
import Logika.Jatek;

public class Betoltes implements MainMenuItem {
    @Override
    public String getName() {
        return "Betoltes <filename>";
    }

    @Override
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception {
        Jatek.getInstance().betoltes(args);
        state.setCurrent(Jatek.getInstance().getPalya().get(0));
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }
}
