package Editor.Menu;

import Logika.Jatek;
import Editor.SzituacioEpitoMenu;

public class MezoHozzaadasa implements MainMenuItem {


    @Override
    public String getName() {
        return "Szomszed hozzaadasa";
    }

    @Override
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception {
        var ujMezo = SzituacioEpitoMenu.elemGeneralasaNevAlapjan(args);
        if(ujMezo == null)
            throw new Exception("Ismeretlen tipusu mezo!");
        var idmax = Jatek.getInstance().getPalya().stream().max((p1, p2)-> p1.getId() > p2.getId() ? 1 : -1);
        if (idmax.isPresent()) {
            ujMezo.setId(idmax.get().getId() + 1);
            ujMezo.getSzomszedok().add(state.getCurrent());
            Jatek.getInstance().getPalya().add(ujMezo);
            state.getCurrent().getSzomszedok().add(ujMezo);
        }
        //state.setCurrent(ujMezo); Nem lep at alapbol
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[]{ "ures","raktar","ovohely","labor","fertozo"};
    }
}
