package com.shadabdsw.thymeleafdemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shadabdsw.thymeleafdemo.Model.AddMemberReq;
import com.shadabdsw.thymeleafdemo.Model.Member;
import com.shadabdsw.thymeleafdemo.Model.Registration;
import com.shadabdsw.thymeleafdemo.Model.ServiceResponse;
import com.shadabdsw.thymeleafdemo.Model.User;
import com.shadabdsw.thymeleafdemo.Model.Vaccination;
import com.shadabdsw.thymeleafdemo.Model.VaccineEditReq;
import com.shadabdsw.thymeleafdemo.Repositories.MemberRepository;
import com.shadabdsw.thymeleafdemo.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class MyController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private RestTemplate restTemplate;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    // public Optional<User> findPhoneNumber(String phoneNumber) {
    // return userRepository.findByphoneNumber(phoneNumber);
    // }

    @GetMapping("/")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }
    
    @PostMapping("/register")
    public ResponseEntity<Object> submitForm(@ModelAttribute("user") User user, Model model, @RequestBody Registration registration) {
        // Member member = new Member();

        System.out.println(registration);

        if (mongoTemplate.exists(Query.query(Criteria.where("phoneNumber").is(registration.getPhoneNumber())), User.class)
                && mongoTemplate.exists(Query.query(Criteria.where("password").is(registration.getPassword())), User.class)) {

            System.out.println("Welcome Back " + registration.getName());
            // model.addAttribute("member", member);
            // System.out.println(member);

        } else {

            System.out.println("Hello, New User! " + registration.getName());
            // model.addAttribute("member", member);
            // System.out.println(member);
            // user.setUserType("public");
            userRepository.save(user);

        }

        user = userRepository.findByphoneNumber(registration.getPhoneNumber()).get();
        model.addAttribute("user", user);

        System.out.println(user);

        // if (user.getUserType().equals("staff")) {
        //     model.addAttribute("user", user);
        //     return "redirect:/staff";
        // }

        ResponseEntity<String> response = restTemplate.exchange("http://localhost:8081/registration/",
                HttpMethod.GET, null, String.class, registration);

        System.out.println("Response: " + response.getStatusCode().toString());

        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }

    @GetMapping("/public")
    public ResponseEntity<Object> showPublic(Model model, @RequestParam("name") String name, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password) {
        System.out.println("Registration: - " + name + phoneNumber + password);

        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }

    @PostMapping("/addmember")
    public ResponseEntity<Object> addmember(@ModelAttribute("user") User user, @RequestBody AddMemberReq addMemberReq,
            Model model) {
        // public String addmember(@ModelAttribute("user") User user, @RequestBody
        // AddMemberReq addMemberReq, Model model) {

        int flag = 0;
        List<Member> memberDetails = new ArrayList<Member>();
        user = mongoTemplate.findOne(Query.query(Criteria.where("phoneNumber").is(addMemberReq.getPhoneNumber())),
                User.class);
        System.out.println(user.getMember());
        // memberDetails.add(addMemberReq.getMember());
        // System.out.println(memberDetails);
        System.out.println(addMemberReq.getMember());
        System.out.println(addMemberReq.getPhoneNumber());

        if (user.getMember().size() < 1) {
            memberDetails.add(addMemberReq.getMember());
            user.setMember(memberDetails);
            flag++;
            System.out.println("this is running");
        } else {
            memberDetails.addAll(user.getMember());
        }

        // System.out.println("this" + memberDetails);
        System.out.println(user);
        System.out.println(user.getPhoneNumber() + " " + user.getPassword());

        if (flag == 0) {
            if (memberDetails != null && memberDetails.size() > 0 && memberDetails.size() < 4) {

                Member m = addMemberReq.getMember();
                System.out.println("m1" + memberDetails);
                memberDetails.add(m);
                System.out.println("m2" + memberDetails);
                user.setMember(memberDetails);

            } else {

                // memberDetails.add(addMemberReq.getMember());
                System.out.println("4 members already registered.");
            }
        }

        System.out.println("Memberssss: " + memberDetails);
        System.out.println(user);
        // user.setMember(memberDetails);
        userRepository.save(user);
        ServiceResponse<Member> response = new ServiceResponse<Member>("success", addMemberReq.getMember());
        System.out.println(response);
        model.addAttribute("user", user);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
        // return "common :: card";
    }

    @GetMapping("/staff")
    public String staff(Model model, @ModelAttribute("user") User user) {
        List<Member> members = new CopyOnWriteArrayList<Member>();

        for (User u : getAllUsers()) {
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

    @PostMapping("/staff")
    public ResponseEntity<Object> staff(@RequestBody VaccineEditReq vaccineEditReq, @ModelAttribute("user") User user, Model model) throws ParseException {
        
        System.out.println(vaccineEditReq.getAdhaar());
        
        System.out.println(user);

        for(User u: getAllUsers()) {
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
                        userRepository.save(u);
                    }
                }
            }
        }

        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }

}
