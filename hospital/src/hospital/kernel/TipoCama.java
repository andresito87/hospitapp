package hospital.kernel;

/**
 *
 * @author andres
 */
public enum TipoCama {

    CAMA_NORMAL(0, "Cama Normal"),
    CAMA_PARAPLEJIA(1, "Cama Paraplejia"),
    CAMA_TETRAPLEJIA(2, "Cama Tetraplejia"),
    CAMA_UCI(3, "Cama UCI"),
    CAMA_INFANTIL(4, "Cama Infantil");

    private final int codigo;
    private final String descripcion;

    TipoCama(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public int getCodigo() {
        return codigo + 1;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static TipoCama fromCodigo(int codigo) {
        for (TipoCama tipo : TipoCama.values()) {
            if (tipo.getCodigo() == codigo) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de cama no válido: " + codigo);
    }

    public static TipoCama fromDescripcion(String descripcion) {
        for (TipoCama tipo : TipoCama.values()) {
            if (tipo.getDescripcion().equalsIgnoreCase(descripcion)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Descripción de cama no válida: " + descripcion);
    }
}
