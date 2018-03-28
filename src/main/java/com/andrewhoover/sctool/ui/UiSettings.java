package com.andrewhoover.sctool.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by andrew on 3/27/2018.
 */
@Component
public class UiSettings {

    @Autowired
    public UiSettings() {
        scaleMinMMR = 0;
        scaleMaxMMR = 0;
        xScale = 0.0;
        yScale = 0.0;
        clickUp = 0;
        clickDown = 0;
    }

    private int scaleMinMMR;
    private int scaleMaxMMR;

    private double xScale;
    private double yScale;

    private int clickDown;
    private int clickUp;

    public void setClickDown(int clickDown) {
        this.clickDown = clickDown;
    }

    public int getClickDown() {
        return clickDown;
    }

    public double getXScale() {
        return xScale;
    }

    public int getScaleMinMMR() {
        return scaleMinMMR;
    }

    public int getClickUp() {
        return clickUp;
    }

    public void setClickUp(int clickUp) {
        this.clickUp = clickUp;
    }

    public int getScaleMaxMMR() {
        return scaleMaxMMR;
    }

    public void setScaleMaxMMR(Integer scaleMaxMMR) {
        this.scaleMaxMMR = scaleMaxMMR;
    }

    public void setScaleMinMMR(Integer scaleMinMMR) {
        this.scaleMinMMR = scaleMinMMR;
    }

    public void setXScale(double xScale) {
        this.xScale = xScale;
    }

    public void setYScale(double YScale) {
        this.yScale = YScale;
    }

    public double getYScale() {
        return yScale;
    }
}
