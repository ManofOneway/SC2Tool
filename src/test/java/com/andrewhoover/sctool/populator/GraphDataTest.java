package com.andrewhoover.sctool.populator;

import com.andrewhoover.sctool.core.Race;
import com.andrewhoover.sctool.data.database.DataPoint;
import org.junit.Before;
import org.junit.Test;

public class GraphDataTest {
    private GraphData graphData;

    @Before
    public void initGraphData()
    {
        graphData = new GraphData();
    }

    @Test
    public void testNewMin()
    {
        assert graphData.getMinMMR() == 10000;
        graphData.addMmr(500, Race.PROTOSS);
        graphData.addMmr(250, Race.PROTOSS);
        graphData.addMmr(750, Race.PROTOSS);
        assert graphData.getMinMMR() == 250;
    }

    @Test
    public void testNewMax()
    {
        assert graphData.getMaxMMR() == 0;
        graphData.addMmr(500, Race.PROTOSS);
        graphData.addMmr(250, Race.PROTOSS);
        graphData.addMmr(750, Race.PROTOSS);
        assert graphData.getMaxMMR() == 750;
    }

    @Test
    public void testNewMaxNumberPlayers()
    {
        assert graphData.getMaxNumberPlayers() == 1;
        graphData.addMmr(500, Race.PROTOSS);
        graphData.addMmr(250, Race.PROTOSS);
        graphData.addMmr(750, Race.PROTOSS);
        assert graphData.getMaxNumberPlayers() == 1;
        graphData.addMmr(250, Race.TERRAN);
        graphData.addMmr(250, Race.PROTOSS);
        assert graphData.getMaxNumberPlayers() == 2;
    }

    @Test
    public void testMapData()
    {
        assert graphData.getMapData().get(750 + Race.PROTOSS.toString()) == null;
        graphData.addMmr(500, Race.PROTOSS);
        graphData.addMmr(250, Race.PROTOSS);
        graphData.addMmr(750, Race.PROTOSS);
        graphData.addMmr(750, Race.PROTOSS);
        DataPoint dataPoint = graphData.getMapData().get(750 + Race.PROTOSS.toString());

        assert dataPoint.getRace() == Race.PROTOSS;
        assert dataPoint.getMMR() == 750;
        assert dataPoint.getNumberOfPlayers() == 2;
    }

}
