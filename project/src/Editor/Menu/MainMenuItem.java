package Editor.Menu;

import Editor.MenuItem;
import Editor.SzituacioEpitoMenu;
public interface MainMenuItem extends MenuItem {
    public void exec(String args, SzituacioEpitoMenu.GenState state) throws Exception;
}
