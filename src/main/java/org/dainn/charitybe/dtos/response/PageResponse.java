package org.dainn.charitybe.dtos.response;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponse<T> {
    private Integer page;
    private Integer size;
    private long totalPages;
    private List<T> data = new ArrayList<>();
}
