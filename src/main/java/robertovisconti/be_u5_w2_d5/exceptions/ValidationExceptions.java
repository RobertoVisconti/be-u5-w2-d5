package robertovisconti.be_u5_w2_d5.exceptions;

import java.util.List;

public class ValidationExceptions extends RuntimeException {

    private final List<String> listaErrori;

    public ValidationExceptions(List<String> listaErrori) {
        super("Ci sono errori di validazione");
        this.listaErrori = listaErrori;
    }

    public List<String> getListaErrori() {
        return this.listaErrori;
    }
}
