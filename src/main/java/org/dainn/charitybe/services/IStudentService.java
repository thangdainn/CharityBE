package org.dainn.charitybe.services;

import org.dainn.charitybe.dtos.StudentDTO;
import org.dainn.charitybe.dtos.request.StudentSearch;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IStudentService {
    StudentDTO insert(StudentDTO dto);
    StudentDTO update(StudentDTO dto);
    void delete(List<Integer> ids);
    StudentDTO findById(Integer id);
    StudentDTO findByName(String name);
    StudentDTO findByMssv(String mssv);
    List<StudentDTO> findAll();
    List<StudentDTO> findAll(Integer status);
    Page<StudentDTO> findAllByConditions(StudentSearch request);
}
