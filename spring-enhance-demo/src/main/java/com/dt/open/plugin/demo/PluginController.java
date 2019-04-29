package com.dt.open.plugin.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;

@RestController("plugin")
@RequiredArgsConstructor(onConstructor = @_(@Autowired))
public class PluginController {

    SpringPluginFactory factory;

    @GetMapping("/list")
    public String getPlugins(HttpServletRequest request, HttpServletResponse response){
        List<PluginConfig> list;

        list=factory.getPluginList();
        request.setAttribute("plugins",list);
        return "plugins";
    }

}
