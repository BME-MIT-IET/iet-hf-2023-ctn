package Editor.Menu;

import Editor.SzituacioEpitoMenu;

public class Mentes implements MainMenuItem {

    @Override
    public String getName() {
        return "Mentes, es kilepes <fajlnev>";
    }

    @Override
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception {
        state.setSaveName(args);
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }
}
