package com.alareubang.koala.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.alareubang.koala.config.auth.User;
import com.alareubang.koala.config.auth.UserRepository;

@Controller
@EnableJpaRepositories(basePackages = {"com.alareubang.koala.config.auth"})
public class LoginController {
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
	@GetMapping("/auth/loginForm")
	public String login(Model model) {
		return "/auth/loginForm";
	}
	
	@GetMapping("/")
	public String index(Model model) {
		return "/index";
	}
	
	@GetMapping("/auth/joinForm")
    public String joinForm(){
        return "/auth/joinForm";
    }
	
	@PostMapping("/auth/join")
    public String join(User user){
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user); // 회원가입이 잘 됨. 비밀번호 => 1234 => 시큐리티로 로그인이 되지 않는다. 패스워드 암호화필요
        return "redirect:/auth/loginForm";
    }
}
