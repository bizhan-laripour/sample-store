package org.egs.sampleStore.controller;

import org.egs.sampleStore.dto.CommentDto;
import org.egs.sampleStore.enums.Role;
import org.egs.sampleStore.exception.CustomException;
import org.egs.sampleStore.security.AutherizationHeader;
import org.egs.sampleStore.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CommentController {

    private CommentService commentService;

    private AutherizationHeader autherizationHeader;

    @Autowired
    public CommentController(CommentService commentService, AutherizationHeader autherizationHeader){
        this.commentService = commentService;
        this.autherizationHeader = autherizationHeader;
    }

    @RequestMapping(method = RequestMethod.POST , path = "/api/comment/add_comment")
    public ResponseEntity<CommentDto> addComment(@RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.save(commentDto));
    }

    @RequestMapping(method = RequestMethod.POST , path = "/api/comment/delete_comment")
    public ResponseEntity<CommentDto> deleteComment(@RequestBody CommentDto commentDto){
        String role = autherizationHeader.getRoleFromToken();
        String username = autherizationHeader.getUsernameFromWitoutToken();
        if(role.equals(Role.ROLE_ADMIN.getAuthority()) || commentDto.getUserDto().getUsername().equals(username)){
            commentService.delete(commentDto);
            return ResponseEntity.ok(commentDto);
        }
        throw new CustomException("you dont have permission to delete this comment" , HttpStatus.FORBIDDEN);

    }

    @RequestMapping(method = RequestMethod.POST , path = "/api/comment/find_comments_on_product")
    public ResponseEntity<List<CommentDto>> findCommentsOnProduct(@RequestBody CommentDto commentDto){
        return ResponseEntity.ok(commentService.findCommentsOnProduct(commentDto));
    }

    @RequestMapping(method = RequestMethod.POST , path = "/api/comment/find_comments_on_product_by_current_user")
    public ResponseEntity<List<CommentDto>> findCommentsOnProductByCurrentUser(CommentDto commentDto){
        return ResponseEntity.ok(commentService.findCommentsOnSpecificProductionByUser(commentDto));
    }


    @RequestMapping(method = RequestMethod.POST , path = "/api/comment/rate_product")
    public ResponseEntity<CommentDto> rateProduct(@RequestBody CommentDto commentDto){
        if(commentDto.getRate() > 5 || commentDto.getRate() < 1){
            throw new CustomException("the rate must be in range of 1 to 5" , HttpStatus.BAD_REQUEST);
        }
        commentService.rateAProduct(commentDto);
        return ResponseEntity.ok(commentService.save(commentDto));
    }


}
