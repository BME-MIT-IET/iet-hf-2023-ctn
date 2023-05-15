package ui;

import Logika.Jatek;
import Logika.Virologus;
import Modell.Anyagok.Aminosav;
import Modell.Anyagok.Nukleotid;
import Modell.Palya.*;
import Modell.Vedofelszereles.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

import static Editor.TestUtils.nev;
import static ui.utils.createButton;

public class Map {
    JPanel mapPanel = new JPanel();
    Virologus adottJatekos = Jatek.getInstance().getAktiv();
    FoMenu menu;

    public Map(FoMenu menu){
        this.menu = menu;
        mapPanel.setLayout(null);
    }

    ArrayList<JButton> mezoGombok = new ArrayList<>();
    public void update(){
        mapPanel.removeAll();
        mezoGombok.clear();


        adottJatekos = Jatek.getInstance().getAktiv();
        var mezo = adottJatekos.getJelenlegiMezo();

        var size = mapPanel.getSize();

        var r = size.getHeight() < size.getWidth()? ((size.getHeight()/2) - (size.getHeight()/8)) : ((size.getWidth()/2) - (size.getWidth()/8));

        var panel = generateCurrentFieldPanel();
        panel.setBounds((size.width/2 - (int)(r/2.4f)) - (panel.getPreferredSize().width / 4),
                (size.height/2 - (int)(r/2.4f)), (int)(r/1.0f), (int)(r/1.2f));
        mapPanel.add(panel);

        float theta = 0;
        var res = mezo.getSzomszedok().size();

        double szelessegSzorzo = 1.25;

        for(var it : mezo.getSzomszedok()){
            JButton nextNeighbor = new JButton(it.getSzomszedok().size() + "");
            nextNeighbor.setFont(nextNeighbor.getFont().deriveFont(Font.BOLD));

            // Ha itt jartunk elozoleg akkor zolddel jelezzuk ezt
            if(adottJatekos.elozoTerulete(it)){
                nextNeighbor.setForeground(Color.green);
                nextNeighbor.setBackground(Color.green);
                nextNeighbor.setOpaque(true);
            }
            // Ha mar jartunk itt de nem ez volt az elozo teruletunk akkor pedig sargaval
            else if(it.jartEMarItt(adottJatekos)){
                nextNeighbor.setForeground(Color.ORANGE);
                nextNeighbor.setBackground(Color.ORANGE);
                nextNeighbor.setOpaque(true);
            }

            mapPanel.add(nextNeighbor);
            mezoGombok.add(nextNeighbor);
            nextNeighbor.setBounds(((size.width/2) - (size.width/16)) + (int)Math.round(r * Math.cos(theta)), ((size.height/2) - (size.height/16)) + (int)Math.round( r * Math.sin(theta)),
                    (int)(40*szelessegSzorzo),(int)(40*szelessegSzorzo));
            nextNeighbor.addActionListener(a->travelToField(it));
            theta += Math.PI / (res / 2.f);
        }
        mapPanel.setBackground(new Color(80,80,80));

        mapPanel.invalidate();
        mapPanel.validate();
        mapPanel.repaint();
    }

    private JPanel generateCurrentFieldPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        var mezo = Jatek.getInstance().getAktiv().getJelenlegiMezo();


        panel.add(new JLabel("Jelenlegi Játékos: " + Jatek.getInstance().getAktiv().getNev()));
        panel.add(new JLabel("Jelenlegi Mezo: " + Jatek.getInstance().getAktiv().getJelenlegiMezo().getTipus()), gbc);


        if(mezo instanceof Labor || mezo instanceof FertozoLabor) {
            panel.add(createButton("Kód letapogatása",
                p -> {
                    //Virologus jelenlegi = Jatek.getInstance().getAktiv();
                    //jelenlegi.kodLetapogatasa();
                    adottJatekos.kodLetapogatasa();
                    this.menu.update();
                }
            ),gbc);
        }
        if(mezo instanceof Ovohely) {
            panel.add(createButton("Felszerelés felvétele",
                    p -> {
                Object[] options = { "Balta", "Kesztyű", "Köpeny", "Zsák"};
                int selectedValue = JOptionPane.showOptionDialog(null, "Milyen Felszerelést akarsz felvenni?", "Felszerelés Kiválasztása",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, options, options[0]);

                Vedofelszereles valasztott = null;

                switch(selectedValue){
                    case 0: valasztott = new Balta(); break;
                    case 1: valasztott = new Kesztyu(); break;
                    case 2: valasztott = new Kopeny(); break;
                    case 3: valasztott = new Zsak(); break;
                }

                if(valasztott != null && menu.vettMarFel == false) {
                    adottJatekos.vedofelszerelesFelvetele(valasztott);
                    menu.vettMarFel = true;
                }


                this.menu.update();
            }
            ),gbc);
        }
        if(mezo instanceof Raktar) {
            panel.add(createButton("Anyagok felvétele",
                p -> {
                    String mennyisegS = JOptionPane.showInputDialog("Mennyi anyagot akarsz felvenni?", 100);
                    int mennyiseg;

                    try{
                        mennyiseg = Integer.parseInt(mennyisegS);
                    } catch (Exception e){
                        JOptionPane.showMessageDialog(null, "Helytelen formátum!");
                        return;
                    }

                    Object[] options = { "Aminosav", "Nukleotid" };
                    int valsaztottAnyag = JOptionPane.showOptionDialog(null,
                            "Milyen anyagot akarsz felvenni?", "Felszerelés Kiválasztása",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,
                            null, options, options[0]);

                    if(valsaztottAnyag == 0){
                        Jatek.getInstance().getAktiv().anyagFelvetele(new Aminosav(mennyiseg));
                    }
                    else if(valsaztottAnyag == 1){
                        Jatek.getInstance().getAktiv().anyagFelvetele(new Nukleotid(mennyiseg));
                    }
                    this.menu.update();
                }
            ), gbc);
        }
        return panel;
    }

    private void travelToField(TeruletiElem next){
        boolean debug = false;  // GOD mod, true eseten barmennyit tudunk lepni

        // Annak erdekeben h legyen benne debug
        for(Virologus adott : Jatek.getInstance().getJatekosok()){
            if(adott.getNev().toUpperCase(Locale.ROOT).equals("DEBUG")){
                debug = true;
                break;
            }
        }

        for(var it: mezoGombok) it.setEnabled(false);

        // csak akkor lepunk ha mar nem leptunk
        if( debug || !this.menu.lepettMar ){
            // itt leptetjuk masik mezore
            Jatek.getInstance().getAktiv().mozgas(next);
            this.menu.lepettMar = true;
        }

        menu.update();
    }

}
