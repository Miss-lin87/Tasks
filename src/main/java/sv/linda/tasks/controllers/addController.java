package sv.linda.tasks.controllers;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class addController implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry reg) {
        reg.addViewController("redirect:/tasks/all").setViewName("testing");
    }
}