/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JServer;
import java.util.Vector;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class frmViews extends javax.swing.JFrame {
     private Statement st=null;
     private ResultSet rs=null;
     private Vector vColum=new Vector();
     private Vector vData =new Vector();
     public  Connection conn;
    public frmViews() {
        initComponents();
        setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btn_them = new javax.swing.JButton();
        btn_xoa = new javax.swing.JButton();
        btn_thoat = new javax.swing.JButton();
        tf_user = new javax.swing.JTextField();
        tf_pass = new javax.swing.JTextField();
        a = new javax.swing.JLabel();
        b = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(table);

        btn_them.setText("Thêm");
        btn_them.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_themActionPerformed(evt);
            }
        });

        btn_xoa.setText("xóa");
        btn_xoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_xoaActionPerformed(evt);
            }
        });

        btn_thoat.setText("Thoát");
        btn_thoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_thoatActionPerformed(evt);
            }
        });

        a.setText("user");

        b.setText("pass");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(a)
                        .addGap(18, 18, 18)
                        .addComponent(tf_user, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_them)
                        .addGap(37, 37, 37)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(b)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tf_pass, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btn_xoa, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btn_thoat)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tf_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(a))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(tf_pass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(b)))
                .addGap(28, 28, 28)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_them)
                    .addComponent(btn_xoa)
                    .addComponent(btn_thoat))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_thoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_thoatActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
    }//GEN-LAST:event_btn_thoatActionPerformed

    private void btn_themActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_themActionPerformed
        
        String u=tf_user.getText();
        String p=tf_pass.getText();
        if(!checkUserOnSQL(u)){
        themUser(u,p);
        }else{
           JOptionPane.showMessageDialog(this,"Không thể thêm tài khoản này vì nó đã \n được đăng kí bởi người dùng khác"); 
        }
        tf_user.setText("");
        tf_pass.setText("");
        clearTable(table);
        showTableDangNhap();
        
    }//GEN-LAST:event_btn_themActionPerformed

    private void btn_xoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_xoaActionPerformed
        String u=tf_user.getText();
        xoaUser(u);
        tf_user.setText("");
        tf_pass.setText("");
        clearTable(table);
        showTableDangNhap();
    }//GEN-LAST:event_btn_xoaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
       frmViews view=new frmViews();
       view.showTableDangNhap();
   
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel a;
    private javax.swing.JLabel b;
    private javax.swing.JButton btn_them;
    private javax.swing.JButton btn_thoat;
    private javax.swing.JButton btn_xoa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    private javax.swing.JTextField tf_pass;
    private javax.swing.JTextField tf_user;
    // End of variables declaration//GEN-END:variables

    
     public void showTableDangNhap() {
        try {
            connect();
            
              try {
                st=conn.createStatement();
                String sql="select * from thongtintaikhoan";
                rs=st.executeQuery(sql);
                ResultSetMetaData rsmt =rs.getMetaData();
                for(int i=1;i<=rsmt.getColumnCount();i++){
                vColum.add(rsmt.getColumnName(i));
                }
                 while (rs.next())
                {
                Vector vRow =new Vector();
                for(int i=1;i<=rsmt.getColumnCount();i++){
                    vRow.add(rs.getString(i));
                }
                
                vData.add(vRow);
                table.setModel(new DefaultTableModel(vData,vColum));
                }
            
             } catch (SQLException ex) {
            ex.printStackTrace();
            }    
            finally{
            try {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
              }
            
            } 
            
        } catch (SQLException ex) {
            Logger.getLogger(frmViews.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }

     
     
      private boolean checkUserOnSQL(String nick){
            boolean kq=false;
        try {        
            String url = "sun.jdbc.odbc.JdbcOdbcDriver";   
            Class.forName(url); //khia bao driver;
            conn=DriverManager.getConnection("jdbc:odbc:DBSJ","","");
            st=conn.createStatement();
            String sql= "select * from thongtintaikhoan where username= '"+nick+"' ";
            rs=st.executeQuery(sql); 
           if(rs!=null){
                    if(rs.next()){
                      kq=true;
                    }else{
                      kq=false;
                    }        
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
            
            return kq;
        }
        
     
     
     
    public void xoaUser(String u) throws HeadlessException {
           try {
             connect();
         } catch (SQLException ex) {
             ex.printStackTrace();
         }
         
          String username=tf_user.getText();
          String password=tf_pass.getText();
          if(!username.equals("")){             
          try {
               st=conn.createStatement();
               String sql="delete thongtintaikhoan where username='"+ u+ "' ";
               JOptionPane.showMessageDialog(this,"da xoa tai khoan nay");
               rs=st.executeQuery(sql);
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
            finally{
            try {
                rs.close();
                st.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            
           } 
        }//end if
          else{
              JOptionPane.showMessageDialog(this,"ban da de trong ja tri can nhap");
          }
    }

    
    public void themUser(String u,String p) throws HeadlessException {
          try {
            connect();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
         String username=u;
         String password=p;
         if((!username.equals(""))&(!password.equals(""))){             
         try {
              st=conn.createStatement();
              String sql="insert into thongtintaikhoan(username,password) values('"+username+"','"+password+"') ";
              JOptionPane.showMessageDialog(this,"da them tai khoan nay");
              rs=st.executeQuery(sql);
          } catch (SQLException ex) {
              ex.printStackTrace();
          }
           finally{
           try {
               rs.close();
               st.close();
               conn.close();
           } catch (SQLException ex) {
               ex.printStackTrace();
           }
           
          } 
       }//end if
         else{
             JOptionPane.showMessageDialog(this,"ban da de trong ja tri can nhap");
         }
    }
    
    
   public void connect() throws SQLException {
       // conn=DriverManager.getConnection(dbUrl);
       conn=DriverManager.getConnection("jdbc:odbc:DBSJ","","");
    }  
    
  public  void clearTable(JTable table) {
   
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.setRowCount(0);
        model.setColumnCount(0);
   }  
    
}   
    
    
    
