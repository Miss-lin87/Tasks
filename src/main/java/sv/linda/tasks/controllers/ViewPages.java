package sv.linda.tasks.controllers;

import org.springframework.web.servlet.ModelAndView;

public interface ViewPages {
    ModelAndView NEWLOGIN = new ModelAndView("addLogin");
    ModelAndView NEWTASK = new ModelAndView("addTask");
    ModelAndView LOGIN = new ModelAndView("loginPage");
    ModelAndView MAIN = new ModelAndView("tasksPage");
    ModelAndView REDIRECT_MAIN = new ModelAndView("redirect:/main");
    ModelAndView ERROR = new ModelAndView("error");
}
