package dam.app.extras;

public enum EnumPaths {
    PATH_FIELDS("images-fields-official/"),
    PATH_IMAGES_REVIEW("reviews-images/");

    private final String value;
    EnumPaths(String value){
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
