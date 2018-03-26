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
                // TODO: This token should not be hard coded. Among other issues, it expires every 30 days.
                // Could be command line arg, or set in a .properties file.
                new Core("6td6f7fvku6wtjvf684twcvg");
            }
        });
    }
}
