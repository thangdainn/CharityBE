package org.dainn.charitybe.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class Paging {
    public static Pageable getPageable(org.dainn.charitybe.dtos.request.PageRequest request) {
        Sort sort;
        if (StringUtils.hasText(request.getSortBy())) {
            sort = request.getSortDir().equalsIgnoreCase(Sort.Direction.ASC.name())
                    ? Sort.by(request.getSortBy()).ascending() : Sort.by(request.getSortBy()).descending();
        } else {
            sort = Sort.unsorted();
        }
        return PageRequest.of(request.getPage(), request.getSize(), sort);
    }

    public static Sort getSort(String sortBy, String sortDir) {
        return StringUtils.hasText(sortBy) ? (sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending()) : Sort.unsorted();
    }
}
