package org.dainn.charitybe.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.charitybe.constants.PageConstant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {
    private Integer page;
    private Integer size = PageConstant.DEFAULT_PAGE_SIZE;
    private String sortBy = PageConstant.DEFAULT_SORT_BY;
    private String sortDir = PageConstant.DEFAULT_SORT_DIRECTION;
}
