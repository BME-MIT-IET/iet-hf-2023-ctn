package Editor.ViroMenu;

import Editor.MenuItem;
import Logika.Virologus;

public interface ViroMenuItem extends MenuItem {

    public int exec(String args, Virologus viro) throws Exception;

}
