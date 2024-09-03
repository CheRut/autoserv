package com.garage.autoservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class PlannedRepairJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobName;
    private String serialNumber;
    private String cardNumber;
    private String fluidType;
    private Long remainingMileage;
    private LocalDate remainingTime;
    private String orderNumber;
    private String notes;

    private int partQuantity;

    private float fluidVolume;
    private boolean inPark;

    private String jobType;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getFluidType() {
        return fluidType;
    }

    public void setFluidType(String fluidType) {
        this.fluidType = fluidType;
    }

    public Long getRemainingMileage() {
        return remainingMileage;
    }

    public void setRemainingMileage(Long remainingMileage) {
        this.remainingMileage = remainingMileage;
    }

    public LocalDate getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(LocalDate remainingTime) {
        this.remainingTime = remainingTime;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isInPark() {
        return inPark;
    }

    public void setInPark(boolean inPark) {
        this.inPark = inPark;
    }

    // Constructors, equals, hashCode, toString methods
    public PlannedRepairJob() {
    }

    public PlannedRepairJob(String jobName, String serialNumber, String cardNumber, String fluidType, Long remainingMileage, LocalDate remainingTime, String orderNumber, String notes, boolean inPark) {
        this.jobName = jobName;
        this.serialNumber = serialNumber;
        this.cardNumber = cardNumber;
        this.fluidType = fluidType;
        this.remainingMileage = remainingMileage;
        this.remainingTime = remainingTime;
        this.orderNumber = orderNumber;
        this.notes = notes;
        this.inPark = inPark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlannedRepairJob that = (PlannedRepairJob) o;

        if (inPark != that.inPark) return false;
        if (!id.equals(that.id)) return false;
        if (!jobName.equals(that.jobName)) return false;
        if (!serialNumber.equals(that.serialNumber)) return false;
        if (!cardNumber.equals(that.cardNumber)) return false;
        if (!fluidType.equals(that.fluidType)) return false;
        if (!remainingMileage.equals(that.remainingMileage)) return false;
        if (!remainingTime.equals(that.remainingTime)) return false;
        if (!orderNumber.equals(that.orderNumber)) return false;
        return notes.equals(that.notes);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + jobName.hashCode();
        result = 31 * result + serialNumber.hashCode();
        result = 31 * result + cardNumber.hashCode();
        result = 31 * result + fluidType.hashCode();
        result = 31 * result + remainingMileage.hashCode();
        result = 31 * result + remainingTime.hashCode();
        result = 31 * result + orderNumber.hashCode();
        result = 31 * result + notes.hashCode();
        result = 31 * result + (inPark ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PlannedRepairJob{" +
                "id=" + id +
                ", jobName='" + jobName + '\'' +
                ", serialNumber='" + serialNumber + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", fluidType='" + fluidType + '\'' +
                ", remainingMileage=" + remainingMileage +
                ", remainingTime=" + remainingTime +
                ", orderNumber='" + orderNumber + '\'' +
                ", notes='" + notes + '\'' +
                ", inPark=" + inPark +
                '}';
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType=jobType;
    }

    public int getPartQuantity() {
        return partQuantity;
    }

    public void setPartQuantity(int partQuantity) {
        this.partQuantity = partQuantity;
    }

    public float getFluidVolume() {
        return fluidVolume;
    }

    public void setFluidVolume(float fluidQuantity) {
        this.fluidVolume = fluidQuantity;
    }
}
