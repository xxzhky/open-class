package com.dt.open.hotloaded.plg;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PluginStore {
    private List<PluginConfig> plugins;
    private String name;
    private Date lastModify;
}
