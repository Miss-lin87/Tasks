package sv.linda.tasks.controllers;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
@Data
public class ViewPages {
    private final PageConfig pages;
    private View view;

    @Autowired
    public ViewPages(PageConfig pageConfig) {
        this.pages = pageConfig;
        this.view = new View();
    }

    public class View {
        ModelAndView login = new ModelAndView(pages.getLogin());
        ModelAndView tasks = new ModelAndView(pages.getTasks());
        ModelAndView error = new ModelAndView(pages.getError());
        ModelAndView addLogin = new ModelAndView(pages.getAdd().getLogin());
        ModelAndView addTask = new ModelAndView(pages.getAdd().getTask());
        ModelAndView main = new ModelAndView("redirect:/main");
    }
}