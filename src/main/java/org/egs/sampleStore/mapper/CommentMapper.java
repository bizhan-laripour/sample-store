package org.egs.sampleStore.mapper;

import org.egs.sampleStore.dto.CommentDto;
import org.egs.sampleStore.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentMapper {

    private UserMapper userMapper;
    private ProductMapper productMapper;

    @Autowired
    public CommentMapper(UserMapper userMapper , ProductMapper productMapper){
        this.userMapper = userMapper;
        this.productMapper = productMapper;
    }

    public Comment dtoToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setCommentText(commentDto.getCommentText());
        comment.setRate(commentDto.getRate());
        comment.setProduct(productMapper.dtoToEntity(commentDto.getProduct()));
        comment.setUser(userMapper.dtoToEntoty(commentDto.getUserDto()));
        comment.setDate(commentDto.getDate());
        return comment;
    }


    public CommentDto entityToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setCommentText(comment.getCommentText());
        commentDto.setDate(comment.getDate());
        commentDto.setRate(comment.getRate());
        commentDto.setUserDto(userMapper.entityToDto(comment.getUser()));
        commentDto.setProduct(productMapper.entityToDto(comment.getProduct()));
        return commentDto;
    }

    public List<Comment> dtosToEntities(List<CommentDto> commentDtoList){
        List<Comment> result = new ArrayList<>();
        for(CommentDto obj : commentDtoList){
            result.add(dtoToEntity(obj));
        }
        return result;
    }

    public List<CommentDto> entitiesToDto(List<Comment> comments){
        List<CommentDto> result = new ArrayList<>();
        for(Comment obj : comments){
            result.add(entityToDto(obj));
        }
        return result;
    }
}
