package ca.ekrini.parking.payload;

import ca.ekrini.parking.model.Address;
import ca.ekrini.parking.model.Position;
import ca.ekrini.parking.model.Recurrence;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class ParkingResponse {

    private Long id;

    private String number;

    private String size;

    @NotNull
    private Double price;

    private String comment;

    @NotNull
    private LocalTime timeStart;

    @NotNull
    private LocalTime timeEnd;

    @NotNull
    private Date date;

    @NotNull
    private Recurrence recurrence;

    @NotNull
    private Address address;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
