package co.edu.uniandes.csw.artwork.api;

import co.edu.uniandes.csw.artwork.entities.CommentEntity;
import java.util.List;

public interface ICommentLogic {
    public int countComments();
    public List<CommentEntity> getComments(Long artworkId);
    public List<CommentEntity> getComments(Integer page, Integer maxRecords, Long artworkId);
    public CommentEntity getComment(Long id);
    public CommentEntity createComment(Long artworkId, CommentEntity entity);
    public void deleteComment(Long id);
}
