package Editor.Menu;

import Logika.Jatek;
import Editor.SzituacioEpitoMenu;

import java.util.Arrays;

public class SzomszedsagLetrehozasa implements MainMenuItem {


    @Override
    public String getName() {
        return "Szomszedsagi kapcsolat kialakitasa";
    }

    @Override
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception {
        var ids = Arrays.stream(args.split(",")).mapToInt(p-> Integer.parseInt(p)).toArray();
        var egyik  = Jatek.getInstance().getPalya().stream().filter(p->p.getId() == ids[0]).findFirst();
        var masik  = Jatek.getInstance().getPalya().stream().filter(p->p.getId() == ids[1]).findFirst();
        if (egyik.isPresent() && masik.isPresent()) {
            egyik.get().getSzomszedok().add(masik.get());
            masik.get().getSzomszedok().add(egyik.get());
        }
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }
}
