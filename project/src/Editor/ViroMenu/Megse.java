package Editor.ViroMenu;

import Logika.Virologus;

public class Megse implements ViroMenuItem{
    @Override
    public String getName() {
        return "Megse";
    }

    @Override
    public int exec(String args, Virologus viro) throws Exception {
        return -1;
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }
}
