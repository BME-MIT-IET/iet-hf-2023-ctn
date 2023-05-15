import javax.swing.*;

public class JatekUIMain {

    public static void main(String[] args){

        try{
            //A sima bun ronda, mondjuk ez se szep
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception e){
            //A felhasznalot nem erdekli ha nem sikerult betolteni
        }

        var start = new ui.Start();
        
    }

}
