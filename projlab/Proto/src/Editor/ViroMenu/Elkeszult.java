package Editor.ViroMenu;

import Logika.Virologus;

public class Elkeszult implements ViroMenuItem{
    @Override
    public String getName() {
        return "Elkeszult";
    }

    @Override
    public int exec(String args, Virologus viro) throws Exception {
        return 1;
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }
}
