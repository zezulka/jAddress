/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.jaddress;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

/**
 *
 * @author Miloslav Zezulka, 2017
 */
public class ChooseFile {
    private JFrame frame;
    public ChooseFile() {
        frame = new JFrame();

        frame.setVisible(true);
        frame.setTitle(Loader.FILENAME_PROMPT);
        BringToFront();
    }
    public File getFile() {
        JFileChooser fc = new JFileChooser();
        if(JFileChooser.APPROVE_OPTION == fc.showOpenDialog(null)){
            frame.setVisible(false);
            return fc.getSelectedFile();
        }else {
            System.out.println("Next time select a file.");
            System.exit(1);
        }
        return null;
    }

    private void BringToFront() {                  
                    frame.setExtendedState(JFrame.ICONIFIED);
            frame.setExtendedState(JFrame.NORMAL);

    }

}
