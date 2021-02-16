package dam.app.extras;

public enum EnumSortOption {
    NOMBRE_ALFABETICO("name"),
    PUNTUACION_ALTA("high_rating"),PUNTUACION_BAJA("rating"),
    DIRECCION_CERCANA("closer_address"), DIRECCION_LEJANA("far_address"),
    FECHA_CERCANA("closer_dateOfComment"), FECHA_LEJANA("dateOfComment");

    private final String value;
    EnumSortOption(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}


