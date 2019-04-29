package com.dt.open.hotloaded.plg;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
//@Builder
@Accessors(chain = true)
@RequiredArgsConstructor(staticName = "of")
public class PluginConfig implements Serializable {
    private String id;

    private String name;
    private String className;
    private String jarRemoteUrl;
    private String jarFile;//弃用
    private Boolean active;
    private String version;
    private PluginConfig(String name){

    }
}
