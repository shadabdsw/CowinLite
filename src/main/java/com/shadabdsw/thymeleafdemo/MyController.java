package com.shadabdsw.thymeleafdemo;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.shadabdsw.thymeleafdemo.Model.AddMemberReq;
import com.shadabdsw.thymeleafdemo.Model.Member;
import com.shadabdsw.thymeleafdemo.Model.ServiceResponse;
import com.shadabdsw.thymeleafdemo.Model.User;
import com.shadabdsw.thymeleafdemo.Model.Vaccination;
import com.shadabdsw.thymeleafdemo.Model.VaccineEditReq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MyController {
    // @Autowired
    // UserRepository userRepository;

    // @Autowired
    // MemberRepository memberRepository;

    // @Autowired
    // MongoTemplate mongoTemplate;

    @Autowired
    RestTemplate restTemplate;

    public User[] getAllUsers() {
        
        // userRepository.findAll().forEach(users::add);

        ResponseEntity<User[]> response = restTemplate.getForEntity("http://localhost:8081/registration/getAllUsers/", User[].class);
        User[] users = response.getBody();

        // for (User user : users) {
        //     System.out.println(user);
        // }

        return users;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }
    
    @PostMapping("/register")
    public String submitForm(@ModelAttribute("user") User user, Model model, RedirectAttributes redirectAttributes) throws URISyntaxException {

        // if (mongoTemplate.exists(Query.query(Criteria.where("phoneNumber").is(user.getPhoneNumber())), User.class)
        //         && mongoTemplate.exists(Query.query(Criteria.where("password").is(user.getPassword())), User.class)) {

        // boolean login = false;
        // login = ;
        // System.out.println(login); 

        user.setMember(new ArrayList<Member>());

        if(restTemplate.getForObject("http://localhost:8081/registration/login/" + user.getPhoneNumber() + "/" + user.getPassword(), Boolean.class)) {

            System.out.println("Welcome Back " + user.getName());
            // model.addAttribute("member", member);
            // System.out.println(member);

        } else {

            System.out.println("Hello, New User! " + user.getName());
            // model.addAttribute("member", member);
            // System.out.println(member);
            // user.setUserType("public");
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            URI uri = new URI("http://localhost:8081/registration/save/");
            User user1 = new User();
            user1.setName(user.getName());
            user1.setPhoneNumber(user.getPhoneNumber());
            user1.setPassword(user.getPassword());
            user1.setUserType("public");
            user1.setMember(user.getMember());

            HttpEntity<User> request = new HttpEntity<User>(user1, headers);

            restTemplate.postForObject(uri, request, User.class);

            // System.out.println("Printing user: " + u.getName());

            // userRepository.save(user);

        }

        user = restTemplate.getForObject("http://localhost:8081/registration/getUserByPhoneNumber/" + user.getPhoneNumber(), User.class);
        System.out.println(user);
        // user = userRepository.findByphoneNumber(user.getPhoneNumber()).get();
        model.addAttribute("user", user);

        // System.out.println(user);

        if (user.getUserType().equals("staff")) {
            model.addAttribute("user", user);
            System.out.println("This is name - " + user.getName());
            redirectAttributes.addFlashAttribute("user", user);
            return "redirect:/staff";
        }

        return "public";
    }

    @GetMapping("/public")
    public ResponseEntity<Object> showPublic(Model model, @RequestParam("name") String name, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("password") String password) {
        System.out.println("Registration: - " + name + phoneNumber + password);

        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }

    @PostMapping("/addmember")
    public ResponseEntity<Object> addmember(@ModelAttribute("user") User user, @RequestBody AddMemberReq addMemberReq, Model model) throws URISyntaxException {
        // public String addmember(@ModelAttribute("user") User user, @RequestBody
        // AddMemberReq addMemberReq, Model model) {

        int flag = 0;
        List<Member> memberDetails = new ArrayList<Member>();
        // user = mongoTemplate.findOne(Query.query(Criteria.where("phoneNumber").is(addMemberReq.getPhoneNumber())),
        //         User.class);
        user = restTemplate.getForObject("http://localhost:8081/registration/getUserByPhoneNumber/" + addMemberReq.getPhoneNumber(), User.class);

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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = new URI("http://localhost:8081/registration/update/");
        User user1 = new User();
        user1.set_id(user.get_id());
        user1.setMember(user.getMember());

        HttpEntity<User> request = new HttpEntity<User>(user1, headers);

        restTemplate.postForObject(uri, request, User.class);

        // userRepository.save(user);
        ServiceResponse<Member> response = new ServiceResponse<Member>("success", addMemberReq.getMember());
        System.out.println(response);
        model.addAttribute("user", user);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
        // return "common :: card";
    }

    @GetMapping("/staff")
    public String staff(Model model, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {

        List<Member> members = new CopyOnWriteArrayList<Member>();

        for (User u : getAllUsers()) {
            if (u.getUserType().equals("public")) {
                members.addAll(u.getMember());
            }
            for(Member m : members) {
                if(m.getVaccinationStatus().equals("Boosted")) {
                    members.remove(m);
                }
            }
        }

        // System.out.println(members);
        model.addAttribute("members", members);
        model.addAttribute("user", user);
        System.out.println("User is here - " + user.getName());
        return "staff";
    }

    @PostMapping("/staff")
    public ResponseEntity<Object> staff(@RequestBody VaccineEditReq vaccineEditReq, @ModelAttribute("user") User user, Model model) throws ParseException, URISyntaxException {
        model.addAttribute("user", user);
        System.out.println(vaccineEditReq.getAdhaar());
        
        System.out.println(user);

        for(User u: getAllUsers()) {
            if(u.getUserType().equals("public")) {
                for(Member m: u.getMember()) {
                    if(m.getAdhaar().equals(vaccineEditReq.getAdhaar())) {
                        Calendar cal = Calendar.getInstance();
                        if(m.getVaccinationStatus().equals("Boosted")) {
                            System.out.println("Fully Vaccinated & Boosted");
                        } else if(m.getVaccinationStatus().equals("Full")) {
                            cal.add(Calendar.MONTH, 1);
                            m.setVaccinationStatus("Boosted");
                        } else {
                            cal.add(Calendar.MONTH, 3);
                            if(m.getVaccinationStatus().equals("Partial")) {
                                m.setVaccinationStatus("Full");
                            } else {
                                m.setVaccinationStatus("Partial");
                            }
                        }
                        Vaccination v = new Vaccination();
                        v.setVaccinationCentre(vaccineEditReq.getVaccinationCentre());
                        v.setVaccinationBy(vaccineEditReq.getVaccinationBy());
                        v.setVaccinationType(vaccineEditReq.getVaccinationType());
                        v.setVaccinationBy(user.getName());
                        System.out.println("Name: " + user.getName());
                        String sDate = vaccineEditReq.getVaccinationDate();
                        SimpleDateFormat vaccinationDate = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = vaccinationDate.parse(sDate);
                        v.setVaccinationDate(date);
                        v.setNextVaccinationDate(cal.getTime());
                        m.getVaccine().add(v);

                        System.out.println(u);

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);

                        URI uri = new URI("http://localhost:8081/registration/update/");
                        User user1 = new User();
                        user1.set_id(u.get_id());
                        user1.setMember(u.getMember());

                        HttpEntity<User> request = new HttpEntity<User>(user1, headers);

                        restTemplate.postForObject(uri, request, User.class);
                        // userRepository.save(u);
                    }
                }
            }
        }

        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }

}
