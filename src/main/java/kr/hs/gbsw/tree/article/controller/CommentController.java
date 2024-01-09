package kr.hs.gbsw.tree.article.controller;

import kr.hs.gbsw.tree.article.model.Comment;
import kr.hs.gbsw.tree.article.dto.CreateCommentDto;
import kr.hs.gbsw.tree.article.dto.DeleteCommentDto;
import kr.hs.gbsw.tree.article.dto.UpdateCommentDto;
import kr.hs.gbsw.tree.article.service.CommentService;
import kr.hs.gbsw.tree.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;


    @GetMapping("/{articleId}/comments")
    public List<Comment> getComments(
            @PathVariable int articleId
    ) {
        return commentService.getComments(articleId);
    }

    @PostMapping("/{articleId}/comments")
    public Comment createComments(
            @PathVariable int articleId,
            @AuthenticationPrincipal User user,
            @RequestBody CreateCommentDto dto
    ) {
        return commentService.createComment(articleId, user, dto);
    }

    @PutMapping("/comments/{id}")
    public Comment updateComment(
            @PathVariable int id,
            @AuthenticationPrincipal User user,
            @RequestBody UpdateCommentDto dto
    ) {
        Comment comment = commentService.updateComment(id, user, dto);
        return comment;
    }

    @DeleteMapping("/comments/{id}")
    public void deleteComment(
            @PathVariable int id,
            @RequestBody DeleteCommentDto dto
    ) {
        commentService.deleteComment(id, dto.getPassword());
    }
}












