package com.example.webbookingroom.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "user_hotel", schema = "booking_web", catalog = "")
public class UserHotel {
    @Basic
    @Column(name = "user_id")
    private long userId;
    @Basic
    @Column(name = "hotel_id")
    private long hotelId;
    @Basic
    @Column(name = "selectedLocation")
    private String selectedLocation;
    @Basic
    @Column(name = "selectedHotelType")
    private String selectedHotelType;
    @Basic
    @Column(name = "selectedAmountOfPeople")
    private Integer selectedAmountOfPeople;
    @Basic
    @Column(name = "checkin")
    private Date checkin;
    @Basic
    @Column(name = "checkout")
    private Date checkout;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getHotelId() {
        return hotelId;
    }

    public void setHotelId(long hotelId) {
        this.hotelId = hotelId;
    }

    public String getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(String selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    public String getSelectedHotelType() {
        return selectedHotelType;
    }

    public void setSelectedHotelType(String selectedHotelType) {
        this.selectedHotelType = selectedHotelType;
    }

    public Integer getSelectedAmountOfPeople() {
        return selectedAmountOfPeople;
    }

    public void setSelectedAmountOfPeople(Integer selectedAmountOfPeople) {
        this.selectedAmountOfPeople = selectedAmountOfPeople;
    }

    public Date getCheckin() {
        return checkin;
    }

    public void setCheckin(Date checkin) {
        this.checkin = checkin;
    }

    public Date getCheckout() {
        return checkout;
    }

    public void setCheckout(Date checkout) {
        this.checkout = checkout;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserHotel userHotel = (UserHotel) o;
        return userId == userHotel.userId && hotelId == userHotel.hotelId && id == userHotel.id && Objects.equals(selectedLocation, userHotel.selectedLocation) && Objects.equals(selectedHotelType, userHotel.selectedHotelType) && Objects.equals(selectedAmountOfPeople, userHotel.selectedAmountOfPeople) && Objects.equals(checkin, userHotel.checkin) && Objects.equals(checkout, userHotel.checkout);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, hotelId, selectedLocation, selectedHotelType, selectedAmountOfPeople, checkin, checkout, id);
    }
}
