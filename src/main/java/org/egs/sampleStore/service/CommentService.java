package org.egs.sampleStore.service;

import org.egs.sampleStore.dao.CommentRepository;
import org.egs.sampleStore.dto.CommentDto;
import org.egs.sampleStore.dto.ProductDto;
import org.egs.sampleStore.dto.UserDto;
import org.egs.sampleStore.entity.Comment;
import org.egs.sampleStore.mapper.CommentMapper;
import org.egs.sampleStore.security.AutherizationHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private CommentRepository commentRepository;

    private CommentMapper commentMapper;

    private AutherizationHeader autherizationHeader;

    private UserService userService;

    private ProductService productService;

    @Autowired
    public CommentService(CommentRepository commentRepository , CommentMapper commentMapper , AutherizationHeader autherizationHeader,
                          UserService userService , ProductService productService){
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
        this.autherizationHeader = autherizationHeader;
        this.userService = userService;
        this.productService = productService;
    }

    @Transactional
    public CommentDto save(CommentDto comment){
        String username = autherizationHeader.getUsernameFromWitoutToken();
        UserDto userDto = userService.findByUsername(username);
        ProductDto productDto = productService.findProductByProductName(comment.getProduct().getProductName());
        comment.setProduct(productDto);
        comment.setUserDto(userDto);
        Comment comment1 = commentRepository.save(commentMapper.dtoToEntity(comment));
        return commentMapper.entityToDto(comment1);
    }


    public List<CommentDto> findAll(){
        List<Comment> comments = commentRepository.findAll();
        if(comments.size() != 0){
           return commentMapper.entitiesToDto(comments);
        }
        return null;
    }

    @Transactional
    public void delete(CommentDto comment){
        commentRepository.delete(commentMapper.dtoToEntity(comment));
    }

    public List<CommentDto> findCommentsByUser(String username){
        List<Comment> comments = commentRepository.findCommentsByUser_Username(username);
        if(comments != null && comments.size() != 0){
            return commentMapper.entitiesToDto(comments);
        }
        return null;
    }

    public List<CommentDto> findCommentsOnSpecificProductionByUser(CommentDto commentDto){
        String username = autherizationHeader.getUsernameFromWitoutToken();
        List<Comment> comments = commentRepository.findCommentsByUser_UsernameAndProduct_ProductName(username ,commentDto.getProduct().getProductName());
        if(comments != null && comments.size() != 0){
            return commentMapper.entitiesToDto(comments);
        }
        return null;
    }

    public List<CommentDto> findCommentsOnProduct(CommentDto commentDto){
        List<Comment> comments = commentRepository.findCommentsByProduct_ProductName(commentDto.getProduct().getProductName());
        if(comments != null && comments.size() != 0){
            return commentMapper.entitiesToDto(comments);
        }
        return null;
    }

    public void rateAProduct(CommentDto commentDto){
        List<Comment> comments = commentRepository.findCommentsByProduct_ProductName(commentDto.getProduct().getProductName());
        long size = comments.size() + 1;
        double avrage = commentDto.getRate();
        double result = 0;
        for(Comment comment : comments){
            avrage += comment.getRate();
        }
        if(size != 0) {
             result = avrage / size;
        }else {
            result = avrage;
        }
        ProductDto productDto = productService.findProductByProductName(commentDto.getProduct().getProductName());
        productDto.setAverageRate(result);
        productService.save(productDto);
    }
}
