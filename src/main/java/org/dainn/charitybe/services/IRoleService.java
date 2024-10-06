package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.RoleDTO;
import org.dainn.charitybe.dtos.request.RoleSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoleService {
    RoleDTO insert(RoleDTO roleDTO);
    RoleDTO update(RoleDTO roleDTO);
    void delete(List<Integer> ids);
    RoleDTO findById(Integer id);
    RoleDTO findByName(String name);
    List<RoleDTO> findAll();

    List<RoleDTO> findAll(Integer status);

    Page<RoleDTO> findAllByName(RoleSearch request);
}
