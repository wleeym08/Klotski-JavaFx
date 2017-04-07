/**
 * Program Name: MoveInfo.java
 * Discussion:   MoveInfo class for storing move information
 * Written By:   Zhiying Li
 * Date:         2016/12/12
 */

public class MoveInfo {
    private int index;
    private double beginX;
    private double beginY;
    private double endX;
    private double endY;
    
    public MoveInfo(int index, double beginX, double beginY,
        double endX, double endY) {
        this.index = index;
        this.beginX = beginX;
        this.beginY = beginY;
        this.endX = endX;
        this.endY = endY;
    }
    
    public int getIndex() {
        return index;
    }
    
    public double getBeginX() {
        return beginX;
    }
    
    public double getBeginY() {
        return beginY;
    }
    
    public double getEndX() {
        return endX;
    }
    
    public double getEndY() {
        return endY;
    }
    
    public void setIndex(int index) {
        this.index = index;
    } 
    
    public void setBeginX(double beginX) {
        this.beginX = beginX;
    } 
    
    public void setBeginY(double beginY) {
        this.beginY = beginY;
    } 
    
    public void setEndX(double endX) {
        this.endX = endX;
    } 
    
    public void setEndY(double endY) {
        this.endY = endY;
    } 
}
