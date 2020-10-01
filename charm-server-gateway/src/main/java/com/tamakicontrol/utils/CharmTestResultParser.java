package com.tamakicontrol.utils;

import com.tamakicontrol.CharmTestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;

public class CharmTestResultParser {

    private static final Logger logger = LoggerFactory.getLogger(CharmTestResultParser.class);

    public static CharmTestResult parse(String message) throws ParseException {
        CharmTestResult testResult = new CharmTestResult();

        try {

            // This line of code can separate message and store them in the split array by ","
            String[] split = message.split(",");

            if (split.length < 155)
                throw new ParseException("Was not Provided CSV Input", 0);

            // The very fist character EZ reader send out is a "<", which is not useful to us,
            // so we want to remove "<" and store it in testOutputName.
            testResult.setTestOutputName(split[0].replace("<", ""));
            testResult.setStructVersion(split[1]);
            testResult.setChannelNumber(Integer.parseInt(split[2]));
            testResult.setAssay(split[3]);

            // EZ reader send out date and time in two separate field. We want them to be stored in
            // a single variable date.
            // The below code convert the original output(28SEP2020,14:10:21)
            // into Mon Sep 28 14:10:21 IST 2020, which meet the standard format of Date datatype.
            String sDate1 = split[4] + " " + split[5];
            Date date1 = new SimpleDateFormat("ddMMMyyyy hh:mm:ss").parse(sDate1);
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
            testResult.setXLine(Double.parseDouble(split[16]));
            testResult.setInterpretation(split[17]);
            testResult.setTestTemperature(Double.parseDouble(split[18]));

            // There are 128 remission data points coming out from EZ reade,
            // we want to store them individually.
            Double[] charmRemissions = new Double[128];
            for (int i = 0; i < 128; i++) {
                charmRemissions[i] = Double.parseDouble(split[i + 19]);
            }

            testResult.setRemissions(charmRemissions);


            testResult.setTipRed(Double.parseDouble(split[147]));
            testResult.setTipGreen(Double.parseDouble(split[148]));
            testResult.setTipBlue(Double.parseDouble(split[149]));
            testResult.setDirt(Double.parseDouble(split[150]));
            testResult.setFlow(Double.parseDouble(split[151]));
            testResult.setInterpString(split[152]);
            testResult.setY_Line(Double.parseDouble(split[153]));

            // The transmission message from EZ reader is ended by a ">"
            testResult.setZ_Line(Double.parseDouble(split[154].replace(">", "")));

        }catch (Exception e){
            logger.error("Failed to parse", e);
            throw new ParseException("Failed to Parse", 0);
        }

        return testResult;
    }
}



    
    
