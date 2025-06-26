package sv.linda.tasks.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import java.util.Properties;

public class ViewPages {
    private final Properties prop;

    @Autowired
    public ViewPages(Properties properties) {
        this.prop = properties;
    }

    public ModelAndView getPage(String key) {
        return new ModelAndView(prop.getProperty(key));
    }
}
