package com.dt.open.plugin.demo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PluginConfig implements Serializable {
    private String id;
    private String name;
    private String className;
    private String jarRemoteUrl;
    private String jarFile;//弃用
    private Boolean active;
    private String version;
}
