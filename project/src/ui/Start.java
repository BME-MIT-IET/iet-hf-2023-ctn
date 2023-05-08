package ui;

import Logika.Jatek;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Start {
    public JButton bUjJatek;
    public JButton bLoad;
    public JButton bExit;
    JFrame frame = new JFrame();

    private JLabel getCenteredLabel(String text){
        var label = new JLabel(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        return label;
    }

    public Start(){
        var layout =  new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);

        frame.setLayout(new GridBagLayout());

        //ne bal felul legyen
        frame.setLocationRelativeTo(null);
        //ne legyen kicsi
        frame.setPreferredSize(new Dimension(240,180));
        bUjJatek = new JButton("Új játék");
        bLoad = new JButton("Betöltés");
        bExit = new JButton("Kilépés");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(2, 4, 4, 4);
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;

        frame.add(getCenteredLabel("Start Menü"), gbc);

        gbc.insets = new Insets(4, 4, 4, 4);
        frame.add(bUjJatek, gbc);
        frame.add(bLoad, gbc);
        frame.add(bExit, gbc);

        gbc.insets = new Insets(4, 4, 2, 4);
        frame.add(getCenteredLabel("TNFCS - Vak virologusok"), gbc);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //events
        bExit.addActionListener(a->System.exit(0));
        bLoad.addActionListener(e->handleLoad(e));
        bUjJatek.addActionListener(e->{
            frame.setVisible(false);
            new JatekSetup(this);
        });
        frame.setVisible(true);
    }

    public void show(){
        this.frame.setVisible(true);
    }

    private void handleLoad(ActionEvent actionEvent) {
        JFileChooser fc = new JFileChooser();
        int res = fc.showOpenDialog(null);

        if(res == JFileChooser.APPROVE_OPTION){
            String path = fc.getSelectedFile().getAbsolutePath();
            Jatek.getInstance().betoltes(path);
            frame.setVisible(false);
            new FoMenu(this);
        }
        frame.setVisible(true);
    }
}
