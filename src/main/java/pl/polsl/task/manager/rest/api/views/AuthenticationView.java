package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class AuthenticationView {

    @ApiModelProperty(required = true, example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYW5vIiwiZXhwIjoxNTc0OTQ1NDY3LCJpYXQiOjE1NzQ5Mjc0Njd9.1nVS9zoTiJ7ZRBLRsKwxf2rrcTxn6M6HfCRHNvnI5nC-52cvjtR0PiLMjU4XQaUkKPywttOi8OS6jeloHbQ8LA")
    @NonNull
    private String token;

    @ApiModelProperty(required = true, position = 1)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Date expirationDate;



}