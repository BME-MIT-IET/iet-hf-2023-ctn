package Logika;

import Logika.Idozites.Timer;
import Modell.Genetika.GenetikaiKod;
import Modell.Palya.TeruletiElem;

import javax.swing.*;
import java.io.*;
import java.util.*;

/**
 * TODO: Kell még egy halom modell, gondolom MVC UI lesz, a csatlakozó, és discovery screen modellje is saját kell legyen
 * Talán mivel még a CLIre gyúrunk, ezeket hanyagolhatjuk, viszont a connectivity modulnak lehetnek még segédobjektumai.
 * Opcionális, tehát lehet elkerülhető a mostani dokumentáció.
 * A mezőkezelés talán most inkább a térkép mind objektum feladata lehetne, mert db szerű kell legyen a játékos-és map management
 * hogy a hálózati sync, és a borok egszerű query API-ja menjen.
 * */



public class Jatek implements Serializable {

    //region singleton
    private static Jatek inst = new Jatek();

    private static Random r = new Random();

    public static void reset(){
        inst = new Jatek();
    }

    //private PalyaKonfiguracio palyaKonfig;

    public static Jatek getInstance(){
        return inst;
    }
    //endregion

    //region init
    public void init(ArrayList<Virologus> virologusok, PalyaKonfiguracio palyaKonfig){
        this.jatekosok = virologusok;
        palya = palyaKonfig.palyaGeneralasa();

        // Jatekosok elhelyezese random teruleti elemekre
        for(int i = 0; i < jatekosok.size(); i++){
            // random start teruleti elem kialakitasa
            TeruletiElem randomTerulet = palya.get(r.nextInt(palya.size()));

            // Teruleti elemre helyezzuk az adott virologust/jatekost
            randomTerulet.belepes(jatekosok.get(i));
            // Az adott virologusnak/jatekosnak megadjuk a kezdo teruletet
            jatekosok.get(i).setStartTerulet( randomTerulet);
        }
        aktiv = jatekosok.get(0);
    }

    public void nextRound(){
        // uj index megallapitasa, figyelunk h ne legyen tulindexeles
        var index = jatekosok.indexOf(aktiv);
        index = (index + 1) % jatekosok.size();

        // szolunk az idozitonek, hogy vege van a kornek
        idozito.nextRound();


        setAktiv(index);


    }

    //endregion

    //region attributes
    ArrayList<Virologus> jatekosok = new ArrayList<>();
    ArrayList<TeruletiElem> palya = new ArrayList<>();
    Timer idozito = new Timer();
    Virologus aktiv = jatekosok.isEmpty() ? null : jatekosok.get(0);

    //endregion

    //region getters
    public ArrayList<Virologus> getJatekosok() {
        return jatekosok;
    }
    public ArrayList<TeruletiElem> getPalya() {
        return palya;
    }
    public Timer getIdozito(){return idozito;}

    public void jatekVege(Virologus nyertes){
        System.out.println(nyertes.getNev().toUpperCase(Locale.ROOT) + " virologus nyert!");
        JOptionPane.showMessageDialog(null, "Gratulálunk, \"" + nyertes.getNev() + "\" megnyerte a játékot!", "Nyertes Kihírdetése", JOptionPane.INFORMATION_MESSAGE, null);
    }

    public void ennyitIsmerek(Virologus virologus, int ennyit){
        HashSet<GenetikaiKod> gkk = new HashSet<>();
        for(TeruletiElem t : palya){
            gkk.add(t.letapogatas());
        }
        HashSet<String> dbAgens = new HashSet<>();
        for(GenetikaiKod cucc : gkk){
            if(cucc == null) continue;  // De hogy mi a faxomert van egy ures halmazban egy buzi null faxom mar tenyleg a javaba
            dbAgens.add(cucc.getKodNeve());
        }
        if(dbAgens.size() <= ennyit){
            // megnyerte a jatekot a virologus
            jatekVege(virologus);
        }
    }

    public void halalJelentes(Virologus ki){
        jatekosok.remove(ki);
        System.out.println(ki.getNev() +" virologus sajnos gyilkossag aldozata lett.");
    }

    public void betoltes(String fname){
        try{

            FileInputStream fis = new FileInputStream(fname);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Jatek.inst = (Jatek)ois.readObject();

        }catch (Exception e){
            System.out.println("Betoltes sikertelen: " +e.getMessage());
        }
    }

    ///TESZTELETLEN!!!!
    public void mentes(String filename){
        try{
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(getInstance());
        }
        catch (Exception e){
            System.out.println("Mentes sikertelen: " +e.getMessage());
        }
    }

    public void setAktiv(int index) { aktiv = jatekosok.get(index); }
    public Virologus getAktiv() { return aktiv; }

    //endregion
}