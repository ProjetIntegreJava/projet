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
        setId(id);
        setFirstName(first_name);
        setLastName(last_name);
        setPseudo(pseudo);
        setBirthdate(birthdate);
        setNationality(nationality);
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    public void setNationality(String nationality){
        this.nationality = nationality;
    }

    public String getPseudo() {
        return pseudo;
    }
}
