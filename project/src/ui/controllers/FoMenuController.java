package ui.controllers;

import Logika.Jatek;
import ui.FoMenu;

import javax.swing.*;
import java.io.File;

public class FoMenuController {
    FoMenu view;

    public FoMenuController(FoMenu view){
        this.view = view;
    }

    public void mentesKezelese(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Mentés helyének kiválasztása");

        int res = fileChooser.showSaveDialog(this.view.getFrame());

        if (res == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            Jatek.getInstance().mentes(fileToSave.getAbsolutePath());
            view.getFrame().dispose();
        }
    }

}
