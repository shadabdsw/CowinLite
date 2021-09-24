package com.shadabdsw.thymeleafdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

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
        return "home";
    }

    @GetMapping("/addmember")
    public String addMemberForm(Model model, User user, @RequestParam("phoneNumber") String phoneNumber) {
        System.out.println("phone Number -> " + phoneNumber);
        Member member = new Member();
        model.addAttribute("member", member);
        System.out.println(user.getPhoneNumber());
        return "addmember_form";
    }

    @PostMapping("/addmember")
    public String submitMemberForm(@ModelAttribute("member") Member member,
            @RequestParam("phoneNumber") String phoneNumber) {
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
