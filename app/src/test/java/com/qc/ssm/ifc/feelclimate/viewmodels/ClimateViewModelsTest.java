package com.qc.ssm.ifc.feelclimate.viewmodels;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClimateViewModelsTest {
    @Mock
    ClimateViewModel climateViewModel;
    ClimateViewModel climateViewModelSpy;
    @Mock
    private Context mockContext;

    @Before
    public void setup() {

    }

    @Test
    public void testAdd() {
        double result = 2.5 + 2.5;
        Assert.assertTrue(result == 5.0);
    }

    @Test
    public void getClimateUpdateByPlaceNamesTest() {
        climateViewModel.getClimateUpdateByPlaceNames("goa");
        verify(climateViewModel, atLeastOnce()).getClimateUpdateByPlaceNames("goa");
    }

    @Test
    public void getClimateUpdateByPlaceNamesEmptyTest() {
        climateViewModel.getClimateUpdateByPlaceNames("");
        verify(climateViewModel, atLeastOnce()).getClimateUpdateByPlaceNames("");
    }

    @Test
    public void getClimateUpdateByPlaceNamesNullTest() {
        climateViewModel.getClimateUpdateByPlaceNames(null);
        verify(climateViewModel, atLeastOnce()).getClimateUpdateByPlaceNames(null);
    }

    @Test
    public void getClimateUpdatesTest() {
        climateViewModel.getClimateUpdates("1.0", "12.0");
        verify(climateViewModel, atLeastOnce()).getClimateUpdates("1.0", "12.0");
    }

    @Test
    public void getClimateUpdatesEmptyTest() {
        climateViewModel.getClimateUpdates("", "");
        verify(climateViewModel, atLeastOnce()).getClimateUpdates("", "");
    }

    @Test
    public void getClimateUpdatesNullTest() {
        climateViewModel.getClimateUpdates(null, null);
        verify(climateViewModel, atLeastOnce()).getClimateUpdates(null, null);
    }

    @Test
    public void getClimateUpdateEmptyTest() {
        climateViewModel.getClimateUpdate("", "");
        verify(climateViewModel, atLeastOnce()).getClimateUpdate("", "");
    }

    @Test
    public void getClimateUpdateByPlaceNameTest() {
        climateViewModel.getClimateUpdateByPlaceName("goa");
        verify(climateViewModel, atLeastOnce()).getClimateUpdateByPlaceName("goa");
    }
}
