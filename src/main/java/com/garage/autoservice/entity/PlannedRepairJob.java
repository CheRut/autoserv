package com.garage.autoservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PlannedRepairJob {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jobName;
    private String enterpriseNumber;
    private String cardNumber;
    private String fluidType;
    private Long remainingMileage;
    private String remainingTime;
    private String orderNumber;
    private String notes;

    private String PartCardNumber;


    private String fluidCardNumber;
    private Integer partQuantity;

    private Float fluidVolume;
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

    public String getEnterpriseNumber() {
        return enterpriseNumber;
    }

    public void setEnterpriseNumber(String serialNumber) {
        this.enterpriseNumber = serialNumber;
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

    public String getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(String remainingTime) {
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

    public PlannedRepairJob(String jobName, String enterpriseNumber, String cardNumber, String fluidType, Long remainingMileage, String remainingTime, String orderNumber, String notes, boolean inPark) {
        this.jobName = jobName;
        this.enterpriseNumber = enterpriseNumber;
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
        if (!enterpriseNumber.equals(that.enterpriseNumber)) return false;
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
        result = 31 * result + enterpriseNumber.hashCode();
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
                ", serialNumber='" + enterpriseNumber + '\'' +
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

    public Integer getPartQuantity() {
        return partQuantity;
    }

    public void setPartQuantity(Integer partQuantity) {
        this.partQuantity = partQuantity;
    }

    public Float getFluidVolume() {
        return fluidVolume;
    }

    public void setFluidVolume(Float fluidQuantity) {
        this.fluidVolume = fluidQuantity;
    }

    public String getFluidCardNumber() {
        return fluidCardNumber;
    }
    public void setFluidCardNumber(String fluidCardNumber) {
        this.fluidCardNumber = fluidCardNumber;
    }

    public String getPartCardNumber() {
        return PartCardNumber;
    }

    public void setPartCardNumber(String partCardNumber) {
        PartCardNumber = partCardNumber;
    }
}
