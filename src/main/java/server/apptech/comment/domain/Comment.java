package server.apptech.comment.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.comment.dto.CommentCreateRequest;
import server.apptech.comment.dto.CommentUpdateRequest;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<Comment> childComments = new ArrayList<>();

    @Column(name="content")
    private String content;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="file_id")
    private File file;
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

    public void setParent(Comment comment){
        this.parent = comment;
        comment.addChildComment(comment);
    }
    public void setFile(File file){
        this.file = file;
        file.belongToComment(this);
    }

    public void addChildComment(Comment comment){
        childComments.add(comment);
    }

    public void updateComment(CommentUpdateRequest commentUpdateRequest){
        this.content = commentUpdateRequest.getContent();
    }

    public void removeFile() {
        this.file = null;
    }
}
