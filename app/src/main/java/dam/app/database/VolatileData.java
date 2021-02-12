package dam.app.database;

import com.google.android.gms.maps.model.LatLng;

import java.time.LocalDate;
import java.util.ArrayList;

import dam.app.model.Comment;
import dam.app.model.Field;

public  class VolatileData {
    
    public static ArrayList<Field> getFields(){
        ArrayList<Field> a = new ArrayList<>();
        Field f1 = new Field(), f2 = new Field(), f3 = new Field(), f4 = new Field();
        f1.setName("El fútbolito");
        f1.setAddress("Lavaisse 610");
        f1.setPhoneOfContact("3459823462");
        f1.setImageUUID("a");
        f1.setPositionX("-31.617074");
        f1.setPositionY("-60.675567");
        f1.setRating(3.8f);

        f2.setName("Los sin sangre");
        f2.setAddress("Laprida 8990");
        f2.setPhoneOfContact("3426637869");
        f2.setImageUUID("b");
        f2.setPositionX("-31.613421");
        f2.setPositionY("-60.683842");
        f2.setRating(4.5f);

        f3.setName("La boca");
        f3.setAddress("4 de Enero 3751");
        f3.setPhoneOfContact("3459829222");
        f3.setImageUUID("c");
        f3.setPositionX("-31.631391");
        f3.setPositionY("-60.707875");
        f3.setRating(4.2f);

        f4.setName("El salado");
        f4.setAddress("Gdor. Candioti 1700");
        f4.setPhoneOfContact("3432123469");
        f4.setImageUUID("d");
        f4.setPositionX("-31.640270");
        f4.setPositionY("-60.694922");
        f4.setRating(4.65f);

        a.add(f1);
        a.add(f2);
        a.add(f3);
        a.add(f4);
        return a;
    }

    public static ArrayList<Comment> getComments(){
        ArrayList<Comment> a = new ArrayList<>();
        Comment c1 = new Comment(), c2 = new Comment(), c3 = new Comment(), c4 = new Comment(), c5 = new Comment();
        c1.setDateOfComment(LocalDate.now().minusDays(10));
        c2.setDateOfComment(LocalDate.now().minusDays(8));
        c3.setDateOfComment(LocalDate.now().minusDays(6));
        c4.setDateOfComment(LocalDate.now().minusDays(17));
        c5.setDateOfComment(LocalDate.now().minusDays(2));

        c1.setScore(4);
        c2.setScore(5);
        c3.setScore(3);
        c4.setScore(3);
        c5.setScore(5);

        c1.setUsername("Oriol Sales");
        c2.setUsername("Javier Valdivia");
        c3.setUsername("Moises Pallares");
        c4.setUsername("Carlos Manuel Abril");
        c5.setUsername("Jordi Valcarcel");

        c1.setComment("bajo muchísimo la calidad de la pizza, antes traía mucho queso, salsa de buena calidad, la verdad que la que encargamos anoche no tenía condimentos, preferiría pagar más pero que sea como las de antes. gracias!.  bajo muchísimo la calidad de la pizza, antes traía mucho queso, salsa de buena calidad, la verdad que la que encargamos anoche no tenía condimentos, preferiría pagar más pero que sea como las de antes. gracias!");
        c2.setComment("Muy buenas empanadas, abundantes no como los souveniles de otros lugares");
        c3.setComment("las papas estaban como muy marrones daba la sensacion que el aceite que se utilizo era muy usado y los panes de la ensalada cesar no eran como los que consumi en el local, no estaban dorados,era solo pan cortado, el resto rico. las papas estaban como muy marrones daba la sensacion que el aceite que se utilizo era muy usado y los panes de la ensalada cesar no eran como los que consumi en el local, no estaban dorados,era solo pan cortado, el resto rico");
        c4.setComment("excelente la boca. Ni hablar las empanadas de choclo.");
        c5.setComment("encargue canelones de verdura y me llegaron sin salsa...tendrían que aclararlo en la selección del menú y brindar la opción se elegir la salsa para acompañar");

        c1.setIdField(1);
        c2.setIdField(1);
        c3.setIdField(2);
        c4.setIdField(3);
        c5.setIdField(4);

        a.add(c1);
        a.add(c2);
        a.add(c3);
        a.add(c4);
        a.add(c5);
        return a;
    }
}
