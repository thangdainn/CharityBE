package org.dainn.charitybe.services;


import jakarta.servlet.http.HttpServletRequest;
import org.dainn.charitybe.dtos.UserDTO;
import org.dainn.charitybe.dtos.request.UserSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {
    UserDTO insert(UserDTO dto);
    UserDTO update(UserDTO dto);
    void delete(List<Integer> ids);
    UserDTO findById(Integer id);
    UserDTO getMyInfo(HttpServletRequest request);
    List<UserDTO> findAll();
    List<UserDTO> findAll(Integer status);
    Page<UserDTO> findWithSpec(UserSearch request);
}
