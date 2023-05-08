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
        var egyik  =Jatek.getInstance().getPalya().stream().filter(p->p.getId() == ids[0]).findFirst().get();
        var masik  =Jatek.getInstance().getPalya().stream().filter(p->p.getId() == ids[1]).findFirst().get();
        egyik.getSzomszedok().add(masik);
        masik.getSzomszedok().add(egyik);
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }
}
