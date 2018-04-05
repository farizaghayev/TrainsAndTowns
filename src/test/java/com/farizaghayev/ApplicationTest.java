package com.farizaghayev;

import com.farizaghayev.app.ServiceMap;
import com.farizaghayev.app.ServiceMapImpl;
import com.farizaghayev.service.TrainTownService;
import com.farizaghayev.service.TrainTownServiceException;
import com.farizaghayev.service.TrainTownServiceImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

/**
 * Created by faghayev on 4/5/2018.
 */
public class ApplicationTest {

    private TrainTownService service;

    @Before
    public void initObjects() {
        ServiceMap map = new ServiceMapImpl();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("graph.txt").getFile());
            map.init(file.getAbsolutePath());
            service = new TrainTownServiceImpl(map);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCase1() {
        int ans = service.distance("A", "B", "C");
        assertEquals(9, ans);
    }
    @Test
    public void testCase2() {
        int ans = service.distance("A", "D");
        assertEquals(5, ans);
    }

    @Test
    public void testCase3() {
        int ans = service.distance("A", "D", "C");
        assertEquals(13, ans);
    }

    @Test
    public void testCase4() {
        int ans = service.distance("A", "E", "B", "C", "D");
        assertEquals(22, ans);
    }

    @Test(expected = TrainTownServiceException.class)
    public void testCase5() {
        int ans = service.distance("A", "E", "D");
    }



    @Test
    public void testCase6() {
        int ans = service.countEdgesMaxStop("C", "C", 3);
        assertEquals(2, ans);
    }


    @Test
    public void testCase7() {
        int ans = service.countEdgesWithStop("A", "C", 4);
        assertEquals(3, ans);
    }

    @Test
    public void testCase8() {
        int ans = service.distanceShortestPath("A", "C");
        assertEquals(9, ans);
    }


    @Test
    public void testCase9() {
        int ans = service.distanceShortestPath("B", "B");
        assertEquals(9, ans);
    }

    @Test
    public void testCase10() {
        int ans = service.countEdgesMaxDistance("C", "C", 30);
        assertEquals(7, ans);
    }



}
