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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class MyController {

    @Autowired
    RestTemplate restTemplate;

    //get the list of all users
    public User[] getAllUsers() {

        ResponseEntity<User[]> response = restTemplate.getForEntity("http://localhost:8081/registration/getAllUsers/", User[].class);
        User[] users = response.getBody();

        return users;
    }

    //home page
    @GetMapping("/")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user); //send all user data to register page
        return "register";
    }
    
    //checks and moves to next page depending on registered phone number
    @PostMapping("/register")
    public String submitForm(@ModelAttribute("user") User user, Model model) throws URISyntaxException {

        user.setMember(new ArrayList<Member>()); //initialize member list

        if(restTemplate.getForObject("http://localhost:8081/registration/login/" + user.getPhoneNumber() + "/" + user.getPassword(), Boolean.class)) {

            //logins user
            System.out.println("Welcome Back " + user.getName());

        } else {

            //creates new user
            System.out.println("Hello, New User! " + user.getName());

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

        }

        user = restTemplate.getForObject("http://localhost:8081/registration/getUserByPhoneNumber/" + user.getPhoneNumber(), User.class);
        System.out.println(user);
        model.addAttribute("user", user);

        if (user.getUserType().equals("staff")) {
            return "staffDash";
        }

        if (user.getUserType().equals("admin")) {
            model.addAttribute("user", user);
            System.out.println("This is name - " + user.getName());
            return "adminDash";
        }

        return "public";
    }

    @PostMapping("/addmember")
    public ResponseEntity<Object> addmember(@ModelAttribute("user") User user, @RequestBody AddMemberReq addMemberReq, Model model) throws URISyntaxException {

        int flag = 0;
        List<Member> memberDetails = new ArrayList<Member>();

        user = restTemplate.getForObject("http://localhost:8081/registration/getUserByPhoneNumber/" + addMemberReq.getPhoneNumber(), User.class);

        System.out.println(user.getMember());
        
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

                System.out.println("4 members already registered.");
            }
        }

        System.out.println("Memberssss: " + memberDetails);
        System.out.println(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = new URI("http://localhost:8081/registration/update/");
        User user1 = new User();
        user1.set_id(user.get_id());
        user1.setMember(user.getMember());

        HttpEntity<User> request = new HttpEntity<User>(user1, headers);

        restTemplate.postForObject(uri, request, User.class);

        ServiceResponse<Member> response = new ServiceResponse<Member>("success", addMemberReq.getMember());
        System.out.println(response);
        model.addAttribute("user", user);

        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @GetMapping("/staffPublicTable/{id}")
    public String staff(Model model, @PathVariable String id) {

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

        model.addAttribute("members", members);

        User user;
        user = restTemplate.getForObject("http://localhost:8081/registration/getUserById/" + id, User.class);
        model.addAttribute("user", user);

        return "staffPublicTable";
    }

    @GetMapping("/adminStaffTable/{id}")
    public String admin(Model model, @PathVariable String id) {

        List<User> users = new ArrayList<User>();

        for(User u: getAllUsers()) {
            if(u.getUserType().equals("staff")) {
                users.add(u);
            }
        }

        model.addAttribute("users", users);
        
        return "adminStaffTable";
        
        
    }

    @PostMapping("/staff")
    public ResponseEntity<Object> staff(@RequestBody VaccineEditReq vaccineEditReq, Model model) throws ParseException, URISyntaxException {
        System.out.println(vaccineEditReq.getAdhaar());

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
                    }
                }
            }
        }

        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }

    
}
