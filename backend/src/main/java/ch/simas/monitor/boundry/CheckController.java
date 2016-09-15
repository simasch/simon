package ch.simas.monitor.boundry;

import ch.simas.monitor.xml.Hosts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CheckController {

    @Autowired
    private CheckResource checkResource;

    @RequestMapping("/results")
    public ModelAndView latestResults() {
        Hosts hosts = checkResource.getLatest();

        return new ModelAndView("results", "hosts", hosts);
    }

}
