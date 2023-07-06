package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetFollowingMembersUsecase {

    private final MemberReadService memberReadService;
    private final FollowReadService followReadService;

    public List<MemberDto> execute(Long memberId){
        var followings = followReadService.getFollowings(memberId);
        var followingMemberIds = followings.stream().map(f->f.getToMemberId()).toList();
        return memberReadService.getMembers(followingMemberIds);
    }
}
