package com.tamakicontrol;

import java.util.Date;

public class CharmTestResult {

    private String testOutputName;
    public String getTestOutputName(){
        return testOutputName;
    }
    public void setTestOutputName(String testOutputName){
        this.testOutputName = testOutputName;
    }


    private String structVersion;
    public String getStructVersion(){
        return structVersion;
    }
    public void setStructVersion(String structVersion){
        this.structVersion = structVersion;
    }


    private int channelNumber;
    public int getChannelNumber(){
        return channelNumber;
    }
    public void setChannelNumber(int channelNumber){
        this.channelNumber = channelNumber;
    }


    private String assay;
    public String getAssay(){
        return assay;
    }
    public void setAssay(String assay){
        this.assay = assay;
    }


    private Date date;
    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }


    private String result;
    public String getResult(){
        return result;
    }
    public void setResult(String result){
        this.result = result;
    }


    private Double answer;
    public Double getAnswer(){
        return answer;
    }
    public void setAnswer(Double answer){
        this.answer = answer;
    }


    private int unitSN;
    public int getUnitSN(){
        return unitSN;
    }
    public void setUnitSN(int unitSN){
        this.unitSN = unitSN;
    }


    private int lotNumber;
    public int getLotNumber(){
        return lotNumber;
    }
    public void setLotNumber(int lotNumber){
        this.lotNumber = lotNumber;
    }


    private int sampleID;
    public int getSampleID(){
        return sampleID;
    }
    public void setSampleID(int sampleID){
        this.sampleID = sampleID;
    }


    private int operatorID;
    public int getOperatorID(){
        return operatorID;
    }
    public void setOperatorID(int operatorID){
        this.operatorID = operatorID;
    }


    private int mode;
    public int getMode(){
        return mode;
    }
    public void setMode(int mode){
        this.mode = mode;
    }


    private int failCode;
    public int getFailCode(){
        return failCode;
    }
    public void setFailCode(int failCode){
        this.failCode = failCode;
    }


    private int controlLINE;
    public int getControlLINE(){
        return controlLINE;
    }
    public void setControlLINE(int controlLINE){
        this.controlLINE = controlLINE;
    }


    private int testLINE;
    public int getTestLINE(){
        return testLINE;
    }
    public void setTestLINE(int testLINE){
        this.testLINE = testLINE;
    }

    private int XLine;
    public int getXLine(){
        return XLine;
    }
    public void setXLine(int XLine){
        this.XLine = XLine;
    }


    private String interpretation;
    public String getInterpretation(){
        return interpretation;
    }
    public void setInterpretation(String interpretation){
        this.interpretation = interpretation;
    }


    private Double testTemperature;
    public Double getTestTemperature(){
        return testTemperature;
    }
    public void setTestTemperature(Double testTemperature){
        this.testTemperature = testTemperature;
    }



    private Double[] remissions = new Double[128];
    public Double[] getRemissions(){
        return remissions;
    }
    public void setRemissions(Double[] remissions){

    private double[] remissions = new double[128];
    public double[] getRemissions(){
        return remissions;
    }
    public void setRemissions(double[] remissions){
        this.remissions = remissions;
    }



    private Double tipRed;
    public Double getTipRed(){
        return tipRed;
    }
    public void setTipRed(Double tipRed){
        this.tipRed = tipRed;
    }


    private Double tipGreen;
    public Double getTipGreen(){
        return tipGreen;
    }
    public void setTipGreen(Double tipGreen){
        this.tipGreen = tipGreen;
    }


    private Double tipBlue;
    public Double getTipBlue(){
        return tipBlue;
    }
    public void setTipBlue(Double tipBlue){
        this.tipBlue = tipBlue;
    }


    private Double dirt;
    public Double getDirt(){
        return dirt;
    }
    public void setDirt(Double dirt){
        this.dirt = dirt;
    }


    private Double flow;
    public Double getFlow(){
        return flow;
    }
    public void setFlow(Double flow){
        this.flow = flow;
    }


    private String interpString;
    public String getInterpString(){
        return interpString;
    }
    public void setInterpString(String interpString){
        this.interpString = interpString;
    }


    private Double yLine;
    public Double getYLine(){
        return yLine;
    }
    public void setYLine(Double yLine){
        this.yLine = yLine;
    }

    private Double zLine;
    public Double getZLine(){
        return zLine;
    }
    public void setZLine(Double zLine){
        this.zLine = zLine;
    }

}




/*
 * Example data:
 * <28092002.020,A6,2,SL-3,28SEP2020,14:10:21,Negative,-1547,6182,181,79,5,30,0,1900,3348,0,,55.7,76.07,74.32,73.87,73.35,72.23,72.36,72.76,72.97,72.93,71.56,71.58,72.90,71.78,71.07,70.76,70.80,72.21,72.93,72.10,72.16,71.23,71.15,72.23,71.52,70.22,69.87,71.25,70.53,70.41,69.28,68.93,68.04,69.83,68.75,68.19,66.70,65.96,64.45,65.24,64.44,61.64,54.28,39.19,22.82,32.80,42.31,50.41,57.31,61.86,63.77,64.16,64.77,62.89,63.57,64.66,63.55,64.73,65.07,64.85,62.95,64.48,62.76,63.21,60.11,59.46,52.97,41.61,42.17,44.05,48.26,56.63,60.58,62.16,63.29,63.51,63.21,65.19,65.18,63.55,64.88,64.84,64.39,62.82,62.63,64.08,61.22,57.48,52.98,50.88,51.08,48.27,43.57,48.72,74.83,90.82,105.33,113.63,117.68,119.41,118.67,119.50,121.41,122.76,125.94,124.94,125.50,125.85,129.48,129.73,129.13,131.41,134.76,137.57,136.37,138.33,138.82,138.84,146.50,158.10,185.48,199.62,215.17,231.55,241.71,232.00,234.09,237.75,249.27,475,666,985,423,67,,0,0
 * */

/*
International EZ Reader Data Transmissions
V3.10 and greater
Comma Separated Data
For Ethernet start character ‘<’
TestOutputName (12 bytes),
StructVersion (2 bytes),
ChannelNumber (maximum 2 bytes),
Assay (maximum of 19 bytes),
Date (9 bytes),
Time (8 bytes),
Result (maximum of 9 bytes),
Answer (maximum of 10 bytes),
UnitSN (maximum of 15 bytes),
LotNumber (maximum of 20 bytes),
SampleID (maximum of 20 bytes),
OperatorID (maximum of 23 bytes),
Mode (maximum 2 bytes),
FailCode (maximum 2 bytes),
controlLINE (maximum of 15 bytes),
testLINE (maximum of 15 bytes),
XLine (maximum of 15 bytes),
Interpretation (1 byte),
TestTemperature (maximum of 5 bytes),
Remmisions[128]); (128 Doubleing point values with each one being a maximum of 6 bytes[3.2 format]
followed by a comma),
TipRed (maximum of 4 bytes),
TipGreen (maximum of 4 bytes),
TipBlue (maximum of 4 bytes),
Dirt (maximum of 15 bytes),
Flow (maximum of 15 bytes),
InterpString(maximum of 15 bytes),
YLine (maximum of 15 bytes),
ZLine (maximum of 15 bytes)
Carriage return\Line Feed
For Ethernet end character ‘>’
USB Transmissions do not use the ‘<’ and ‘>’.
Note: V3.04 does not include : InterpString, YLine and ZLine
For version 3.10 and greater : Please ignore the Interpretation field. It remains in the
transmission field to maintain compatibility. The actual interpretations are now contained in the
InterpString field.
*
* */
