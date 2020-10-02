package io.github.monthalcantara.nossobancodigital.advice;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class ErrosApi {

    @Getter
    List<String> erros;

    public ErrosApi(String e){
        this.erros = Arrays.asList(e);
    }

    public ErrosApi(List<String> listErrors) {
        this.erros = listErrors;
    }
}
