/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

/**
 *
 * @author Seventh
 */
public class StockTemplate {
    
    private double avgPrice = -1;
    private double deviation = 0.25;
    private PayoutInformation divFrequency = PayoutInformation.Any;
    private double minYield = -1;
    private double minDividend = -1;
    private double investment = -1;
    private double returnRate = -1;
    
    public double getAvgPrice() { return avgPrice; }
    public void setAvgPrice(double toSet) { avgPrice = toSet; } 
    
    public double getDeviation() { return deviation; }
    public void setDeviation(double toSet) { deviation = toSet; } 
    
    public double getMinYield() { return minYield; }
    public void setMinYield(double toSet) { minYield = toSet; } 
    
    public double getMinDividend() { return minDividend; }
    public void setMinDividend(double toSet) { minDividend = toSet; }
    
    public double getInvestment() { return investment; }
    public void setInvestment(double toSet) { investment = toSet; } 
    
    public PayoutInformation getDivFrequency() { return divFrequency; }
    public void setDivFrequency(PayoutInformation toSet) { divFrequency = toSet; }
    
    public StockTemplate() {
        
    }
    
    public boolean compareTo(Stock stock) {
        boolean result = true;
        if (avgPrice != -1) {
            double value = stock.getPrice();
            if (!inBounds(value)) {
                result = false;
            }
        }
        if (divFrequency != PayoutInformation.Any) {
            PayoutInformation info = stock.getPayoutInfo();
            if (getDivFrequency() != info) {
                result = false;
            }
        }
        if (minYield != -1) {
            if (minYield >= stock.getAnnualYield()) {
                result = false;
            }
        }
        if (minDividend != -1) {
            if (minDividend >= stock.getDividend()) {
                result = false;
            }
        }
        if (investment != -1 && returnRate != -1) {
            // TODO: Find the returnRate and compare
        }
        return result;
    }
    
    private boolean inBounds(double value) {
        boolean result = false;
        double min, max;
        min = avgPrice - (avgPrice * deviation);
        max = avgPrice + (avgPrice * deviation);
        if (value <= max && value >= min) {
            result = true;
        }
        return result;
    }
}
