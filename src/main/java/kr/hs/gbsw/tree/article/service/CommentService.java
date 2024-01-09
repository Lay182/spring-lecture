package kr.hs.gbsw.tree.article.service;

import kr.hs.gbsw.tree.article.model.Comment;
import kr.hs.gbsw.tree.article.repository.CommentRepository;
import kr.hs.gbsw.tree.article.dto.CreateCommentDto;
import kr.hs.gbsw.tree.article.dto.UpdateCommentDto;
import kr.hs.gbsw.tree.article.model.Article;
import kr.hs.gbsw.tree.article.repository.ArticleRepository;
import kr.hs.gbsw.tree.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public List<Comment> getComments(int articleId) {
        if (!articleRepository.existsById(articleId)) {
            throw new IllegalArgumentException("존재하지 않는 게시물입니다.");
        }
        return commentRepository.findByArticleId(articleId);
    }

    public Comment getComment(int id) {
        if(!commentRepository.existsById(id)) {
            throw new IllegalArgumentException("댓글이 존재하지 않습니다.");
        }
        return commentRepository.findById(id).orElse(null);
    }

    public Comment createComment(int articleId, User user, CreateCommentDto dto) {
        Article article = articleRepository.findById(articleId).orElse(null);
        if (article == null) {
            throw new IllegalArgumentException("존재하지 않는 게시물입니다.");
        }

        LocalDateTime now = LocalDateTime.now();

        Comment comment = new Comment(
                0,
                dto.getContent(),
                dto.getAuthor(),
                dto.getPassword(),
                user,
                article,
                now,
                now
        );
        return commentRepository.save(comment);
    }

    public Comment updateComment(int id, UpdateCommentDto dto) {
        // 있어!, 댓글을 일딴 DB에서 조회하자
        Comment original = getComment(id);
        // 그다음은? 오리지널 비밀번호랑 사용자가 준 비밀번호가 같은지 검사한다.
        if (original.getPassword().equals(dto.getPassword())) {
            // 이제야 수정 절차를 진행해도 되긋네.
            original.setContent(dto.getContent());
            original.setAuthor(dto.getAuthor());
            original.setUpdatedAt(LocalDateTime.now());
            return commentRepository.save(original);
        }
        // 그럼 여기서 부터는 오류가 생긴 지점이네.
        // 여기는 비밀번호가 안맞을때
        throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
    }

    public void deleteComment(int id, String password) {
        Comment original = getComment(id);
        if (!original.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
        commentRepository.deleteById(id);
    }
}
