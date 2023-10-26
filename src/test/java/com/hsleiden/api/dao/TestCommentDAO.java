package com.hsleiden.api.dao;
import com.hsleiden.api.dao.CommentDAO;
import com.hsleiden.api.dao.CommentRepository;
import com.hsleiden.api.exception.NotFoundException;
import com.hsleiden.api.model.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class TestCommentDAO {
    @Mock
    private CommentRepository commentRepository;
    private CommentDAO SUT;
    @BeforeEach
    public void setup(){
        this.SUT = new CommentDAO(this.commentRepository);
    }
    @Test
    public void should_return_updated_comment_when_replace_is_called(){
        Comment dummyComment = new Comment();
        when(this.commentRepository.findById(anyLong())).thenReturn(Optional.of(dummyComment));
        int dummyId = 0;
        Comment actualComment = this.SUT.replace(dummyComment, dummyId);
        assertThat(actualComment, is(dummyComment));
        //Mockito.verify(commentRepository, times(2)).findById(anyLong());
    }
    @Test
    public void should_throw_not_found_exception_when_comment_does_not_excist_when_replace_is_called(){
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());
        Comment dummyComment = new Comment();
        int dummyId = 0;
        NotFoundException exception = assertThrows(NotFoundException.class, () -> this.SUT.replace(dummyComment, dummyId));
        assertThat(exception.getMessage(), is("Comment with id: " + dummyId + " not found"));
    }
}

