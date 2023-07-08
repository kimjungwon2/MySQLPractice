package com.example.fastcampusmysql.domain.post.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Timeline {

    private final Long id;
    private final Long postId;
    private final Long memberId;

    private final LocalDateTime createdAt;

    @Builder
    public Timeline(Long id, Long postId, Long memberId, LocalDateTime createdAt) {
        this.id = id;
        this.postId = Objects.requireNonNull(postId);
        this.memberId = Objects.requireNonNull(memberId);
        this.createdAt = createdAt ==null? LocalDateTime.now():createdAt;
    }
}
