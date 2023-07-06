package com.example.fastcampusmysql.application.controller;


import com.example.fastcampusmysql.application.usecase.CreateFollowMemberUsecase;
import com.example.fastcampusmysql.application.usecase.GetFollowingMembersUsecase;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/follow")
public class FollowController {
    final private CreateFollowMemberUsecase createFollowMemberUsecase;
    private final GetFollowingMembersUsecase getFollowingMembersUsecase;

    @PostMapping("/{fromId}/{toId}")
    public void register(@PathVariable Long fromId, @PathVariable Long toId) {
        createFollowMemberUsecase.execute(fromId, toId);
    }

    @GetMapping("/members/{toId}")
    public List<MemberDto> create(@PathVariable Long fromId) {
        return getFollowingMembersUsecase.execute(fromId);
    }

}