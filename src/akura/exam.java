/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akura;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Pasindu01
 */
public class exam extends javax.swing.JFrame {

    /**
     * Creates new form exam
     */
    public exam() {
        initComponents();
        
        setIcon();
        
        jTable1.getTableHeader().setOpaque(false);
        jTable1.getTableHeader().setBackground(new Color(196, 88, 53));
        jTable1.getTableHeader().setForeground(new Color(255, 255, 255));
        jTable1.setRowHeight(25);
        
        jTable2.getTableHeader().setOpaque(false);
        jTable2.getTableHeader().setBackground(new Color(196, 88, 53));
        jTable2.getTableHeader().setForeground(new Color(255, 255, 255));
        jTable2.setRowHeight(25);
        
        jPanel3.setBackground(new Color(51,6,149));
        jPanel2.setBackground(new Color(51,51,51));
        overview.setVisible(true);
        scheduleExam.setVisible(false);
        
        data();
        tblClass();
        
    }
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.jpg")));
    }
    
    DefaultTableModel model = new DefaultTableModel();
    
    public ArrayList<examData> examdata(){
        ArrayList<examData> examDataList = new ArrayList<>();
        try {
            Connection con = DatabaseConnection.getcon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select *, (select Class_Name from class where exam.Class = class.Class_ID) as cname, (select Grade from class where exam.Class = class.Class_ID) as grade,(select Subject_Name from subject where Subject_ID in (select Subject from class where class.Class_ID = exam.Class)) as sname from exam;");
            examData data;
            while(rs.next()){
                data = new examData(rs.getString("Exam_ID"), rs.getString("sname"), rs.getString("Class"), rs.getString("cname"), rs.getDate("ExamDate"), rs.getTime("Time"), rs.getString("grade"));
                examDataList.add(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return examDataList;
    }
    
    private void data(){
        ArrayList<examData> list = examdata();
        model = (DefaultTableModel) jTable1.getModel();
        Object[] row = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getExam_ID();
            row[2] = list.get(i).getSname();
            row[1] = list.get(i).getcClass();
            row[3] = list.get(i).getCname();
            row[4] = list.get(i).getGrade();
            row[5] = list.get(i).getExam_Date();
            row[6] = list.get(i).getTime();
            model.addRow(row);
        }
    }
    
//    Search Method 1
    public void search(String str){
        model = (DefaultTableModel) jTable1.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        jTable1.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }
    
    public ArrayList<ClassData> dataTec_1(){
            ArrayList<ClassData> classData = new ArrayList<>();
            
            try {
                Connection con = DatabaseConnection.getcon();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from class c inner join subject s on c.Subject = s.Subject_ID inner join teacher t on t.Teacher_ID = c.Teacher");
                ClassData data;
                while(rs.next()){
                    data = new ClassData(rs.getString("c.Class_ID"),rs.getString("c.Class_Name"),rs.getString("c.Grade"),rs.getString("t.Name_with_initials"), rs.getString("s.Subject_Name"),rs.getString("c.Sche_time"), rs.getString("c.Schedule_date"), rs.getDouble("c.Class_Fee"));
                    classData.add(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        return classData;
    }
    
//    Data set to tblClassData Table
    public void tblClass(){
        ArrayList<ClassData> list = dataTec_1();
        model = (DefaultTableModel) jTable2.getModel();
        
        Object row[] = new Object[4];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getClass_ID();
            row[1] = list.get(i).getClass_Name();
            row[3] = list.get(i).getGrade();
            row[2] = list.get(i).getName_with_initials();
            model.addRow(row);
        }
    }
    
//    Search method 2
    public void search_1(String str){
        model = (DefaultTableModel) jTable2.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        jTable2.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        scheduleExam = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtSearch1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        lblcid = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        btnTeacherDelete = new javax.swing.JButton();
        btnTeacherDelete1 = new javax.swing.JButton();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jFormattedTextField2 = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lbleid = new javax.swing.JTextField();
        overview = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        btnClassesDelete = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Schedul Exams | Akura");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_exam_24px.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Schedule Exam");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(51, 51, 51));
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_ratings_24px_1.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Overview");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(692, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 70));

        scheduleExam.setBackground(new java.awt.Color(255, 255, 255));
        scheduleExam.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        scheduleExam.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(657, 11, -1, 438));

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Class ID", "Class Name", "Subject", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setFillsViewportHeight(true);
        jTable2.setFocusable(false);
        jTable2.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable2.setRowHeight(25);
        jTable2.setSelectionBackground(new java.awt.Color(102, 153, 255));
        jTable2.setShowVerticalLines(false);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        scheduleExam.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 620, 370));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Select a class from here. You can schedule a exam for selected class");
        scheduleExam.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 550, 20));

        txtSearch1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtSearch1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearch1KeyReleased(evt);
            }
        });
        scheduleExam.add(txtSearch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 421, 30));

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Class ID / Exam ID");
        scheduleExam.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, 30));

        lblcid.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        scheduleExam.add(lblcid, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 110, 250, 20));

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Schedule New Class");
        scheduleExam.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 20, 340, 30));

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel10.setText("Exam ID");
        scheduleExam.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 170, 90, 20));

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel12.setText("Schedule Date");
        scheduleExam.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 230, 100, 20));

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel14.setText("Class ID");
        scheduleExam.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 110, 90, 20));

        jLabel16.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel16.setText("Schedule Time");
        scheduleExam.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 290, 100, 20));

        btnTeacherDelete.setBackground(new java.awt.Color(0, 0, 255));
        btnTeacherDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnTeacherDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnTeacherDelete.setText("Clear");
        btnTeacherDelete.setBorderPainted(false);
        btnTeacherDelete.setFocusPainted(false);
        btnTeacherDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTeacherDeleteActionPerformed(evt);
            }
        });
        scheduleExam.add(btnTeacherDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 400, 105, 42));

        btnTeacherDelete1.setBackground(new java.awt.Color(0, 0, 255));
        btnTeacherDelete1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnTeacherDelete1.setForeground(new java.awt.Color(255, 255, 255));
        btnTeacherDelete1.setText("Save");
        btnTeacherDelete1.setBorderPainted(false);
        btnTeacherDelete1.setFocusPainted(false);
        btnTeacherDelete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTeacherDelete1ActionPerformed(evt);
            }
        });
        scheduleExam.add(btnTeacherDelete1, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 400, 105, 42));

        jFormattedTextField1.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("HH:mm:ss"))));
        jFormattedTextField1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        scheduleExam.add(jFormattedTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 280, 250, 30));

        jFormattedTextField2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("yyyy-MM-dd"))));
        jFormattedTextField2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        scheduleExam.add(jFormattedTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 220, 250, 30));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("yyyy-MM-dd");
        scheduleExam.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 250, 130, 20));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("HH:MM:SS");
        scheduleExam.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 310, 130, 20));

        lbleid.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        scheduleExam.add(lbleid, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 160, 250, 30));

        getContentPane().add(scheduleExam, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 1110, 460));

        overview.setBackground(new java.awt.Color(255, 255, 255));
        overview.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Exam ID", "Subject", "Class", "Class Name", "Grade", "Exam Date", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setFillsViewportHeight(true);
        jTable1.setFocusable(false);
        jTable1.setGridColor(new java.awt.Color(153, 153, 153));
        jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable1.setRowHeight(25);
        jTable1.setSelectionBackground(new java.awt.Color(102, 153, 255));
        jTable1.setShowVerticalLines(false);
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);

        overview.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 1090, 350));

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Class ID / Exam ID");
        overview.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 11, 165, 35));

        txtSearch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtSearch.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        overview.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(193, 11, 421, 35));

        jButton10.setBackground(new java.awt.Color(0, 0, 255));
        jButton10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Search");
        jButton10.setBorderPainted(false);
        jButton10.setFocusPainted(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        overview.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(632, 15, 105, -1));

        btnClassesDelete.setBackground(new java.awt.Color(0, 0, 255));
        btnClassesDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnClassesDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnClassesDelete.setText("Delete");
        btnClassesDelete.setBorderPainted(false);
        btnClassesDelete.setFocusPainted(false);
        btnClassesDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClassesDeleteActionPerformed(evt);
            }
        });
        overview.add(btnClassesDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 410, 105, 43));

        getContentPane().add(overview, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 1110, 460));

        jButton3.setBackground(new java.awt.Color(255, 0, 0));
        jButton3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Back");
        jButton3.setBorderPainted(false);
        jButton3.setFocusPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 560, 100, 40));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        search(txtSearch.getText().toUpperCase());
    }//GEN-LAST:event_txtSearchKeyReleased

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        search(txtSearch.getText().toUpperCase());
        txtSearch.setText("");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
        jPanel3.setBackground(new Color(51,6,149));
        jPanel2.setBackground(new Color(51,51,51));
        overview.setVisible(true);
        scheduleExam.setVisible(false);
    }//GEN-LAST:event_jPanel3MouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
        jPanel2.setBackground(new Color(51,6,149));
        jPanel3.setBackground(new Color(51,51,51));
        scheduleExam.setVisible(true);
        overview.setVisible(false);
    }//GEN-LAST:event_jPanel2MouseClicked

    private void txtSearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearch1KeyReleased
        search_1(txtSearch1.getText().toUpperCase());
    }//GEN-LAST:event_txtSearch1KeyReleased

    private void btnTeacherDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTeacherDeleteActionPerformed
        lblcid.setText(null);
        lbleid.setText(null);
        jFormattedTextField2.setText(null);
        jFormattedTextField1.setText(null);
    }//GEN-LAST:event_btnTeacherDeleteActionPerformed

    private void btnTeacherDelete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTeacherDelete1ActionPerformed
        if(!lbleid.getText().equals("") && !jFormattedTextField2.getText().equals("") && !jFormattedTextField1.getText().equals("")){
            try {
                Connection con = DatabaseConnection.getcon();
                PreparedStatement st = con.prepareStatement("insert into exam values('"+lbleid.getText().toUpperCase()+"', '"+jFormattedTextField2.getText()+"', '"+jFormattedTextField1.getText()+"', '"+lblcid.getText().toUpperCase()+"')");
                st.executeUpdate();
                JOptionPane.showMessageDialog(rootPane, "Successfully saved a exam...");
            } catch (SQLException | HeadlessException e) {
                JOptionPane.showMessageDialog(rootPane, "Already define "+lbleid.getText(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "Cannot have empty field... ", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnTeacherDelete1ActionPerformed

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        model = (DefaultTableModel) jTable2.getModel();
        int row = jTable2.getSelectedRow();
        lblcid.setText(model.getValueAt(row, 0).toString());
    }//GEN-LAST:event_jTable2MouseClicked

    
    //    Retrive admin password
    private String Login(){
        String aPass = null;
        Connection con = DatabaseConnection.getcon();
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select Password from user where AuthLevel = 'admin'");
            if(rs.next()){
                aPass = rs.getString("Password");
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return aPass;
    }
    //    Hash function for encrypt user enter password.
    private static String hash(String data){
        String hashText = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(data.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            hashText = number.toString(16);
            while(hashText.length() <32){
                hashText = "0" + hashText;
            }
        } catch (Exception e) {
        }
     return hashText;
        
    }
    private void btnClassesDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClassesDeleteActionPerformed
        try {
            int row = jTable1.getSelectedRow();
            String id = jTable1.getValueAt(row, 0).toString();
            String sql = "delete from exam where Exam_ID = '"+id+"'";

            adminpassconfirm admin = new adminpassconfirm();
            admin.getSql(sql);
            admin.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Please select a row...");
        }
    }//GEN-LAST:event_btnClassesDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new exam().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnClassesDelete;
    public javax.swing.JButton btnTeacherDelete;
    public javax.swing.JButton btnTeacherDelete1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JLabel lblcid;
    private javax.swing.JTextField lbleid;
    private javax.swing.JPanel overview;
    private javax.swing.JPanel scheduleExam;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSearch1;
    // End of variables declaration//GEN-END:variables
}
