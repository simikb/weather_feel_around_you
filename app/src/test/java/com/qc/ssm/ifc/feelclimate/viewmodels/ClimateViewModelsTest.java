package com.qc.ssm.ifc.feelclimate.viewmodels;

import junit.framework.TestCase;

import org.junit.Before;

public class ClimateViewModelsTest extends TestCase {

    @Before
    void before()
    {

    }
    public void testAdd() {
        double result= 2.5 + 2.5;
        assertTrue(result == 5.0);
    }
}
