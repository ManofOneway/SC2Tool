package com.andrewhoover.sctool.core;

import com.andrewhoover.sctool.data.DataConfig;
import com.andrewhoover.sctool.populator.DataPopulator;
import com.andrewhoover.sctool.populator.PopulatorConfig;
import com.andrewhoover.sctool.ui.Core;
import com.andrewhoover.sctool.ui.UiConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.swing.*;

/**
 * Created by andrew on 3/23/2018.
 */
public class Application
{

    public static void main(String[] args)
    {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(UiConfig.class, DataConfig.class, PopulatorConfig.class);
        applicationContext.getBean(DataPopulator.class).load();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Core(applicationContext);
            }
        });
    }
}
