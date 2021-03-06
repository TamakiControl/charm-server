package com.tamakicontrol;

import com.inductiveautomation.ignition.common.sqltags.model.types.DataType;
import com.tamakicontrol.utils.PublishTag;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

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
Remmisions[128]); (128 Double point values with each one being a maximum of 6 bytes[3.2 format]
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


/**
 * Charm Test Result
 *
 * Per CHARM documentation this is the expected object to be returned from a CHARM reader.
 *
 * */
public class CharmTestResult {

    @PublishTag(name="TestOutputName", dataType=DataType.String)
    private String testOutputName;
    // getTestOutputName() can return value stored in testOutputName variable
    public String getTestOutputName(){
        return testOutputName;
    }
    // setTestOutputName(String testOutputName) can assign a value to testOutputName
    public void setTestOutputName(String testOutputName){
        this.testOutputName = testOutputName;
    }

    @PublishTag(name="StructVersion", dataType=DataType.String)
    private String structVersion;
    public String getStructVersion(){
        return structVersion;
    }
    public void setStructVersion(String structVersion){
        this.structVersion = structVersion;
    }

    @PublishTag(name="ChannelNumber", dataType=DataType.String)
    private String channelNumber;
    public String getChannelNumber(){
        return channelNumber;
    }
    public void setChannelNumber(String channelNumber){
        this.channelNumber = channelNumber;
    }

    @PublishTag(name="Assay", dataType=DataType.String)
    private String assay;
    public String getAssay(){
        return assay;
    }
    public void setAssay(String assay){
        this.assay = assay;
    }

    @PublishTag(name="Date", dataType=DataType.DateTime)
    private Date date;
    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }


    @PublishTag(name="Result", dataType=DataType.String)
    private String result;
    public String getResult(){
        return result;
    }
    public void setResult(String result){
        this.result = result;
    }


    @PublishTag(name="Answer", dataType= DataType.String)
    private String answer;
    public String getAnswer(){
        return answer;
    }
    public void setAnswer(String answer){
        this.answer = answer;
    }

    @PublishTag(name="SerialNo", dataType=DataType.String)
    private String unitSN;
    public String getUnitSN(){
        return unitSN;
    }
    public void setUnitSN(String unitSN){
        this.unitSN = unitSN;
    }

    @PublishTag(name="LotNumber", dataType=DataType.String)
    private String lotNumber;
    public String getLotNumber(){
        return lotNumber;
    }
    public void setLotNumber(String lotNumber){
        this.lotNumber = lotNumber;
    }

    @PublishTag(name="SampleID", dataType=DataType.String)
    private String sampleID;
    public String getSampleID(){
        return sampleID;
    }
    public void setSampleID(String sampleID){
        this.sampleID = sampleID;
    }

    @PublishTag(name="OperatorID", dataType=DataType.String)
    private String operatorID;
    public String getOperatorID(){
        return operatorID;
    }
    public void setOperatorID(String operatorID){
        this.operatorID = operatorID;
    }

    @PublishTag(name="Mode", dataType=DataType.String)
    private String mode;
    public String getMode(){
        return mode;
    }
    public void setMode(String mode){
        this.mode = mode;
    }

    @PublishTag(name="FailCode", dataType=DataType.String)
    private String failCode;
    public String getFailCode(){
        return failCode;
    }
    public void setFailCode(String failCode){
        this.failCode = failCode;
    }

    @PublishTag(name="ControlLINE", dataType=DataType.String)
    private String controlLINE;
    public String getControlLINE(){
        return controlLINE;
    }
    public void setControlLINE(String controlLINE){
        this.controlLINE = controlLINE;
    }

    @PublishTag(name="TestLINE", dataType=DataType.String)
    private String testLINE;
    public String getTestLINE(){
        return testLINE;
    }
    public void setTestLINE(String testLINE){
        this.testLINE = testLINE;
    }

    @PublishTag(name="XLine", dataType= DataType.String)
    private String XLine;
    public String getXLine(){
        return XLine;
    }
    public void setXLine(String XLine){
        this.XLine = XLine;
    }

    @PublishTag(name="Interpretation", dataType= DataType.String)
    private String interpretation;
    public String getInterpretation(){
        return interpretation;
    }
    public void setInterpretation(String interpretation){
        this.interpretation = interpretation;
    }

    @PublishTag(name="TestTemperature", dataType= DataType.String)
    private String testTemperature;
    public String getTestTemperature(){
        return testTemperature;
    }
    public void setTestTemperature(String testTemperature){
        this.testTemperature = testTemperature;
    }

    @PublishTag(name="Remissions", dataType= DataType.StringArray)
    private String[] remissions = new String[128];
    public String[] getRemissions(){
        return remissions;
    }
    public void setRemissions(String[] remissions){
        this.remissions = remissions;
    }

    @PublishTag(name="TipRed", dataType= DataType.String)
    private String tipRed;
    public String getTipRed(){
        return tipRed;
    }
    public void setTipRed(String tipRed){
        this.tipRed = tipRed;
    }

    @PublishTag(name="TipGreen", dataType= DataType.String)
    private String tipGreen;
    public String getTipGreen(){
        return tipGreen;
    }
    public void setTipGreen(String tipGreen){
        this.tipGreen = tipGreen;
    }

    @PublishTag(name="TipBlue", dataType= DataType.String)
    private String tipBlue;
    public String getTipBlue(){
        return tipBlue;
    }
    public void setTipBlue(String tipBlue){
        this.tipBlue = tipBlue;
    }

    @PublishTag(name="Dirt", dataType= DataType.String)
    private String dirt;
    public String getDirt(){
        return dirt;
    }
    public void setDirt(String dirt){
        this.dirt = dirt;
    }

    @PublishTag(name="Flow", dataType= DataType.String)
    private String flow;
    public String getFlow(){
        return flow;
    }
    public void setFlow(String flow){
        this.flow = flow;
    }

    @PublishTag(name="InterpString", dataType= DataType.String)
    private String interpString;
    public String getInterpString(){
        return interpString;
    }
    public void setInterpString(String interpString){
        this.interpString = interpString;
    }

    @PublishTag(name="YLine", dataType= DataType.String)
    private String y_Line;
    public String getY_Line() {
        return y_Line;
    }
    public void setY_Line(String y_Line) {
        this.y_Line = y_Line;
    }

    @PublishTag(name="ZLine", dataType= DataType.String)
    private String z_Line;
    public String getZ_Line() {
        return z_Line;
    }
    public void setZ_Line(String z_Line) {
        this.z_Line = z_Line;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CharmTestResult that = (CharmTestResult) o;

        if (!channelNumber.equals(that.channelNumber)) return false;
        if (!unitSN.equals(that.unitSN)) return false;
        if (!lotNumber.equals(that.lotNumber)) return false;
        if (!sampleID.equals(that.sampleID)) return false;
        if (!operatorID.equals(that.operatorID)) return false;
        if (!mode.equals(that.mode)) return false;
        if (!failCode.equals(that.failCode)) return false;
        if (!controlLINE.equals(that.controlLINE)) return false;
        if (!testLINE.equals(that.testLINE)) return false;
        if (!Objects.equals(testOutputName, that.testOutputName))
            return false;
        if (!Objects.equals(structVersion, that.structVersion))
            return false;
        if (!Objects.equals(assay, that.assay)) return false;
        if (!Objects.equals(date, that.date)) return false;
        if (!Objects.equals(result, that.result)) return false;
        if (!Objects.equals(answer, that.answer)) return false;
        if (!Objects.equals(XLine, that.XLine)) return false;
        if (!Objects.equals(interpretation, that.interpretation))
            return false;
        if (!Objects.equals(testTemperature, that.testTemperature))
            return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(remissions, that.remissions)) return false;
        if (!Objects.equals(tipRed, that.tipRed)) return false;
        if (!Objects.equals(tipGreen, that.tipGreen)) return false;
        if (!Objects.equals(tipBlue, that.tipBlue)) return false;
        if (!Objects.equals(dirt, that.dirt)) return false;
        if (!Objects.equals(flow, that.flow)) return false;
        if (!Objects.equals(interpString, that.interpString)) return false;
        if (!Objects.equals(y_Line, that.y_Line)) return false;
        if (!Objects.equals(z_Line, that.z_Line)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = testOutputName != null ? testOutputName.hashCode() : 0;
        result1 = 31 * result1 + (structVersion != null ? structVersion.hashCode() : 0);
        result1 = 31 * result1 + (channelNumber != null ? channelNumber.hashCode() : 0);
        result1 = 31 * result1 + (assay != null ? assay.hashCode() : 0);
        result1 = 31 * result1 + (date != null ? date.hashCode() : 0);
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        result1 = 31 * result1 + (answer != null ? answer.hashCode() : 0);
        result1 = 31 * result1 + (unitSN != null ? unitSN.hashCode() : 0);
        result1 = 31 * result1 + (lotNumber != null ? lotNumber.hashCode() : 0);
        result1 = 31 * result1 + (sampleID != null ? sampleID.hashCode() : 0);
        result1 = 31 * result1 + (operatorID != null ? operatorID.hashCode() : 0);
        result1 = 31 * result1 + (mode != null ? mode.hashCode() : 0);
        result1 = 31 * result1 + (failCode != null ? failCode.hashCode() : 0);
        result1 = 31 * result1 + (controlLINE != null ? controlLINE.hashCode() : 0);
        result1 = 31 * result1 + (testLINE != null ? testLINE.hashCode() : 0);
        result1 = 31 * result1 + (XLine != null ? XLine.hashCode() : 0);
        result1 = 31 * result1 + (interpretation != null ? interpretation.hashCode() : 0);
        result1 = 31 * result1 + (testTemperature != null ? testTemperature.hashCode() : 0);
        result1 = 31 * result1 + Arrays.hashCode(remissions);
        result1 = 31 * result1 + (tipRed != null ? tipRed.hashCode() : 0);
        result1 = 31 * result1 + (tipGreen != null ? tipGreen.hashCode() : 0);
        result1 = 31 * result1 + (tipBlue != null ? tipBlue.hashCode() : 0);
        result1 = 31 * result1 + (dirt != null ? dirt.hashCode() : 0);
        result1 = 31 * result1 + (flow != null ? flow.hashCode() : 0);
        result1 = 31 * result1 + (interpString != null ? interpString.hashCode() : 0);
        result1 = 31 * result1 + (y_Line != null ? y_Line.hashCode() : 0);
        result1 = 31 * result1 + (z_Line != null ? z_Line.hashCode() : 0);
        return result1;
    }
}