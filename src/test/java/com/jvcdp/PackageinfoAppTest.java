package com.jvcdp;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.jvcdp.controller.HomeController;

public class PackageinfoAppTest {

	@Test
    public void testApp() {
		HomeController hc = new HomeController();
		String result = hc.home();
        assertEquals( result, "Command Line/Git Job Dispatcher Service!" );
	}
}
