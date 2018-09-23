package com.andrewhoover.sctool.ui;

import org.springframework.context.ApplicationContext;

import javax.swing.*;

/**
 * Created by andrew on 1/13/2018.
 */

public class Core {

    public Core(ApplicationContext applicationContext) {
        initUI(applicationContext);
    }

    private void initUI(ApplicationContext applicationContext) {
        JFrame frame = applicationContext.getBean(JFrame.class);

        frame.setTitle("SC2Tool");
        frame.setVisible(true);
        frame.setSize(1200, 800);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}