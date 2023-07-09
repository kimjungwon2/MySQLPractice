package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.CursorRequest;
import com.example.fastcampusmysql.util.PageCursor;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostReadService {

    private final PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request){
        return postRepository.groupByCreatedDate(request);
    }

    public Page<Post> getPosts(Long memberId, PageRequest pageRequest) {
        return postRepository.findAllByMemberId(memberId, pageRequest);
    }

    public Post getPost(Long postID){
        return postRepository.findById(postID, false).orElseThrow();
    }

    public List<Post> getPosts(List<Long> ids) {
        return postRepository.findAllByIdIn(ids);
    }

    public PageCursor<Post> getPosts(List<Long> memberIds, CursorRequest cursorRequest) {
        var posts = findAllBy(memberIds, cursorRequest);

        var nextKey = posts.stream().mapToLong(Post::getId).min().orElse(CursorRequest.NONE_KEY);

        return new PageCursor<>(cursorRequest.next(nextKey),posts);

    }

    private List<Post> findAllBy(List<Long> memberIds, CursorRequest cursorRequest) {
        if(cursorRequest.hasKey()) {
            return postRepository.findAllByLessThanIdAndMemberIdInAndOrderByIdDesc(
                    cursorRequest.key(), memberIds, cursorRequest.size());
        }
        return postRepository.findAllByMemberIdInAndOrderByIdDesc(memberIds,
                    cursorRequest.size());
    }
}
