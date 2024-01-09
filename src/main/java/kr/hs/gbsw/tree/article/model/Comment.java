package kr.hs.gbsw.tree.article.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kr.hs.gbsw.tree.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String content;
    private String author;
    @JsonIgnore
    private String password;
    @ManyToOne
    private User user;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
