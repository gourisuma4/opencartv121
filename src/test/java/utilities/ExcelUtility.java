package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility 
{
      public static  FileInputStream fi;
      public static  FileOutputStream fo;
      public static  XSSFWorkbook workbook;
      public static XSSFSheet sheet;
      public static XSSFRow row;
      public static XSSFCell cell;
      public static CellStyle style;
      String path;
      
      public ExcelUtility(String path)
      {
    	  this.path=path;
      }
      
      public int getRowCount(String sheetname) throws IOException
      {
    	  fi=new FileInputStream(path);
    	  workbook=new XSSFWorkbook(fi);
    	  sheet=workbook.getSheet(sheetname);
    	  int rowcount=sheet.getLastRowNum();
    	  workbook.close();
    	  fi.close();
    	  return rowcount;
      }
      public int getCellCount(String sheetname,int rownum) throws IOException
      {
    	  fi=new FileInputStream(path);
    	  workbook=new XSSFWorkbook(fi);
    	  sheet=workbook.getSheet(sheetname); 
    	  row=sheet.getRow(rownum);
    	  int cellcount=row.getLastCellNum();
    	  workbook.close();
    	  fi.close();
    	  return cellcount;
      }
      public  String getCellData(String sheetname,int rownum,int colnum) throws IOException
      { 
    	  fi=new FileInputStream(path);
    	  workbook=new XSSFWorkbook(fi);
    	  sheet=workbook.getSheet(sheetname); 
    	  row=sheet.getRow(rownum);
    	  cell=row.getCell(colnum);
    	  
    	  DataFormatter formatter=new DataFormatter();
    	  String data;
    	  try
    	  { 
    		  //data=cell.toString();
    		  
    		  data=formatter.formatCellValue(cell); //Returns the formatted cell value of a cell as string regardless of the cell type
    	  }
    	  catch(Exception e)
    	  {
    		  data="";
    	  }
    	  
    	  workbook.close();
    	  fi.close();
    	  return data;
     
     }
      
      public void SetCellData(String sheetname,int rownum,int colnum,String data) throws IOException
      {
    	  File xlfile=new File(path);
    	  if(!xlfile.exists())        //if file not exist create a new file
    	  {
    		  workbook=new XSSFWorkbook();
    		  fo=new FileOutputStream(path);
    		  workbook.write(fo);
    	  }
    	  fi=new FileInputStream(path);
    	  workbook=new XSSFWorkbook(fi);
    	  
    	  if(workbook.getSheetIndex(sheetname)==-1) //if sheet not exists then create a new sheet
    	  workbook.createSheet(sheetname);
   		  sheet=workbook.getSheet(sheetname); 
    	 
   		  if(sheet.getRow(rownum)==null)       //if row not exists then create a new row
   		  sheet.getRow(rownum);
   		  row=sheet.getRow(rownum);
    	  cell=row.getCell(colnum);
    	  cell.setCellValue(data);
    	  fo=new FileOutputStream(path);
    	  workbook.write(fo);
    	  workbook.close();
    	  fi.close();
    	  fo.close();
      }
      public void FillGreenColor(String sheetname,int rownum,int colnum) throws IOException
      {
    	  fi=new FileInputStream(path);
    	  workbook=new XSSFWorkbook(fi);
    	  sheet=workbook.getSheet(sheetname); 
    	  row=sheet.getRow(rownum);
    	  cell=row.getCell(colnum);
    	  style=workbook.createCellStyle();
    	  style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
    	  style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    	  cell.setCellStyle(style);
    	  fo=new FileOutputStream(path);
    	  workbook.write(fo);
    	  workbook.close();
    	  fi.close();
    	  fo.close();
    	  
      }
      public  void FillREDColor(String xlsheet,int rownum,int colnum) throws IOException
      {
    	  fi=new FileInputStream(path);
    	  workbook=new XSSFWorkbook(fi);
    	  sheet=workbook.getSheet(xlsheet); 
    	  row=sheet.getRow(rownum);
    	  cell=row.getCell(colnum);
    	  style=workbook.createCellStyle();
    	  style.setFillForegroundColor(IndexedColors.RED.getIndex());
    	  style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    	  cell.setCellStyle(style);
    	  fo=new FileOutputStream(path);
    	  workbook.write(fo);
    	  workbook.close();
    	  fi.close();
    	  fo.close();
    	  
      }
      
}
