package server.apptech.file.domain;

import jakarta.persistence.*;
import server.apptech.advertisement.domain.Advertisement;
import server.apptech.comment.comment.Comment;
import server.apptech.global.domain.BaseEntity;

@Entity
public class File extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "advertisement_id")
    private Advertisement advertisement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    private FileType fileType;
    private String uuid;
    private String url;
}
