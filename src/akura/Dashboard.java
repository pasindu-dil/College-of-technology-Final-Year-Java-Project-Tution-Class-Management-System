/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package akura;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import static java.time.LocalDateTime.now;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;


/**
 *
 * @author Pasindu01
 */
public class Dashboard extends javax.swing.JFrame {

    /**
     * Creates new form Dashboard
     */
    public Dashboard() {
        initComponents();
        
        setIcon();
        
        tblDashboard.getTableHeader().setOpaque(false);
        tblDashboard.getTableHeader().setBackground(new Color(196, 88, 53));
        tblDashboard.getTableHeader().setForeground(new Color(255, 255, 255));
        tblDashboard.setRowHeight(25);
        
        
        /* Dashboard Button */
        dashboard.setBackground(new Color(26,26,26));
        dashboardTab.setVisible(true);
        
        //Classes Count Display.
        
        DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("EEEE");
//        System.out.println(dtfDate.format(now));
        int value = 0;
        try {
            Statement st = dbCon();
            ResultSet rs = st.executeQuery("select count(*) from class where Schedule_date = '"+dtfDate.format(now())+"'");
            while(rs.next()){
                value = rs.getInt(1);
                lblTotalClassesinToday.setText(value+"");
            }
        } catch (Exception e) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, e);
        }
        
//          Exam Count
        DateTimeFormatter dtfEDate = DateTimeFormatter.ofPattern("yyyy-M-dd");
        int value2 = 0;
        Statement st = dbCon();
        try {
            ResultSet rs = st.executeQuery("select count(*) from exam where ExamDate = '"+dtfEDate.format(now())+"' ");
            while(rs.next()){
                value2 = rs.getInt(1);
                lblExamsinToday.setText(value2+"");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        /*Pie Chart*/
        
        DefaultPieDataset barchartdata1 = new DefaultPieDataset();
        barchartdata1.setValue("Total exams in today",value2);
        barchartdata1.setValue("Total class in today", value);
        
        JFreeChart barchart1 = ChartFactory.createPieChart("Class & Exams",barchartdata1,  true, true, false);
        
        PiePlot piechrt = new PiePlot();
        piechrt.setBackgroundPaint(Color.WHITE);
//        
        
        ChartPanel chartpanel1 = new ChartPanel(barchart1);
        chartpanel1.setPreferredSize(new Dimension(300,300));
        chartpanel1.setBackground(new Color(0,0,0,0));
        jPanel10.removeAll();
        jPanel10.add(chartpanel1, BorderLayout.CENTER);
        jPanel10.validate();
        
        DateTimeFormatter dtfDatePay = DateTimeFormatter.ofPattern("yyyy-MMMM");
        try {
            st = dbCon();
            ResultSet rs = st.executeQuery("select sum(Payment), Month from payment where Month = '"+dtfDatePay.format(now())+"'");
            while(rs.next()){
                lblMonthlyIncome.setText(rs.getString("sum(Payment)"));
            }
        } catch (Exception e) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, e);
        }
        
        /* Area Chart */
        DefaultCategoryDataset barchartdata = new DefaultCategoryDataset();
        
        
        double amount = 0;
        try {
            st = dbCon();
            ResultSet rs = st.executeQuery("select sum(Payment), Month from payment group by (Month) order by(Date) ASC");
            while(rs.next()){
                amount = rs.getDouble("sum(payment)");
                barchartdata.setValue(amount, "Amount", rs.getString("Month"));
            }
        } catch (Exception e) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, e);
        }
        
        
        
        JFreeChart barchart = ChartFactory.createAreaChart("Total Income", "Month", "Amount", barchartdata, PlotOrientation.VERTICAL, true, true, false);
        CategoryPlot barchrt = barchart.getCategoryPlot();
        barchrt.setRangeGridlinePaint(Color.BLUE);
        barchart.setBackgroundPaint(Color.WHITE);
        
        
        ChartPanel chartpanel = new ChartPanel(barchart);
        chartpanel.setPreferredSize(new Dimension(400,200));
        chartpanel.setBackground(new Color(0,0,0,0));
        jPanel9.removeAll();
        jPanel9.add(chartpanel, BorderLayout.CENTER);
        jPanel9.validate();
        
