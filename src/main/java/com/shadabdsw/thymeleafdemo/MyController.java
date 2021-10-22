package com.shadabdsw.thymeleafdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shadabdsw.thymeleafdemo.Model.Member;
import com.shadabdsw.thymeleafdemo.Model.ServiceResponse;
import com.shadabdsw.thymeleafdemo.Model.User;
import com.shadabdsw.thymeleafdemo.Model.AddMemberReq;

import com.shadabdsw.thymeleafdemo.Repositories.MemberRepository;
import com.shadabdsw.thymeleafdemo.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class MyController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public Optional<User> findPhoneNumber(String phoneNumber) {
        return userRepository.findByphoneNumber(phoneNumber);
    }

    @GetMapping("/register")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        // System.out.println(getAllUsers());
        return "register_form";
    }



    @PostMapping("/home")
    public String submitForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("name", "shadab");
        System.out.println(user);
        Member member = new Member();
        if (mongoTemplate.exists(Query.query(Criteria.where("phoneNumber").is(user.getPhoneNumber())), User.class)
                && mongoTemplate.exists(Query.query(Criteria.where("password").is(user.getPassword())), User.class)) {

            System.out.println("Welcome Back");
            model.addAttribute("member", member);

            // user = mongoTemplate.findOne(Query.query(Criteria.where("phoneNumber").is(user.getPhoneNumber())),
            //         User.class);
            // model.addAttribute("user", user);

        
        } else {
            System.out.println("Hello, New User!");
            model.addAttribute("member", member);
            userRepository.save(user);

        }
        model.addAttribute("user", user);
        return "home";
    }

    @PostMapping("/addmember")
    public ResponseEntity<Object> addmember(@ModelAttribute("user") User user, @RequestBody AddMemberReq addMemberReq) {
        int flag = 0;
        List<Member> memberDetails = new ArrayList<Member>();
        user = mongoTemplate.findOne(Query.query(Criteria.where("phoneNumber").is(addMemberReq.getPhoneNumber())),
                    User.class);
        System.out.println(user.getMember());
        // memberDetails.add(addMemberReq.getMember());
        // System.out.println(memberDetails);
        System.out.println(addMemberReq.getMember());
        System.out.println(addMemberReq.getPhoneNumber());
        
        if(user.getMember() == null) {
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

        if(flag == 0) {
            if (memberDetails != null && memberDetails.size() > 0 && memberDetails.size() < 4) {
                // memberDetails.addAll(user.getMember());

                Member m = addMemberReq.getMember();
                System.out.println("m1"+ memberDetails);
                memberDetails.add(m);
                System.out.println("m2"+ memberDetails);
                user.setMember(memberDetails);
            } else {
                // memberDetails.add(addMemberReq.getMember());
                System.out.println("whoops");
            }
        }
  
        System.out.println("Memberssss: " + memberDetails);
        System.out.println(user);
        // user.setMember(memberDetails);
        userRepository.save(user);
        ServiceResponse<Member> response = new ServiceResponse<Member>("success", addMemberReq.getMember());
        System.out.println(response);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


    // @PostMapping("/register")
    // public String submitForm(@ModelAttribute("user") User user) {
    //     System.out.println(user);
    //     if (mongoTemplate.exists(Query.query(Criteria.where("phoneNumber").is(user.getPhoneNumber())), User.class)
    //             && mongoTemplate.exists(Query.query(Criteria.where("password").is(user.getPassword())), User.class)) {
    //         System.out.println("Welcome back");
    //         return "home";
    //     } else {
    //         System.out.println("Created new User");
    //         userRepository.insert(user);
    //         return "home";
    //     }
    // }

    // @GetMapping("/addmember")
    // public String addMemberForm(Model model, User user, @RequestParam("phoneNumber") String phoneNumber) {
    //     System.out.println("phone Number -> " + phoneNumber);
    //     Member member = new Member();
    //     model.addAttribute("member", member);
    //     System.out.println(user.getPhoneNumber());
    //     return "addmember_form";
    // }

    // @PostMapping("/addmember")
    // public String submitMemberForm(@ModelAttribute("member") Member member,
    //         @RequestParam("phoneNumber") String phoneNumber) {
    //     // Optional<User> findUserQuery = userRepository.findByphoneNumber(phoneNumber);
    //     System.out.println("Phone Number - " + phoneNumber);
    //     // User userValues = findUserQuery.get();
    //     User user = mongoTemplate.findOne(Query.query(Criteria.where("phoneNumber").is(phoneNumber)), User.class);
    //     List<Member> memberDetails = new ArrayList<Member>();
    //     Member m = new Member(member.getAdhaar(), member.getName(), member.getGender(), member.getDob());
    //     memberDetails.add(m);
    //     user.setMember(memberDetails);
    //     // if (user.getMember().size() == 1) {
    //     // }
    //     userRepository.save(user);
    //     return "addmember_success";
    // }

    









}