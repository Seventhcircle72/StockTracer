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
    private Exchange market;                                                        // Market Exchange platform
    private String symbol;                                                          // Exchange symbol for stock
    private String name;                                                            // Name of stock
    private double price = 0;                                                       // Price per share
    private double dividend = 0;                                                    // Dividend amount
    private PayoutInformation payout = PayoutInformation.None;                      // Quarterly or Monthly dividends
    private URL link;                                                               // URL link to TMX quote page
    // End of variable declaration
    
    // Get and Set methods
    public Exchange getMarket() { return market; }
    public void setMarket(Exchange toSet) { market = toSet; }
    
    public String getSymbol() { return symbol; }
    public final void setSymbol(String toSet) { symbol = toSet; }
    
    public String getName() { return name; }
    public final void setName(String toSet) { name = toSet; }
    
    public double getPrice() { return price; }
    public final void setPrice(double toSet) { price = toSet; }
    
    public double getDividend() { return dividend; }
    public final void setDividend(double toSet) { dividend = toSet; }
    
    public URL getURL() { return link; }
    public final void setURL(URL toSet) { link = toSet; }
    
    public PayoutInformation getPayoutInfo() { return payout; }
    public final void setPayoutInfo( PayoutInformation toSet ) { payout = toSet; }
    // End of Get and Set methods

    // Constructor
    public Stock(String symbol, String stockName, double stockPrice,
            double dividend, URL url, PayoutInformation interest) {
        setSymbol(symbol);
        setName(stockName);
        setPrice(stockPrice);
        setDividend(dividend);
        setURL(url);
        setPayoutInfo(interest);
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
        DecimalFormat decimal = new DecimalFormat("0.00");
        String result = "";
        return result;
    }
}
