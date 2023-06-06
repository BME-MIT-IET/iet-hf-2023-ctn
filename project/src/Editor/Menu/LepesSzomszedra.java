package Editor.Menu;

import Logika.Jatek;
import Editor.SzituacioEpitoMenu;

public class LepesSzomszedra implements MainMenuItem {
    @Override
    public String getName() {
        return "Lepes szomszedra <ID>";
    }

    @Override
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception {
        var fieldId = Integer.parseInt(args.trim());

        var next = Jatek.getInstance().getPalya().stream().filter(p->p.getId() == fieldId) .findFirst();

        if(next.isEmpty())
            throw new Exception("Nem talalhato ilyen ID-vel teruleti elem.");
        state.setCurrent(next.get());
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }
}
