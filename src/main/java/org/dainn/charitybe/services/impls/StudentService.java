package org.dainn.charitybe.services.impls;

import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.dtos.StudentDTO;
import org.dainn.charitybe.dtos.request.StudentSearch;
import org.dainn.charitybe.enums.ErrorCode;
import org.dainn.charitybe.exceptions.AppException;
import org.dainn.charitybe.models.StudentEntity;
import org.dainn.charitybe.repositories.IStudentRepository;
import org.dainn.charitybe.services.IStudentService;
import org.dainn.charitybe.mapper.IStudentMapper;
import org.dainn.charitybe.utils.Paging;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService implements IStudentService {
    private final IStudentRepository studentRepository;
    private final IStudentMapper studentMapper;

    @Override
    public StudentDTO insert(StudentDTO dto) {
        StudentEntity entity = studentMapper.toEntity(dto);
        return studentMapper.toDTO(studentRepository.save(entity));
    }

    @Override
    public StudentDTO update(StudentDTO dto) {
        StudentEntity old = studentRepository.findById(dto.getId())
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED));
        StudentEntity entity = studentMapper.updateEntity(old, dto);
        return studentMapper.toDTO(studentRepository.save(entity));
    }

    @Override
    public void delete(List<Integer> ids) {
        studentRepository.deleteAllByIdInBatch(ids);
    }

    @Override
    public StudentDTO findById(Integer id) {
        return studentMapper.toDTO(studentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED)));
    }

    @Override
    public StudentDTO findByName(String name) {
        return studentMapper.toDTO(studentRepository.findByName(name)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED)));
    }

    @Override
    public StudentDTO findByMssv(String mssv) {
        return studentMapper.toDTO(studentRepository.findByMssv(mssv)
                .orElseThrow(() -> new AppException(ErrorCode.STUDENT_NOT_EXISTED)));
    }

    @Override
    public List<StudentDTO> findAll(Integer status) {
        return studentRepository.findAllByStatus(status).stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<StudentDTO> findAllByConditions(StudentSearch request) {
        Page<StudentEntity> page = studentRepository.findAllByConditions(request.getKeyword(), request.getStatus(), Paging.getPageable(request));
        return page.map(studentMapper::toDTO);
    }
}