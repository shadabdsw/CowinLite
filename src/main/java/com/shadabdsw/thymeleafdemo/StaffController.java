package com.shadabdsw.thymeleafdemo;

import com.shadabdsw.thymeleafdemo.services.StaffService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shadabdsw.thymeleafdemo.Model.Member;
import com.shadabdsw.thymeleafdemo.Model.User;
import com.shadabdsw.thymeleafdemo.Model.Vaccination;
import com.shadabdsw.thymeleafdemo.Model.VaccineEditReq;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/")
    public String staff(Model model, @ModelAttribute("user") User user) {
        List<Member> members = new CopyOnWriteArrayList<Member>();

        for (User u : staffService.getAllUsers()) {
            if (u.getUserType().equals("public")) {
                members.addAll(u.getMember());
            }
            for(Member m : members) {
                if(m.getVaccinationStatus().equals("Full"))
                members.remove(m);
            }   
        }

        // System.out.println(members);
        model.addAttribute("members", members);

        model.addAttribute("user", user);
        System.out.println(user.getName());
        System.out.println(user.getPhoneNumber());

        return "staff";
    }

    @PostMapping("/")
    public ResponseEntity<Object> staff(@RequestBody VaccineEditReq vaccineEditReq, @ModelAttribute("user") User user, Model model) throws ParseException {
        
        System.out.println(vaccineEditReq.getAdhaar());
        
        System.out.println(user);

        for(User u: staffService.getAllUsers()) {
            if(u.getUserType().equals("public")) {
                for(Member m: u.getMember()) {
                    if(m.getAdhaar().equals(vaccineEditReq.getAdhaar())) {
                        if(m.getVaccinationStatus().equals("None")) {
                            m.setVaccinationStatus("Partial");
                            Vaccination v = new Vaccination();
                            v.setVaccinationCentre(vaccineEditReq.getVaccinationCentre());
                            v.setVaccinationBy(vaccineEditReq.getVaccinationBy());
                            v.setVaccinationType(vaccineEditReq.getVaccinationType());
                            v.setVaccinationBy(user.getName());
                            String sDate = vaccineEditReq.getVaccinationDate();
                            SimpleDateFormat vaccinationDate = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = vaccinationDate.parse(sDate);
                            v.setVaccinationDate(date);
                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.MONTH, 3);
                            v.setNextVaccinationDate(cal.getTime());
                            m.getVaccine().add(v);
                        } else if(m.getVaccinationStatus().equals("Partial")) {
                            m.setVaccinationStatus("Full");
                            Vaccination v = new Vaccination();
                            v.setVaccinationCentre(vaccineEditReq.getVaccinationCentre());
                            v.setVaccinationBy(vaccineEditReq.getVaccinationBy());
                            v.setVaccinationType(vaccineEditReq.getVaccinationType());
                            v.setVaccinationBy(user.getName());
                            String sDate = vaccineEditReq.getVaccinationDate();
                            SimpleDateFormat vaccinationDate = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = vaccinationDate.parse(sDate);
                            v.setVaccinationDate(date);
                            Calendar cal = Calendar.getInstance();
                            cal.add(Calendar.MONTH, 3);
                            v.setNextVaccinationDate(cal.getTime());
                            m.getVaccine().add(v);
                        } else {
                            System.out.println("Fully Vaccinated");
                        }
                        staffService.saveStaff(u);
                    }
                }
            }
        }

        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }
    
}
