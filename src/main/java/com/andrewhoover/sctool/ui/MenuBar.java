package com.andrewhoover.sctool.ui;

import com.andrewhoover.sctool.data.*;
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
    private GraphData graphData;

    @Autowired
    public MenuBar(JFrame frame, UiSettings uiSettings, GraphData graphData) {
        this.graphData = graphData;
        createMenuBar(frame, uiSettings);
    }

    private void createMenuBar(JFrame frame, UiSettings uiSettings) {

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem runMenuItem = new JMenuItem("Run from Files");
        runMenuItem.setMnemonic(KeyEvent.VK_R);
        runMenuItem.setToolTipText("Run with local files.");
        runMenuItem.addActionListener((ActionEvent event) -> {
            populateData(new DataPopulaterFile(), uiSettings);
            frame.repaint();
        });

        JMenuItem downloadAndSaveMenuItem = new JMenuItem("Download and Save");
        downloadAndSaveMenuItem.setMnemonic(KeyEvent.VK_D);
        downloadAndSaveMenuItem.setToolTipText("Download local files and save them to /jsons.");
        downloadAndSaveMenuItem.addActionListener((ActionEvent event) -> {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you'd like to download files? It will take 10-15 minutes.", "Warning", dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION) {
                populateData(new DataPopulaterUrlSaveFiles(), uiSettings);
                frame.repaint();
            }
        });

        JMenuItem downloadWithoutSaveMenuItem = new JMenuItem("Download Without Saving");
        downloadWithoutSaveMenuItem.setMnemonic(KeyEvent.VK_O);
        downloadWithoutSaveMenuItem.setToolTipText("Download local files without saving them.");
        downloadWithoutSaveMenuItem.addActionListener((ActionEvent event) -> {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you'd like to download files? It will take 10-15 minutes.", "Warning", dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION) {
                populateData(new DataPopulaterUrlNoSave(), uiSettings);
                frame.repaint();
            }
        });

        JMenuItem resetScaleMenuItem = new JMenuItem("Reset Scale");
        resetScaleMenuItem.setMnemonic(KeyEvent.VK_S);
        resetScaleMenuItem.setToolTipText("Reset the scale to full MMR range found.");
        resetScaleMenuItem.addActionListener((ActionEvent event) -> {
            uiSettings.setScaleMaxMMR(graphData.getMaxMMR());
            uiSettings.setScaleMinMMR(graphData.getMinMMR());
            frame.repaint();
        });

        file.add(runMenuItem);
        file.add(downloadAndSaveMenuItem);
        file.add(downloadWithoutSaveMenuItem);
        file.add(resetScaleMenuItem);

        menuBar.add(file);

        frame.setJMenuBar(menuBar);
    }

    private void populateData(DataPopulater dataPopulater, UiSettings uiSettings)
    {
        dataPopulater.populateData(graphData);
        uiSettings.setScaleMaxMMR(graphData.getMaxMMR());
        uiSettings.setScaleMinMMR(graphData.getMinMMR());
    }
}
