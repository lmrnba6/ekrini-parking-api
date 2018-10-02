package ca.ekrini.parking.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 40)
    private String addressOneLine;

    @NotBlank
    @Size(max = 40)
    private String city;

    @NotBlank
    @Size(max = 40)
    private String state;

    @NotBlank
    @Size(max = 10)
    private String zip;

    @OneToOne(fetch = FetchType.LAZY)
    private Position position = new Position();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressOneLine() {
        return addressOneLine;
    }

    public void setAddressOneLine(String addressOneLine) {
        this.addressOneLine = addressOneLine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Address() {

    }



}