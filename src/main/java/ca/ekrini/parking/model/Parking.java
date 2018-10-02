package ca.ekrini.parking.model;

import ca.ekrini.parking.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "parkings")
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String size;

    private Double price;

    private String comment;

    private LocalTime timeStart;

    private LocalTime timeEnd;

    private Date date;

    private Recurrence recurrence;

    @OneToOne(fetch=FetchType.EAGER)
    private User user = new User();

    @OneToOne(fetch=FetchType.EAGER)
    private Address address = new Address();

    public Parking() {

    }

    public Parking(String number, String size, Double price, String comment, LocalTime timeStart, LocalTime timeEnd, Date date, Recurrence recurrence, User user, Address address) {
        this.number = number;
        this.size = size;
        this.price = price;
        this.comment = comment;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.date = date;
        this.recurrence = recurrence;
        this.user = user;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Recurrence getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(Recurrence recurrence) {
        this.recurrence = recurrence;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}