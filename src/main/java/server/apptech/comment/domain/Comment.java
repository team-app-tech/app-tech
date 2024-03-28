package server.apptech.comment.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.comment.dto.CommentCreateRequest;
import server.apptech.comment.dto.CommentUpdateRequest;
import server.apptech.commentlike.domain.CommentLike;
import server.apptech.commentreply.domain.CommentReply;
import server.apptech.file.domain.File;
import server.apptech.global.domain.BaseEntity;
import server.apptech.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="advertisement_id")
    private Advertisement advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="content")
    private String content;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="file_id")
    private File file;

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> commentLikes = new ArrayList<>();

    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentReply> commentReplies = new ArrayList<>();
//    private int likeCnt;
//    private int commentRepliesCnt;

    public static Comment of(CommentCreateRequest commentCreateRequest, User user){
        return Comment.builder()
                .content(commentCreateRequest.getContent())
                .user(user)
                .build();
    }

    public void setAdvertisement(Advertisement advertisement){
        this.advertisement = advertisement;
        advertisement.addComment(this);
    }

    public void setFile(File file){
        this.file = file;
        file.belongToComment(this);
    }

    public void updateComment(CommentUpdateRequest commentUpdateRequest){
        this.content = commentUpdateRequest.getContent();
    }

    public void removeFile() {
        this.file = null;
    }
}
