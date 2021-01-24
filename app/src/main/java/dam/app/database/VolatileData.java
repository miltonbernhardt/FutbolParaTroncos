package dam.app.database;

import java.util.ArrayList;

import dam.app.model.Field;

public  class VolatileData {
    
    public static ArrayList<Field> getFields(){
        ArrayList<Field> a = new ArrayList<>();
        Field f1 = new Field(), f2 = new Field(), f3 = new Field(), f4 = new Field();
        f1.setName("El fútbolito");
        f1.setAddress("Siempre Diva 500");
        f1.setPhoneOfContact("3459823462");
        f1.setImageUUID("a");
        f1.setRating(3.8f);

        f2.setName("Los sin sangre");
        f2.setAddress("Laprida 8990");
        f2.setPhoneOfContact("3426637869");
        f2.setImageUUID("b");
        f2.setRating(4.5f);

        f3.setName("La boca");
        f3.setAddress("Bv. Martínez 982");
        f3.setPhoneOfContact("3459829222");
        f3.setImageUUID("c");
        f3.setRating(4.2f);

        f4.setName("El salado");
        f4.setAddress("Velez 4571");
        f4.setPhoneOfContact("3432123469");
        f4.setImageUUID("d");
        f4.setRating(4.65f);

        a.add(f1);
        a.add(f2);
        a.add(f3);
        a.add(f4);
        return a;
    }
}
