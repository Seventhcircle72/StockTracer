/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package source;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Seventh
 */
public class WebReader {
    
    private BufferedReader bufferedReader;
    private FileReader fileReader;
    private static WebReader reader;
    private static final String URL_PATH = "https://web.tmxmoney.com/quote.php?qm_symbol=";
    private static final String SYMBOL_PATH = "./src/resources/TSXkeys.txt";
    private static final int NUM_SYMBOLS = 2205;
    private String[] symbols;
    
    public WebReader() {
        loadKeys();
    }
    
    public boolean isOverYieldPercent(Stock stock, double yieldPercent) {
        boolean result = false;
        if (stock.getAnnualYield() > yieldPercent) {
            result = true;
        }
        return result;
    }
    
    public String[] getSymbols() {
        return symbols;
    }
    
    public Stock findStock(String path) {
        String html = connectToURL(path);
        Stock result = generateStock(html, path);
        return result;
    }
    
    private Stock generateStock(String html, String stockURL) {
        Stock result;
        result = new Stock(findStockSymbol(html), findStockName(html), 
            findStockPrice(html), findDividends(html), stockURL, 
            findDivFrequency(html), findExDivDate(html), findChange(html), 
            findChangePercent(html));
        return result;
    }
    
    private String connectToURL(String url) {
        String result = "";
        try {
            URL path = new URL(url);
            bufferedReader = new BufferedReader(new InputStreamReader(path.openStream()));
            String inputLine;
            String html = "";
            while ((inputLine = bufferedReader.readLine()) != null) {
                html += inputLine + "\n";
            }
            result = html;
            bufferedReader.close();
        } catch (MalformedURLException ex) {
            System.out.print("Malformed URL");
        } catch (IOException ex) {
            System.out.print("IO Exception");
        }
        return result;
    }
    
    // String Parser Methods
    private String findStockSymbol(String html) {
        String result = "";
        Pattern regex = Pattern.compile("\\<title\\>(.*)(\\[.*\\]).*"
                + "\\<\\/title\\>");
        Matcher matcher = regex.matcher(html);
        if (matcher.find() == true) {
            result = matcher.group(2);
        }
        return result;
    }
    
    private String findStockName(String html) {
        String result = "";
        Pattern regex = Pattern.compile("\\<title\\>(.*)(\\[.*\\]).*"
                + "\\<\\/title\\>");
        Matcher matcher = regex.matcher(html);
        if (matcher.find() == true) {
            result = matcher.group(1);
        }
        return result;
    }
    
    private double findStockPrice(String html) {
        double result = 0;
        String price = "";
        Pattern regex = Pattern.compile("\\<div class\\=\\\"quote\\-price priceLarge\\\"\\>\\s*\\$\\s*\\<span\\>(\\d?\\d?\\d\\.\\d\\d\\d?)\\<\\/span\\>");
        Matcher matcher = regex.matcher(html);
        if (matcher.find() == true) {
            price = matcher.group(1);
            result = Double.valueOf(price);
        }
        return result;
    }
    
    private double findChange(String html) {
        double result = 0;
        String change = "";
        Pattern regex = Pattern.compile("\\<span class\\=\\\"quote\\-small\\-text\\\"\\>Change\\:\\<\\/span\\>\\<br \\/\\>\\s*(\\-?\\d?\\d\\.\\d\\d\\d?)");
        Matcher matcher = regex.matcher(html);
        if (matcher.find() == true) {
            change = matcher.group(1);
            result = Double.valueOf(change);
        }
        return result;
    }
    
    private double findChangePercent(String html) {
        double result = 0;
        String change = "";
        Pattern regex = Pattern.compile("\\<span class\\=\\\"quote\\-small\\-text\\\"\\>Change\\:\\<\\/span\\>\\<br \\/\\>\\s*\\-?\\d?\\d\\.\\d\\d\\s*\\((\\-?\\d?\\d\\.\\d\\d)\\%\\)");
        Matcher matcher = regex.matcher(html);
        if (matcher.find() == true) {
            change = matcher.group(1);
            result = Double.valueOf(change);
        }
        return result;
    }
    
