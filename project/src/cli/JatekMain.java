package cli;

import java.util.Scanner;

public class JatekMain {
    public static final java.util.Scanner Scanner = new Scanner(System.in);
    public static void main(String[] args){
        StartMenu st = new StartMenu();
        FoMenu fomenu = new FoMenu();

        // Addig fut a jatek amig a cli.StartMenu-bol ki nem lepunk System.exit(0)...
        while(true){
            st.startMenu();
            try{
                // ez inditja el jatekot es jatszat maig ki nem lepunk
                fomenu.JatekInditasa();

            }catch (Exception e){
                System.out.println(1
                );
            }
        }
    }
}

