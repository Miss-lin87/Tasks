package sv.linda.tasks.controllers;

import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

public interface ViewPages {
    ModelAndView NEWLOGIN = new ModelAndView("addLogin");
    ModelAndView NEWTASK = new ModelAndView("addTask");
    ModelAndView LOGIN = new ModelAndView("loginPage");
    ModelAndView MAIN = new ModelAndView("tasksPage");
    ModelAndView ERROR = new ModelAndView("error");
}
