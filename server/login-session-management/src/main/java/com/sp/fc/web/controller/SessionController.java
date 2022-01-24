package com.sp.fc.web.controller;

import com.sp.fc.user.domain.SpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller
public class SessionController {

    @Autowired
    private SessionRegistry sessionRegistry;

    @GetMapping("/sessions")
    public String sessions(Model model) { // sessionList page 에 넘김
        // userSession 에 principal 를 받아서 mapping
        model.addAttribute("sessionList", sessionRegistry.getAllPrincipals().stream().map(p->UserSession.builder()
                .username(((SpUser)p).getUsername()) // SpUser 에서 user 이름을 받음
                .sessions(sessionRegistry.getAllSessions(p, false).stream().map(s-> // pricipal 로 getAllsessions 만료된 것 말고 살아있는 session 만 가져옴
                        SessionInfo.builder()
                                .sessionId(s.getSessionId())
                                .time(s.getLastRequest())
                                .build())
                        .collect(Collectors.toList()))
                .build()).collect(Collectors.toList())); // UserSession 에 sessions 리스트가 주입 됨
        return "/sessionList";
    }

    @PostMapping("/session/expire")
    public String expireSession(@RequestParam String sessionId) {
        // expire code
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        if(!sessionInformation.isExpired()) {
            sessionInformation.expireNow();
        }
        return "redirect:/sessions"; // 살아있는 session만 나타남
    }

    public String sessionExpired() {
        return "/sessionExpired";
    }
}
