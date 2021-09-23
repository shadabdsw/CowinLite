package com.shadabdsw.thymeleafdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.shadabdsw.thymeleafdemo.Model.Member;
import com.shadabdsw.thymeleafdemo.Model.User;
import com.shadabdsw.thymeleafdemo.Repositories.MemberRepository;
import com.shadabdsw.thymeleafdemo.Repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MemberRepository memberRepository;

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
        System.out.println(getAllUsers());
        return "register_form";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("user") User user) {
        System.out.println(user);
        userRepository.insert(user);
        System.out.println(findPhoneNumber(user.getPhoneNumber()));
        return "home";
    }

    @RequestMapping(path = "/addmember", method = RequestMethod.GET)
    public String addMemberForm(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);
        // model.addAttribute("name", phone);
        System.out.println(member);
        return "addmember_form";
    }

    // public User updateUser(String phoneNumber, User user) {
    // Optional<User> findUserQuery = userRepository.findByphoneNumber(phoneNumber);
    // User userValues = findUserQuery.get();
    // userValues.setMember();
    // return userRepository.save(userValues);
    // }

    @RequestMapping(path = "/addmember", method = RequestMethod.POST)
    public String submitMemberForm(@ModelAttribute("member") Member member,
            @RequestParam(name = "phone", required = false) String phoneNumber) {
        Optional<User> findUserQuery = userRepository.findByphoneNumber(phoneNumber);
        System.out.println("Phone Number - " + phoneNumber);
        User userValues = findUserQuery.get();
        List<Member> memberDetails = new ArrayList<Member>();
        Member m = new Member(member.getAdhaar(), member.getName(), member.getDob(), member.getGender());
        memberDetails.add(m);
        userValues.setMember(memberDetails);
        userRepository.save(userValues);
        return "addmember_success";
    }

}
