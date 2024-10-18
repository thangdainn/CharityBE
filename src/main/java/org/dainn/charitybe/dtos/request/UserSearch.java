package org.dainn.charitybe.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dainn.charitybe.enums.Provider;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserSearch extends BaseSearch {

    private Integer roleId;
    private Provider provider;
    private Integer status = 1;

}
