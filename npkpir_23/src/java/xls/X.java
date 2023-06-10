/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import data.Data;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

/**
 *
 * @author Osito
 */
public class X {
    
    
    public static Object x(Cell cell) {
        Object zwrot = null;
        if (cell!=null) {
            CellType celltype = cell.getCellType();
            switch (celltype) {
                case BLANK:
                    break;
                case NUMERIC:
                    zwrot = cell.getNumericCellValue();
                    break;
                case STRING:
                    zwrot = cell.getStringCellValue();
                    break;
                default:
                    zwrot = cell.getDateCellValue();
                    break;
            }
        }
        return zwrot;
    }

    public static String xS(Cell cell) {
        String zwrot = null;
        if (cell!=null) {
            CellType celltype = cell.getCellType();
            switch (celltype) {
                case BLANK:
                    break;
                case NUMERIC:
                    int liczba = (int) cell.getNumericCellValue();
                    zwrot = String.valueOf(liczba);
                    break;
                case STRING:
                    zwrot = cell.getStringCellValue();
                    break;
                default:
                    Date data = cell.getDateCellValue();
                    zwrot = Data.data_yyyyMMdd(data);
                    break;
            }
        }
        return zwrot;
    }
    
     public static String xDG(Cell cell) {
        String zwrot = null;
        if (cell!=null) {
            CellType celltype = cell.getCellType();
            switch (celltype) {
                case BLANK:
                    break;
                case STRING:
                    zwrot = cell.getStringCellValue();
                    break;
                default:
                    Date data = cell.getDateCellValue();
                    zwrot = Data.data_yyyyMMdd(data);
                    break;
            }
        }
        return zwrot;
    }
}
