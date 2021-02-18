package dam.app.extras;

public enum EnumStateReserve {
    CANCELADA("CANCELADA"), // si el cliente la cancela o si pasó la fecha y no la usó (el establecimiento debería indicarlo)
    ACTIVA("ACTIVA"), // no pasó la fecha de uso
    USADA("USADA"); // pasó la fecha de uso

    private final String value;

    EnumStateReserve(String value){
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
