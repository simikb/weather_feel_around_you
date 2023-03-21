package com.qc.ssm.ifc.feelclimate.repository;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClimateRepositoryTest {

    @Mock
    ClimateRepository climateRepository;

    @Before
    public void setup() {

    }

    @Test
    public void getClimateTest() {
        climateRepository.getClimate("goa", null);
        verify(climateRepository, atLeastOnce()).getClimate("goa", null);
    }

    @Test
    public void getClimateEmptyTest() {
        climateRepository.getClimate("", null);
        verify(climateRepository, atLeastOnce()).getClimate("", null);
    }

    @Test
    public void getClimateNullTest() {
        climateRepository.getClimate(null, null);
        verify(climateRepository, atLeastOnce()).getClimate(null, null);
    }

    @Test
    public void getClimateByLocationNullTest() {
        climateRepository.getClimateByLocation(null, null, null);
        verify(climateRepository, atLeastOnce()).getClimateByLocation(null, null, null);
    }

    @Test
    public void getClimateByLocationTest() {
        climateRepository.getClimateByLocation("1.0", "2.0", null);
        verify(climateRepository, atLeastOnce()).getClimateByLocation("1.0", "2.0", null);
    }

    @Test
    public void getClimateByLocationsTest() {
        climateRepository.getClimateByLocations("1.0", "2.0");
        verify(climateRepository, atLeastOnce()).getClimateByLocations("1.0", "2.0");
    }

    @Test
    public void getClimateByLocationsNullTest() {
        climateRepository.getClimateByLocations(null, null);
        verify(climateRepository, atLeastOnce()).getClimateByLocations(null, null);
    }

    @Test
    public void getClimateByLocationsEmptyTest() {
        climateRepository.getClimateByLocations("", "");
        verify(climateRepository, atLeastOnce()).getClimateByLocations("", "");
    }

    @Test
    public void getClimatesNullTest() {
        climateRepository.getClimates(null);
        verify(climateRepository, atLeastOnce()).getClimates(null);
    }

    @Test
    public void getClimatesTest() {
        climateRepository.getClimates("goa");
        verify(climateRepository, atLeastOnce()).getClimates("goa");
    }

    @Test
    public void getClimatesEmptyTest() {
        climateRepository.getClimates("");
        verify(climateRepository, atLeastOnce()).getClimates("");
    }
}
