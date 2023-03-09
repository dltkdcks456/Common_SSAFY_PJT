package com.example.demo.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class BoardImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_image_id")
    private Long boardImageId;

    @JoinColumn(name = "board_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Board boardId;

    @Column(length = 255)
    private String name;

    @Column(length = 255)
    private String path;
    @Column(length = 255)
    private String type;

    @Builder
    public BoardImage(Long boardImageId, Board boardId, String name, String path, String type) {
        this.boardImageId = boardImageId;
        this.boardId = boardId;
        this.name = name;
        this.path = path;
        this.type = type;
    }
}
