package com.andrewhoover.sctool.ui;

import com.andrewhoover.sctool.core.Race;
import com.andrewhoover.sctool.data.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andrew on 1/13/2018.
 */
public class Core {

    private Globals globals = new Globals();
    // Currently the OAuthToken is hard coded in core.Application. Need a way to get it from user input.
    private String OAuthToken;

    public Core(String OAuthToken) {
        this.OAuthToken = OAuthToken;
        initUI();
    }

    private void initUI() {

        final JFrame frame = new JFrame();

        frame.setJMenuBar(createMenuBar(frame));

        drawGraph(frame);

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point click = e.getPoint();
                globals.clickDown = (int) ((click.getX() - 50) / globals.xScale) - 25;
                globals.clickDown += globals.scaleMinMMR;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point click = e.getPoint();
                globals.clickUp = (int) ((click.getX() - 50) / globals.xScale) - 25;
                globals.clickUp += globals.scaleMinMMR;

                if(globals.clickUp > globals.clickDown) {
                    globals.scaleMaxMMR = round(globals.clickUp);
                    globals.scaleMinMMR = round(globals.clickDown);
                    frame.repaint();
                }
            }
        });

        frame.setTitle("SC2Tool");
        frame.setVisible(true);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void drawGraph(JFrame frame) {

        final Color gridColor = new Color(200, 200, 200, 200);
        final int padding = 50;

        frame.add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                /*For each race's collection of (MMR, Number of Players) data, convert to (X, Y) coordinates to be drawn
                  using a scale factor and static offset to leave room for the axis.*/
                globals.xScale = (((double) getWidth() - padding*2) / (globals.scaleMaxMMR - globals.scaleMinMMR));
                globals.yScale = (((double) getHeight() - padding*2) / globals.graphData.getMaxNumberPlayers());

                HashMap<Race, List<Point>> graphPoints = new HashMap<>();
                graphPoints.put(Race.PROTOSS, new ArrayList<>());
                graphPoints.put(Race.ZERG, new ArrayList<>());
                graphPoints.put(Race.TERRAN, new ArrayList<>());

                for( int i = globals.scaleMinMMR; i < globals.scaleMaxMMR; i+=10 ) {
                    for(Race race : Race.values())
                    {
                        if(globals.graphData.getRaceData().get(race).getRace().containsKey(i))
                        {
                            int x1 = (int) ((i - globals.scaleMinMMR + 1) * globals.xScale + padding);
                            int y1 = (int) ((globals.graphData.getMaxNumberPlayers() - globals.graphData.getRaceData().get(race).getKey(i)) * globals.yScale + padding);
                            graphPoints.get(race).add( new Point(x1, y1));
                        }
                    }
                }

                g2.setColor( Color.white );
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor( Color.black );

                if(globals.graphData.getMaxNumberPlayers() != 0) {

                    HashMap<Race, Integer> averagesToScale = new HashMap<>();
                    for(Race race : Race.values())
                    {
                        averagesToScale.put(race, (int)((globals.graphData.getRaceData().get(race).getRaceAvg() - globals.scaleMinMMR + 1) * globals.xScale + padding));
                    }

                    //Draw Y Axis (tick marks and label)
                    for (int i = 0; i < globals.graphData.getMaxNumberPlayers(); i = i + (globals.graphData.getMaxNumberPlayers()/10) ) {
                        int x1 = padding;
                        int x2 = padding + 1;
                        int y1 = getHeight() - ((i * (getHeight() - 2*padding)) / (globals.graphData.getMaxNumberPlayers()) +  padding);
                        int y2 = y1;
                        g2.setColor(gridColor);
                        g2.drawLine(padding + 1, y1, getWidth() - padding, y2);
                        g2.setColor(Color.black);
                        String yLabel = ((int) ((0 + (globals.graphData.getMaxNumberPlayers()) * ((i * 1.0) / globals.graphData.getMaxNumberPlayers())) * 100)) / 100.0 + "";
                        FontMetrics metrics = g2.getFontMetrics();
                        int labelWidth = metrics.stringWidth(yLabel);
                        g2.drawString(yLabel, x1 - labelWidth - 5, y1 + (metrics.getHeight() / 2) - 3);
                        g2.drawLine(x1, y1, x2, y2);
                    }

                    // Draw X Axis (tick marks and label)
                    for (int i = 0; i < globals.scaleMaxMMR; i = i + ((globals.scaleMaxMMR - globals.scaleMinMMR)/10) ) {
                        int x1 = i * (getWidth() - padding * 2) / (globals.scaleMaxMMR - globals.scaleMinMMR - 1) + padding;
                        int x2 = x1;
                        int y1 = getHeight() - padding;
                        int y2 = y1;
                        g2.setColor(gridColor);
                        g2.drawLine(x1, getHeight() - padding - 1, x2, padding);
                        g2.setColor(Color.black);
                        String xLabel = ((int) ((0 + (globals.scaleMaxMMR) * ((i * 1.0) / (globals.scaleMaxMMR))) * 100)) / 100.0 + globals.scaleMinMMR + "";
                        FontMetrics metrics = g2.getFontMetrics();
                        int labelWidth = metrics.stringWidth(xLabel);
                        g2.drawString(xLabel, x1 - labelWidth / 2, y1 + metrics.getHeight() + 3);
                        g2.drawLine(x1, y1, x2, y2);
                    }

                    g2.drawLine(padding, getHeight() - padding, padding, padding);
                    g2.drawLine(padding, getHeight() - padding, getWidth() - padding, getHeight() - padding);

                    // Draw the core data lines
                    for(Race race : Race.values())
                    {
                        switch (race)
                        {
                            case PROTOSS:
                                g2.setColor( Color.orange );
                                break;
                            case ZERG:
                                g2.setColor( Color.magenta );
                                break;
                            case TERRAN:
                                g2.setColor( Color.red );
                                break;
                            default:
                        }

                        for( int i = 0; i < graphPoints.get(race).size() - 1; i++ ) {
                            int x1 = graphPoints.get(race).get(i).x;
                            int y1 = graphPoints.get(race).get(i).y;
                            int x2 = graphPoints.get(race).get(i + 1).x;
                            int y2 = graphPoints.get(race).get(i + 1).y;
                            g2.drawLine(x1, y1, x2, y2);
                        }
                        g2.drawLine(averagesToScale.get(race), padding, averagesToScale.get(race), getHeight() - (padding+1));
                    }
                }
            }
        });
    }

    private JMenuBar createMenuBar(JFrame frame) {

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem runMenuItem = new JMenuItem("Run from Files");
        runMenuItem.setMnemonic(KeyEvent.VK_R);
        runMenuItem.setToolTipText("Run with local files.");
        runMenuItem.addActionListener((ActionEvent event) -> {
            populateData(new DataPopulaterFile());
            frame.repaint();
        });

        JMenuItem downloadAndSaveMenuItem = new JMenuItem("Download and Save");
        downloadAndSaveMenuItem.setMnemonic(KeyEvent.VK_D);
        downloadAndSaveMenuItem.setToolTipText("Download local files and save them to /jsons.");
        downloadAndSaveMenuItem.addActionListener((ActionEvent event) -> {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog (null, "Are you sure you'd like to download files? It will take 10-15 minutes.", "Warning", dialogButton);
            if(dialogResult == JOptionPane.YES_OPTION) {
                populateData(new DataPopulaterUrlSaveFiles(OAuthToken));
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
                populateData(new DataPopulaterUrlNoSave(OAuthToken));
                frame.repaint();
            }
        });

        JMenuItem resetScaleMenuItem = new JMenuItem("Reset Scale");
        resetScaleMenuItem.setMnemonic(KeyEvent.VK_S);
        resetScaleMenuItem.setToolTipText("Reset the scale to full MMR range found.");
        resetScaleMenuItem.addActionListener((ActionEvent event) -> {
            globals.scaleMinMMR = globals.graphData.getMinMMR();
            globals.scaleMaxMMR = globals.graphData.getMaxMMR();
            frame.repaint();
        });

        file.add(runMenuItem);
        file.add(downloadAndSaveMenuItem);
        file.add(downloadWithoutSaveMenuItem);
        file.add(resetScaleMenuItem);

        menuBar.add(file);

        return menuBar;
    }

    private void populateData(DataPopulater dataPopulater)
    {
        globals.graphData = dataPopulater.populateData();
        globals.scaleMaxMMR = globals.graphData.getMaxMMR();
        globals.scaleMinMMR = globals.graphData.getMinMMR();
    }

    private Integer round(Integer rating) {
        return ((rating+5)/10)*10;
    }
}