//        Date time display on dashboard header
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE yyyy-MMM-dd");
        LocalDateTime now = LocalDateTime.now();
        lblDateTime.setText(dtf.format(now));
        
//        Student Count Display
        try {
            Connection con = DatabaseConnection.getcon();
            Statement st1 = con.createStatement();
            ResultSet rs = st1.executeQuery("select count(*) from student");
            while(rs.next()){
                String value1 = rs.getString(1);
                lblStudentCount.setText(value1);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(chartpanel, e);
        }
        
//        DashBoard table data method
        dashboardTblData();
        
        
    }
    
    private void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("icon.jpg")));
    }
    
    
//    Main Database Connection Method for Dashboard Class.
    public Statement dbCon(){
        
        try {
            Connection con = DatabaseConnection.getcon();
            Statement st = con.createStatement();
            return st;
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    // Default Table Model for Dashboard Class
    DefaultTableModel model = new DefaultTableModel();
    
    //Dashboard tabel data view coding.
    public void dashboardTblData(){
        Statement st = dbCon();
        try {
            ResultSet rs = st.executeQuery("select * from class c inner join subject s on c.Subject = s.Subject_ID inner join teacher t on t.Teacher_ID = c.Teacher");
            while(rs.next()){
                String arry[] = {rs.getString("c.Class_ID"),rs.getString("t.Name_with_initials"),rs.getString("c.Grade"),rs.getString("s.Subject_Name"),rs.getString("c.Schedule_date"), rs.getString("c.Sche_time")};
                model = (DefaultTableModel) tblDashboard.getModel();
                model.addRow(arry);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        lblDateTime = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblUserPosition = new javax.swing.JLabel();
        dashboard = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        Student = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        Teacher = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Classes = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        classTimeTable = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        examTimeTable = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        Exam = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        Mark = new javax.swing.JPanel();
        jLabel29 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        Report = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        logout = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        dashboardTab = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lblStudentCount = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        lblTotalClassesinToday = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblExamsinToday = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblMonthlyIncome = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDashboard = new javax.swing.JTable();
        jPanel12 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        lblSetting = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("අකුර Class Management System");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Iskoola Pota", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("අකුර System");

        jPanel13.setBackground(new java.awt.Color(255, 0, 0));
        jPanel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel13MouseClicked(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setText("Payments & Cards");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel14.setBackground(new java.awt.Color(0, 153, 0));
        jPanel14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel14MouseClicked(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Add Subjects");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel15.setBackground(new java.awt.Color(102, 102, 255));
        jPanel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel15MouseClicked(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Add New Student");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel16.setBackground(new java.awt.Color(69, 71, 69));
        jPanel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel16MouseClicked(evt);
            }
        });

        jLabel33.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("Add New Teacher");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel33, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        lblDateTime.setBackground(new java.awt.Color(255, 255, 255));
        lblDateTime.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblDateTime.setForeground(new java.awt.Color(204, 0, 0));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDateTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(4, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDateTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_user_24px.png"))); // NOI18N
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 68, 70));

        jLabel3.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Welcome");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 11, 128, 30));

        lblUserPosition.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblUserPosition.setForeground(new java.awt.Color(204, 255, 255));
        lblUserPosition.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel4.add(lblUserPosition, new org.netbeans.lib.awtextra.AbsoluteConstraints(86, 47, 128, -1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(jPanel4, gridBagConstraints);

        dashboard.setBackground(new java.awt.Color(51, 51, 51));
        dashboard.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboardMouseClicked(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setLabelFor(dashboard);
        jLabel5.setText("Dashboard");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_overview_32px_1.png"))); // NOI18N

        javax.swing.GroupLayout dashboardLayout = new javax.swing.GroupLayout(dashboard);
        dashboard.setLayout(dashboardLayout);
        dashboardLayout.setHorizontalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dashboardLayout.createSequentialGroup()
                        .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10))))
        );
        dashboardLayout.setVerticalGroup(
            dashboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(dashboard, gridBagConstraints);

        Student.setBackground(new java.awt.Color(51, 51, 51));
        Student.setEnabled(false);
        Student.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                StudentMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                StudentMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                StudentMouseExited(evt);
            }
        });

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_student_registration_30px_1.png"))); // NOI18N

        jLabel31.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setLabelFor(dashboard);
        jLabel31.setText("Students");

        javax.swing.GroupLayout StudentLayout = new javax.swing.GroupLayout(Student);
        Student.setLayout(StudentLayout);
        StudentLayout.setHorizontalGroup(
            StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );
        StudentLayout.setVerticalGroup(
            StudentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(StudentLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 44;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel2.add(Student, gridBagConstraints);

        Teacher.setBackground(new java.awt.Color(51, 51, 51));
        Teacher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TeacherMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TeacherMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TeacherMouseExited(evt);
            }
        });

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_teacher_30px.png"))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setLabelFor(dashboard);
        jLabel7.setText("Teachers");

        javax.swing.GroupLayout TeacherLayout = new javax.swing.GroupLayout(Teacher);
        Teacher.setLayout(TeacherLayout);
        TeacherLayout.setHorizontalGroup(
            TeacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TeacherLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(TeacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );
        TeacherLayout.setVerticalGroup(
            TeacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TeacherLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.ipady = -14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(114, 0, 0, 0);
        jPanel2.add(Teacher, gridBagConstraints);

        Classes.setBackground(new java.awt.Color(51, 51, 51));
        Classes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ClassesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ClassesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ClassesMouseExited(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setLabelFor(dashboard);
        jLabel8.setText("Classes");

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_google_classroom_32px.png"))); // NOI18N

        javax.swing.GroupLayout ClassesLayout = new javax.swing.GroupLayout(Classes);
        Classes.setLayout(ClassesLayout);
        ClassesLayout.setHorizontalGroup(
            ClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ClassesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        ClassesLayout.setVerticalGroup(
            ClassesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ClassesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = -1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(114, 0, 0, 0);
        jPanel2.add(Classes, gridBagConstraints);

        classTimeTable.setBackground(new java.awt.Color(51, 51, 51));
        classTimeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                classTimeTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                classTimeTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                classTimeTableMouseExited(evt);
            }
        });

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_timetable_24px.png"))); // NOI18N

        jLabel9.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setLabelFor(dashboard);
        jLabel9.setText("Class Time Table");

        javax.swing.GroupLayout classTimeTableLayout = new javax.swing.GroupLayout(classTimeTable);
        classTimeTable.setLayout(classTimeTableLayout);
        classTimeTableLayout.setHorizontalGroup(
            classTimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(classTimeTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        classTimeTableLayout.setVerticalGroup(
            classTimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, classTimeTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = -1;
        gridBagConstraints.ipady = -13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(108, 0, 0, 0);
        jPanel2.add(classTimeTable, gridBagConstraints);

        examTimeTable.setBackground(new java.awt.Color(51, 51, 51));
        examTimeTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                examTimeTableMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                examTimeTableMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                examTimeTableMouseExited(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setLabelFor(dashboard);
        jLabel10.setText("Exam Time");

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_timetable_24px_1.png"))); // NOI18N

        jLabel32.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setLabelFor(dashboard);
        jLabel32.setText("Table");

        javax.swing.GroupLayout examTimeTableLayout = new javax.swing.GroupLayout(examTimeTable);
        examTimeTable.setLayout(examTimeTableLayout);
        examTimeTableLayout.setHorizontalGroup(
            examTimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(examTimeTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(examTimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(examTimeTableLayout.createSequentialGroup()
                        .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 2, Short.MAX_VALUE))
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        examTimeTableLayout.setVerticalGroup(
            examTimeTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, examTimeTableLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(108, 0, 0, 0);
        jPanel2.add(examTimeTable, gridBagConstraints);

        Exam.setBackground(new java.awt.Color(51, 51, 51));
        Exam.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExamMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ExamMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ExamMouseExited(evt);
            }
        });

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_exam_24px.png"))); // NOI18N

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setLabelFor(dashboard);
        jLabel11.setText("Exam");

        javax.swing.GroupLayout ExamLayout = new javax.swing.GroupLayout(Exam);
        Exam.setLayout(ExamLayout);
        ExamLayout.setHorizontalGroup(
            ExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );
        ExamLayout.setVerticalGroup(
            ExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ExamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = 66;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(108, 0, 0, 0);
        jPanel2.add(Exam, gridBagConstraints);

        Mark.setBackground(new java.awt.Color(51, 51, 51));
        Mark.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MarkMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                MarkMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                MarkMouseExited(evt);
            }
        });

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_get_quote_24px.png"))); // NOI18N

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setLabelFor(dashboard);
        jLabel12.setText("Student Marks");

        javax.swing.GroupLayout MarkLayout = new javax.swing.GroupLayout(Mark);
        Mark.setLayout(MarkLayout);
        MarkLayout.setHorizontalGroup(
            MarkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarkLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MarkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );
        MarkLayout.setVerticalGroup(
            MarkLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MarkLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                .addGap(15, 15, 15))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(108, 0, 0, 0);
        jPanel2.add(Mark, gridBagConstraints);

        Report.setBackground(new java.awt.Color(51, 51, 51));
        Report.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReportMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ReportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ReportMouseExited(evt);
            }
        });

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_pie_chart_report_24px.png"))); // NOI18N

        jLabel14.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setLabelFor(dashboard);
        jLabel14.setText("Reports");

        javax.swing.GroupLayout ReportLayout = new javax.swing.GroupLayout(Report);
        Report.setLayout(ReportLayout);
        ReportLayout.setHorizontalGroup(
            ReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ReportLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                .addContainerGap())
        );
        ReportLayout.setVerticalGroup(
            ReportLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ReportLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel30, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(108, 0, 0, 0);
        jPanel2.add(Report, gridBagConstraints);

        logout.setBackground(new java.awt.Color(204, 0, 0));
        logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setLabelFor(dashboard);
        jLabel13.setText("Log Out");

        javax.swing.GroupLayout logoutLayout = new javax.swing.GroupLayout(logout);
        logout.setLayout(logoutLayout);
        logoutLayout.setHorizontalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                .addContainerGap())
        );
        logoutLayout.setVerticalGroup(
            logoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoutLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.ipadx = 49;
        gridBagConstraints.ipady = 79;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(108, 0, 0, 0);
        jPanel2.add(logout, gridBagConstraints);

        dashboardTab.setBackground(new java.awt.Color(204, 235, 243));

        jPanel3.setBackground(new java.awt.Color(255, 204, 204));
        jPanel3.setPreferredSize(new java.awt.Dimension(250, 83));

        lblStudentCount.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel36.setText("Students");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStudentCount, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblStudentCount, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel36)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(102, 102, 255));

        lblTotalClassesinToday.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel37.setText("Total Classes in Today");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotalClassesinToday, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTotalClassesinToday, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel37)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(255, 102, 102));
        jPanel6.setPreferredSize(new java.awt.Dimension(250, 83));

        lblExamsinToday.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N

        jLabel40.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel40.setText("Exams in Today");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblExamsinToday, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblExamsinToday, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel40)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(0, 153, 0));

        lblMonthlyIncome.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N

        jLabel42.setFont(new java.awt.Font("Dialog", 1, 13)); // NOI18N
        jLabel42.setText("Monthly Income");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMonthlyIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(49, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMonthlyIncome, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel42)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setLayout(new java.awt.CardLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setLayout(new java.awt.CardLayout());

        jPanel11.setBackground(new java.awt.Color(255, 51, 51));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 8, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        tblDashboard.setAutoCreateRowSorter(true);
        tblDashboard.setFont(new java.awt.Font("Dialog", 1, 12)); // NOI18N
        tblDashboard.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Class ID", "Teacher Name", "Grade", "Subject", "Date", "Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDashboard.setFillsViewportHeight(true);
        tblDashboard.setFocusable(false);
        tblDashboard.setGridColor(new java.awt.Color(255, 255, 255));
        tblDashboard.setIntercellSpacing(new java.awt.Dimension(0, 0));
        tblDashboard.setRowHeight(25);
        tblDashboard.setSelectionBackground(new java.awt.Color(102, 153, 255));
        tblDashboard.setShowVerticalLines(false);
        tblDashboard.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblDashboard);
        if (tblDashboard.getColumnModel().getColumnCount() > 0) {
            tblDashboard.getColumnModel().getColumn(2).setResizable(false);
            tblDashboard.getColumnModel().getColumn(3).setResizable(false);
            tblDashboard.getColumnModel().getColumn(4).setResizable(false);
            tblDashboard.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 148, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel8Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 51, 51));
        jLabel20.setText("Remainder !");

        jLabel21.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 51, 51));
        jLabel21.setText("Data will update in every 30 minutes");

        lblSetting.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        lblSetting.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSetting.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icons8_Settings_32px.png"))); // NOI18N
        lblSetting.setText("Settings");
        lblSetting.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSettingMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(lblSetting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSetting, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel21)))
                .addContainerGap())
        );

        javax.swing.GroupLayout dashboardTabLayout = new javax.swing.GroupLayout(dashboardTab);
        dashboardTab.setLayout(dashboardTabLayout);
        dashboardTabLayout.setHorizontalGroup(
            dashboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardTabLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(dashboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(dashboardTabLayout.createSequentialGroup()
                        .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(dashboardTabLayout.createSequentialGroup()
                        .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(dashboardTabLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        dashboardTabLayout.setVerticalGroup(
            dashboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dashboardTabLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(dashboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addGroup(dashboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8)
                .addGroup(dashboardTabLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(dashboardTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
                    .addComponent(dashboardTab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    
    private void ClassesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClassesMouseClicked
        new Classes().setVisible(true);
        
        
    }//GEN-LAST:event_ClassesMouseClicked

    private void logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutMouseClicked
        int x =JOptionPane.showConfirmDialog(Teacher, "Are you want to logout");
        if(x==0){
            new LoginPage().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_logoutMouseClicked

    private void ReportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReportMouseClicked
        new report().setVisible(true);
    }//GEN-LAST:event_ReportMouseClicked

    private void MarkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MarkMouseClicked
        new addMarks().setVisible(true);
    }//GEN-LAST:event_MarkMouseClicked

    private void ExamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExamMouseClicked
        new exam().setVisible(true);
    }//GEN-LAST:event_ExamMouseClicked

    private void examTimeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_examTimeTableMouseClicked
        new examTimeTable().setVisible(true);
    }//GEN-LAST:event_examTimeTableMouseClicked

    private void classTimeTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classTimeTableMouseClicked
        TimeTable timetbl = new TimeTable();
        timetbl.setVisible(true);
        timetbl.panelTimeTable.setVisible(false);
        
        
        
    }//GEN-LAST:event_classTimeTableMouseClicked

    private void jPanel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel15MouseClicked
        Students student = new Students();
        student.setVisible(true);
        student.addNewStudentSTab.setVisible(true);
        student.enrollTab.setVisible(false);
        student.viewOverview.setVisible(false);
        student.addNewBtn.setOpaque(true);
        student.addNewBtn.setBackground(new Color(51,6,149));
        student.overviewBtn.setBackground(new Color(51,51,51));
        student.enrollBtn.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_jPanel15MouseClicked

    private void jPanel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel16MouseClicked
        Teacher teacher = new Teacher();
        teacher.setVisible(true);
        teacher.paneAddTeacher.setBackground(new Color(51,6,149));
        teacher.paneOverview.setBackground(new Color(51,51,51));
        teacher.AddNewTeacherTab.setVisible(true);
        teacher.OverviewTab.setVisible(false);
    }//GEN-LAST:event_jPanel16MouseClicked

    private void ClassesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClassesMouseEntered
        Classes.setBackground(new Color(26,26,26));
    }//GEN-LAST:event_ClassesMouseEntered

    private void classTimeTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classTimeTableMouseEntered
        classTimeTable.setBackground(new Color(26,26,26));
    }//GEN-LAST:event_classTimeTableMouseEntered

    private void examTimeTableMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_examTimeTableMouseEntered
        examTimeTable.setBackground(new Color(26,26,26));        // TODO add your handling code here:
    }//GEN-LAST:event_examTimeTableMouseEntered

    private void ExamMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExamMouseEntered
        Exam.setBackground(new Color(26,26,26));
    }//GEN-LAST:event_ExamMouseEntered

    private void MarkMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MarkMouseEntered
        Mark.setBackground(new Color(26,26,26));
    }//GEN-LAST:event_MarkMouseEntered

    private void ReportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReportMouseEntered
        Report.setBackground(new Color(26,26,26));
    }//GEN-LAST:event_ReportMouseEntered

    private void ClassesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ClassesMouseExited
        Classes.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_ClassesMouseExited

    private void classTimeTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classTimeTableMouseExited
        classTimeTable.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_classTimeTableMouseExited

    private void examTimeTableMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_examTimeTableMouseExited
        examTimeTable.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_examTimeTableMouseExited

    private void ExamMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExamMouseExited
        Exam.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_ExamMouseExited

    private void MarkMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MarkMouseExited
        Mark.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_MarkMouseExited

    private void ReportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReportMouseExited
        Report.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_ReportMouseExited

    private void lblSettingMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSettingMouseClicked
        new Settings().setVisible(true);
    }//GEN-LAST:event_lblSettingMouseClicked

    private void jPanel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel13MouseClicked
        new Payments().setVisible(true);
    }//GEN-LAST:event_jPanel13MouseClicked

    private void jPanel14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel14MouseClicked
        new Subject().setVisible(true);
    }//GEN-LAST:event_jPanel14MouseClicked

    private void StudentMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentMouseExited
        Student.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_StudentMouseExited

    private void StudentMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentMouseEntered
        Student.setBackground(new Color(26,26,26));
    }//GEN-LAST:event_StudentMouseEntered
    
    userAuth ua = new userAuth();
    private void StudentMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_StudentMouseClicked
        new Students().setVisible(true);
        
        Students std = new Students();
        ua.setComponent(std.addNewBtn);
        
    }//GEN-LAST:event_StudentMouseClicked

    private void dashboardMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboardMouseClicked
        dashboardTab.setVisible(true);

        dashboard.setBackground(new Color(26,26,26));
        Student.setBackground(new Color(51,51,51));
        Teacher.setBackground(new Color(51,51,51));
        Classes.setBackground(new Color(51,51,51));
        classTimeTable.setBackground(new Color(51,51,51));
        examTimeTable.setBackground(new Color(51,51,51));
        Exam.setBackground(new Color(51,51,51));
        Mark.setBackground(new Color(51,51,51));
        Report.setBackground(new Color(51,51,51));

        try {
            Connection con = DatabaseConnection.getcon();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(*) from student");
            while(rs.next()){
                String value = rs.getString(1);
                lblStudentCount.setText(value);
            }
        } catch (Exception e) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, e);
        }

        //Dashboard label data coding.
        DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("EEEE");
        try {
            Statement st = dbCon();
            ResultSet rs = st.executeQuery("select count(*) from class where Schedule_date = '"+dtfDate.format(now())+"'");
            while(rs.next()){
                int value = rs.getInt(1);
                lblTotalClassesinToday.setText(value+"");
            }
        } catch (Exception e) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, e);
        }
        //        Statement st = dbCon();
        DateTimeFormatter dtfEDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Statement st = dbCon();
        try {
            ResultSet rs = st.executeQuery("select count(*) from exam where ExamDate = '"+dtfEDate.format(now())+"' ");
            while(rs.next()){
                int value1 = rs.getInt(1);
                lblExamsinToday.setText(value1+"");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_dashboardMouseClicked

    private void TeacherMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TeacherMouseExited
        Teacher.setBackground(new Color(51,51,51));
    }//GEN-LAST:event_TeacherMouseExited

    private void TeacherMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TeacherMouseEntered
        Teacher.setBackground(new Color(26,26,26));
    }//GEN-LAST:event_TeacherMouseEntered

    private void TeacherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TeacherMouseClicked
        new Teacher().setVisible(true);

    }//GEN-LAST:event_TeacherMouseClicked

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
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dashboard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Dashboard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Classes;
    private javax.swing.JPanel Exam;
    private javax.swing.JPanel Mark;
    private javax.swing.JPanel Report;
    public javax.swing.JPanel Student;
    private javax.swing.JPanel Teacher;
    private javax.swing.JPanel classTimeTable;
    private javax.swing.JPanel dashboard;
    private javax.swing.JPanel dashboardTab;
    private javax.swing.JPanel examTimeTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDateTime;
    private javax.swing.JLabel lblExamsinToday;
    private javax.swing.JLabel lblMonthlyIncome;
    public javax.swing.JLabel lblSetting;
    private javax.swing.JLabel lblStudentCount;
    private javax.swing.JLabel lblTotalClassesinToday;
    public javax.swing.JLabel lblUserPosition;
    private javax.swing.JPanel logout;
    private javax.swing.JTable tblDashboard;
    // End of variables declaration//GEN-END:variables
}
