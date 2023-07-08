package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreatePostUsecase;
import com.example.fastcampusmysql.application.usecase.GetTimelinePostsUsecase;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final GetTimelinePostsUsecase getTimelinePostsUsecase;
    private final CreatePostUsecase createPostUsecase;

    @PostMapping("")
    public Long create(PostCommand command){
        return createPostUsecase.execute(command);
    }

    @GetMapping("/members/{memberId}")
    public Page<Post> getPosts(
            @PathVariable Long memberId,
            @RequestParam Integer page,
            @RequestParam Integer size
    ) {
        return postReadService.getPosts(memberId, PageRequest.of(page, size));
    }

    @GetMapping("/members/{memberId}")
    public PageCursor<Post> getPostsByCursor(
            @PathVariable List<Long> memberIds,
            CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberIds, cursorRequest);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(@RequestBody DailyPostCountRequest request){
        return postReadService.getDailyPostCounts(request);
    }

    @GetMapping("/member/{memberId}/timeline")
    public PageCursor<Post> getTimeLine(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ){
        return getTimelinePostsUsecase.execute(memberId, cursorRequest);
    }

    @PostMapping("/{postId}/like")
    public void likePost(@PathVariable Long postId){
 //       postWriteService.likePost(postId);
        postWriteService.likePostByOptimisticLock(postId);
    }



}