    private double findDividends(String html) {
        double result = 0;
        String dividends = "";
        Pattern regex = Pattern.compile("\\<.*\\>Dividend\\:\\<\\/td\\>\\s*\\<td class\\=\\\"\\\"\\>(\\d\\.\\d*)");
        Matcher matcher = regex.matcher(html);
        if (matcher.find() == true) {
            dividends = matcher.group(1);
            result = Double.valueOf(dividends);
        }
        return result;
    }
    
    private PayoutInformation findDivFrequency(String html) {
        PayoutInformation result = PayoutInformation.None;
        String info = "";
        Pattern regex = Pattern.compile("\\<td\\sclass\\=\\\"\\\"\\>Div\\.\\sFrequency\\:\\<\\/td\\>\\s*\\<td\\sclass\\=\\\"\\\"\\>(.*)\\<\\/td\\>");
        Matcher matcher = regex.matcher(html);
        if (matcher.find() == true) {
            info = matcher.group(1);
            if (info.equals("Quarterly")) {
                result = PayoutInformation.Quarterly;
            } else if (info.equals("Monthly")) {
                result = PayoutInformation.Monthly;
            }
        }
        return result;
    }
    
    private String findExDivDate(String html) {
        String result = "0000/00/00";
        Pattern regex = Pattern.compile("\\<td\\sclass\\=\\\"\\\"\\>Ex\\-Div\\sDate\\:\\<\\/td\\>\\s*\\<td\\sclass\\=\\\"\\\"\\>(.*)\\<\\/td\\>");
        Matcher matcher = regex.matcher(html);
        if (matcher.find() == true) {
            result = matcher.group(1);
        }
        return result;
    }
    
    private boolean isPayingDividends(String exDivDate) {
        boolean result = false;
        int payable, current;
        String currentDate = getCurrentDate();
        String payableYear = StringSlicer.sliceEnd(exDivDate, 4);
        String currentYear = StringSlicer.sliceEnd(currentDate, 4);
        payable = Integer.valueOf(payableYear);
        current = Integer.valueOf(currentYear);
        if (payable > current) {
            result = true;
        } else if (payable == current) {
            String payableMonth = StringSlicer.sliceRange(exDivDate, 5, 7);
            String currentMonth = StringSlicer.sliceRange(currentDate, 5, 7);
            payable = Integer.valueOf(payableMonth);
            current = Integer.valueOf(currentMonth);
            if (payable > current) {
                result = true;
            } else if (payable == current) {
                String payableDay = StringSlicer.sliceStart(exDivDate, 8);
                String currentDay = StringSlicer.sliceStart(currentDate, 8);
                payable = Integer.valueOf(payableDay);
                current = Integer.valueOf(currentDay);
                if (payable > current) {
                    result = true;
                }
            }
        }
        return result;
    }
    // End of String Parser Methods
    
    private String[] loadKeys() {
        String key;
        symbols = new String[NUM_SYMBOLS];
        int index = NUM_SYMBOLS;
        try {
            FileReader fileReader = new FileReader(SYMBOL_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int localIndex = 0;
            while (localIndex < index) {
                key = bufferedReader.readLine();
                symbols[localIndex] = key;
                localIndex++;
            }
        } catch (FileNotFoundException ex) {
            System.out.print("File Not Found");
        } catch (IOException ex) {
            System.out.print("IO Exception");
        }
        
        return symbols;
    }
    
    private String getCurrentDate() {
        String result = "";
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date today = new Date();
        result = format.format(today);
        return result;
    }
    
    // DO NOT USE: Unused Methods
    private String getKeyFromLine(String line) {
        String toFind = ".TO";
        int index = line.indexOf(toFind);
        String result = StringSlicer.sliceEnd(line, index);
        return result;
    }
    // End of Unused Methods
}
