package Editor.ViroMenu;

import Logika.Virologus;
import Modell.Anyagok.Aminosav;
import Modell.Anyagok.Nukleotid;

import java.util.Locale;

public class AnyagHozzaadasa implements ViroMenuItem{
    @Override
    public String getName() {
        return "Anyag hozzaadasa";
    }

    @Override
    public int exec(String args, Virologus viro) throws Exception {
        args = args.toLowerCase(Locale.ROOT).trim();
        var args_split = args.split(",");
        if ("nukleotid".startsWith(args_split[0]))
            viro.anyagTarolasa(new Nukleotid(Integer.parseInt(args_split[1])));
        else if("aminosav".startsWith(args_split[0]))
            viro.anyagTarolasa(new Aminosav(Integer.parseInt(args_split[1])));
        else
            throw new Exception("Ismeretlen anyagtipus!");
        return 0;
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[]{"Nukleotid", "Aminosav" };
    }
}
