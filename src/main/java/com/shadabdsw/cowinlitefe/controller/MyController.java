package com.shadabdsw.cowinlitefe.controller;

import com.shadabdsw.cowinlitefe.model.*;
import com.shadabdsw.cowinlitefe.request.AddMemberRequest;
import com.shadabdsw.cowinlitefe.request.StaffEditRequest;
import com.shadabdsw.cowinlitefe.request.VaccineEditRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
@RequiredArgsConstructor
public class MyController {

    @Value("${backend.server.domain}")
    private final String backendServerDomain;

    @Value("#{${backend.server.endpoints}}")
    private final Map<String, String> endpointsMap;

    private final RestTemplate restTemplate;

    private final ErrorHandler error = new ErrorHandler();

    @GetMapping("/")
    public String homePage(Model model) {
        error.setStatus(false); // so that the error message is not shown forever
        error.setMessage("");

        model.addAttribute("user", new User()); // send all user data to register page
        model.addAttribute("error", error); // send error data to register page
        return "register";
    }

    @PostMapping("/register-user")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        user.setMember(new ArrayList<>());
        try {
            ResponseEntity<User> responseEntity = restTemplate.postForEntity(endpointsMap.get("register"), user, User.class);

            if (HttpStatus.CONFLICT.equals(responseEntity.getStatusCode())) {
                throw new RuntimeException();
            }

            error.setStatus(false);
            error.setMessage("User Registered Successfully! Please Login.");

            return "register";
        } catch (Exception e) {
            return handleException(e, "user");
        } finally {
            model.addAttribute("error", error);
        }
    }

    @PostMapping("/login-user")
    public String loginUser(@ModelAttribute("user") User user, Model model) {

        try {
            ResponseEntity<User> response = restTemplate.postForEntity(
                    endpointsMap.get("login") + "?phoneNumber=" + user.getPhoneNumber() + "&password="
                            + user.getPassword(),
                    user,
                    User.class);

            User u = (User) response.getBody();

            model.addAttribute("user", u);

            if (u.getUserType().equals("admin")) {
                return "adminDash";
            } else if (u.getUserType().equals("staff")) {
                return "staffDash";
            } else {
                return "public";
            }
        } catch (Exception e) {
            model.addAttribute("error", error);
            return handleException(e, "user");
        }
    }

    @GetMapping("/get-all-users")
    public User[] getAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(
                backendServerDomain.concat("/getAllUsers/"),
                User[].class);
        User[] users = response.getBody();

        return users;
    }

    public String handleException(Exception e, String context) {
        if (context.equals("user")) {
            if (e.getMessage().contains("401")) {
                return setErrorMessage("error/401", "401!");
            } else if (e.getMessage().contains("403")) {
                return setErrorMessage("register", "Incorrect Password!");
            } else if (e.getMessage().contains("404")) {
                return setErrorMessage("register", "User Not Found!");
            } else if (e.getMessage().contains("405")) {
                return setErrorMessage("error/405", "Method Not Allowed!");
            } else if (e.getMessage().contains("409")) {
                return setErrorMessage("register", "User Already Exists!");
            } else {
                return setErrorMessage("error/500", "Something Went Wrong!");
            }
        } else {
            return setErrorMessage("error/500", "Else Statement!");
        }
    }

    public String setErrorMessage(String view, String errMessage) {
        error.setStatus(true);
        error.setMessage(errMessage);
        return view;
    }


    @PostMapping("/addmember")
    public ResponseEntity<Object> addmember(@ModelAttribute("user") User user, @RequestBody AddMemberRequest addMemberRequest,
                                            Model model) throws URISyntaxException {

        int flag = 0;
        List<Member> memberDetails = new ArrayList<Member>();

        user = restTemplate.getForObject(
                backendServerDomain.concat("/getUserByPhoneNumber/" + addMemberRequest.getPhoneNumber()), User.class);

        if (user.getMember().size() < 1) {
            memberDetails.add(addMemberRequest.getMember());
            user.setMember(memberDetails);
            flag++;
            System.out.println("this is running");
        } else {
            memberDetails.addAll(user.getMember());
        }

        System.out.println(user.getPhoneNumber() + " " + user.getPassword());

        if (flag == 0) {
            if (memberDetails != null && memberDetails.size() > 0 && memberDetails.size() < 4) {
                Member m = addMemberRequest.getMember();
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

        URI uri = new URI(backendServerDomain.concat("/update/"));
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
        user = restTemplate.getForObject(backendServerDomain.concat("/getUserById/" + id), User.class);
        model.addAttribute("user", user);

        return "staffPublicTable";
    }

    @PostMapping("/staff")
    public ResponseEntity<Object> staff(@RequestBody VaccineEditRequest vaccineEditRequest, Model model)
            throws ParseException, URISyntaxException {
        System.out.println(vaccineEditRequest.getAadhaar());

        for (User u : getAllUsers()) {
            if (u.getUserType().equals("public")) {
                for (Member m : u.getMember()) {
                    if (m.getAadhaar().equals(vaccineEditRequest.getAadhaar())) {
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
                        v.setVaccinationCentre(vaccineEditRequest.getVaccinationCentre());
                        v.setVaccinationBy(vaccineEditRequest.getVaccinationBy());
                        v.setVaccinationType(vaccineEditRequest.getVaccinationType());
                        String sDate = vaccineEditRequest.getVaccinationDate();
                        SimpleDateFormat vaccinationDate = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = vaccinationDate.parse(sDate);
                        v.setVaccinationDate(date);
                        v.setNextVaccinationDate(cal.getTime());
                        m.getVaccine().add(v);

                        System.out.println(u);

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);

                        URI uri = new URI(backendServerDomain.concat("/update/"));
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

        String url = backendServerDomain.concat("/deleteUserByPhoneNumber/" + phoneNumber);
        restTemplate.delete(url);

        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }

    @PutMapping("/admin")
    public ResponseEntity<Object> admin(@RequestBody StaffEditRequest staffEditRequest, Model model)
            throws ParseException, URISyntaxException {

        System.out.println("SER - " + staffEditRequest);
        User user1;
        user1 = restTemplate.getForObject(
                backendServerDomain.concat("/getUserByPhoneNumber/" + staffEditRequest.getOldPhoneNumber()),
                User.class);
        System.out.println(user1);

        User newUser = new User(staffEditRequest.getName(), staffEditRequest.getNewPhoneNumber(), user1.getPassword(),
                user1.getUserType(), user1.getMember());

        restTemplate.put(backendServerDomain.concat("/update/" + user1.get_id()), newUser);

        return new ResponseEntity<Object>("success", HttpStatus.OK);
    }

}