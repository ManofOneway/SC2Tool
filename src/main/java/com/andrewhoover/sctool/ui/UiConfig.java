package com.andrewhoover.sctool.ui;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;

/**
 * Created by andrew on 3/27/2018.
 */
@Configuration
@ComponentScan
public class UiConfig {

    @Bean
    public JFrame jFrame() {
        return new JFrame();
    }
}
