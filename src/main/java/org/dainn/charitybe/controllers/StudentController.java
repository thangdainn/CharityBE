package org.dainn.charitybe.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.dainn.charitybe.constants.Endpoint;
import org.dainn.charitybe.dtos.StudentDTO;
import org.dainn.charitybe.dtos.response.PageResponse;
import org.dainn.charitybe.services.IStudentService;
import org.dainn.charitybe.utils.ValidateString;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.dainn.charitybe.dtos.request.StudentSearch;

import java.util.List;

@RestController
@RequestMapping(Endpoint.Student.BASE)
@RequiredArgsConstructor
public class StudentController {
    private final IStudentService studentService;

    @GetMapping
    public ResponseEntity<?> getAll(@ModelAttribute StudentSearch request) {
        request.setKeyword(ValidateString.trimString(request.getKeyword()));
        if (request.getPage() == null) {
            return ResponseEntity.ok(studentService.findAll(request.getStatus()));
        }
        Page<StudentDTO> page = studentService.findAllByConditions(request);

        return ResponseEntity.ok(PageResponse.<StudentDTO>builder()
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalElements(page.getTotalElements())
                .data(page.getContent())
                .build());
    }

    @GetMapping(Endpoint.Student.ID)
    public ResponseEntity<?> get(@Min(1) @PathVariable Integer id) {
        return ResponseEntity.ok(studentService.findById(id));
    }

    @PostMapping
    //PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody StudentDTO dto) {
        return ResponseEntity.ok(studentService.insert(dto));
    }

    @PutMapping(Endpoint.Student.ID)
    //PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Min(1) @PathVariable Integer id,
                                    @Valid @RequestBody StudentDTO dto) {
        dto.setId(id);
        return ResponseEntity.ok(studentService.update(dto));
    }

    @DeleteMapping
    //PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@RequestBody List<Integer> ids) {
        studentService.delete(ids);
        return ResponseEntity.ok().build();
    }
}