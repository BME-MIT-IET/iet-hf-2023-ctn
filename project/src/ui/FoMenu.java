package ui;

import Logika.Jatek;
import Logika.Virologus;
import Modell.Palya.FertozoLabor;
import Modell.Palya.Labor;
import Modell.Palya.Ovohely;
import Modell.Palya.Raktar;
import ui.controllers.FoMenuController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static Editor.TestUtils.nev;
import static ui.utils.createButton;

public class FoMenu {


    private void kilepesKezelese(){
        Jatek.reset();
        this.frame.dispose();
    }

    private JPanel createBottomRow(){
        JPanel panel = new JPanel();

        panel.add(createButton("", p->kilepesKezelese()));
        panel.add(createButton("Kilepes", p->kilepesKezelese()));

        panel.add(new JLabel("LehetosegMatrix"));

        return panel;
    }

    private JPanel createExitPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        //panel.setBackground(new Color(0,0,0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new Insets(4,4,4,4);
        gbc.fill = GridBagConstraints.BOTH;

        panel.add(createButton("Mentes", p->ctrl.mentesKezelese()), gbc);
        panel.add(createButton("Kilepes", p->kilepesKezelese()), gbc);
        panel.add(createButton("Következő kör", p->KovetkezoJatekos()), gbc);

        return panel;
    }

    Map map = new Map(this);

    JPanel mapPanel;
    //JLabel lAktivJatekos = new JLabel("Jelenleg asdf virológus léphet.");

    Start parent;
    JFrame frame;

    Stats statsComponent = new Stats(this);

    Virologus aktivJatekos = Jatek.getInstance().getAktiv();

    boolean lepettMar = false;
    boolean vettMarFel = false;

    private void KovetkezoJatekos(){
        lepettMar = false;
        vettMarFel = false;
        Jatek.getInstance().nextRound();
        // Uj jatekos beallitasa
        aktivJatekos = Jatek.getInstance().getAktiv();
        update();
    }

    public JFrame getFrame(){ return frame; }

    FoMenuController ctrl;
    JPanel statisztikaPanel;

    public FoMenu(Start parent){
        this.parent = parent;
        frame = new JFrame();
        frame.setLayout(new GridBagLayout());
        //frame.setLocationRelativeTo(parent.frame);
        frame.setLocation(480,220);
        frame.setSize(new Dimension(960, 540));
        GridBagConstraints gbc = new GridBagConstraints();

        this.ctrl = new FoMenuController(this);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(2, 4, 4, 4);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;

        frame.setTitle("TNCFS - Vak virológusok | Játékban");

        //Stats panel
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        // gyors lekerjuk a statpanelt
        statisztikaPanel = statsComponent.getStatsPanel();
        frame.add(statisztikaPanel, gbc);

        //Exit|Save panel
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.SOUTH;
        frame.add(createExitPanel(), gbc);


        //AktivJatekos
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //frame.add(lAktivJatekos, gbc);

        //Map
        gbc.gridheight = 2;
        gbc.gridwidth = 1;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 8;
        gbc.weighty = 100;
        gbc.fill = GridBagConstraints.BOTH;
        frame.add(map.mapPanel, gbc);

        frame.setVisible(true);
        update();
    }

    public void update(){
        aktivJatekos = Jatek.getInstance().getAktiv();

        //statisztikaPanel.removeAll();
        statisztikaPanel = statsComponent.getStatsPanel();

        statisztikaPanel.invalidate();
        statisztikaPanel.revalidate();
        statisztikaPanel.repaint();

        map.update();
        statsComponent.update();

        frame.invalidate();
        frame.revalidate();
        frame.repaint();
    }

}
