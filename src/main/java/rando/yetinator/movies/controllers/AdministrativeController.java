package rando.yetinator.movies.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller		
public class AdministrativeController {
	
	@RequestMapping("/admin")
	public String home(Model model){
		
		model.addAttribute("random", "This is the administrative page.  You can mess stuff up here.  If you don't belong, then go away.  ");
		model.addAttribute("random2", "");
		return "admin";
	}
	
}
