package com.andrewhoover.sctool.ui;

import com.andrewhoover.sctool.populator.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * Created by andrew on 3/27/2018.
 */
@Component
public class MenuBar {
    private final DataPopulator dataPopulator;

    @Autowired
    public MenuBar(JFrame frame, UiSettings uiSettings, DataPopulator dataPopulator) {
        this.dataPopulator = dataPopulator;
        createMenuBar(frame, uiSettings);
    }

    private void createMenuBar(JFrame frame, UiSettings uiSettings) {

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem downloadAndSaveMenuItem = new JMenuItem("Download and Save");
        downloadAndSaveMenuItem.setMnemonic(KeyEvent.VK_D);
        downloadAndSaveMenuItem.setToolTipText("Download local files and save them to /jsons.");
        downloadAndSaveMenuItem.addActionListener((ActionEvent event) -> {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you'd like to download files? It will take 10-15 minutes.", "Warning", dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION) {
                populateData();
                frame.repaint();
            }
        });

        JMenuItem resetScaleMenuItem = new JMenuItem("Reset Scale");
        resetScaleMenuItem.setMnemonic(KeyEvent.VK_S);
        resetScaleMenuItem.setToolTipText("Reset the scale to full MMR range found.");
        resetScaleMenuItem.addActionListener((ActionEvent event) -> {
            uiSettings.setScaleToDefaults();
            frame.repaint();
        });

        file.add(downloadAndSaveMenuItem);
        file.add(resetScaleMenuItem);

        menuBar.add(file);

        frame.setJMenuBar(menuBar);
    }

    private void populateData()
    {
        dataPopulator.populateData();
    }
}
