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

        //Permitimos que el panel pueda desplazarse con la rueda del rat�n
        scrollPane.setWheelScrollingEnabled(true);

        //Abrimos la p�gina principal del blog y buscamos en el c�digo fuente la primera entrada
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
     * Detecta la pulsaci�n del bot�n para leer la entrada anterior
     * @param evt
     */
    private void anteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anteriorActionPerformed
        //Actualizamos la entrada actual
        entradaActual = entradaAntigua;
        leerEntrada(entradaActual);

    }//GEN-LAST:event_anteriorActionPerformed

    /**
     * Detecta la pulsaci�n del bot�n para leer la entrada siguiente
     * @param evt
     */
    private void siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siguienteActionPerformed
        //Actualizamos la entrada actual
        entradaActual = entradaNueva;
        leerEntrada(entradaActual);
    }//GEN-LAST:event_siguienteActionPerformed

    /**
     * M�todo que lee la entrada contenida en la URL dir
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
            //Creamos el flujo de datos para leer el c�digo fuente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(url.openStream()));

            String linea;
            char[] lineaC;
            texto = "";
            //Leemos hasta el final del documento
            while((linea=entrada.readLine())!=null){
                //Si encontramos una l�nea determinada, paramos y comenzamos a identificar las partes de la entrada
                if(linea.equals("<!-- google_ad_section_start(name=default) -->")){
                    int i = 0;
                    int contador = 0;

                    //Nos saltamos varias l�neas
                    entrada.readLine();
                    entrada.readLine();
                    entrada.readLine();

                    //Comenzamos leyendo la l�nea de la fecha
                    linea = "";
                    lineaC = entrada.readLine().toCharArray();

                    //Como no nos gusta el formato que se da a la fecha para representarlo en el programa,
                    //nos saltamos las etiquetas y buscamos directamente la informaci�n de la fecha mediante dos bucles.
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
                    //Ponemos todo en may�sculas
                    linea = linea.toUpperCase();

                    //Le a�adimos las etiquetas que queremos
                    texto += "<FONT SIZE=\"+1\" COLOR=\"white\" FACE=\"Verdana\">"+linea+"</FONT>";

                    //Nos saltamos varias l�neas m�s
                    for(i=0; i<7; i++){
                        entrada.readLine();
                    }

                    //Representamos el t�tulo de la entrada ya localizado
                    texto += "<h2><FONT SIZE=\"+1\" COLOR=\"white\" FACE=\"Verdana\"><BOLD>"+formatearHTML(entrada.readLine())+
                            "</BOLD></FONT></h2>";

                    //Saltamos l�neas
                    entrada.readLine();
                    entrada.readLine();
                    entrada.readLine();

                    //Leemos el cuerpo de la entrada, que se encontrar� en una �nica l�nea
                    linea = entrada.readLine();
                    linea = formatearHTML(linea);

                    //Ponemos el texto de color blanco
                    linea = linea.replaceAll("<span style=\"", "<span style=\"color: white;");
                    linea = linea.replaceAll("<div style=\"", "<div style=\"color:white;");
                    linea = linea.replaceAll("<div  style=\"", "<div style=\"color:white;");
                    linea = linea.replaceAll("<blockquote style=\"","<blockquote style=\"color:white;");
                    linea = linea.replaceAll("<div face=\"verdana\" style=\"", "<div face=\"verdana\" style=\"color:white;");

                    //Centramos las posibles im�genes
                    linea = linea.replaceAll("<img", "<p align=\"center\"><img");

                    //Para introducir la etiquete de cierre de p�rrafo hay que localizar de forma m�s
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
                    //cuyo formato es fijo en el c�digo fuente.
                    //En caso de no existir una u otra, deshabilitamos el bot�n correspondiente
                    siguiente.setEnabled(false);
                    anterior.setEnabled(false);
                    while(!(linea = entrada.readLine()).equals("<a class='home-link' href='http://discorporating.blogspot.com/'>P�gina principal</a>")){
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
            JOptionPane.showMessageDialog(this, "Error en la conexi�n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        //Mostramos el texto
        areaTexto.setText(texto);

        //Intentamos retornar las barras de scroll a su posici�n inicial
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        verticalScrollBar.setValue(verticalScrollBar.getMinimum());
        horizontalScrollBar.setValue(horizontalScrollBar.getMinimum());
    }

    /**
     * Busca la primera entrada de una p�gina determinada (la principal del blog, generalmente)
     * @param dir Direcci�n web donde buscar
     */
    public void buscarPrimeraEntrada(String dir){
        URL url = null;

        try {
            url = new URL(dir);
        } catch (MalformedURLException ex) {

        }

        //Simplemente se busca el enlace en el c�digo fuente a la primera entrada representada
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
            JOptionPane.showMessageDialog(this, "Error en la conexi�n",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * M�todo para corregir los errores de formato en la lectura del c�digo
     * @param linea L�nea de la cual queremos corregir los errores
     * @return L�nea corregida
     */
    public String formatearHTML(String linea){
        try {
            linea = new String(linea.getBytes(), "ISO-8859-1");
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Discorporate.class.getName()).log(Level.SEVERE, null, ex);
        }

        linea = linea.replaceAll("á", "�");
        linea = linea.replaceAll("é", "�");
        linea = linea.replaceAll("í", "�");
        linea = linea.replaceAll("ó", "�");
        linea = linea.replaceAll("ú", "�");
        linea = linea.replaceAll("ñ", "�");
        linea = linea.replaceAll("Á", "�");
        linea = linea.replaceAll("º", "�");
        linea = linea.replaceAll("ê", "�");

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


