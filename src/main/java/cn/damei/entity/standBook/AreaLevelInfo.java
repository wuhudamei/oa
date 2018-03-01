package cn.damei.entity.standBook;
import cn.damei.entity.IdEntity;
public class AreaLevelInfo extends IdEntity {
    private String orderNo;
    private Double floorSettleArea;
    private Double floorTileBudgetArea;
    private Double floorTileSettleArea;
    private Double dosage;
    private Double measurehousearea;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public Double getFloorSettleArea() {
        return floorSettleArea;
    }
    public void setFloorSettleArea(Double floorSettleArea) {
        this.floorSettleArea = floorSettleArea;
    }
    public Double getFloorTileBudgetArea() {
        return floorTileBudgetArea;
    }
    public void setFloorTileBudgetArea(Double floorTileBudgetArea) {
        this.floorTileBudgetArea = floorTileBudgetArea;
    }
    public Double getFloorTileSettleArea() {
        return floorTileSettleArea;
    }
    public void setFloorTileSettleArea(Double floorTileSettleArea) {
        this.floorTileSettleArea = floorTileSettleArea;
    }
    public Double getDosage() {
        return dosage;
    }
    public void setDosage(Double dosage) {
        this.dosage = dosage;
    }
    public Double getMeasurehousearea() {
        return measurehousearea;
    }
    public void setMeasurehousearea(Double measurehousearea) {
        this.measurehousearea = measurehousearea;
    }
}
