
package JClient;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Hashtable;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class JClient extends javax.swing.JFrame {
    private Socket client_sk;
    private DataStream datastream;
    private DataOutputStream dos;
    private DataInputStream dis;
    private String nick,yourname,pass;
    private Chatrieng  form_rieng,form_rieng1;
    protected static JClient client;
    protected  Hashtable<String,Chatrieng> listchatrieng = new Hashtable();
    // ham khoi tao cho doi tuong;
    public JClient() {
        initComponents();
        p2.setVisible(false); //an di panel chat khi chua dang nhap;
        p3.setVisible(false);
        p4.setVisible(false);
        msg_area.setEditable(false);
        online_area.setEditable(false);
        jtf_nick.setEditable(false);
        setVisible(true);
    }

    
    //---------[ Socket ]-----------//	
	private void go() {
		try {
                        String IPServer=JOptionPane.showInputDialog("Nhập Địa Chỉ IP Của Server:");
                        client_sk =new Socket(InetAddress.getByName(IPServer),1991);
			//client_sk = new Socket("192.168.1.106",1991);// tao socket ket noi toi host;                  
                        //client_sk = new Socket("localhost",1991);
                        // set up luong du lieu tu socket;luong nhan va gui du lieu;
			dos=new DataOutputStream(client_sk.getOutputStream());
			dis=new DataInputStream(client_sk.getInputStream());
		
			//client.close();
		   } catch (Exception e) {
			JOptionPane.showMessageDialog(this,"Lỗi kết nối tới Server","Message Dialog",JOptionPane.WARNING_MESSAGE);
			System.exit(0);
		             }
	         }
        
        
         // ghi du lieu va data output sream; thuc chat la gui du lieu vao socket; gui du lieu cho server;
	private void sendMSG(String data){
		try {
			dos.writeUTF(data);
			dos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		        }
	    }
        
         // send ca kieu mesage va noi dung chat;ham nay dua tren ham tren sendMSG;day la ham sendMSG 2 tham so dau vao;
	public void sendMSG(String msg1,String msg2){
		sendMSG(msg1);
		sendMSG(msg2);
	}
        
        
        
         // send ca kieu mesage va noi dung chat;ham nay dua tren ham tren sendMSG;day la ham sendMSG 2 tham so dau vao;
	public void sendMSG(String title,String to,String msg){
		sendMSG(title);
                sendMSG(to);
		sendMSG(msg);
	}
        
        
        // nhan du lieu tu data input stream;thuc chat la lay du lieu tu server;lay du lieu tu socket
	public String getMSG(){
		String data=null;
		try {
			data=dis.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
        // nhan du lieu theo title va body nhan tu server; while chatting;
	public void showMSG(String msg1, String msg2){
		int stt = Integer.parseInt(msg1); // ep kieu msg1 sang kieu integer de so sanh;mesage1 chinh la title cua tin nhan;
		switch (stt) {
		// tin nhắn của những người khác; hien thi then tin nhan cua nhung client vua gui;
		case 4:
			this.msg_area.append(msg2); // stt=3 hien thi len cua so chat noi dung chat vua nhan;
                        this.client.setVisible(true); //lam noi from len;
			break;
		// update danh sách o phan nhung client online
		case 5:
			this.online_area.setText(msg2); //stt=4 hien thi len cua so online chat danh sach online;
                        this.client.setVisible(true);
			break;
                 
		}
	     }
        
        // kiem tra chat rieng;
        public void CheckChatRieng(String msg1){
            int stt = Integer.parseInt(msg1);
            switch (stt){             
                case 11: //hien cu so chat rieng sau khi xac nhan ten hop le;
                    tf_yourname.setText("");// xoa noi dung tf;
                    if(!listchatrieng.containsKey(this.yourname)){
                    form_rieng =new Chatrieng(this.nick,this.yourname);// tao the hien cua doi tuong 
                    form_rieng.setVisible(true);  
                    listchatrieng.put(yourname,form_rieng);//them vao listchatrieng;
                    }
                    break;
                case 12:
                    JOptionPane.showMessageDialog(this,"Nick bạn muốn chát hiện không online!");
                    break;
                  
            }
        }
        
        
        // hien thi tin nhan chat rieng khi nhan duoc;
        public void ShowMessageChatRieng(String title,String from ,String message){
            int stt = Integer.parseInt(title);
           if(stt==7){
                    if(!listchatrieng.containsKey(from)){
                    form_rieng1 =new Chatrieng(this.nick,from);// tao form chat rieng;
                    form_rieng1.setVisible(true); 
                    listchatrieng.put(from,form_rieng1);//them vao listchatrieng;
                    }
                    listchatrieng.get(from).setVisible(true);
                    listchatrieng.get(from).message_area.append(from+":"+message+"\n");// hien thi noi dung tin nhan chat rieng;           
                   }       
               }
        
        
        
       //----------------------------------------------
	private void check_and_Send(String msg){
                String my_nick=jtf_nick.getText();
		if(msg.compareTo("")!=0){ //kiem tra message phai khac rong thi moi gui;
			this.msg_area.append(my_nick + " :" + msg + "\n"); //hien thi noi dung chat len cua so cua ban than va gui di;
			sendMSG("1",msg+"\n"); //gui du lieu cho server theo title la "1";
		}
	}
        
         
        //kiem tra dang nhap;
	private boolean checkLogin(String nick,String pass){
                boolean check=false;
		if((nick.equals(""))||(pass.equals(""))) //kiem tra nick phai khac rong;neu bang rong thi tra ve gia tri fasle;
			check= false;
		else if(nick.compareTo("0")==0){ // neu nick bnag 0 cung tra ve gia tri false nen dang hap loi;
			check= false;
		}
		else{
			sendMSG(nick); //gui ten nick  cho phia server 
                        sendMSG(pass);
                       // sau khi gui cho server de server kiem tra lai xem co trung nick nao khong
                       //server gui lai send("0") neu da trung voi 1 nick nao do;luc nay client tra ve checklogin la false.khong cho dang nhap;
			int sst = Integer.parseInt(getMSG()); //nhan lai thong bao tu server xem co duoc khong;
			if(sst==0) check= false; //tra ve gia tri false khong cho dang nhap vi trung nick;
			if(sst==10) check = true; // cho phep dang nhap vi nick nay da duoc server xac nhan hop le;
                      
		      }
                  return check; 
	       }

        //ham thoat;
	private void exit(){
		try {
			sendMSG("0"); //gui thong bao cho server rang se thoat;
			dos.close();
			dis.close();
			client_sk.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.exit(0); // lenh nay dong tat cac cac form xuat phat tu form chinh(vi du form chat rieng);
	} 
        
        
       
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        p1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jtf_nick1 = new javax.swing.JTextField();
        login_bt = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        tf_pass = new javax.swing.JTextField();
        p2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jtf_nick = new javax.swing.JTextField();
        exit_bt = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        p4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jtf_message = new javax.swing.JTextField();
        send_bt = new javax.swing.JButton();
        clear_bt = new javax.swing.JButton();
        p3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        msg_area = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        online_area = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        btn_chatrieng = new javax.swing.JButton();
        tf_yourname = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nick");

        login_bt.setText("Đăng Nhập");
        login_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                login_btActionPerformed(evt);
            }
        });

        jButton2.setText("Logout");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel5.setText("pass");

        javax.swing.GroupLayout p1Layout = new javax.swing.GroupLayout(p1);
        p1.setLayout(p1Layout);
        p1Layout.setHorizontalGroup(
            p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tf_pass, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                    .addComponent(jtf_nick1))
                .addGap(18, 18, 18)
                .addGroup(p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(login_bt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(106, 106, 106))
        );
        p1Layout.setVerticalGroup(
            p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_nick1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(login_bt))
                .addGap(20, 20, 20)
                .addGroup(p1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tf_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        jLabel2.setText("Nick");

        exit_bt.setText("Thoát");
        exit_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit_btActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout p2Layout = new javax.swing.GroupLayout(p2);
        p2.setLayout(p2Layout);
        p2Layout.setHorizontalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jtf_nick, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(exit_bt))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        p2Layout.setVerticalGroup(
            p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(p2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtf_nick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(exit_bt)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel3.setText("Tin Nhắn");

        send_bt.setText("Send");
        send_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                send_btActionPerformed(evt);
            }
        });

        clear_bt.setText("Xóa");
        clear_bt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clear_btActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout p4Layout = new javax.swing.GroupLayout(p4);
        p4.setLayout(p4Layout);
        p4Layout.setHorizontalGroup(
            p4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(jtf_message, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(send_bt)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clear_bt, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );
        p4Layout.setVerticalGroup(
            p4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, p4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(p4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(send_bt)
                    .addComponent(clear_bt)
                    .addComponent(jtf_message, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );

        msg_area.setBackground(new java.awt.Color(51, 255, 204));
        msg_area.setColumns(20);
        msg_area.setRows(5);
        jScrollPane1.setViewportView(msg_area);

        online_area.setBackground(new java.awt.Color(255, 153, 204));
        online_area.setColumns(20);
        online_area.setLineWrap(true);
        online_area.setRows(5);
        jScrollPane2.setViewportView(online_area);

        jLabel4.setText("Online List");

        btn_chatrieng.setText("Chat Riêng");
        btn_chatrieng.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_chatriengActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout p3Layout = new javax.swing.GroupLayout(p3);
        p3.setLayout(p3Layout);
        p3Layout.setHorizontalGroup(
            p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                .addGroup(p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(p3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(tf_yourname, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(p3Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel4))
                    .addGroup(p3Layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addComponent(btn_chatrieng))
                    .addGroup(p3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18))
        );
        p3Layout.setVerticalGroup(
            p3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(p3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(p3Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tf_yourname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_chatrieng)
                .addGap(19, 19, 19))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(p4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(p3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(p1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(p4, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exit_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_btActionPerformed
        // TODO add your handling code here:
        datastream.stopThread();
	exit();
    }//GEN-LAST:event_exit_btActionPerformed

    private void clear_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clear_btActionPerformed
        // TODO add your handling code here:
        jtf_message.setText("");
    }//GEN-LAST:event_clear_btActionPerformed

    private void send_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_send_btActionPerformed
        // TODO add your handling code here:
        String mesage=jtf_message.getText();
        check_and_Send(mesage);
        jtf_message.setText("");//xoa noi dung nhap van ban da duco gui di de nguoi dung nhap van ban moi;
    }//GEN-LAST:event_send_btActionPerformed

    private void login_btActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_login_btActionPerformed
        // TODO add your handling code here:
                nick=jtf_nick1.getText();  //lay ten nick nguoi dung nhap vao de chuan bi kiem tra;
                pass=tf_pass.getText();
        if(checkLogin(nick,pass)){
		p1.setVisible(false);//an panel dang nhap di;
                p2.setVisible(true);// hien thi cua so va cac dieu khine de thuc hien chat 
                p3.setVisible(true);
                p4.setVisible(true);       
		jtf_nick.setText(nick);
		this.setTitle("My Name Is "+jtf_nick1.getText());
		msg_area.append("Đã đăng nhập thành công\n"); //hien thi tren cua so chat cua ca nhan da dang nhap thanh cong;
		datastream = new DataStream(this, this.dis); // ham khoi tao cua datastream co tham so dau vao la client va dis;
                // data_input_stream de nhan du lieu tu socket;
           }
	else{
		JOptionPane.showMessageDialog(this,"Nick này không hợp lệ,Hoặc đã có người sử dụng","Message Dialog",JOptionPane.WARNING_MESSAGE);
	    }
    }//GEN-LAST:event_login_btActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        exit();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btn_chatriengActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_chatriengActionPerformed
        // TODO add your handling code here:
         yourname=tf_yourname.getText();
        //kiem tra ten nay co hop le de duoc cho phep chat hay khong;     
        if((!yourname.equals(""))&(!yourname.equals(nick))){
            sendMSG("2",yourname);  //gui ten nick  cho phia server kiem tra;
           
        }
        else{
            JOptionPane.showMessageDialog(this,"Nick bạn muốn chát không hợp lệ!");
        }
      
    }//GEN-LAST:event_btn_chatriengActionPerformed

    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JClient.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
              client=  new JClient();
              client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              client.go(); // thuc hien goi phuong thuc go() cua  doi tuong client;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_chatrieng;
    private javax.swing.JButton clear_bt;
    private javax.swing.JButton exit_bt;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jtf_message;
    private javax.swing.JTextField jtf_nick;
    private javax.swing.JTextField jtf_nick1;
    private javax.swing.JButton login_bt;
    private javax.swing.JTextArea msg_area;
    private javax.swing.JTextArea online_area;
    private javax.swing.JPanel p1;
    private javax.swing.JPanel p2;
    private javax.swing.JPanel p3;
    private javax.swing.JPanel p4;
    private javax.swing.JButton send_bt;
    private javax.swing.JTextField tf_pass;
    private javax.swing.JTextField tf_yourname;
    // End of variables declaration//GEN-END:variables
}