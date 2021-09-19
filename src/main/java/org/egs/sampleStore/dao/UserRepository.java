package org.egs.sampleStore.dao;

import org.egs.sampleStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findUserByUsername(String username);
    User findUserByUsernameAndPassword(String username , String password);
    User findByUsernameAndPassword(String username , String password);
    User findByPassword(String password);
}
