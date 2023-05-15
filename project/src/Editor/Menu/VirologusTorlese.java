package Editor.Menu;

import Editor.SzituacioEpitoMenu;
import Logika.Jatek;

public class VirologusTorlese implements MainMenuItem {
    @Override
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception {
        var next = Jatek.getInstance().getJatekosok().stream().filter(p->p.getNev().startsWith(args)).findFirst().get();
        next.gyilkossagElszenvedese();
        Jatek.getInstance().getJatekosok().remove(next);
        state.getCurrent().getErinthetok().remove(next);
    }

    @Override
    public String getName() {
        return "Virologus torlese";
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }

}
