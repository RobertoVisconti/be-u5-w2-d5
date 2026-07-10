package robertovisconti.be_u5_w2_d5.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "dipendente")
@Getter
@NoArgsConstructor
public class Dipendente {

    @Id
    @GeneratedValue
    @Column(name = "id_dipendente")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cognome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "img_profile")
    private String imgProfile;

    public Dipendente(String username, String nome, String cognome, String email, String imgProfile) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.imgProfile = imgProfile;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImgProfile(String imgProfile) {
        this.imgProfile = imgProfile;
    }
}
