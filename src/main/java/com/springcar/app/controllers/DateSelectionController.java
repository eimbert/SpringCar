package com.springcar.app.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.springcar.app.models.entity.Reservation;
import com.springcar.app.models.service.interfaces.IOfficeMasterService;

@Controller
public class DateSelectionController {

	
	@Autowired
	IOfficeMasterService officeService;
	
	@GetMapping("/reservation/dateselection")
	public String showDateSelection (Model model, HttpSession session) {
	
		model.addAttribute("offices", officeService.findAll());
		return "/reservation/dateselection/index";
		
	}
	
	@PostMapping("/reservation/dateselection")
	public String setDateSelection(@ModelAttribute("reservation") Reservation rent, HttpSession session) {
		
		if (initDateIsBeforeFinalDate(rent)) {
			session.setAttribute("reservation", rent);
			session.setAttribute("differenceInDays", Utils.calculateDifferenceInDays (rent));
		} else {
			session.setAttribute("errorDates", "Pick up date must be before return date!");
			return "/reservation/dateselection/index"; 
		}
		return "redirect:/reservation/extrasconfig/";
	}
	
	public boolean initDateIsBeforeFinalDate(Reservation rent) {
		if (rent.getInitDate().compareTo(rent.getFinalDate()) > 0) {
			return false;
		} else {
			return true;
		}
	}
	
}
