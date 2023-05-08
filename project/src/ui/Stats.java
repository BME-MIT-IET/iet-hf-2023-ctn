package ui;

import Logika.Jatek;
import Logika.Virologus;
import Modell.Agensek.Agens;
import Modell.Agensek.Benulas;
import Modell.Agensek.Felejtes;
import Modell.Genetika.GenetikaiKod;
import Modell.TulajdonsagModosito;
import Modell.Vedofelszereles.Balta;
import Modell.Vedofelszereles.Vedofelszereles;
import Modell.Vedofelszereles.Zsak;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

public class Stats {
    private Virologus currentViro = Jatek.getInstance().getAktiv();

    public JPanel getStatsPanel() {
        update();
        return statsPanel;
    }

    private JPanel statsPanel;
    private JPanel currentViroStatsPanel = generateCurrentViroStatsPanel();
    private JPanel sameFieldPlayerActionsPanel = generateSameFieldActionsPanel();
    private FoMenu fomenu;

    public Stats(FoMenu fmenu){
        this.fomenu = fmenu;

        statsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.insets = new Insets(4,4,4,4);
        statsPanel.add(new JLabel("Stats"), gbc);

        statsPanel.add(currentViroStatsPanel, gbc);
        statsPanel.add(sameFieldPlayerActionsPanel, gbc);
    }

    private JPanel generateCurrentViroStatsPanel(){
        JPanel result = new JPanel(new GridBagLayout());
        var gbc = new GridBagConstraints();
        gbc.gridx = 0;


        // Itt irja ki hogy milyen anyagaink vannak
        result.add(new JLabel("Saját Anyagok:"), gbc);
        String darabolt[] = currentViro.getAnyagok().toString().split("\n");

        // Soronkent irja ki az adatokat nem omlesztve
        //System.out.println(Jatek.getInstance().getAktiv().getNev());
        for(String sor : darabolt){
            result.add(new JLabel(sor), gbc);
            //System.out.println(sor);
        }
        for (Vedofelszereles vf : currentViro.getAktivVedofelszerelesek()) {
            if (vf instanceof Zsak) {
                String[] zsakos = ((Zsak) vf).getTar().toString().split("\n");
                for(String sor : zsakos){
                    result.add(new JLabel(sor), gbc);
                }
            }
        }

        result.add(new JLabel(" "), gbc);

        result.add(new JLabel("Megtanult genetikai kódok:"), gbc);
        if(currentViro.getIsmertKodok().size() == 0){
            result.add(new JLabel("Nincs tanult genetikai kód!"), gbc);
        }
        else {
            Set<GenetikaiKod> kodjai = currentViro.getIsmertKodok();
            for (var i : kodjai) {
                //System.out.println(i);
                JButton agensKeszites = new JButton(i.getKodNeve() + " ágens készítése");
                agensKeszites.addActionListener(ae -> {
                            currentViro.agensKeszitese(i);
                            fomenu.update();
                        }

                        // TODO: Frissuljon a UI, update() kellene gombnyomasnal, nem csak itt...
                );
                result.add(agensKeszites, gbc);
            }
        }

        result.add(new JLabel(" "), gbc);

        result.add(new JLabel("Rahato agensek:"), gbc);
        if(currentViro.getAktivModositok().size() == 0){
            result.add(new JLabel("Nincs aktiv tulajdonsagmodosito!"), gbc);
        }
        else {
            ArrayList<TulajdonsagModosito> agensek = currentViro.getAktivModositok();
            for (var i : agensek) {
                if (i instanceof Agens) {
                    String agens = ((Agens) i).getNev();
                    result.add(new JLabel(agens), gbc);
                }
            }
        }

        result.add(new JLabel(" "), gbc);

        result.add(new JLabel("Hordozott felszerelések:"), gbc);
        if(currentViro.getHordozottVedofelszerelesek().size() == 0){
            result.add(new JLabel("Nincs hordozott védőfelszerelés!"), gbc);
        }
        else {
            var hordozottak = currentViro.getHordozottVedofelszerelesek();
            for (var i : hordozottak) {
                JButton aktivalas = new JButton(i.getNeve() + " aktivalasa");
                aktivalas.addActionListener(ae -> {
                    if (currentViro.getAktivVedofelszerelesek().size() < 3) {
                        currentViro.felszerelesAktivalasa(i);
                        fomenu.update();
                    }
                }

                );
                result.add(aktivalas, gbc);
            }
        }

        result.add(new JLabel(" "), gbc);

        result.add(new JLabel("Aktiv felszerelések:"), gbc);
        if(currentViro.getAktivVedofelszerelesek().size() == 0){
            result.add(new JLabel("Nincs aktiv védőfelszerelés!"), gbc);
        }
        else {
            var aktivak = currentViro.getAktivVedofelszerelesek();
            for (var i : aktivak) {
                JButton eltetel = new JButton(i.getNeve() + " eltetele");
                eltetel.addActionListener(ae -> {
                            currentViro.felszerelesEltetele(i);
                            fomenu.update();
                        }

                );
                result.add(eltetel, gbc);
            }
        }
        return  result;
    }

