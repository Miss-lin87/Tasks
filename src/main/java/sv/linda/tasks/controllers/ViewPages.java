package sv.linda.tasks.controllers;

import org.springframework.web.servlet.ModelAndView;

import java.util.Properties;

public class ViewPages {
    private Properties prop;

    public ViewPages(Properties prop) {
        this.prop = prop;
    }

    public ModelAndView getPage(String key) {
        return new ModelAndView(prop.getProperty(key));
    }
}
