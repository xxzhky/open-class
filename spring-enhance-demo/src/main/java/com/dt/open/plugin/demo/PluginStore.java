package com.dt.open.plugin.demo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PluginStore {
    private List<PluginConfig> plugins;

    private String name;

    private Date lastModify;
}