    private JPanel generateSameFieldActionsPanel(){
        JPanel result = new JPanel(new GridBagLayout());
        var gbc=  new GridBagConstraints();
        gbc.gridx = 0;

        result.add(new JLabel("A jelenlegi mezőn álló virológusok:"), gbc);

        // Osszes szomszedra vegigmegyunk es a lehetosegeknek megfeleloen adunk neki akciogombokat
        for (var it: currentViro.getJelenlegiMezo().getErinthetok()) {
            JPanel viroActionsPanel = new JPanel(new GridBagLayout());
            viroActionsPanel.add(new JLabel(it.getNev()),gbc);
            result.add(viroActionsPanel, gbc);


            // Agens kenese gombok hozzadasa
            ArrayList<Agens> agensei = currentViro.getFelhasznalhatoAgensek();
            for (int i = 0; i < agensei.size(); i++) {
                if( it == currentViro && agensei.get(i) instanceof Benulas) continue;
                if( it == currentViro && agensei.get(i) instanceof Felejtes) continue;

                JButton agensKenes = new JButton(agensei.get(i).getNev() + " ágens kenése");
                int finalI = i;
                agensKenes.addActionListener(ae -> {
                    currentViro.agensVetese(
                            agensei.get(finalI),
                            it
                    );
                    fomenu.update();
                }
                );
                result.add(agensKenes, gbc);
            }

            // Felszerelesek rablasa gombok hozzadasa
            ArrayList<Vedofelszereles> felszerelesei = it.getAktivVedofelszerelesek();
            for (var i : felszerelesei) {
                if (it != currentViro) {
                    JButton rablas = new JButton(i.getNeve() + " rablása");
                    rablas.addActionListener(ae -> {
                        currentViro.lopas(it, i);
                        fomenu.update();
                    }
                    );
                    result.add(rablas, gbc);
                }
            }
            // Anyag rablas hozzaadasa
            if(it != currentViro){
                JButton rablas = new JButton("Anyag rablása");
                rablas.addActionListener(ae -> {
                            currentViro.lopas(it, null);
                            fomenu.update();
                        }
                );
                result.add(rablas, gbc);
            }

            // Ha van nalunk balta akkor plussz Balta hasznalat lehetoseget is hozza adunk
            //boolean vanBalta = false;
                for (var vf : currentViro.getAktivVedofelszerelesek()) {
                    if (it != currentViro && vf instanceof Balta) {
                        // Ha van balta akkor adunk neki baltazasi lehetoseget
                        // persze magat nem tudja lebaltazni...
                        //vanBalta = true;
                        JButton baltazas = new JButton("Balta használata");
                        baltazas.addActionListener( ae -> {
                            currentViro.baltaHasznalat(it);
                            fomenu.update();
                        });
                        result.add(baltazas, gbc);
                        break;
                    }
                }
            /*if(it != currentViro && vanBalta){
                JButton baltazas = new JButton("Balta használata");
                baltazas.addActionListener( ae -> {
                            currentViro.baltaHasznalat(it);
                            fomenu.update();
                        });
                result.add(baltazas, gbc);
            }*/
            result.add(new JLabel(" "), gbc);
        }

        return result;
    }

    public void update(){
        currentViro = Jatek.getInstance().getAktiv();

        // ez megoldotta, NE VEDD KI
        statsPanel.removeAll();

        currentViroStatsPanel = generateCurrentViroStatsPanel();
        sameFieldPlayerActionsPanel = generateSameFieldActionsPanel();

        statsPanel.add(generateCurrentViroStatsPanel());
        statsPanel.add(generateSameFieldActionsPanel());

        statsPanel.invalidate();
        statsPanel.revalidate();
        statsPanel.repaint();
    }


}
