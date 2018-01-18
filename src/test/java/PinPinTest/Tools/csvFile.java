package PinPinTest.Tools;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class csvFile {


    public static void main(String[] args) throws IOException {
        String relativelyPath=System.getProperty("user.dir");
        System.out.println(relativelyPath);
        File file = new File(relativelyPath+"/src/test/resources/parameter.xlsx");
        FileInputStream fileInputStream = null;
        {
            try {
                fileInputStream = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Workbook workbook = new XSSFWorkbook(fileInputStream);
        int nmberOfSheets = workbook.getNumberOfSheets();
        Sheet firstSheet=workbook.getSheetAt(0);

       // for (int i = 0; i < nmberOfSheets; i++) {
       //     Sheet firstSheet = workbook.getSheetAt(i);
            String sheetName= firstSheet.getSheetName();
            System.out.println(sheetName);
            Iterator<Row> iterator = firstSheet.rowIterator();
            List<String> para1= new ArrayList<String>();
            int j=0;
            while (iterator.hasNext()) {

                Row nextRow = iterator.next();
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                System.out.print("row:"+j+"-----");
                j++;
                int i=0;
//                while (cellIterator.hasNext()) {
                    Cell nextCell = cellIterator.next();
//                    int columnIndex = nextCell.getColumnIndex();
//                    System.out.printf("%-2d:%-15s",columnIndex,nextCell);
                    para1.add(nextCell.toString());
//                }System.out.println();
            }
System.out.println(para1);
    }
    //    public static void main(String[] args) throws IOException {
//        File inFile = new File("src/test/resources/parameter.csv");
//
//        String inString = "";
//        String tmpString = "";
//
//        BufferedReader br = new BufferedReader(new FileReader(inFile));
//
//        while (br.ready()) {
//            String line = br.readLine();
//            StringTokenizer st = new StringTokenizer(line, ",");
//            while (st.hasMoreTokens()) {
//                System.out.print(st.nextToken() + "\t");
//            }
//            System.out.println();
//        }
//
//           br.close();
//
//        }
}

