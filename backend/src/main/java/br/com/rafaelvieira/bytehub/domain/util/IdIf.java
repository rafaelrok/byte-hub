package br.com.rafaelvieira.bytehub.domain.util;

import java.io.Serializable;

public interface IdIf<I extends Serializable> {
    I getId();
    void setId(I id);
}
