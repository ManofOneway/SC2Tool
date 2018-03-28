package com.andrewhoover.sctool.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by andrew on 3/27/2018.
 */
@Component
public class ActionListeners {
    
    @Autowired
    public ActionListeners(JFrame frame, UiSettings uiSettings) {

        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Point click = e.getPoint();
                uiSettings.setClickDown((int) ((click.getX() - 50) / uiSettings.getXScale()) - 25);
                uiSettings.setClickDown(uiSettings.getClickDown() + uiSettings.getScaleMinMMR());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                Point click = e.getPoint();
                uiSettings.setClickUp((int) ((click.getX() - 50) / uiSettings.getXScale()) - 25);
                uiSettings.setClickUp(uiSettings.getClickUp() + uiSettings.getScaleMinMMR());

                if(uiSettings.getClickUp() > uiSettings.getClickDown()) {
                    uiSettings.setScaleMaxMMR(round(uiSettings.getClickUp()));
                    uiSettings.setScaleMinMMR(round(uiSettings.getClickDown()));
                    frame.repaint();
                }
            }
        });
    }

    private Integer round(Integer rating) {
        return ((rating+5)/10)*10;
    }
}
