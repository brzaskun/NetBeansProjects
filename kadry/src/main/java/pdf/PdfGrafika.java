/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfSpotColor;
import javax.inject.Named;


/**
 *
 * @author Osito
 */
@Named

public class PdfGrafika {
    public static final String RESULT = "c:/graphics_state.pdf";
    public static void prost(PdfContentByte cb, int x, int y, int x1, int y1) {
        cb.saveState();
        PdfSpotColor color = new PdfSpotColor(RESULT, BaseColor.BLACK);
        cb.setLineWidth((float) 0.5);
        cb.setColorStroke(color, (float) 0.5);
        cb.setFlatness(y1);
        cb.rectangle(x, y, x1, y1);
        cb.stroke();
        cb.restoreState();
    }
}
