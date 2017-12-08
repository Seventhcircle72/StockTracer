/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.net.URL;
import java.text.DecimalFormat;

/**
 *
 * @author Seventh
 */
public class Stock {
    
    // Variable declaration
    private String symbol;                                                          // Exchange symbol for stock
    private String name;                                                            // Name of stock
    private String exDivDate;                                                       // Last day to purchase stock to qualify for dividends
    private double change = 0;
    private double changePercent = 0;
    private double price = 0;                                                       // Price per share
    private double dividend = 0;                                                    // Dividend amount
    private double annualYield = 0;                                                 // Annual dividend yield per share
    private PayoutInformation payout = PayoutInformation.None;                      // Quarterly or Monthly dividends
    private String link;                                                               // URL link to TMX quote page
    // End of variable declaration
    
    // Get and Set methods
    public String getSymbol() { return symbol; }
    public final void setSymbol(String toSet) { symbol = toSet; }
    
    public String getName() { return name; }
    public final void setName(String toSet) { name = toSet; }
    
    public double getPrice() { return price; }
    public final void setPrice(double toSet) { price = toSet; }
    
    public double getDividend() { return dividend; }
    public final void setDividend(double toSet) { dividend = toSet; }
    
    public String getURL() { return link; }
    public final void setURL(String toSet) { link = toSet; }
    
    public PayoutInformation getPayoutInfo() { return payout; }
    public final void setPayoutInfo( PayoutInformation toSet ) { payout = toSet; }
    
    public String getExDivDate() { return exDivDate; }
    public final void setExDivDate(String toSet) { exDivDate = toSet; }
    
    public double getChange() { return change; }
    public final void setChange(double toSet) { change = toSet; }
    
    public double getChangePercent() { return changePercent; }
    public final void setChangePercent(double toSet) { changePercent = toSet; }
    
    public double getAnnualYield() { return annualYield; }
    public void setAnnualYield(double toSet) { annualYield = toSet; }
    // End of Get and Set methods

    // Constructor
    public Stock(String symbol, String stockName, double stockPrice,
            double dividend, String url, PayoutInformation interest, String exDiv, 
            double change, double changePercent) {
        setSymbol(symbol);
        setName(stockName);
        setPrice(stockPrice);
        setDividend(dividend);
        setURL(url);
        setPayoutInfo(interest);
        annualYield = calculateDividendYieldInPercent();
        setExDivDate(exDiv);
        setChange(change);
        setChangePercent(changePercent);
    }
    
    public double calculateAnnualDividendIncomeOnInvestment(double investment) {
        double result = 0;
        if (getPayoutInfo() == PayoutInformation.Monthly) {
            result = calculateAnnualDividendIncomeByMonth(investment);
        } else if (getPayoutInfo() == PayoutInformation.Quarterly) {
            result = calculateAnnualDividendIncomeByQuarter(investment);
        }
        return result;
    }
    
    private double calculateDividendIncomeOnInvestment(double investment) {
        int shares = calculateAvailableShares(investment);
        return (shares * getDividend());
    }
    
    private double calculateAnnualDividendIncomeByMonth(double investment) { 
        return (calculateDividendIncomeOnInvestment(investment) * 12); 
    }
    
    private double calculateAnnualDividendIncomeByQuarter(double investment) { 
        return (calculateDividendIncomeOnInvestment(investment) * 4);
    }
    
    public double calculateDividendYieldInPercent() {
        double dividendTotal = getDividend();
        if (getPayoutInfo() == PayoutInformation.Monthly) {
            dividendTotal = dividendTotal * 12;
        } else if (getPayoutInfo() == PayoutInformation.Quarterly) {
            dividendTotal = dividendTotal * 4;
        }
        double result = 0;
        double pricePerShare = getPrice();
        if (pricePerShare > 0) {
            result = (dividendTotal / pricePerShare) * 100;
        }
        return result;
    }
    
    public double calculateDividendYield() {
        double result = 0;
        double dividends = getDividend();
        if (getPayoutInfo() == PayoutInformation.Monthly) {
            result = dividends * 12;
        } else if (getPayoutInfo() == PayoutInformation.Quarterly) {
            result = dividends * 4;
        }
        return result;
    }
    
    public double calculateReturnOnInvestment(double investment, double gain,
                                                            int duration) {
        double result = 0;
        if (duration > 0) {
            result = (((gain - investment) / investment) / duration);
        }
        return result;
    }
    
    public double calculateRateOfReturn(double investment, double totalDividends) {
        double result = 0;
        return result;
    }
    
    public int calculateAvailableShares(double investment) {
        int result = 0;
        if (price > 0) {
            result = (int) (investment / price);
        }
        return result;
    }
    
    @Override
    public String toString() {
        DecimalFormat decimal = new DecimalFormat("##0.00#");
        String result = "";
        result += (getName() + getSymbol() + " | $" + getPrice() +  "\n" 
                + "Change: $" + getChange() + " (" + getChangePercent() + "%)" 
                + "\n"
                + "Dividend: $" + decimal.format(getDividend()) + "\n"
                + "Dividend Payout: " + getPayoutInfo() + "\n"
                + "Yield: " + decimal.format(getAnnualYield()) + "% ($" 
                + decimal.format(calculateDividendYield()) + ")" + "\n"
                + "-----------------------------------------------------\n");
        return result;
    }
}
