package br.com.rafaelvieira.bytehub.domain.dto;

import br.com.rafaelvieira.bytehub.domain.util.IdIf;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BaseDTO<I extends Serializable> implements Serializable, IdIf<I>{

    @Getter
    @Setter
    private I id;

    protected BaseDTO(final I id) {
        this.id = id;
    }
}
