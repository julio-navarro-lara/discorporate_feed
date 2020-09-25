/*
 * Discorporate.java
 *
 * Created on 18-may-2010, 15:18:02
 */

package feed;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.*;


/**
 * Lector de entradas del blog Discorporate (http://discorporating.blogspot.com)
 * @author Julio Navarro Lara
 */
public class Discorporate extends javax.swing.JFrame {

    //Objeto que permite abrir los links en el navegador predeterminado
    LinkListener links = new LinkListener(this);

    String entradaActual;
    String entradaAntigua;
    String entradaNueva;

    public Discorporate() {
        initComponents();

        areaTexto.setEditable(false);
        //Fijamos el tipo de texto a representar como HTML
        areaTexto.setContentType("text/html");
        areaTexto.addHyperlinkListener(links);

        //Permitimos que el panel pueda desplazarse con la rueda del ratón
        scrollPane.setWheelScrollingEnabled(true);

        //Abrimos la página principal del blog y buscamos en el código fuente la primera entrada
        buscarPrimeraEntrada("http://discorporating.blogspot.com");
        //La mostramos
        leerEntrada(entradaActual);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        anterior = new javax.swing.JButton();
        siguiente = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        areaTexto = new javax.swing.JEditorPane();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Discorporate (http://discorporating.blogspot.com)");
        setLocationByPlatform(true);
        setResizable(false);

        anterior.setIcon(new javax.swing.ImageIcon("E:\\programming\\TA\\DiscorporateFeed\\src\\feed\\anterior.gif")); // NOI18N
        anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                anteriorActionPerformed(evt);
            }
        });

        siguiente.setIcon(new javax.swing.ImageIcon("E:\\programming\\TA\\DiscorporateFeed\\src\\feed\\siguiente.gif")); // NOI18N
        siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siguienteActionPerformed(evt);
            }
        });

        areaTexto.setBackground(new java.awt.Color(0, 0, 0));
        areaTexto.setEditable(false);
        areaTexto.setAutoscrolls(false);
        scrollPane.setViewportView(areaTexto);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(102, 0, 0));
        jLabel1.setText("DISCORPORATE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(anterior)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 92, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addGap(77, 77, 77)
                        .addComponent(siguiente)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(anterior)
                        .addComponent(siguiente))
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Detecta la pulsación del botón para leer la entrada anterior
     * @param evt
     */
    private void anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anteriorActionPerformed
        //Actualizamos la entrada actual
        entradaActual = entradaAntigua;
        leerEntrada(entradaActual);

    }//GEN-LAST:event_anteriorActionPerformed

    /**
     * Detecta la pulsación del botón para leer la entrada siguiente
     * @param evt
     */
    private void siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siguienteActionPerformed
        //Actualizamos la entrada actual
        entradaActual = entradaNueva;
        leerEntrada(entradaActual);
    }//GEN-LAST:event_siguienteActionPerformed

    /**
     * Método que lee la entrada contenida en la URL dir
     * @param dir URL que contiene la entrada
     */
    public void leerEntrada(String dir){
        URL url = null;
        String texto = "";

        //Creamos un nuevo objeto URL
        try {
            url = new URL(dir);
        } catch (MalformedURLException ex) {

        }

        try {
            //Creamos el flujo de datos para leer el código fuente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(url.openStream()));

            String linea;
            char[] lineaC;
            texto = "";
            //Leemos hasta el final del documento
            while((linea=entrada.readLine())!=null){
                //Si encontramos una línea determinada, paramos y comenzamos a identificar las partes de la entrada
                if(linea.equals("<!-- google_ad_section_start(name=default) -->")){
                    int i = 0;
                    int contador = 0;

                    //Nos saltamos varias líneas
                    entrada.readLine();
                    entrada.readLine();
                    entrada.readLine();

                    //Comenzamos leyendo la línea de la fecha
                    linea = "";
                    lineaC = entrada.readLine().toCharArray();

                    //Como no nos gusta el formato que se da a la fecha para representarlo en el programa,
                    //nos saltamos las etiquetas y buscamos directamente la información de la fecha mediante dos bucles.
                    do{
                        if(lineaC[i]=='>'){
                            contador++;
                        }
                        i++;
                    }while(contador<2);
                    
                    while(lineaC[i]!='<'){
                        linea += lineaC[i];
                        i++;
                    }
                    //Representamos correctamente acentos y otros caracteres especiales
                    linea = formatearHTML(linea);
                    //Ponemos todo en mayúsculas
                    linea = linea.toUpperCase();

                    //Le añadimos las etiquetas que queremos
                    texto += "<FONT SIZE=\"+1\" COLOR=\"white\" FACE=\"Verdana\">"+linea+"</FONT>";

                    //Nos saltamos varias líneas más
                    for(i=0; i<7; i++){
                        entrada.readLine();
                    }

                    //Representamos el título de la entrada ya localizado
                    texto += "<h2><FONT SIZE=\"+1\" COLOR=\"white\" FACE=\"Verdana\"><BOLD>"+formatearHTML(entrada.readLine())+
                            "</BOLD></FONT></h2>";

                    //Saltamos líneas
                    entrada.readLine();
                    entrada.readLine();
                    entrada.readLine();

                    //Leemos el cuerpo de la entrada, que se encontrará en una única línea
                    linea = entrada.readLine();
                    linea = formatearHTML(linea);

                    //Ponemos el texto de color blanco
                    linea = linea.replaceAll("<span style=\"", "<span style=\"color: white;");
                    linea = linea.replaceAll("<div style=\"", "<div style=\"color:white;");
                    linea = linea.replaceAll("<div  style=\"", "<div style=\"color:white;");
                    linea = linea.replaceAll("<blockquote style=\"","<blockquote style=\"color:white;");
                    linea = linea.replaceAll("<div face=\"verdana\" style=\"", "<div face=\"verdana\" style=\"color:white;");

                    //Centramos las posibles imágenes
                    linea = linea.replaceAll("<img", "<p align=\"center\"><img");

                    //Para introducir la etiquete de cierre de párrafo hay que localizar de forma más
                    //complicada el final de la imagen.
                    StringBuffer linea2 = new StringBuffer(linea);

                    int indice = 0;
                    while((indice = linea2.indexOf("<img", indice))!=-1){
                        indice = linea2.indexOf(">", indice);
                        linea2.insert(indice+1, "</p>");
                    }
                    linea = linea2.toString();
                    
                    texto += linea;
                    
                    StringTokenizer sToken;

                    //Seguimos leyendo para encontrar los enlaces a la entrada anterior y la siguiente,
                    //cuyo formato es fijo en el código fuente.
                    //En caso de no existir una u otra, deshabilitamos el botón correspondiente
                    siguiente.setEnabled(false);
                    anterior.setEnabled(false);
                    while(!(linea = entrada.readLine()).equals("<a class='home-link' href='http://discorporating.blogspot.com/'>Página principal</a>")){
                        if(linea.equals("<span id='blog-pager-newer-link'>")){
                            linea = entrada.readLine();
                            sToken = new StringTokenizer(linea, "'");
                            for(i=0; i<3; i++){
                                sToken.nextToken();
                            }
                            entradaNueva = sToken.nextToken();
                            siguiente.setEnabled(true);
                        }
                        if(linea.equals("<span id='blog-pager-older-link'>")){
                            linea = entrada.readLine();
                            sToken = new StringTokenizer(linea, "'");
                            for(i=0; i<3; i++){
                                sToken.nextToken();
                            }
                            entradaAntigua = sToken.nextToken();
                            anterior.setEnabled(true);
                            break;
                        }
                    }

                    //Cerramos el flujo
                    entrada.close();
                    
                    break;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la conexión",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        //Mostramos el texto
        areaTexto.setText(texto);

        //Intentamos retornar las barras de scroll a su posición inicial
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMinimum());
        horizontalScrollBar.setValue(horizontalScrollBar.getMinimum());
    }

    /**
     * Busca la primera entrada de una página determinada (la principal del blog, generalmente)
     * @param dir Dirección web donde buscar
     */
    public void buscarPrimeraEntrada(String dir){
        URL url = null;

        try {
            url = new URL(dir);
        } catch (MalformedURLException ex) {

        }

        //Simplemente se busca el enlace en el código fuente a la primera entrada representada
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(url.openStream()));

            String linea;
            StringTokenizer sToken;
            linea = "";
            while((linea=entrada.readLine())!=null){
                if(linea.equals("<!-- google_ad_section_start(name=default) -->")){
                    int i = 0;
                    int contador = 0;

                    for(i=0; i<11; i++){
                        entrada.readLine();
                    }

                    linea = entrada.readLine();

                    sToken = new StringTokenizer(linea, "'");
                    sToken.nextToken();
                    entradaActual = sToken.nextToken();

                    entrada.close();

                    break;
                }
            }


        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error en la conexión",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Método para corregir los errores de formato en la lectura del código
     * @param linea Línea de la cual queremos corregir los errores
     * @return Línea corregida
     */
    public String formatearHTML(String linea){
        try {
            linea = new String(linea.getBytes(), "ISO-8859-1");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Discorporate.class.getName()).log(Level.SEVERE, null, ex);
        }

        linea = linea.replaceAll("Ã¡", "á");
        linea = linea.replaceAll("Ã©", "é");
        linea = linea.replaceAll("Ã­", "í");
        linea = linea.replaceAll("Ã³", "ó");
        linea = linea.replaceAll("Ãº", "ú");
        linea = linea.replaceAll("Ã±", "ñ");
        linea = linea.replaceAll("Ã", "Á");
        linea = linea.replaceAll("Âº", "º");
        linea = linea.replaceAll("Ãª", "ê");

        return linea;
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Discorporate objeto = new Discorporate();
                
                JScrollBar verticalScrollBar = objeto.scrollPane.getVerticalScrollBar();
                JScrollBar horizontalScrollBar = objeto.scrollPane.getHorizontalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());
                horizontalScrollBar.setValue(horizontalScrollBar.getMinimum());

                objeto.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton anterior;
    private javax.swing.JEditorPane areaTexto;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JButton siguiente;
    // End of variables declaration//GEN-END:variables

}


