//package com.garage.autoservice.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class VehicleRepairLog {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String vin;  // VIN-код автомобиля
//
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<RepairJob> repairJobs;  // Список ремонтных работ для автомобиля
//}
