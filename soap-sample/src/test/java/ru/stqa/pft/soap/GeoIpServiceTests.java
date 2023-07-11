package ru.stqa.pft.soap;

import com.lavasoft.*;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.assertTrue;

public class GeoIpServiceTests {

    @Test
    public void testMyIP() {
        String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation20("176.193.214.52");
        assertTrue(geoIP.contains("RU"));
    }
}