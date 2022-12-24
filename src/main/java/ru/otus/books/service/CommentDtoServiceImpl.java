package ru.otus.books.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.books.models.Comment;
import ru.otus.books.repositories.CommentRepository;

@Service
public class CommentDtoServiceImpl implements CommentDtoService {

    private final CommentRepository repo;

    public CommentDtoServiceImpl(CommentRepository repo) {
        this.repo = repo;
    }

    @PreAuthorize("hasAuthority('RONIN')")
    @Override
    @Transactional
    public void edit(long commentId, String newCommentText) {
        Comment comment = repo.findById(commentId);
        comment.setMessage(newCommentText);
        repo.save(comment);
    }

    @PreAuthorize("hasAuthority('RONIN')")
    @Override
    @Transactional
    public void removeComment(long commentId) {
        repo.deleteById(commentId);
    }
}
