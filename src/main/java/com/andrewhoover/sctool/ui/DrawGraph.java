package com.andrewhoover.sctool.ui;

import com.andrewhoover.sctool.core.Race;
import com.andrewhoover.sctool.populator.GraphData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * Created by andrew on 3/27/2018.
 */
@Component
public class DrawGraph {

    private JFrame frame;
    private UiSettings uiSettings;
    private GraphData graphData;

    @Autowired
    public DrawGraph(JFrame frame, UiSettings uiSettings, GraphData graphData) {
        this.frame = frame;
        this.uiSettings = uiSettings;
        this.graphData = graphData;
        drawGraph();
    }

    private final Color gridColor = new Color(200, 200, 200, 200);
    private final int padding = 50;

    private void drawGraph() {
        frame.add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                /*For each race's collection of (MMR, Number of Players) data, convert to (X, Y) coordinates to be drawn
                  using a scale factor and static offset to leave room for the axis.*/
                uiSettings.setXScale((((double) getWidth() - padding * 2) / (uiSettings.getScaleMaxMMR() - uiSettings.getScaleMinMMR())));
                uiSettings.setYScale((((double) getHeight() - padding * 2) / graphData.getMaxNumberPlayers()));

                HashMap<Race, java.util.List<Point>> graphPoints = new HashMap<>();
                graphPoints.put(Race.PROTOSS, new ArrayList<>());
                graphPoints.put(Race.ZERG, new ArrayList<>());
                graphPoints.put(Race.TERRAN, new ArrayList<>());

                for (int i = uiSettings.getScaleMinMMR(); i < uiSettings.getScaleMaxMMR(); i += 10) {
                    for (Race race : Race.values()) {
                        if (graphData.getRaceData(race).containsKey(i + race.toString())) {
                            int x1 = (int) ((i- uiSettings.getScaleMinMMR() + 1) * uiSettings.getXScale() + padding);
                            int y1 = (int) ((graphData.getMaxNumberPlayers() - graphData.getRaceData(race).get(i + race.toString()).getNumberOfPlayers()) * uiSettings.getYScale() + padding);
                            graphPoints.get(race).add(new Point(x1, y1));
                        }
                    }
                }

                g2.setColor(Color.white);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(Color.black);

                if (graphData.getMaxNumberPlayers() != 0) {

//                    HashMap<Race, Integer> averagesToScale = new HashMap<>();
//                    for (Race race : Race.values()) {
//                        averagesToScale.put(race, (int) ((graphData.getRaceData(race).getRaceAvg() - uiSettings.getScaleMinMMR() + 1) * uiSettings.getXScale() + padding));
//                        System.out.println("Average MMR of " + race.toString() + " " + graphData.getRaceData().get(race).getRaceAvg());
//                    }

                    //Draw Y Axis (tick marks and label)
                    for (int i = 0; i < graphData.getMaxNumberPlayers(); i = i + (graphData.getMaxNumberPlayers() / 10)) {
                        int x1 = padding;
                        int x2 = padding + 1;
                        int y1 = getHeight() - ((i * (getHeight() - 2 * padding)) / (graphData.getMaxNumberPlayers()) + padding);
                        int y2 = y1;
                        g2.setColor(gridColor);
                        g2.drawLine(padding + 1, y1, getWidth() - padding, y2);
                        g2.setColor(Color.black);
                        String yLabel = ((int) ((0 + (graphData.getMaxNumberPlayers()) * ((i * 1.0) / graphData.getMaxNumberPlayers())) * 100)) / 100.0 + "";
                        FontMetrics metrics = g2.getFontMetrics();
                        int labelWidth = metrics.stringWidth(yLabel);
                        g2.drawString(yLabel, x1 - labelWidth - 5, y1 + (metrics.getHeight() / 2) - 3);
                        g2.drawLine(x1, y1, x2, y2);
                    }

                    // Draw X Axis (tick marks and label)
                    for (int i = 0; i < uiSettings.getScaleMaxMMR(); i = i + ((uiSettings.getScaleMaxMMR() - uiSettings.getScaleMinMMR()) / 10)) {
                        int x1 = i * (getWidth() - padding * 2) / (uiSettings.getScaleMaxMMR() - uiSettings.getScaleMinMMR() - 1) + padding;
                        int x2 = x1;
                        int y1 = getHeight() - padding;
                        int y2 = y1;
                        g2.setColor(gridColor);
                        g2.drawLine(x1, getHeight() - padding - 1, x2, padding);
                        g2.setColor(Color.black);
                        String xLabel = ((int) ((0 + (uiSettings.getScaleMaxMMR()) * ((i * 1.0) / (uiSettings.getScaleMaxMMR()))) * 100)) / 100.0 + uiSettings.getScaleMinMMR() + "";
                        FontMetrics metrics = g2.getFontMetrics();
                        int labelWidth = metrics.stringWidth(xLabel);
                        g2.drawString(xLabel, x1 - labelWidth / 2, y1 + metrics.getHeight() + 3);
                        g2.drawLine(x1, y1, x2, y2);
                    }

                    g2.drawLine(padding, getHeight() - padding, padding, padding);
                    g2.drawLine(padding, getHeight() - padding, getWidth() - padding, getHeight() - padding);

                    // Draw the core data lines
                    for (Race race : Race.values()) {
                        switch (race) {
                            case PROTOSS:
                                g2.setColor(Color.orange);
                                break;
                            case ZERG:
                                g2.setColor(Color.magenta);
                                break;
                            case TERRAN:
                                g2.setColor(Color.red);
                                break;
                            default:
                        }

                        for (int i = 0; i < graphPoints.get(race).size() - 1; i++) {
                            int x1 = graphPoints.get(race).get(i).x;
                            int y1 = graphPoints.get(race).get(i).y;
                            int x2 = graphPoints.get(race).get(i + 1).x;
                            int y2 = graphPoints.get(race).get(i + 1).y;
                            g2.drawLine(x1, y1, x2, y2);
                        }
                        //g2.drawLine(averagesToScale.get(race), padding, averagesToScale.get(race), getHeight() - (padding + 1));
                    }
                }
            }
        });
    }
}
