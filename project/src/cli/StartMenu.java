package cli;

import Logika.Jatek;
import Logika.PalyaKonfiguracio;
import Logika.Virologus;
import cli.JatekMain;

import java.util.ArrayList;
import java.util.HashSet;

public class StartMenu {
    public void startMenu(){
        //uj jatek inditas -> hany virologus -> palya generalas
        // jatek betoltes, kilepes

        while(true){
            System.out.println("[1] Uj jatek ");
            System.out.println("[2] Korabbi jatek betoltese ");
            System.out.println("[3] Kilepes ");

            switch (JatekMain.Scanner.nextLine()){
                case "1":
                    try{
                        PalyaKonfiguracio pk1 = new PalyaKonfiguracio();
                        //hany jatekos lesz-> letszamtol fuggoen kicsi,kozepes, nayg palya
                        System.out.print("Hany jatekos szeretne jatszani?");
                        int jatekosSzam = JatekMain.Scanner.nextInt();

                        JatekMain.Scanner.nextLine();

                        // Jatekos nevek bekerese
                        HashSet<String> jatekosNevek = new HashSet<>();
                        while(jatekosNevek.size() < jatekosSzam){
                            System.out.print("Mi legyen a(z) " + (jatekosNevek.size() + 1) + ". neve? ");
                            String beNev = JatekMain.Scanner.nextLine();

                            // esetleg itt valami nev ellenorzes bar nem hinnem h annyira nagyon szukseges lenne...
                            // annyit garantal a jatekosNevek HasSet h csak egyedi nevek fordulhassanak elo
                            jatekosNevek.add(beNev);
                        }
                        // Itt mar az osszes szukseges jatekos nevet megadta, ezeket mar csak letre kell hozni es hozza adni a jatekhoz
                        ArrayList<String> veglegesJatekosNevek = new ArrayList<>( jatekosNevek );
                        ArrayList<Virologus> jatekosok = new ArrayList<>();
                        for(int i =0; i<jatekosSzam; i++){
                            // uj virologus letrehozasa es a nevet beallitjuk sorban
                            Virologus uj = new Virologus();
                            uj.setNev(veglegesJatekosNevek.get(i));

                            // uj jatekos hozzadasa a jatekosokhoz
                            jatekosok.add(uj);
                        }

                        try{
                            if(jatekosSzam<8){
                                //kicsimap
                                pk1 = ggcfg.CfgManager.readConfig("kicsimap.cfg", pk1);
                                System.out.println("kicsi palya betoltott");
                            }
                            else if (jatekosSzam<15 && jatekosSzam>=8){
                                //kozepmap
                                pk1 = ggcfg.CfgManager.readConfig("kozepesmap.cfg", pk1);
                                System.out.println("kozepes palya betoltott");
                            }
                            else {//if(jatekosSzam>=15){
                                //nagymap
                                pk1 = ggcfg.CfgManager.readConfig("nagymap.cfg", pk1);
                                System.out.println("nagy palya betoltott");
                            }
                        }
                        catch (Exception e){
                            System.out.println("Sikertelen volt a palyakonfig betoltese!");
                        }
                        //virologusok arraylistje
                        Jatek.getInstance().init(jatekosok, pk1);
                    }
                    catch(Exception e){
                        System.out.println("Sikertelen muvelet");
                        if(JatekMain.Scanner.hasNextLine())
                            JatekMain.Scanner.nextLine();
                        Jatek.reset();
                        break;
                    }
                    return;


                case "2":
                    try{
                        System.out.println("Ird be a betolteni kivant fajl nevet!");
                        String fajlnev = JatekMain.Scanner.nextLine(); //betolti a fajlnevnek azt amit en odairtam
                        System.out.println("fajlnev: " + fajlnev);
                        Jatek.getInstance().betoltes(fajlnev);
                    }
                    catch (Exception e){
                        Jatek.reset();
                        if(JatekMain.Scanner.hasNextLine())
                            JatekMain.Scanner.nextLine();
                        break;
                    }

                    return;
                case "3":
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }

    }
}
