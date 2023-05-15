package ui;

import Logika.Jatek;
import Logika.PalyaKonfiguracio;
import Logika.Virologus;

import javax.print.attribute.standard.JobName;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class JatekSetup {

    ArrayList<String> jatekosNevek = new ArrayList<>();

    JTextField inputField;
    JFrame frame = new JFrame();

    Start parent;
    String getViroText(){
        return "Jelenleg az alábbi " + jatekosNevek.size() + " virológus kezdhet játékot:";
    }

    boolean jatekElinditva = false;
    JPanel sp_content = new JPanel(new GridBagLayout());
    JLabel virokLabel = new JLabel(getViroText());


    public JatekSetup(Start start){
        // TODO: Majd kivenni
        // region Pelda jatekosnevek
        // Gyors jatekosok hozza adasa Debug gyorsitas erdekeben
        /*jatekosNevek.add("Axi");
        jatekosNevek.add("Bogi");
        jatekosNevek.add("Bori");
        jatekosNevek.add("GG");
        jatekosNevek.add("Csenge");
        jatekosNevek.add("Petya");*/
        // endregion

        this.parent = start;
        //ne bal felul legyen
        frame.setLocationRelativeTo(start.frame);
        //ne legyen kicsi
        frame.setSize(new Dimension(360,240));

        frame.setTitle("TNCFS - Vak virológusok | Új játék");


        frame.setLayout(new GridBagLayout());


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_START;


        //felso sor
        JPanel addRow = new JPanel(new FlowLayout());
        addRow.add(new JLabel("Játékos hozzáadása: "));

        // ez itt keri be a neveket
        inputField = new JTextField();
        inputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    hozzaadasKezelese();
                }
            }
        });

        inputField.setPreferredSize(new Dimension(120,22));
        addRow.add(inputField);
        JButton bAdd = new JButton("Hozzáadás");
        bAdd.addActionListener(a->hozzaadasKezelese());
        addRow.add(bAdd);

        frame.add(addRow, gbc);
        frame.add(virokLabel, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weighty = 10;
        ///content
        sp_content.setMinimumSize(new Dimension(300,200));
        JScrollPane sp = new JScrollPane(sp_content);
        sp.createVerticalScrollBar();
        frame.add(sp, gbc);


        //start|back
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.PAGE_END;
        gbc.weighty = 1;
        frame.add(inditasiSorGeneralasa(), gbc);


        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                if(!jatekElinditva)
                    start.show();
            }
        });
    }

    private void hozzaadasKezelese(){
        var nev = inputField.getText();
        String bekert = nev.trim();

        // JATEK SETUP NEVEK ELLENORZESE
        if(bekert.equals("")){
            JOptionPane.showMessageDialog(this.frame, "Névtelen játékos használata nem engedélyezett!");
            return;
        }
        else if(jatekosNevek.contains(bekert)){
            JOptionPane.showMessageDialog(this.frame, "Nem lehet két ugyanolyan nevű játékos!");
            return;
        }


        this.jatekosNevek.add(nev);
        //UI
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.gridx = 0;
        cons.weightx = 1;
        cons.weighty = 1;
        cons.anchor = GridBagConstraints.PAGE_START;

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(nev), BorderLayout.WEST);
        JButton sorTorlese = new JButton("Törlés");
        panel.add(sorTorlese, BorderLayout.EAST);
        sorTorlese.addActionListener(a->{
            this.jatekosNevek.remove(nev);
            this.sp_content.remove(panel);
            this.virokLabel.setText(getViroText());
            sp_content.revalidate();
            sp_content.repaint();
        });
        this.sp_content.add(panel, cons);
        this.virokLabel.setText(getViroText());
        inputField.setText("");
        sp_content.revalidate();
        sp_content.repaint();
    }

    private JPanel inditasiSorGeneralasa(){
        JPanel panel = new JPanel();

        panel.setAlignmentX(JPanel.RIGHT_ALIGNMENT);
        JButton megse = new JButton("Mégse");
        megse.addActionListener(a->{
            parent.show();
            this.frame.dispose();
        });

        JButton mehet = new JButton("Mehet!");
        mehet.addActionListener(a->{
            //TODO: Implementálni a kezdést
            if(this.jatekosNevek.size() < 2)
            {
                JOptionPane.showMessageDialog(this.frame, "A játékot legalább két játékos tudja játszani!");
                return;
            }
            ArrayList<Virologus> jatekosok = new ArrayList<>();
            this.jatekosNevek.forEach(p->{
                var viro = new Virologus();
                viro.setNev(p);
                jatekosok.add(viro);
            });

            int jatekosSzam = jatekosNevek.size();
            PalyaKonfiguracio pk1 = new PalyaKonfiguracio();

            try{
                if(false){   // es igen, jol latjatok ;)
                    // pelda palya teszthez
                    pk1 = ggcfg.CfgManager.readConfig("map.cfg", pk1);
                    System.out.println("debug palya betoltott");
                }
                else if(jatekosSzam<8){
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

            new FoMenu(parent);
        });

        panel.add(megse);
        panel.add(mehet);

        return panel;
    }



}
