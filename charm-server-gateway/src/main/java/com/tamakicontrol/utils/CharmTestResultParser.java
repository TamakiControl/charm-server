package com.tamakicontrol.utils;

import com.tamakicontrol.CharmTestResult;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

public class CharmTestResultParser {

    public static CharmTestResult parse(String message) throws ParseException {

        String[] split = message.split(",");
        CharmTestResult testResult = new CharmTestResult();
        testResult.setTestOutputName(split[0]);
        testResult.setStructVersion(split[1]);
        testResult.setChannelNumber(Integer.parseInt(split[2]));
        testResult.setAssay(split[3]);


        //28SEP2020,14:10:21,
        String sDate1 = split[4] + " " + split[5];
        Date date1 = new SimpleDateFormat("ddMMMyyyy hh:mm:ss").parse(sDate1); //Mon Sep 28 14:10:21 IST 2020
        testResult.setDate(date1);


        testResult.setResult(split[6]);
        testResult.setAnswer(Double.parseDouble(split[7]));
        testResult.setUnitSN(Integer.parseInt(split[8]));
        testResult.setLotNumber(Integer.parseInt(split[9]));
        testResult.setSampleID(Integer.parseInt(split[10]));
        testResult.setOperatorID(Integer.parseInt(split[11]));
        testResult.setMode(Integer.parseInt(split[12]));
        testResult.setFailCode(Integer.parseInt(split[13]));
        testResult.setControlLINE(Integer.parseInt(split[14]));
        testResult.setTestLINE(Integer.parseInt(split[15]));
        testResult.setXLine(Integer.parseInt(split[16]));
        testResult.setInterpretation(split[17]);
        testResult.setTestTemperature(Double.parseDouble(split[18]));

        Double[] charmRemissions = new Double[128];
        for (int i = 19; i < 147; i++) {
            charmRemissions[i - 19] = Double.parseDouble(split[i]);
        }

        testResult.setRemissions(charmRemissions);


        testResult.setTipRed(Double.parseDouble(split[147]));
        testResult.setTipGreen(Double.parseDouble(split[148]));
        testResult.setTipBlue(Double.parseDouble(split[149]));
        testResult.setDirt(Double.parseDouble(split[150]));
        testResult.setFlow(Double.parseDouble(split[151]));
        testResult.setInterpString(split[152]);
        testResult.setYLine(Double.parseDouble(split[153]));
        testResult.setZLine(Double.parseDouble(split[154]));


        return testResult;
    }
}



    
    
