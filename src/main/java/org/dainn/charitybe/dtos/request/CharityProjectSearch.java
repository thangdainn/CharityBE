package org.dainn.charitybe.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.dainn.charitybe.enums.ProjectFor;
import org.dainn.charitybe.enums.ProjectStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class CharityProjectSearch extends BaseSearch {
    private ProjectFor projectFor;
    private Integer categoryId;
    private ProjectStatus status;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
