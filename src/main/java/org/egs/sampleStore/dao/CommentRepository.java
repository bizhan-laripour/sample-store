package org.egs.sampleStore.dao;

import org.egs.sampleStore.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment , Integer> {

    List<Comment> findCommentsByUser_Username(String username);

    List<Comment> findCommentsByUser_UsernameAndProduct_ProductName(String username , String productName);

    List<Comment> findCommentsByProduct_ProductName(String productName);

}
