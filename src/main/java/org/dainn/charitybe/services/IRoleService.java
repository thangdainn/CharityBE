package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.RoleDTO;
import org.dainn.charitybe.dtos.request.RoleSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoleService extends IBaseService<RoleDTO> {
    RoleDTO findByName(String name);
    List<RoleDTO> findAll();

    List<RoleDTO> findAll(Integer status);

    Page<RoleDTO> findAllByName(RoleSearch request);
}
