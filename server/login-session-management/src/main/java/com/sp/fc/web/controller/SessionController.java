package com.sp.fc.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
public class SessionController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/sessions")
    public String sessions(Model model) { // sessionList page 에 넘김
        // userSession 에 principal 를 받아서 mapping
        model.addAttribute("sessionList", sessionRegistry.getAllPrincipals().stream().map(p->UserSession.builder()
                .build()).collect(Collectors.toList()));
        return "/sessionList";
    }

    @GetMapping("/session/expire")
    public String expireSession(String sessionId) {
        return "redirect:/session";
    }

    public String sessionExpired() {
        return "/sessionExpired";
    }
}
