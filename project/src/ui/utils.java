package ui;

import javax.swing.*;
import java.awt.event.ActionListener;

public class utils {

    public static JButton createButton(String text, ActionListener handler){
        JButton btn = new JButton(text);
        btn.addActionListener(handler);
        return btn;
    }

}
