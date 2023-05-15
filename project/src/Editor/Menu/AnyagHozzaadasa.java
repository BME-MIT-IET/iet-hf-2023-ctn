package Editor.Menu;

import Modell.Anyagok.Aminosav;
import Modell.Anyagok.Nukleotid;
import Modell.Palya.Raktar;
import Editor.SzituacioEpitoMenu;

import java.util.Locale;

public class AnyagHozzaadasa implements MainMenuItem {


    @Override
    public String getName() {
        return "Anyagok hozzaadasa";
    }

    @Override
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception {
        if(!(state.getCurrent() instanceof Raktar))
            throw new Exception("Anyag elhelyezese csak raktarban leheteseges.");
        args = args.toLowerCase(Locale.ROOT).trim();
        var args_split = args.split(",");
        if ("nukleotid".startsWith(args_split[0]))
            state.getCurrent().AnyagokElhelyezese(new Nukleotid(Integer.parseInt(args_split[1])));
        else if("aminosav".startsWith(args_split[0]))
            state.getCurrent().AnyagokElhelyezese(new Aminosav(Integer.parseInt(args_split[1])));
        else
            throw new Exception("Ismeretlen anyagtipus!");

    }

    @Override
    public String[] getPossibleArgs() {
        return new String[]{"Nukleotid", "Aminosav" };
    }
}
