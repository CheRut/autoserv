package com.garage.autoservice.dto;

import java.util.List;

/**
 * DTO (Data Transfer Object) для запроса создания или обновления информации о ремонтной работе.
 * Используется для передачи данных между клиентом и сервером.
 */
public class RepairJobRequest {

    /**
     * Наименование типа ремонтных работ.
     */
    private String jobName;

    /**
     * Интервал проведения ремонтных работ по сроку (в днях).
     */
    private int intervalInDays;

    /**
     * Интервал проведения ремонтных работ по пробегу (в километрах).
     */
    private int intervalInMileage;

    /**
     * Список запчастей, необходимых для выполнения ремонтных работ.
     */
    private List<PartRequest> requiredParts;

    /**
     * Количество единиц каждой запчасти, необходимой для выполнения ремонтных работ.
     * Соответствует списку `requiredParts` по индексу.
     */
    private List<Integer> partQuantities;

    // Геттеры и сеттеры

    /**
     * Возвращает наименование типа ремонтных работ.
     *
     * @return наименование типа ремонтных работ
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Устанавливает наименование типа ремонтных работ.
     *
     * @param jobName наименование типа ремонтных работ
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * Возвращает интервал проведения ремонтных работ по сроку (в днях).
     *
     * @return интервал проведения ремонтных работ по сроку
     */
    public int getIntervalInDays() {
        return intervalInDays;
    }

    /**
     * Устанавливает интервал проведения ремонтных работ по сроку (в днях).
     *
     * @param intervalInDays интервал проведения ремонтных работ по сроку
     */
    public void setIntervalInDays(int intervalInDays) {
        this.intervalInDays = intervalInDays;
    }

    /**
     * Возвращает интервал проведения ремонтных работ по пробегу (в километрах).
     *
     * @return интервал проведения ремонтных работ по пробегу
     */
    public int getIntervalInMileage() {
        return intervalInMileage;
    }

    /**
     * Устанавливает интервал проведения ремонтных работ по пробегу (в километрах).
     *
     * @param intervalInMileage интервал проведения ремонтных работ по пробегу
     */
    public void setIntervalInMileage(int intervalInMileage) {
        this.intervalInMileage = intervalInMileage;
    }

    /**
     * Возвращает список запчастей, необходимых для выполнения ремонтных работ.
     *
     * @return список запчастей
     */
    public List<PartRequest> getRequiredParts() {
        return requiredParts;
    }

    /**
     * Устанавливает список запчастей, необходимых для выполнения ремонтных работ.
     *
     * @param requiredParts список запчастей
     */
    public void setRequiredParts(List<PartRequest> requiredParts) {
        this.requiredParts = requiredParts;
    }

    /**
     * Возвращает количество единиц каждой запчасти.
     *
     * @return количество запчастей
     */
    public List<Integer> getPartQuantities() {
        return partQuantities;
    }

    /**
     * Устанавливает количество единиц каждой запчасти.
     *
     * @param partQuantities количество запчастей
     */
    public void setPartQuantities(List<Integer> partQuantities) {
        this.partQuantities = partQuantities;
    }
}
