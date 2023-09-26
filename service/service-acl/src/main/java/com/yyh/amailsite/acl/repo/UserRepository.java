package com.yyh.amailsite.acl.repo;

import com.yyh.amailsite.acl.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    // 自定义查询方法
    User findByUsername(String username);

    Page<User> findAll(Specification<User> specification, Pageable pageable);
}
