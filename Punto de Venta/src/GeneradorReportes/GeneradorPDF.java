package GeneradorReportes;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GeneradorPDF {
    //BeanCotizaciones cotizacion;
    private Font fuenteBold = new Font(Font.FontFamily.COURIER,10,Font.BOLD);
    private Font fuenteNormal = new Font(Font.FontFamily.COURIER,10,Font.NORMAL);
    private Font fuenteItalica = new Font(Font.FontFamily.COURIER,10,Font.ITALIC);
    PdfPTable tabla = new PdfPTable(8);
    
    public GeneradorPDF(){
        
    }
   
    public void crearTicket(String enc ,String pac,String pro,String bajo1,
            String bajo2,String ruta){
        try {
            Document document= new Document(PageSize.A4,36,36,10,10);
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();
            document.add(getTitulo(enc));
            document.add(getInfo(pac));
            document.add(getInfo(pro));
            document.add(getFooter(bajo1));
            document.add(getTitulo(bajo2));
            document.close();
        } catch (Exception e) {
        }
    }
    
    public void crearReporte(String fecha,String tabla,String ruta,String total){
        try {
            Document document= new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();
            document.add(getTitulo(fecha));
            document.add(getInfo(tabla));
            document.add((getFooter(total)));
            document.close();
        } catch (Exception e) {
        }
    }
    private Paragraph getTitulo(String texto){
        Paragraph p= new Paragraph();
        Chunk c= new Chunk();
        p.setAlignment(Element.ALIGN_CENTER);
        c.append(texto);
        c.setFont(fuenteBold);
        p.add(c);
        return p;
    }
    private Paragraph getInfo(String texto){
        Paragraph p= new Paragraph();
        Chunk c= new Chunk();
        p.setAlignment(Element.ALIGN_LEFT);
        c.append(texto);
        c.setFont(fuenteNormal);
        p.add(c);
        return p;
    }
    private Paragraph getFooter(String texto){
        Paragraph p= new Paragraph();
        Chunk c= new Chunk();
        p.setAlignment(Element.ALIGN_RIGHT);
        c.append(texto);
        c.setFont(fuenteItalica);
        p.add(c);
        return p;
    }
    
   
}