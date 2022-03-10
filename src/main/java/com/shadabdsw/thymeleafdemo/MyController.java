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
import com.shadabdsw.thymeleafdemo.Model.StaffEditReq;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class MyController {

    @Autowired
    RestTemplate restTemplate;

    // get the list of all users
    public User[] getAllUsers() {

        ResponseEntity<User[]> response = restTemplate.getForEntity("http://localhost:8081/registration/getAllUsers/",
                User[].class);
        User[] users = response.getBody();

        return users;
    }

    // home page
    @GetMapping("/")
    public String homePage(Model model) {
        User user = new User();
        model.addAttribute("user", user); // send all user data to register page
        return "register";
    }

    @PostMapping("/loginUser")
    public String loginUser(@ModelAttribute("user") User user, Model model) throws Exception {
        ResponseEntity<User> response = restTemplate
                .postForEntity("http://localhost:8081/registration/loginUser?phoneNumber=" + user.getPhoneNumber() +
                        "&password=" + user.getPassword(), user, User.class);
        User u = (User) response.getBody();
        model.addAttribute("user", u);
        if (u.getUserType().equals("admin")) {
            return "adminDash";
        } else if (u.getUserType().equals("staff")) {
            return "staffDash";
        } else {
            return "public";
        }
    }

    @PostMapping("/registerUser")
    public String registerUser(@ModelAttribute("user") User user) throws Exception {
        user.setMember(new ArrayList<Member>());
        restTemplate.postForEntity("http://localhost:8081/registration/registerUser/", user,
                User.class);
        return "register";

    }

    @PostMapping("/addmember")
    public ResponseEntity<Object> addmember(@ModelAttribute("user") User user, @RequestBody AddMemberReq addMemberReq,
            Model model) throws URISyntaxException {

        int flag = 0;
        List<Member> memberDetails = new ArrayList<Member>();

        user = restTemplate.getForObject(
                "http://localhost:8081/registration/getUserByPhoneNumber/" + addMemberReq.getPhoneNumber(), User.class);

        if (user.getMember().size() < 1) {
            memberDetails.add(addMemberReq.getMember());
            user.setMember(memberDetails);
            flag++;
            System.out.println("this is running");
        } else {
            memberDetails.addAll(user.getMember());
        }

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

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        URI uri = new URI("http://localhost:8081/registration/update/");
        User user1 = new User();
        user1.set_id(user.get_id());
        user1.setMember(user.getMember());

        HttpEntity<User> request = new HttpEntity<User>(user1, headers);

        restTemplate.postForObject(uri, request, User.class);

        model.addAttribute("user", user);

        // return new ResponseEntity<Object>(response, HttpStatus.OK);
        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }

    @GetMapping("/staffPublicTable/{id}")
    public String staff(Model model, @PathVariable String id) {

        List<Member> members = new CopyOnWriteArrayList<Member>();

        for (User u : getAllUsers()) {
            if (u.getUserType().equals("public")) {
                members.addAll(u.getMember());
            }
            for (Member m : members) {
                if (m.getVaccinationStatus().equals("Boosted")) {
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

    @PostMapping("/staff")
    public ResponseEntity<Object> staff(@RequestBody VaccineEditReq vaccineEditReq, Model model)
            throws ParseException, URISyntaxException {
        System.out.println(vaccineEditReq.getAdhaar());

        for (User u : getAllUsers()) {
            if (u.getUserType().equals("public")) {
                for (Member m : u.getMember()) {
                    if (m.getAdhaar().equals(vaccineEditReq.getAdhaar())) {
                        Calendar cal = Calendar.getInstance();
                        if (m.getVaccinationStatus().equals("Boosted")) {
                            System.out.println("Fully Vaccinated & Boosted");
                        } else if (m.getVaccinationStatus().equals("Full")) {
                            cal.add(Calendar.MONTH, 1);
                            m.setVaccinationStatus("Boosted");
                        } else {
                            cal.add(Calendar.MONTH, 3);
                            if (m.getVaccinationStatus().equals("Partial")) {
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

    @GetMapping("/adminStaffTable/{id}")
    public String admin(Model model, @PathVariable String id) {

        List<User> users = new ArrayList<User>();

        for (User u : getAllUsers()) {
            if (u.getUserType().equals("staff")) {
                users.add(u);
            }
        }

        model.addAttribute("users", users);

        return "adminStaffTable";

    }

    @DeleteMapping("/admin/{phoneNumber}")
    public ResponseEntity<Object> admin(@PathVariable("phoneNumber") String phoneNumber, Model model)
            throws ParseException, URISyntaxException {

        String url = "http://localhost:8081/registration/deleteUserByPhoneNumber/" + phoneNumber;
        restTemplate.delete(url);

        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }

    @PutMapping("/admin")
    public ResponseEntity<Object> admin(@RequestBody StaffEditReq staffEditReq, Model model)
            throws ParseException, URISyntaxException {

        System.out.println("SER - " + staffEditReq);
        User user1;
        user1 = restTemplate.getForObject(
                "http://localhost:8081/registration/getUserByPhoneNumber/" + staffEditReq.getOldPhoneNumber(),
                User.class);
        System.out.println(user1);

        User newUser = new User(staffEditReq.getName(), staffEditReq.getNewPhoneNumber(), user1.getPassword(),
                user1.getUserType(), user1.getMember());

        restTemplate.put("http://localhost:8081/registration/update/" + user1.get_id(), newUser);

        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }

}