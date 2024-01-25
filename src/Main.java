
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.io.StringReader;
import java.io.StringWriter;

public class Main {
    private static final String USER_AGENT = "Mozilla/5.0";

    public static void main(String[] args) {

        try {
            System.out.println("Obteniendo info de congresistas camara baja...");
            // imprime string de xml con diputados
            System.out.println(getDiputados());
            System.out.println("Obtención de data camara ok");
        }
        catch(Exception e) {
            System.out.println("Error inesperado: "+  e);
        }
        finally {
            System.out.println("Finally: adiooooos");
        }
    }

    /*
    Funcion para obtener listado de diputados en "XML" pero String jeje
    Se obtiene desde https://opendata.camara.cl/camaradiputados/WServices/WSDiputado.asmx/retornarDiputados?
     */
    private static String getDiputados() {
        try {
            // Declara la URL donde ir a buscar la data de los vagos y vagas
            String url_diputados = "https://opendata.camara.cl/camaradiputados/WServices/WSDiputado.asmx/retornarDiputados?";

            // Retorna lo que retorne el metodo sendGET
            return sendGET(url_diputados).toString();


        }
        catch(IOException e) {
            System.out.println(e);
            return null;
        }
    }

    /*
     * Este metodo sirve para hacer una llamada HTTP GET
     * Returns: responde el contenido de la llamada HTTP GET
     * */
    private static StringBuffer sendGET(String url) throws IOException {
        // Instancia un objeto para la URL a la que se va generar la *conexion*
        URL url_get = new URL(url);

        // Crear HttpURLConnection para generar la conexión
        HttpURLConnection con = (HttpURLConnection)url_get.openConnection();

        // Establecer metodo del request HTTP a GET (obtener datos)
        con.setRequestMethod("GET");

        // TO-DO: Revisar para que genere el XML!!
        // con.setRequestProperty("Content-Type", "application/xml");

        // Obtiene codigo de *respuesta* HTTP  de la conexion
        // https://developer.mozilla.org/en-US/docs/Web/HTTP/Status
        int response_code = con.getResponseCode();

        // Si la respuesta de la conexion es "Correcta":
        if (response_code == HttpURLConnection.HTTP_OK) {
            // StringBuffer sirve para cargar el *contenido* de la respuesta
            // Se usa el StringBuffer para manejar correctamente el *flujo* de data entrante
            // https://docs.oracle.com/javase/8/docs/api/java/io/BufferedReader.html
            BufferedReader input_reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String input_line;
            // Declarar StringBuffer para slmacenar respuesta ("contenido)
            StringBuffer response = new StringBuffer();

            while ((input_line = input_reader.readLine()) != null) {
                response.append(input_line);
            }

            // Cerrar el BufferReader
            // No cerrarlo implica dejar el objeto abierto y que no sea reciclado por el Garbage Collector
            input_reader.close();

            // return el contenido de la respuesta de la conexion
            return response;


        } else {
            System.out.println("Respuesta no OK...");
            return null;
        }

    }

}