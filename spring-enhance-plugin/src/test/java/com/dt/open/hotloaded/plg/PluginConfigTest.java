package com.dt.open.hotloaded.plg;

import org.junit.Test;

import static org.junit.Assert.*;

public class PluginConfigTest {

    @Test
    public void testBuilder() {

//        PluginConfig pc = PluginConfig.builder().active(true).className("xyz").build();
//        System.out.println(pc);
    }

    @Test
    public void testChain(){
        PluginConfig pc= PluginConfig.of().setActive(true).setJarFile("xcvbn.jar");
        System.out.println(pc);
        assertNotNull(pc);
    }
}