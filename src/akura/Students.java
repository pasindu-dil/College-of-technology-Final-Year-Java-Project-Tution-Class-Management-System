/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akura;

import java.awt.Color;
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
public class Students extends javax.swing.JFrame {

    /**
     * Creates new form Students
     */
    DefaultTableModel model;
    
    public Students() {
        initComponents();
        
        setIcon();
        
        
        tblStudent.getTableHeader().setOpaque(false);
        tblStudent.getTableHeader().setBackground(new Color(196, 88, 53));
        tblStudent.getTableHeader().setForeground(new Color(255, 255, 255));
        tblStudent.setRowHeight(25);
        
        tblEnrollData.getTableHeader().setOpaque(false);
        tblEnrollData.getTableHeader().setBackground(new Color(196, 88, 53));
        tblEnrollData.getTableHeader().setForeground(new Color(255, 255, 255));
        tblEnrollData.setRowHeight(25);
        
        overviewBtn.setOpaque(true);
        overviewBtn.setBackground(new Color(51,6,149));
        
        viewOverview.setVisible(true);
        addNewStudentSTab.setVisible(false);
        enrollTab.setVisible(false);
        enrollStudentsPane.setVisible(false);
        
        studentDetailsTab.setVisible(false);
        enrollSuccesTab.setVisible(false);
        
        txtSearch.setText("Enter Student Reg ID or NIC...");
        txtRegNo.setText("PHD");
        
        if(cmbCategory.getSelectedItem().equals("Advance Level (A/L)")){
            jLabel27.setVisible(true);
            jLabel28.setVisible(true);
            rdoStreamArts.setVisible(true);
            rdoStreamBio.setVisible(true);
            rdoStreamComm.setVisible(true);
            rdoStreamPhy.setVisible(true);
            rdoStreamTec.setVisible(true);
            rdoStreamOther.setVisible(true);
        }else{
            jLabel27.setVisible(false);
            jLabel28.setVisible(false);
            rdoStreamBio.setVisible(false);
            rdoStreamComm.setVisible(false);
            rdoStreamPhy.setVisible(false);
            rdoStreamTec.setVisible(false);
            rdoStreamOther.setVisible(false);
            rdoStreamArts.setVisible(false);
        }
        
        tblData();
        
        dataShow();
        
        int id = 0;
        try {
            Connection con = DatabaseConnection.getcon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(Enroll_ID) from enroll");
            while(rs.next()){
                id = rs.getInt("count(Enroll_ID)")+1;
                txtEnrollid.setText(id+"");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        
    }
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.jpg")));
    }
    
    
    
    
    public ArrayList<StudentData> StdData(){
        ArrayList<StudentData> stddatalist = new ArrayList<>();
        
        try {
            Connection conn = DatabaseConnection.getcon();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from std_view");
            StudentData stdData;
            while(rs.next()){
                stdData =new StudentData(rs.getString("Reg_ID"), rs.getString("Name_with_initials"),rs.getString("City"), rs.getString("Gender"), rs.getString("Address1"),rs.getString("Address2"),rs.getString("Mobile_No"),rs.getString("NIC"), rs.getString("Grade"));    
                stddatalist.add(stdData);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return stddatalist;
    }
    
    public void tblData(){
        ArrayList<StudentData> list = StdData();
        model = (DefaultTableModel) tblStudent.getModel();
        Object[] row = new Object[9];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getReg_ID();
            row[1] = list.get(i).getName_with_initials();
            row[2] = list.get(i).getCity();
            row[3] = list.get(i).getGender();
            row[4] = list.get(i).getAddress1();
            row[5] = list.get(i).getAddress2();
            row[6] = list.get(i).getMobile_No();
            row[7] = list.get(i).getNIC();
            row[8] = list.get(i).getGrade();
            model.addRow(row);
        }
    }
    
    public ArrayList<EnrollData> enrollData(){
        ArrayList<EnrollData> dataList = new ArrayList<>();
        
        try {
            Connection conn = DatabaseConnection.getcon();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select enroll.Enroll_ID, enroll.Class, subject.Subject_Name, enroll.Std_Reg_No, student.Name_with_initials, class.Class_Name from enroll inner join student on enroll.Std_Reg_No = student.Reg_ID inner join class on class.Class_ID = enroll.Class inner join subject on subject.Subject_ID = class.Subject");
            EnrollData data;
            while(rs.next()){
                data = new EnrollData(rs.getString("Enroll_ID"),rs.getString("enroll.Class"), rs.getString("subject.Subject_Name"), rs.getString("enroll.Std_Reg_No"), rs.getString("student.Name_with_initials"), rs.getString("class.Class_Name"));;
                dataList.add(data);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return dataList;
    }
    
    public void dataShow(){
        model = (DefaultTableModel) tblEnrollData.getModel();
        ArrayList<EnrollData> list =enrollData();
        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getEnroll_ID();
            row[1] = list.get(i).getClassID();
            row[2] = list.get(i).getSubject_Name();
            row[3] = list.get(i).getStd_Reg_No();
            row[4] = list.get(i).getName_with_initials();
            row[5] = list.get(i).getClass_Name();
            model.addRow(row);
        }
    }
    
    
    public void search(String str){
        model = (DefaultTableModel) tblStudent.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblStudent.setRowSorter(trs);
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

        btngStream = new javax.swing.ButtonGroup();
        btngGender = new javax.swing.ButtonGroup();
        viewOverview = new javax.swing.JPanel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudent = new javax.swing.JTable();
        jButton10 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnStudentDelete = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        overviewBtn = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        viewMarkBtn = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        enrollBtn = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        addNewBtn = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        addNewStudentSTab = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtFullName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtNamewithInitials = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtAddress1 = new javax.swing.JTextField();
        txtAddress2 = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        txtAddress3 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtMobileNo = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        txtGrade = new javax.swing.JTextField();
        txtSchoolName = new javax.swing.JTextField();
        txtParentMobileNo = new javax.swing.JTextField();
        txtParentName = new javax.swing.JTextField();
        txtNic = new javax.swing.JTextField();
        txtDOB = new datechooser.beans.DateChooserCombo();
        cmbExamYear = new javax.swing.JComboBox();
        jLabel26 = new javax.swing.JLabel();
        cmbCity = new javax.swing.JComboBox();
        jLabel27 = new javax.swing.JLabel();
        rdoStreamOther = new javax.swing.JRadioButton();
        rdoStreamPhy = new javax.swing.JRadioButton();
        rdoStreamBio = new javax.swing.JRadioButton();
        rdoStreamComm = new javax.swing.JRadioButton();
        rdoStreamArts = new javax.swing.JRadioButton();
        rdoStreamTec = new javax.swing.JRadioButton();
        jLabel28 = new javax.swing.JLabel();
        cmbCategory = new javax.swing.JComboBox();
        btnSubmit = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        rdoFemale = new javax.swing.JRadioButton();
        rdoMale = new javax.swing.JRadioButton();
        txtRegNo = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        enrollTab = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        studentDetailsTab = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblReg = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        lblGrade = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        enrollSuccesTab = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jButton11 = new javax.swing.JButton();
        jLabel31 = new javax.swing.JLabel();
        txtNIC = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        txtTeacher = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        txtsName = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        txtClasscode1 = new javax.swing.JTextField();
        txtSubject = new javax.swing.JTextField();
        txtEGrade = new javax.swing.JTextField();
        txtEnrollid = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        enrollStudentsPane = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        txtVMReg = new javax.swing.JTextField();
        btnEnrollDelete = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblEnrollData = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Akura Class Management System | Students View");
        setBackground(new java.awt.Color(204, 204, 255));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        viewOverview.setBackground(new java.awt.Color(255, 255, 255));
        viewOverview.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtSearch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtSearch.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtSearchMouseClicked(evt);
            }
        });
        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        viewOverview.add(txtSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 17, 560, 35));

