package sv.linda.tasks.constructors;

import org.springframework.web.servlet.ModelAndView;

public class ModelView {
    private ModelAndView modelAndView;

    public ModelView() {
        this.modelAndView = new ModelAndView();
    }

    public ModelAndView page(String viewName) {
        this.modelAndView.setViewName(viewName);
        return this.modelAndView;
    }
}
