package com.khrd.spring_boot_mini_project.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ApiResponce<T>{
    private String message;
    private HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T payload;
}