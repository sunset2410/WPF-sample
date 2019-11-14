/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SocketChatJava;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author admin
 */
public class Server extends javax.swing.JFrame {
    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    private static ServerSocket server;
    private static Socket connection;
    /**
     * Creates new form Server
     */
    public Server() {
       initComponents();
       setVisible(true);
       userText.setEditable(true);
       chatWindow.setEditable(false);
       userText.addActionListener(               
       new  ActionListener(){           
       public void actionPerformed(ActionEvent e){    
       sendMessage(e.getActionCommand());
       userText.setText("");     
           }   
         }                   
       );   
    }

     // send a message to client
    private void sendMessage(String message){
        try {
            output.writeObject("SERVER - "+message);
            output.flush();          
            showMessage("\n SERVER - "+message);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    
    //ham hien thi mesage, udate chatwindow khi co them tin nhan vua gui hoac nhan;
    private void showMessage(final String text){
        SwingUtilities.invokeLater(         
        new Runnable(){
        public void run(){
           chatWindow.append(text);  
              }       
          }                      
        );   
      }
  
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        userText = new javax.swing.JTextField();
        btn_send = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        chatWindow = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        userText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                userTextActionPerformed(evt);
            }
        });

        btn_send.setText("send");
        btn_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sendActionPerformed(evt);
            }
        });

        chatWindow.setColumns(20);
        chatWindow.setRows(5);
        jScrollPane1.setViewportView(chatWindow);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setText("server");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn_send, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel1)))
                .addContainerGap(138, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(userText, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_send, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void userTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_userTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_userTextActionPerformed

    private void btn_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sendActionPerformed
        String message = userText.getText();
        sendMessage(message);
        userText.setText("");
    }//GEN-LAST:event_btn_sendActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws IOException {
         Server sk=new Server();        
         sk.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        server =new ServerSocket(6789,100);  
           
        while(true){   
         try{
        //wait for connection;     
        sk.showMessage("waiting For someone to connect \n");      
        connection  =server.accept();
        sk.showMessage("Now Connected by"+ connection.getInetAddress().getHostName() );
                
        //setupstream;
        output =new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input=new ObjectInputStream(connection.getInputStream());
        sk.showMessage("\n Streams are now setup " );
       
        //while chatting;
        String message =" you are Connected ";
        sk.sendMessage(message);
        do{         
          message=(String)input.readObject();
          sk.showMessage("\n "+message );    
         }while(true); 
        
         }catch(Exception e){
            e.printStackTrace();
        }finally{ 
          sk.showMessage("\n closing connection...");
          output.close();
          input.close();
          connection.close();
           
          }  
     }
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_send;
    private javax.swing.JTextArea chatWindow;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField userText;
    // End of variables declaration//GEN-END:variables
}