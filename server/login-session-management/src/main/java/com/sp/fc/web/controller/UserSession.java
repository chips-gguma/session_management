package com.sp.fc.web.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSession {

    private String username;
    private List<SessionInfo> sessions; // 세션 정보 목록

    public int getCount() {
        return sessions.size();
    }
}