        btnSearch.setBackground(new java.awt.Color(153, 204, 0));
        btnSearch.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setBorderPainted(false);
        btnSearch.setFocusPainted(false);
        btnSearch.setPreferredSize(new java.awt.Dimension(77, 35));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        viewOverview.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(606, 18, 103, -1));

        tblStudent.setAutoCreateRowSorter(true);
        tblStudent.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tblStudent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Reg ID", "Student Name", "City", "Gender", "Address 1", "Address 2", "Mobile No", "NIC", "Grade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStudent.setFillsViewportHeight(true);
        tblStudent.setFocusable(false);
        tblStudent.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblStudent.setRowHeight(25);
        tblStudent.setSelectionBackground(new java.awt.Color(102, 153, 255));
        tblStudent.setShowVerticalLines(false);
        tblStudent.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblStudent);
        if (tblStudent.getColumnModel().getColumnCount() > 0) {
            tblStudent.getColumnModel().getColumn(0).setResizable(false);
            tblStudent.getColumnModel().getColumn(2).setResizable(false);
            tblStudent.getColumnModel().getColumn(3).setResizable(false);
            tblStudent.getColumnModel().getColumn(4).setResizable(false);
            tblStudent.getColumnModel().getColumn(5).setResizable(false);
        }

        viewOverview.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(28, 65, 1105, 400));

        jButton10.setBackground(new java.awt.Color(0, 0, 255));
        jButton10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("View");
        jButton10.setBorderPainted(false);
        jButton10.setFocusPainted(false);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        viewOverview.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 470, 105, 42));

        jButton4.setBackground(new java.awt.Color(0, 0, 255));
        jButton4.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Update");
        jButton4.setBorderPainted(false);
        jButton4.setFocusPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        viewOverview.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 470, 105, 42));

        btnStudentDelete.setBackground(new java.awt.Color(0, 0, 255));
        btnStudentDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        btnStudentDelete.setForeground(new java.awt.Color(255, 255, 255));
        btnStudentDelete.setText("Delete");
        btnStudentDelete.setBorderPainted(false);
        btnStudentDelete.setFocusPainted(false);
        btnStudentDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStudentDeleteActionPerformed(evt);
            }
        });
        viewOverview.add(btnStudentDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 470, 105, 42));

        getContentPane().add(viewOverview, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 83, 1143, 520));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        overviewBtn.setBackground(new java.awt.Color(51, 51, 51));
        overviewBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                overviewBtnMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Overview");

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_ratings_24px_1.png"))); // NOI18N

        javax.swing.GroupLayout overviewBtnLayout = new javax.swing.GroupLayout(overviewBtn);
        overviewBtn.setLayout(overviewBtnLayout);
        overviewBtnLayout.setHorizontalGroup(
            overviewBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, overviewBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        overviewBtnLayout.setVerticalGroup(
            overviewBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, overviewBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(overviewBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        viewMarkBtn.setBackground(new java.awt.Color(51, 51, 51));
        viewMarkBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                viewMarkBtnMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                viewMarkBtnMouseEntered(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_get_quote_24px.png"))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("View Enrolled Students");

        javax.swing.GroupLayout viewMarkBtnLayout = new javax.swing.GroupLayout(viewMarkBtn);
        viewMarkBtn.setLayout(viewMarkBtnLayout);
        viewMarkBtnLayout.setHorizontalGroup(
            viewMarkBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewMarkBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        viewMarkBtnLayout.setVerticalGroup(
            viewMarkBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, viewMarkBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(viewMarkBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        enrollBtn.setBackground(new java.awt.Color(51, 51, 51));
        enrollBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                enrollBtnMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_google_classroom_32px.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Enroll Class");

        javax.swing.GroupLayout enrollBtnLayout = new javax.swing.GroupLayout(enrollBtn);
        enrollBtn.setLayout(enrollBtnLayout);
        enrollBtnLayout.setHorizontalGroup(
            enrollBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, enrollBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        enrollBtnLayout.setVerticalGroup(
            enrollBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, enrollBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(enrollBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        addNewBtn.setBackground(new java.awt.Color(51, 51, 51));
        addNewBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addNewBtnMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Add New Student");

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_student_registration_30px_1.png"))); // NOI18N

        javax.swing.GroupLayout addNewBtnLayout = new javax.swing.GroupLayout(addNewBtn);
        addNewBtn.setLayout(addNewBtnLayout);
        addNewBtnLayout.setHorizontalGroup(
            addNewBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(5, 5, 5)
                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        addNewBtnLayout.setVerticalGroup(
            addNewBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, addNewBtnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(addNewBtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(overviewBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(enrollBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addNewBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewMarkBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(viewMarkBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addNewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(enrollBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(overviewBtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1179, -1));

        addNewStudentSTab.setBackground(new java.awt.Color(255, 255, 255));
        addNewStudentSTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Add New Students");
        addNewStudentSTab.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1140, 40));

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel8.setText("Full Name");
        addNewStudentSTab.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 164, 30));

        txtFullName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        addNewStudentSTab.add(txtFullName, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 100, 283, 30));

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel9.setText("Name With Initial");
        addNewStudentSTab.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 164, 30));

        txtNamewithInitials.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNamewithInitials.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtNamewithInitialsMouseClicked(evt);
            }
        });
        addNewStudentSTab.add(txtNamewithInitials, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 140, 283, 30));

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel10.setText("Birth Date");
        addNewStudentSTab.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 180, 164, 30));

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel13.setText("Address Line1");
        addNewStudentSTab.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 164, 30));

        txtAddress1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        addNewStudentSTab.add(txtAddress1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 300, 283, 30));

        txtAddress2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        addNewStudentSTab.add(txtAddress2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 340, 283, 30));

        jLabel14.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel14.setText("Address Line2");
        addNewStudentSTab.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 340, 164, 30));

        txtAddress3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        addNewStudentSTab.add(txtAddress3, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 380, 283, 30));

        jLabel15.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel15.setText("Address Line3");
        addNewStudentSTab.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 380, 164, 30));

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel16.setText("City");
        addNewStudentSTab.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 164, 30));

        txtMobileNo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        addNewStudentSTab.add(txtMobileNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 420, 283, 30));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel17.setText("Mobile No");
        addNewStudentSTab.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 420, 164, 30));

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel18.setText("NIC");
        addNewStudentSTab.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 460, 164, 30));

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel19.setText("Parent's Name");
        addNewStudentSTab.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 500, 164, 30));

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel20.setText("Parent's Mobile No.");
        addNewStudentSTab.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 60, 164, 30));

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel21.setText("School Name");
        addNewStudentSTab.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 164, 30));

        jLabel22.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel22.setText("Grade");
        addNewStudentSTab.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 140, 164, 30));

        jLabel23.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel23.setText("Category");
        addNewStudentSTab.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 180, 164, 30));

        jLabel24.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel24.setText("Gender");
        addNewStudentSTab.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 260, 164, 30));

        txtGrade.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        addNewStudentSTab.add(txtGrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 140, 283, 30));

        txtSchoolName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        addNewStudentSTab.add(txtSchoolName, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 100, 283, 30));

        txtParentMobileNo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        addNewStudentSTab.add(txtParentMobileNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 60, 283, 30));

        txtParentName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        addNewStudentSTab.add(txtParentName, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 500, 283, 30));

        txtNic.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        txtNic.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNicKeyReleased(evt);
            }
        });
        addNewStudentSTab.add(txtNic, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 460, 283, 30));

        txtDOB.setCurrentView(new datechooser.view.appearance.AppearancesList("Light",
            new datechooser.view.appearance.ViewAppearance("custom",
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    true,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(0, 0, 255),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(128, 128, 128),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(0, 0, 255),
                    false,
                    true,
                    new datechooser.view.appearance.swing.LabelPainter()),
                new datechooser.view.appearance.swing.SwingCellAppearance(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 12),
                    new java.awt.Color(0, 0, 0),
                    new java.awt.Color(255, 0, 0),
                    false,
                    false,
                    new datechooser.view.appearance.swing.ButtonPainter()),
                (datechooser.view.BackRenderer)null,
                false,
                true)));
    txtDOB.setCalendarBackground(new java.awt.Color(255, 255, 255));
    txtDOB.setCalendarPreferredSize(new java.awt.Dimension(335, 243));
    txtDOB.setNothingAllowed(false);
    try {
        txtDOB.setDefaultPeriods(new datechooser.model.multiple.PeriodSet(new datechooser.model.multiple.Period(new java.util.GregorianCalendar(1999, 4, 17),
            new java.util.GregorianCalendar(1999, 4, 17))));
} catch (datechooser.model.exeptions.IncompatibleDataExeption e1) {
    e1.printStackTrace();
    }
    txtDOB.setFieldFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 13));
    addNewStudentSTab.add(txtDOB, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 180, 283, 30));

    cmbExamYear.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    cmbExamYear.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select exam year...", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033", "2034", "2035" }));
    addNewStudentSTab.add(cmbExamYear, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 320, 283, 30));

    jLabel26.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel26.setText("Exam Year");
    addNewStudentSTab.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 320, 164, 30));

    cmbCity.setEditable(true);
    cmbCity.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    cmbCity.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select your city here...", "Akkaraipattu", "Ambalangoda", "Ampara", "Anuradhapura", "Badulla", "Balangoda", "Bandarawela", "Batticaloa", "Beruwala", "Boralesgamuwa", "Chavakachcheri", "Chilaw", "Colombo", "Dambulla", "Dehiwala-Mount Lavinia", "Embilipitiya", "Eravur", "Galle", "Gampaha", "Gampola", "Hambantota", "Haputale", "Hatton-Dickoya", "Hikkaduwa", "Horana", "Ja-Ela", "Jaffna", "Kadugannawa", "Karandeniya", "Kaduwela (Battaramulla)", "Kalmunai (incl. Sainthamarathu)", "Kalutara", "Kandy", "Kattankudy (Kathankudi)", "Katunayake (-Seeduwa)", "Kegalle", "Kesbewa", "Kilinochchi", "Kinniya", "Kolonnawa", "Kuliyapitiya", "Kurunegala", "Maharagama", "Mannar", "Matale", "Matara", "Minuwangoda", "Moneragala", "Moratuwa", "Mullaitivu", "Nawalapitiya", "Negombo", "Nuwara Eliya", "Panadura", "Peliyagoda", "Point Pedro", "Polonnaruwa", "Puttalam", "Ratnapura", "Seethawakapura (Avissawella)", "Sri Jayawardenepura (Kotte)", "Tangalle", "Thalawakele-Lindula", "Trincomalee", "Valvettithurai", "Vavuniya", "Wattala-Mabole", "Wattegama", "Weligama" }));
    addNewStudentSTab.add(cmbCity, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 220, 283, 30));

    jLabel27.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel27.setText("Stream");
    addNewStudentSTab.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 220, 164, 30));

    rdoStreamOther.setBackground(new java.awt.Color(255, 255, 255));
    btngStream.add(rdoStreamOther);
    rdoStreamOther.setText("Other");
    rdoStreamOther.setFocusPainted(false);
    addNewStudentSTab.add(rdoStreamOther, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 270, 70, -1));

    rdoStreamPhy.setBackground(new java.awt.Color(255, 255, 255));
    btngStream.add(rdoStreamPhy);
    rdoStreamPhy.setText("Physical");
    rdoStreamPhy.setFocusPainted(false);
    addNewStudentSTab.add(rdoStreamPhy, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 230, 90, -1));

    rdoStreamBio.setBackground(new java.awt.Color(255, 255, 255));
    btngStream.add(rdoStreamBio);
    rdoStreamBio.setText("Biology");
    rdoStreamBio.setFocusPainted(false);
    addNewStudentSTab.add(rdoStreamBio, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 230, 90, -1));

    rdoStreamComm.setBackground(new java.awt.Color(255, 255, 255));
    btngStream.add(rdoStreamComm);
    rdoStreamComm.setText("Commerce");
    rdoStreamComm.setFocusPainted(false);
    addNewStudentSTab.add(rdoStreamComm, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 270, -1, -1));

    rdoStreamArts.setBackground(new java.awt.Color(255, 255, 255));
    btngStream.add(rdoStreamArts);
    rdoStreamArts.setText("Arts");
    rdoStreamArts.setFocusPainted(false);
    addNewStudentSTab.add(rdoStreamArts, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 230, 80, -1));

    rdoStreamTec.setBackground(new java.awt.Color(255, 255, 255));
    btngStream.add(rdoStreamTec);
    rdoStreamTec.setText("Technology");
    rdoStreamTec.setFocusPainted(false);
    addNewStudentSTab.add(rdoStreamTec, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 270, 100, -1));

    jLabel28.setBackground(new java.awt.Color(0, 0, 0));
    jLabel28.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
    addNewStudentSTab.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 220, 283, 90));

    cmbCategory.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    cmbCategory.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Primary", "Ordinary Level (O/L)", "Advance Level (A/L)", "Other" }));
    cmbCategory.addItemListener(new java.awt.event.ItemListener() {
        public void itemStateChanged(java.awt.event.ItemEvent evt) {
            cmbCategoryItemStateChanged(evt);
        }
    });
    addNewStudentSTab.add(cmbCategory, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 180, 283, 30));

    btnSubmit.setBackground(new java.awt.Color(51, 0, 204));
    btnSubmit.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    btnSubmit.setForeground(new java.awt.Color(255, 255, 255));
    btnSubmit.setText("Submit");
    btnSubmit.setBorderPainted(false);
    btnSubmit.setFocusPainted(false);
    btnSubmit.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnSubmitActionPerformed(evt);
        }
    });
    addNewStudentSTab.add(btnSubmit, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 490, 120, 40));

    btnReset.setBackground(new java.awt.Color(51, 0, 204));
    btnReset.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    btnReset.setForeground(new java.awt.Color(255, 255, 255));
    btnReset.setText("Reset");
    btnReset.setBorderPainted(false);
    btnReset.setFocusPainted(false);
    btnReset.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnResetActionPerformed(evt);
        }
    });
    addNewStudentSTab.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 490, 120, 40));

    rdoFemale.setBackground(new java.awt.Color(255, 255, 255));
    btngGender.add(rdoFemale);
    rdoFemale.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    rdoFemale.setText("Female");
    rdoFemale.setFocusPainted(false);
    addNewStudentSTab.add(rdoFemale, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, -1, -1));

    rdoMale.setBackground(new java.awt.Color(255, 255, 255));
    btngGender.add(rdoMale);
    rdoMale.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    rdoMale.setText("Male");
    rdoMale.setFocusPainted(false);
    addNewStudentSTab.add(rdoMale, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 260, -1, -1));

    txtRegNo.setEditable(false);
    txtRegNo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    addNewStudentSTab.add(txtRegNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 283, 30));

    jLabel65.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel65.setText("Reg No.");
    addNewStudentSTab.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 164, 30));

    getContentPane().add(addNewStudentSTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 83, 1140, 540));

    enrollTab.setBackground(new java.awt.Color(255, 255, 255));
    enrollTab.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel30.setBackground(new java.awt.Color(255, 255, 255));
    jLabel30.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
    jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel30.setText("Enroll For Class");
    enrollTab.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1140, 30));

    studentDetailsTab.setBackground(new java.awt.Color(255, 255, 255));
    studentDetailsTab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

    jLabel35.setBackground(new java.awt.Color(255, 255, 255));
    jLabel35.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel35.setText("Student Details");

    jLabel38.setBackground(new java.awt.Color(255, 255, 255));
    jLabel38.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel38.setText("Reg ID");

    lblReg.setBackground(new java.awt.Color(255, 255, 255));
    lblReg.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

    jLabel40.setBackground(new java.awt.Color(255, 255, 255));
    jLabel40.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel40.setText("Name");

    lblName.setBackground(new java.awt.Color(255, 255, 255));
    lblName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

    jLabel42.setBackground(new java.awt.Color(255, 255, 255));
    jLabel42.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel42.setText("Address");

    lblGrade.setBackground(new java.awt.Color(255, 255, 255));
    lblGrade.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

    jLabel47.setBackground(new java.awt.Color(255, 255, 255));
    jLabel47.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel47.setText("Grade");

    jTextArea1.setColumns(20);
    jTextArea1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jTextArea1.setRows(5);
    jScrollPane2.setViewportView(jTextArea1);

    javax.swing.GroupLayout studentDetailsTabLayout = new javax.swing.GroupLayout(studentDetailsTab);
    studentDetailsTab.setLayout(studentDetailsTabLayout);
    studentDetailsTabLayout.setHorizontalGroup(
        studentDetailsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(studentDetailsTabLayout.createSequentialGroup()
            .addGroup(studentDetailsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(studentDetailsTabLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
                .addGroup(studentDetailsTabLayout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addGroup(studentDetailsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(studentDetailsTabLayout.createSequentialGroup()
                            .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lblReg, javax.swing.GroupLayout.DEFAULT_SIZE, 284, Short.MAX_VALUE))
                        .addGroup(studentDetailsTabLayout.createSequentialGroup()
                            .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(studentDetailsTabLayout.createSequentialGroup()
                            .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jScrollPane2))
                        .addGroup(studentDetailsTabLayout.createSequentialGroup()
                            .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(lblGrade, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
            .addContainerGap())
    );
    studentDetailsTabLayout.setVerticalGroup(
        studentDetailsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(studentDetailsTabLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(studentDetailsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblReg, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(studentDetailsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(studentDetailsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(studentDetailsTabLayout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE))
                .addGroup(studentDetailsTabLayout.createSequentialGroup()
                    .addGap(30, 30, 30)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
            .addGroup(studentDetailsTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel47, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(lblGrade, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(88, 88, 88))
    );

    enrollTab.add(studentDetailsTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 460, 440));

    enrollSuccesTab.setBackground(new java.awt.Color(255, 255, 255));
    enrollSuccesTab.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

    jLabel44.setBackground(new java.awt.Color(255, 255, 255));
    jLabel44.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel44.setText("Successfully Enroll For Class");

    jLabel45.setBackground(new java.awt.Color(255, 255, 255));
    jLabel45.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel45.setText("Reg ID");

    jLabel48.setBackground(new java.awt.Color(255, 255, 255));
    jLabel48.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

    jLabel50.setBackground(new java.awt.Color(255, 255, 255));
    jLabel50.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel50.setText("Name");

    jLabel51.setBackground(new java.awt.Color(255, 255, 255));
    jLabel51.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

    jLabel52.setBackground(new java.awt.Color(255, 255, 255));
    jLabel52.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel52.setText("Class Code");

    jLabel53.setBackground(new java.awt.Color(255, 255, 255));
    jLabel53.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

    jLabel54.setBackground(new java.awt.Color(255, 255, 255));
    jLabel54.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

    jLabel55.setBackground(new java.awt.Color(255, 255, 255));
    jLabel55.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel55.setText("Grade");

    jLabel56.setBackground(new java.awt.Color(255, 255, 255));
    jLabel56.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel56.setText("Teacher");

    jLabel57.setBackground(new java.awt.Color(255, 255, 255));
    jLabel57.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

    jLabel58.setBackground(new java.awt.Color(255, 255, 255));
    jLabel58.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel58.setText("Subject");

    jLabel59.setBackground(new java.awt.Color(255, 255, 255));
    jLabel59.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N

    jButton11.setBackground(new java.awt.Color(51, 0, 204));
    jButton11.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jButton11.setForeground(new java.awt.Color(255, 255, 255));
    jButton11.setText("View Time Table");
    jButton11.setBorderPainted(false);
    jButton11.setFocusPainted(false);
    jButton11.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton11ActionPerformed(evt);
        }
    });

    javax.swing.GroupLayout enrollSuccesTabLayout = new javax.swing.GroupLayout(enrollSuccesTab);
    enrollSuccesTab.setLayout(enrollSuccesTabLayout);
    enrollSuccesTabLayout.setHorizontalGroup(
        enrollSuccesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(enrollSuccesTabLayout.createSequentialGroup()
            .addGroup(enrollSuccesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(enrollSuccesTabLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel44, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(enrollSuccesTabLayout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addGroup(enrollSuccesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(enrollSuccesTabLayout.createSequentialGroup()
                            .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(enrollSuccesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(enrollSuccesTabLayout.createSequentialGroup()
                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel48, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(enrollSuccesTabLayout.createSequentialGroup()
                                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel51, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(enrollSuccesTabLayout.createSequentialGroup()
                                .addComponent(jLabel52, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(enrollSuccesTabLayout.createSequentialGroup()
                                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(enrollSuccesTabLayout.createSequentialGroup()
                            .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 30, Short.MAX_VALUE)))
            .addContainerGap())
        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, enrollSuccesTabLayout.createSequentialGroup()
            .addGap(0, 0, Short.MAX_VALUE)
            .addComponent(jButton11)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    enrollSuccesTabLayout.setVerticalGroup(
        enrollSuccesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(enrollSuccesTabLayout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(18, 18, 18)
            .addGroup(enrollSuccesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel48, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(enrollSuccesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel51, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(enrollSuccesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jLabel52, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                .addComponent(jLabel53, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(enrollSuccesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(enrollSuccesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel56, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel57, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(enrollSuccesTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel58, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel59, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(36, 36, 36)
            .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(31, Short.MAX_VALUE))
    );

    enrollTab.add(enrollSuccesTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 460, 440));

    jLabel31.setBackground(new java.awt.Color(255, 255, 255));
    jLabel31.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel31.setText("NIC / Reg ID");
    enrollTab.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 140, 90, 30));

    txtNIC.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    txtNIC.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            txtNICKeyPressed(evt);
        }
    });
    enrollTab.add(txtNIC, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 140, 350, 30));

    jLabel32.setBackground(new java.awt.Color(255, 255, 255));
    jLabel32.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel32.setText("Class Code");
    enrollTab.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 90, 30));

    jLabel33.setBackground(new java.awt.Color(255, 255, 255));
    jLabel33.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel33.setText("Subject");
    enrollTab.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 90, 30));

    txtTeacher.setEditable(false);
    txtTeacher.setBackground(new java.awt.Color(255, 255, 255));
    txtTeacher.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    enrollTab.add(txtTeacher, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 390, 350, 30));

    jLabel34.setBackground(new java.awt.Color(255, 255, 255));
    jLabel34.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel34.setText("Student Name");
    enrollTab.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 190, -1, 30));

    txtsName.setEditable(false);
    txtsName.setBackground(new java.awt.Color(255, 255, 255));
    txtsName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    txtsName.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            txtsNameActionPerformed(evt);
        }
    });
    enrollTab.add(txtsName, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 190, 350, 30));

    jLabel36.setBackground(new java.awt.Color(255, 255, 255));
    jLabel36.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel36.setText("Grade");
    enrollTab.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 340, 90, 30));

    jLabel37.setBackground(new java.awt.Color(255, 255, 255));
    jLabel37.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel37.setText("Teacher");
    enrollTab.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 390, 90, 30));

    jButton7.setBackground(new java.awt.Color(51, 0, 204));
    jButton7.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jButton7.setForeground(new java.awt.Color(255, 255, 255));
    jButton7.setText("Clear");
    jButton7.setBorderPainted(false);
    jButton7.setFocusPainted(false);
    jButton7.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton7ActionPerformed(evt);
        }
    });
    enrollTab.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, 90, 40));

    jButton8.setBackground(new java.awt.Color(51, 0, 204));
    jButton8.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jButton8.setForeground(new java.awt.Color(255, 255, 255));
    jButton8.setText("Enroll");
    jButton8.setBorderPainted(false);
    jButton8.setFocusPainted(false);
    jButton8.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton8ActionPerformed(evt);
        }
    });
    enrollTab.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 430, 90, 40));

    txtClasscode1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    txtClasscode1.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyPressed(java.awt.event.KeyEvent evt) {
            txtClasscode1KeyPressed(evt);
        }
    });
    enrollTab.add(txtClasscode1, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 240, 350, 30));

    txtSubject.setEditable(false);
    txtSubject.setBackground(new java.awt.Color(255, 255, 255));
    txtSubject.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    enrollTab.add(txtSubject, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 290, 350, 30));

    txtEGrade.setEditable(false);
    txtEGrade.setBackground(new java.awt.Color(255, 255, 255));
    txtEGrade.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    enrollTab.add(txtEGrade, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 350, 30));

    txtEnrollid.setEditable(false);
    txtEnrollid.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    enrollTab.add(txtEnrollid, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 100, 350, 30));

    jLabel39.setBackground(new java.awt.Color(255, 255, 255));
    jLabel39.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel39.setText("Enroll ID");
    enrollTab.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 90, 30));

    getContentPane().add(enrollTab, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 83, 1140, 540));

    enrollStudentsPane.setBackground(new java.awt.Color(255, 255, 255));
    enrollStudentsPane.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

    jLabel60.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
    jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel60.setText("Enrolled Students");
    enrollStudentsPane.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1140, 30));

    jLabel61.setBackground(new java.awt.Color(255, 255, 255));
    jLabel61.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    jLabel61.setText("Student NIC / Reg ID");
    enrollStudentsPane.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 150, 30));

    txtVMReg.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
    txtVMReg.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            txtVMRegKeyReleased(evt);
        }
    });
    enrollStudentsPane.add(txtVMReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 364, 30));

    btnEnrollDelete.setBackground(new java.awt.Color(51, 0, 255));
    btnEnrollDelete.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
    btnEnrollDelete.setForeground(new java.awt.Color(255, 255, 255));
    btnEnrollDelete.setText("Delete");
    btnEnrollDelete.setBorderPainted(false);
    btnEnrollDelete.setFocusPainted(false);
    btnEnrollDelete.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnEnrollDeleteActionPerformed(evt);
        }
    });
    enrollStudentsPane.add(btnEnrollDelete, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 500, 110, -1));

    tblEnrollData.setAutoCreateRowSorter(true);
    tblEnrollData.setModel(new javax.swing.table.DefaultTableModel(
        new Object [][] {

        },
        new String [] {
            "Enroll ID", "Class ID", "Subject", "Student Reg No", "Student Name", "Class Name"
        }
    ) {
        boolean[] canEdit = new boolean [] {
            false, false, false, false, false, false
        };

        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return canEdit [columnIndex];
        }
    });
    tblEnrollData.setFillsViewportHeight(true);
    tblEnrollData.setFocusable(false);
    tblEnrollData.setIntercellSpacing(new java.awt.Dimension(0, 0));
    tblEnrollData.setRowHeight(25);
    tblEnrollData.setSelectionBackground(new java.awt.Color(102, 153, 255));
    tblEnrollData.setShowVerticalLines(false);
    tblEnrollData.getTableHeader().setReorderingAllowed(false);
    jScrollPane4.setViewportView(tblEnrollData);
    if (tblEnrollData.getColumnModel().getColumnCount() > 0) {
        tblEnrollData.getColumnModel().getColumn(1).setResizable(false);
        tblEnrollData.getColumnModel().getColumn(2).setResizable(false);
        tblEnrollData.getColumnModel().getColumn(3).setResizable(false);
        tblEnrollData.getColumnModel().getColumn(5).setResizable(false);
    }

    enrollStudentsPane.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 1080, 390));

    getContentPane().add(enrollStudentsPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(18, 83, 1140, 540));

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
    getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 630, 100, 40));

    pack();
    setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    
    
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String text = txtSearch.getText().toUpperCase();
        search(text);
        txtSearch.setText("Enter Student Reg ID or NIC...");
    }//GEN-LAST:event_btnSearchActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        view.btnVupdate.setVisible(true);
        view.txtVNameInitial.setEditable(true);
        view.txtVadd1.setEditable(true);
        view.txtVadd2.setEditable(true);
        view.txtVadd3.setEditable(true);
        view.txtVcategory.setEditable(true);
        view.txtVcity.setEditable(true);
        view.txtVdob.setEditable(true);
        view.txtVexamyear.setEditable(true);
        view.txtVfullName.setEditable(true);
        view.txtVgender.setEditable(true);
        view.txtVgrade.setEditable(true);
        view.txtVnic.setEditable(false);
        view.txtVpname.setEditable(true);
        view.txtVpno.setEditable(true);
        view.txtVreg.setEditable(false);
        view.txtVschool.setEditable(true);
        view.txtVstream.setEditable(true);
        view.txtVMobNo.setEditable(true);
        view.lblViewTitle.setText("Update Student's Details");
        try{
            model = (DefaultTableModel) tblStudent.getModel();
            int index = tblStudent.getSelectedRow();
            String regid = model.getValueAt(index, 0).toString();
        
            Connection conn = DatabaseConnection.getcon();
            Statement st;
            try {
                st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from student where Reg_ID = '"+regid+"'");
                while(rs.next()){
                    view.txtVreg.setText(rs.getString("Reg_ID"));
                    view.txtVfullName.setText(rs.getString("Full_Name"));
                    view.txtVNameInitial.setText(rs.getString("Name_with_initials"));
                    view.txtVdob.setText(rs.getString("DoB"));
                    view.txtVcity.setText(rs.getString("City"));
                    view.txtVgender.setText(rs.getString("Gender"));
                    view.txtVadd1.setText(rs.getString("Address1"));
                    view.txtVadd2.setText(rs.getString("Address2"));
                    view.txtVadd3.setText(rs.getString("Address3"));
                    view.txtVMobNo.setText(rs.getString("Mobile_No"));
                    view.txtVnic.setText(rs.getString("NIC"));
                    view.txtVpname.setText(rs.getString("ParentsName"));
                    view.txtVpno.setText(rs.getString("ParentsMobile"));
                    view.txtVgrade.setText(rs.getString("Grade"));
                    view.txtVcategory.setText(rs.getString("Category"));
                    view.txtVstream.setText(rs.getString("Stream"));
                    view.txtVexamyear.setText(rs.getString("Exam_Year"));
                    view.txtVschool.setText(rs.getString("School_Name"));
                }
                view.setVisible(true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Something went gone wrong...!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception ex){
            view.setVisible(false);
            JOptionPane.showMessageDialog(rootPane, "Please select a row from table!...");
           
        }
    }//GEN-LAST:event_jButton4ActionPerformed
    
    DataView view = new DataView();
    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        
        view.btnVupdate.setVisible(false);
        view.txtVNameInitial.setEditable(false);
        view.txtVadd1.setEditable(false);
        view.txtVadd2.setEditable(false);
        view.txtVadd3.setEditable(false);
        view.txtVcategory.setEditable(false);
        view.txtVcity.setEditable(false);
        view.txtVdob.setEditable(false);
        view.txtVexamyear.setEditable(false);
        view.txtVfullName.setEditable(false);
        view.txtVgender.setEditable(false);
        view.txtVgrade.setEditable(false);
        view.txtVnic.setEditable(false);
        view.txtVpname.setEditable(false);
        view.txtVpno.setEditable(false);
        view.txtVreg.setEditable(false);
        view.txtVschool.setEditable(false);
        view.txtVstream.setEditable(false);
        view.txtVMobNo.setEditable(false);
        view.lblViewTitle.setText("Student Details");
        try{
        model = (DefaultTableModel) tblStudent.getModel();
        int index = tblStudent.getSelectedRow();
        String regid = model.getValueAt(index, 0).toString();
        
        
            Connection conn = DatabaseConnection.getcon();
            Statement st;
            try {
                st = conn.createStatement();
                ResultSet rs = st.executeQuery("select * from student where Reg_ID = '"+regid+"'");
                while(rs.next()){
                    view.txtVreg.setText(rs.getString("Reg_ID"));
                    view.txtVfullName.setText(rs.getString("Full_Name"));
                    view.txtVNameInitial.setText(rs.getString("Name_with_initials"));
                    view.txtVdob.setText(rs.getString("DoB"));
                    view.txtVcity.setText(rs.getString("City"));
                    view.txtVgender.setText(rs.getString("Gender"));
                    view.txtVadd1.setText(rs.getString("Address1"));
                    view.txtVadd2.setText(rs.getString("Address2"));
                    view.txtVadd3.setText(rs.getString("Address3"));
                    view.txtVMobNo.setText(rs.getString("Mobile_No"));
                    view.txtVnic.setText(rs.getString("NIC"));
                    view.txtVpname.setText(rs.getString("ParentsName"));
                    view.txtVpno.setText(rs.getString("ParentsMobile"));
                    view.txtVgrade.setText(rs.getString("Grade"));
                    view.txtVcategory.setText(rs.getString("Category"));
                    view.txtVstream.setText(rs.getString("Stream"));
                    view.txtVexamyear.setText(rs.getString("Exam_Year"));
                    view.txtVschool.setText(rs.getString("School_Name"));
                }
                view.setVisible(true);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(rootPane, "Something went gone wrong...!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }catch(Exception ex){
            view.setVisible(false);
            JOptionPane.showMessageDialog(rootPane, "Please select a row from table!...");
           
        }
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void overviewBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_overviewBtnMouseClicked
        overviewBtn.setOpaque(true);
        overviewBtn.setBackground(new Color(51,6,149));
        addNewBtn.setBackground(new Color(51,51,51));
        enrollBtn.setBackground(new Color(51,51,51));
        viewMarkBtn.setBackground(new Color(51,51,51));
        
        viewOverview.setVisible(true);
        addNewStudentSTab.setVisible(false);
        enrollTab.setVisible(false);
        enrollStudentsPane.setVisible(false);

    }//GEN-LAST:event_overviewBtnMouseClicked
    
    private void addNewBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addNewBtnMouseClicked
        addNewBtn.setOpaque(true);
        addNewBtn.setBackground(new Color(51,6,149));
        overviewBtn.setBackground(new Color(51,51,51));
        enrollBtn.setBackground(new Color(51,51,51));
        viewMarkBtn.setBackground(new Color(51,51,51));
        
        viewOverview.setVisible(false);
        addNewStudentSTab.setVisible(true);
        enrollTab.setVisible(false);
        enrollStudentsPane.setVisible(false);
        
    }//GEN-LAST:event_addNewBtnMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtAddress1.setText(null);
        txtAddress2.setText(null);
        txtAddress3.setText(null);
        txtDOB.setText(null);
        txtFullName.setText(null);
        txtGrade.setText(null);
        txtMobileNo.setText(null);
        txtNic.setText(null);
        txtNamewithInitials.setText(null);
        txtParentMobileNo.setText(null);
        txtParentName.setText(null);
        txtRegNo.setText("PHD");
        txtSchoolName.setText(null);
        btngGender.clearSelection();
        btngStream.clearSelection();
        cmbCategory.setSelectedIndex(0);
        cmbCity.setSelectedIndex(0);
        cmbExamYear.setSelectedIndex(0);
        
    }//GEN-LAST:event_btnResetActionPerformed

    private void enrollBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_enrollBtnMouseClicked
        enrollBtn.setOpaque(true);
        enrollBtn.setBackground(new Color(51,6,149));
        addNewBtn.setBackground(new Color(51,51,51));
        overviewBtn.setBackground(new Color(51,51,51));
        viewMarkBtn.setBackground(new Color(51,51,51));
        
        enrollTab.setVisible(true);
        viewOverview.setVisible(false);
        addNewStudentSTab.setVisible(false);
        enrollStudentsPane.setVisible(false);
    }//GEN-LAST:event_enrollBtnMouseClicked

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        
        studentDetailsTab.setVisible(false);
        try {
            Connection conn = DatabaseConnection.getcon();
            String sql = "insert into enroll(Enroll_ID, Std_Reg_No, Class) values('"+txtEnrollid.getText().toUpperCase()+"','"+lblReg.getText()+"', '"+txtClasscode1.getText().toUpperCase()+"')";
            PreparedStatement st = conn.prepareStatement(sql);
            st.executeUpdate();
            enrollSuccesTab.setVisible(true);
            JOptionPane.showMessageDialog(rootPane, "Successfully enrolled "+lblReg.getText());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Invalid you enterd!... Please try again...");
        }
        try {
            Connection conn = DatabaseConnection.getcon();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select Name_with_initials from student where student.Reg_ID = '"+lblReg.getText()+"'");
            while(rs.next()){
                jLabel51.setText(rs.getString("student.Name_with_initials"));
            }
            jLabel53.setText(txtClasscode1.getText());
            jLabel48.setText(lblReg.getText());
            jLabel54.setText(txtEGrade.getText());
            jLabel57.setText(txtTeacher.getText());
            jLabel59.setText(txtSubject.getText());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Something went gone wrong...!", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        jButton7ActionPerformed(evt);
        
        int id = 0;
        try {
            Connection con = DatabaseConnection.getcon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(Enroll_ID) from enroll");
            while(rs.next()){
                id = rs.getInt("count(Enroll_ID)")+1;
                txtEnrollid.setText(id+"");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }//GEN-LAST:event_jButton8ActionPerformed

    private void txtsNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtsNameActionPerformed
        if(!(txtsName.getText().equals(""))){
            studentDetailsTab.setVisible(true);
            enrollSuccesTab.setVisible(false);
        }
    }//GEN-LAST:event_txtsNameActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        txtNIC.setText(null);
        txtsName.setText(null);
        txtClasscode1.setText(null);
        txtEGrade.setText(null);
        txtSubject.setText(null);
        txtTeacher.setText(null);
        txtEnrollid.setText(null);
        studentDetailsTab.setVisible(false);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void viewMarkBtnMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewMarkBtnMouseEntered
        
    }//GEN-LAST:event_viewMarkBtnMouseEntered

    private void viewMarkBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_viewMarkBtnMouseClicked
        viewMarkBtn.setOpaque(true);
        viewMarkBtn.setBackground(new Color(51,6,149));
        addNewBtn.setBackground(new Color(51,51,51));
        overviewBtn.setBackground(new Color(51,51,51));
        enrollBtn.setBackground(new Color(51,51,51));
        
        enrollStudentsPane.setVisible(true);
        viewOverview.setVisible(false);
        addNewStudentSTab.setVisible(false);
        enrollTab.setVisible(false);
    }//GEN-LAST:event_viewMarkBtnMouseClicked

    private void txtSearchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtSearchMouseClicked
        if(txtSearch.getText().equals("Enter Student Reg ID or NIC...")){
            txtSearch.setText(null);
        }
    }//GEN-LAST:event_txtSearchMouseClicked
        String gender=null; 
        String stream=null;
    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        
        if(rdoMale.isSelected()){
            gender = "Male";
        }else if(rdoFemale.isSelected()){
            gender = "FeMale";
        }
        
        if(rdoStreamPhy.isSelected()){
            stream = "Physical";
        }else if(rdoStreamBio.isSelected()){
            stream = "Biology";
        }else if(rdoStreamComm.isSelected()){
            stream = "Commerce";
        }else if(rdoStreamArts.isSelected()){
            stream = "Arts";
        }else if(rdoStreamTec.isSelected()){
            stream = "Technology";
        }else if(rdoStreamOther.isSelected()){
            stream = "Other";
        }
        
        Connection con = DatabaseConnection.getcon();
        String fullName = txtFullName.getText().toUpperCase();
        String nameWithIni = txtNamewithInitials.getText().toUpperCase();
        try {
            String sql = "insert into student values('"+txtRegNo.getText().toUpperCase()+"', '"+fullName+"', '"+nameWithIni+"', '"+txtDOB.getText()+"'"
                    + ", '"+cmbCity.getSelectedItem()+"', '"+gender+"', '"+txtAddress1.getText()+"', '"+txtAddress2.getText()+"'"
                    + ", '"+txtAddress3.getText()+"', '"+txtMobileNo.getText()+"', '"+txtNic.getText()+"', '"+txtParentName.getText()+"'"
                    + ", '"+txtParentMobileNo.getText()+"', '"+txtGrade.getText()+"', '"+cmbCategory.getSelectedItem()+"'"
                    + ", '"+stream+"', '"+cmbExamYear.getSelectedItem()+"', '"+txtSchoolName.getText()+"')";
            PreparedStatement st = con.prepareStatement(sql);
            st.executeUpdate();
            JOptionPane.showMessageDialog(rootPane, "Congratulations!... Sucsessfully added new student...");
            btnResetActionPerformed(evt);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(rootPane, "OOPS!... Sometime you entered invalid data...");
        }
        
    }//GEN-LAST:event_btnSubmitActionPerformed
    // automatic convert full name to initials.
    private void txtNamewithInitialsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtNamewithInitialsMouseClicked
        
        String name[] = txtFullName.getText().split(" ");
        char fst; String n = null;
        String count = null;
        for (int i = 0; i < name.length-1; i++) {
            fst = name[i].charAt(0);
            for (int j = 0; j < 1; j++) {
                
                n = fst+".";
                System.out.print(n);
            }
            
        }
    }//GEN-LAST:event_txtNamewithInitialsMouseClicked

    private void cmbCategoryItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCategoryItemStateChanged
        if(cmbCategory.getSelectedItem().equals("Advance Level (A/L)")){
            jLabel27.setVisible(true);
            jLabel28.setVisible(true);
            rdoStreamArts.setVisible(true);
            rdoStreamBio.setVisible(true);
            rdoStreamComm.setVisible(true);
            rdoStreamPhy.setVisible(true);
            rdoStreamTec.setVisible(true);
            rdoStreamOther.setVisible(true);
        }else{
            jLabel27.setVisible(false);
            jLabel28.setVisible(false);
            rdoStreamBio.setVisible(false);
            rdoStreamComm.setVisible(false);
            rdoStreamPhy.setVisible(false);
            rdoStreamTec.setVisible(false);
            rdoStreamOther.setVisible(false);
            rdoStreamArts.setVisible(false);
        }
    }//GEN-LAST:event_cmbCategoryItemStateChanged

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        String text = txtSearch.getText().toUpperCase();
        search(text);
    }//GEN-LAST:event_txtSearchKeyReleased

    private void txtNICKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNICKeyPressed
        try {
            Connection conn = DatabaseConnection.getcon();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from student where Reg_ID like '%"+txtNIC.getText().toUpperCase()+"%' or NIC like'%"+txtNIC.getText()+"%'");
            while(rs.next()){
                txtsName.setText(rs.getString("Name_with_initials"));
                lblReg.setText(rs.getString("Reg_ID"));
                lblName.setText(rs.getString("Name_with_initials"));
                jTextArea1.setText(rs.getString("Address1")+",\n"+rs.getString("Address2")+",\n"+rs.getString("Address3")+",\n "+rs.getString("City"));
                lblGrade.setText(rs.getString("Grade"));
            }
            studentDetailsTab.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Error...");
        }
    }//GEN-LAST:event_txtNICKeyPressed

    private void txtClasscode1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClasscode1KeyPressed
        try {
            Connection conn = DatabaseConnection.getcon();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from class inner join teacher on class.Teacher = teacher.Teacher_ID inner join subject on subject.Subject_ID = class.Subject where class.Class_ID like '%"+txtClasscode1.getText()+"%'");
            while(rs.next()){
                txtSubject.setText(rs.getString("class.Subject"));
                txtEGrade.setText(rs.getString("class.Grade"));
                txtTeacher.setText(rs.getString("teacher.Name_with_initials"));
                
            }
            studentDetailsTab.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e+"Error...");
        }
    }//GEN-LAST:event_txtClasscode1KeyPressed

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
    private void btnStudentDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStudentDeleteActionPerformed
        try {
            int row = tblStudent.getSelectedRow();
            String id = tblStudent.getValueAt(row, 0).toString();
            String sql = "delete from student where Reg_ID = '"+id+"'";
            
            adminpassconfirm admin = new adminpassconfirm();
            admin.getSql(sql);
            admin.setVisible(true);
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Please select a row...");
        }
    }//GEN-LAST:event_btnStudentDeleteActionPerformed

    private void txtVMRegKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtVMRegKeyReleased
        String str = txtVMReg.getText().toUpperCase();
        
        model = (DefaultTableModel) tblEnrollData.getModel();
        TableRowSorter<DefaultTableModel> trs = new TableRowSorter<>(model);
        tblEnrollData.setRowSorter(trs);
        trs.setRowFilter(RowFilter.regexFilter(str));
    }//GEN-LAST:event_txtVMRegKeyReleased

    
    private void btnEnrollDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnrollDeleteActionPerformed
        try {
            int row = tblEnrollData.getSelectedRow();
            String id = tblEnrollData.getValueAt(row, 0).toString();
            String sql = "delete from enroll where Enroll_ID = '"+id+"'";
            
            adminpassconfirm admin = new adminpassconfirm();
            admin.getSql(sql);
            admin.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "Please select a row...");
        }
    }//GEN-LAST:event_btnEnrollDeleteActionPerformed

    private void txtNicKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNicKeyReleased
        txtRegNo.setText(txtNic.getText().toUpperCase()+"PHD");
    }//GEN-LAST:event_txtNicKeyReleased

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        this.dispose();
        new TimeTable().setVisible(true);
    }//GEN-LAST:event_jButton11ActionPerformed

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
            java.util.logging.Logger.getLogger(Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Students.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Students().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JPanel addNewBtn;
    protected javax.swing.JPanel addNewStudentSTab;
    public javax.swing.JButton btnEnrollDelete;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    public javax.swing.JButton btnStudentDelete;
    private javax.swing.JButton btnSubmit;
    private javax.swing.ButtonGroup btngGender;
    private javax.swing.ButtonGroup btngStream;
    private javax.swing.JComboBox cmbCategory;
    private javax.swing.JComboBox cmbCity;
    private javax.swing.JComboBox cmbExamYear;
    protected javax.swing.JPanel enrollBtn;
    private javax.swing.JPanel enrollStudentsPane;
    private javax.swing.JPanel enrollSuccesTab;
    protected javax.swing.JPanel enrollTab;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblGrade;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblReg;
    protected javax.swing.JPanel overviewBtn;
    private javax.swing.JRadioButton rdoFemale;
    private javax.swing.JRadioButton rdoMale;
    private javax.swing.JRadioButton rdoStreamArts;
    private javax.swing.JRadioButton rdoStreamBio;
    private javax.swing.JRadioButton rdoStreamComm;
    private javax.swing.JRadioButton rdoStreamOther;
    private javax.swing.JRadioButton rdoStreamPhy;
    private javax.swing.JRadioButton rdoStreamTec;
    private javax.swing.JPanel studentDetailsTab;
    private javax.swing.JTable tblEnrollData;
    public javax.swing.JTable tblStudent;
    private javax.swing.JTextField txtAddress1;
    private javax.swing.JTextField txtAddress2;
    private javax.swing.JTextField txtAddress3;
    private javax.swing.JTextField txtClasscode1;
    private datechooser.beans.DateChooserCombo txtDOB;
    private javax.swing.JTextField txtEGrade;
    private javax.swing.JTextField txtEnrollid;
    private javax.swing.JTextField txtFullName;
    private javax.swing.JTextField txtGrade;
    private javax.swing.JTextField txtMobileNo;
    private javax.swing.JTextField txtNIC;
    private javax.swing.JTextField txtNamewithInitials;
    private javax.swing.JTextField txtNic;
    private javax.swing.JTextField txtParentMobileNo;
    private javax.swing.JTextField txtParentName;
    private javax.swing.JTextField txtRegNo;
    private javax.swing.JTextField txtSchoolName;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSubject;
    private javax.swing.JTextField txtTeacher;
    private javax.swing.JTextField txtVMReg;
    private javax.swing.JTextField txtsName;
    private javax.swing.JPanel viewMarkBtn;
    protected javax.swing.JPanel viewOverview;
    // End of variables declaration//GEN-END:variables
}
