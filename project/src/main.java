import Editor.SzituacioEpitoMenu;
import cli.JatekMain;

import java.util.*;

public class main {

    public static void main(String[] args) throws CloneNotSupportedException {
        try{
            JatekUIMain.main(args);
        }
        catch (Exception e){}
        JatekMain.main(args);
    }
}
