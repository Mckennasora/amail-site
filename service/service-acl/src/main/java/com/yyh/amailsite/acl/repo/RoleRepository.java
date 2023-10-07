package com.yyh.amailsite.acl.repo;

import com.yyh.amailsite.acl.model.role.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, String> {

    Page<Role> findAll(Specification<Role> specification, Pageable pageable);

    List<Role> findByIdInOrderByIdAsc(List<String> roleIds);
}
