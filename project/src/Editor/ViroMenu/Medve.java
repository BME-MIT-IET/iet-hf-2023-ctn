package Editor.ViroMenu;

import Logika.Virologus;
import Modell.Agensek.MedveVirus;

public class Medve implements ViroMenuItem{
    @Override
    public String getName() {
        return "MEDVE!";
    }

    @Override
    public int exec(String args, Virologus viro) throws Exception {
        viro.getAktivModositok().add(new MedveVirus());
        return 0;
    }

    @Override
    public String[] getPossibleArgs() {
        return new String[0];
    }
}
