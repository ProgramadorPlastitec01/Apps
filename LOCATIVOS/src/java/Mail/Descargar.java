package Mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Descargar
  extends HttpServlet
{
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    response.setContentType("text/html;charset=UTF-8");
    try
    {
      String ruta = request.getParameter("ruta_proyecto").toString();
      String nombre_archivo = request.getParameter("file_name").toString();
      String archivo = "\\\\172.16.2.122\\d\\Sistemas de informacion\\Locativos\\Evidencias\\" + ruta + nombre_archivo;
      File f = new File(archivo);
      response.setContentType("application/pdf");
      response.setHeader("Content-Disposition", "attachment; filename=\"" + nombre_archivo + "\"");
      InputStream in = new FileInputStream(f);
      ServletOutputStream outs = response.getOutputStream();
      int bit = 3072;
      try
      {
        while (bit >= 0)
        {
          bit = in.read();
          outs.write(bit);
        }
      }
      catch (IOException ioe)
      {
        ioe.printStackTrace(System.out);
      }
      outs.flush();
      outs.close();
      in.close();
    }
    finally {}
  }
  
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }
  
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException
  {
    processRequest(request, response);
  }
  
  public String getServletInfo()
  {
    return "Short description";
  }
}
