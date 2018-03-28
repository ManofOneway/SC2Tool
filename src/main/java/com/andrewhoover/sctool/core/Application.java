package com.andrewhoover.sctool.core;

import com.andrewhoover.sctool.ui.Core;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;

/**
 * Created by andrew on 3/23/2018.
 */

public class Application
{

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Core();
            }
        });
    }
}
