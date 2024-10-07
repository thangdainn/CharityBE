package org.dainn.charitybe.services;


import org.dainn.charitybe.dtos.UserDTO;
import org.dainn.charitybe.dtos.request.UserSearch;
import org.dainn.charitybe.enums.Provider;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService extends IBaseService<UserDTO> {
    UserDTO findByEmailAndProvider(String email, Provider provider);
    List<UserDTO> findAll();
    List<UserDTO> findAll(Integer status);
    Page<UserDTO> findWithSpec(UserSearch request);
}
