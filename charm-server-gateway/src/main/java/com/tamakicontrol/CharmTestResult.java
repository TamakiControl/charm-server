package com.tamakicontrol;

import com.inductiveautomation.ignition.common.sqltags.model.types.DataType;
import com.tamakicontrol.utils.PublishTag;

// todo
public class CharmTestResult {

    @PublishTag(name="serialNo", dataType= DataType.Int4)
    private int serialNo;

    public int getSerialNo(){
        return serialNo;
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
Remmisions[128]); (128 floating point values with each one being a maximum of 6 bytes[3.2 format]
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