package modelPackage;

import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Player {
    private int id;
    private String firstName;
    private String lastName;
    private String pseudo;
    private LocalDate birthdate;
    private String nationality;

    public Player(int id, String first_name, String last_name, String pseudo, LocalDate birthdate, String nationality){
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.birthdate = birthdate;
        this.nationality = nationality;

    }
}
