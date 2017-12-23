package PinPinTest.Tools;


public class PublicVar {
    public  boolean getFlag() {
        return testCasefinsehed;
    }

    public void setFlag(boolean testCasefinsehed) {
        PublicVar.testCasefinsehed = testCasefinsehed;
    }

    private static boolean testCasefinsehed=true;

    public int getStringLength() {
        return stringLength;
    }

    public void setStringLength(int stringLength) {
        this.stringLength = stringLength;
    }

    int stringLength=0;
}
