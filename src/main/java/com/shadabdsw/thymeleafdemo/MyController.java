package com.shadabdsw.thymeleafdemo;

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

@Controller
public class MyController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    MemberRepository memberRepository;

    @GetMapping("/register")
    public String showForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        System.out.println(userRepository.findAll());
        return "register_form";
    }

    @PostMapping("/register")
    public String submitForm(@ModelAttribute("user") User user) {
        System.out.println(user);
        userRepository.insert(user);
        return "home";
    }

    @GetMapping("/addmember")
    public String addMemberForm(Model model) {
        Member member = new Member();
        model.addAttribute("member", member);

        System.out.println(memberRepository.findAll());
        return "addmember_form";
    }

    @PostMapping("/addmember")
    public String submitMemberForm(@ModelAttribute("member") Member member) {
        System.out.println(member);
        memberRepository.insert(member);
        return "addmember_success";
    }

}
