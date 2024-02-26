package com.example.webbookingroom.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "user_room", schema = "public", catalog = "web_booking_room")
public class UserRoom {
    @Basic
    @Column(name = "user_id")
    private long userId;
    @Basic
    @Column(name = "room_id")
    private long roomId;
    @Basic
    @Column(name = "start_date")
    private Date startDate;
    @Basic
    @Column(name = "end_date")
    private Date endDate;
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

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
        this.roomId = roomId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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
        UserRoom userRoom = (UserRoom) o;
        return userId == userRoom.userId && roomId == userRoom.roomId && id == userRoom.id && Objects.equals(startDate, userRoom.startDate) && Objects.equals(endDate, userRoom.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, roomId, startDate, endDate, id);
    }
}
