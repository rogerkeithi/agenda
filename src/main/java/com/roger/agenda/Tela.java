/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.roger.agenda;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author roger
 */
public class Tela extends javax.swing.JFrame {
    private static int idUsuario;
    private static boolean logado;
    
    private static int id_contato_now;
    private static java.sql.Date dataSql;
    private static int id_compromisso_FK;
    
    private static ArrayList<Usuario> usuario_infos;
    private static ArrayList<Pessoa> pessoa_infos;
    
    private static ArrayList<Pessoa> pessoa_contato_list = new ArrayList<Pessoa>();
    private static ArrayList<Pessoa> pessoa_contato_selecionada = new ArrayList<Pessoa>(0);
    
    private static Image fotoPerfilContatoToSave;
    private static File fileFoto = new File("background.png");
    
    private static int id_compromisso_now;
    
    private String nome_contato;
    private Date data_nasc_contato;
    private long telefone1_contato;
    private long telefone2_contato;
    private String email_contato;
    
    private String logradouro_contato; 
    private int numero_contato;
    private String bairro_contato;
    private String complemento_contato;
    private int cep_contato;
    private String cidade_contato;     
    private String sigla_estado_contato;
    private String pais_contato;
    
    public Tela() {
        initComponents();
    }

    public void setLogin(int id_usuario, boolean logado_confirm){
        ImageIcon image = new ImageIcon("./util/calendar.png");  

        this.setIconImage(image.getImage());
        this.idUsuario = id_usuario;
        this.logado = logado_confirm;
        
        BuscaPessoaBD bpbd = new BuscaPessoaBD();
        
        bpbd.setPessoa(this.idUsuario);
        
        pessoa_infos = bpbd.getPessoa();
        
        this.jLabel3.setText("Olá, "+pessoa_infos.get(0).getNome()+", bem-vindo(a) de volta!");
        buscarTabela();
        
        listaInicio();
    }
    
    public void listaInicio(){
        
        Connection con;
        
        String query = "SELECT * FROM tb_compromisso WHERE ID_USUARIO = ? AND DATA_INICIO = ?";
        PreparedStatement ps;
        ResultSet rs;
        String titulo = "";
        String descricao = "";
        String local = "";
        String dataInicio = "";
        String dataFim = "";
        String hInicio = "";
        String hFim = "";
        try {
            ConnectionFactory cf = new ConnectionFactory();
            con = cf.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setInt(1, idUsuario);
            ps.setDate(2, dataSql);
            rs = ps.executeQuery();
            
            con.commit();
            
            
            while (rs.next()) {
                id_compromisso_now = rs.getInt("ID_COMPROMISSO");
                titulo = rs.getString("TITULO");
                descricao = rs.getString("DESCRICAO");
                local = rs.getString("LOCAL_COMPROMISSO");
                dataInicio = rs.getString("DATA_INICIO");
                dataFim = rs.getString("DATA_FINAL");
                hInicio = rs.getString("HORARIO_INICIO");
                hFim = rs.getString("HORARIO_FINAL");
                
                String id_comp = String.valueOf(id_compromisso_now);
                
                String[] objs = {titulo,dataInicio,dataFim,hInicio,hFim,id_comp};

                DefaultTableModel model3 = (DefaultTableModel) tabelCompromisso.getModel();

                model3.addRow(objs);
                
                
                
                
            }
            ps.close();
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    public void buscarTabela(){
    
        Connection con;
        
        String query = "SELECT * FROM tb_pessoa WHERE ID_PESSOA IN (SELECT ID_PESSOA_CONTATO FROM tb_contato_pertence WHERE ID_USUARIO = ?)";
        PreparedStatement ps;
        ResultSet rs;

        try {
            ConnectionFactory cf = new ConnectionFactory();
            con = cf.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();
            
            con.commit();
            
            while (rs.next()) {
                String id_contato = rs.getString("ID_PESSOA");
                boolean ativo = rs.getBoolean("ATIVO");
                String nome_selected = rs.getString("NOME");
                String email_selected = rs.getString("EMAIL");
                String tel1_selected = rs.getString("TELEFONE1");
                String tel2_selected = rs.getString("TELEFONE2");
                
                if(rs.getString("ID_USUARIO") == null && ativo == true){
                    String[] objs = {id_contato,nome_selected,email_selected,tel1_selected,tel2_selected};
                
                    DefaultTableModel model = (DefaultTableModel) tabelContatos.getModel();

                    model.addRow(objs);
                    
                    DefaultTableModel model2 = (DefaultTableModel) tabelContatosCompromisso.getModel();
                    
                    model2.addRow(objs);
                }
                
            }
            ps.close();
            con.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPrincipal = new javax.swing.JTabbedPane();
        pinicio = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jCalendar1 = new com.toedter.calendar.JCalendar();
        jButton1 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelCompromisso = new javax.swing.JTable();
        jButton7 = new javax.swing.JButton();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel46 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        titulocompromisso = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        dcAlteraData1 = new com.toedter.calendar.JDateChooser();
        jLabel39 = new javax.swing.JLabel();
        horaInicio = new javax.swing.JTextField();
        minInicio = new javax.swing.JTextField();
        horaFim = new javax.swing.JTextField();
        minFim = new javax.swing.JTextField();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelContatosCompromisso = new javax.swing.JTable();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        pcontatos = new javax.swing.JPanel();
        tabbedPaneContato = new javax.swing.JTabbedPane();
        paneListarContato = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelContatos = new javax.swing.JTable();
        jButton4 = new javax.swing.JButton();
        panelAdicionarContato = new javax.swing.JPanel();
        tituloAdicionarContato = new javax.swing.JLabel();
        labelFoto = new javax.swing.JLabel();
        btnVoltarAdicionarContato = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        fieldNomeContato = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        fieldEmailContato = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        fieldTelefone1Contato = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        fieldTelefone2Contato = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        tabbedPaneContato2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        showFotoPerfilContato = new javax.swing.JLabel();
        showNomeContato = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel16 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel17 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel18 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel22 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jLabel23 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        showLogradouroContato = new javax.swing.JLabel();
        showNumeroContato = new javax.swing.JLabel();
        showCidadeContato = new javax.swing.JLabel();
        showEstadoContato = new javax.swing.JLabel();
        showPaisContato = new javax.swing.JLabel();
        showIdadeContato = new javax.swing.JLabel();
        showEmailContato = new javax.swing.JLabel();
        showTelefone1Contato = new javax.swing.JLabel();
        showTelefone2Contato = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        btnSalvarAdicionarContato = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        comboBoxSiglaEstado = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        fieldNumeroContato = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        fieldCidadeContato = new javax.swing.JTextField();
        fieldPaisContato = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        fieldLogradouroContato = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        showFotoPerfilContato2 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jSeparator10 = new javax.swing.JSeparator();
        jSeparator11 = new javax.swing.JSeparator();
        jLabel27 = new javax.swing.JLabel();
        jSeparator12 = new javax.swing.JSeparator();
        jLabel28 = new javax.swing.JLabel();
        jSeparator13 = new javax.swing.JSeparator();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jSeparator14 = new javax.swing.JSeparator();
        jLabel31 = new javax.swing.JLabel();
        jSeparator15 = new javax.swing.JSeparator();
        jLabel32 = new javax.swing.JLabel();
        jSeparator16 = new javax.swing.JSeparator();
        jLabel33 = new javax.swing.JLabel();
        jSeparator17 = new javax.swing.JSeparator();
        jLabel34 = new javax.swing.JLabel();
        jSeparator18 = new javax.swing.JSeparator();
        dcAlteraData = new com.toedter.calendar.JDateChooser();
        tfAlteraEmail = new javax.swing.JTextField();
        tfAlteraTelefone1 = new javax.swing.JTextField();
        tfAlteraTelefone2 = new javax.swing.JTextField();
        tfAlteraLogradouro = new javax.swing.JTextField();
        tfAlteraNum = new javax.swing.JTextField();
        tfAlteraCidade = new javax.swing.JTextField();
        tfAlteraPais = new javax.swing.JTextField();
        cbAlteraEstado = new javax.swing.JComboBox<>();
        tfAlteraNome = new javax.swing.JTextField();
        btnSalvarAlteracoesContato = new javax.swing.JButton();
        btnExcluirContato = new javax.swing.JButton();
        Logout = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Jenda");
        setBackground(new java.awt.Color(38, 34, 26));
        setMinimumSize(new java.awt.Dimension(640, 480));

        tabbedPrincipal.setBackground(new java.awt.Color(51, 51, 51));
        tabbedPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(-3, -3, -3, -3));
        tabbedPrincipal.setToolTipText("");
        tabbedPrincipal.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 18)); // NOI18N
        tabbedPrincipal.setMinimumSize(new java.awt.Dimension(100, 200));
        tabbedPrincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tabbedPrincipalMouseReleased(evt);
            }
        });

        pinicio.setBackground(new java.awt.Color(51, 51, 51));
        pinicio.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 1, 1, 1));

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 4));

        jCalendar1.setBackground(new java.awt.Color(51, 51, 51));
        jCalendar1.setDecorationBackgroundColor(new java.awt.Color(51, 51, 51));
        jCalendar1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 14)); // NOI18N
        jCalendar1.setNullDateButtonText("");
        jCalendar1.setPreferredSize(new java.awt.Dimension(566, 169));
        jCalendar1.setSundayForeground(new java.awt.Color(255, 102, 102));
        jCalendar1.setTodayButtonText("");
        jCalendar1.setWeekOfYearVisible(false);
        jCalendar1.setWeekdayForeground(new java.awt.Color(255, 255, 255));
        jCalendar1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jCalendar1PropertyChange(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 153, 255));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Adicionar compromisso");
        jButton1.setBorder(javax.swing.BorderFactory.createEmptyBorder(-1, -1, -1, -1));
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        tabelCompromisso.setAutoCreateRowSorter(true);
        tabelCompromisso.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 18)); // NOI18N
        tabelCompromisso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Título", "Data Início", "Data Final", "Horario Início", "Horário Final", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelCompromisso.setGridColor(new java.awt.Color(51, 51, 51));
        tabelCompromisso.setRowHeight(40);
        tabelCompromisso.setRowMargin(10);
        tabelCompromisso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tabelCompromissoMouseReleased(evt);
            }
        });
        jScrollPane5.setViewportView(tabelCompromisso);

        jButton7.setBackground(new java.awt.Color(0, 153, 255));
        jButton7.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jButton7.setForeground(new java.awt.Color(255, 255, 255));
        jButton7.setText("Atualizar");
        jButton7.setBorder(javax.swing.BorderFactory.createEmptyBorder(-1, -1, -1, -1));
        jButton7.setBorderPainted(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(16, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 631, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jCalendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 551, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        jTabbedPane2.setBackground(new java.awt.Color(28, 28, 28));
        jTabbedPane2.setBorder(javax.swing.BorderFactory.createEmptyBorder(-3, -3, -3, -3));
        jTabbedPane2.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jTabbedPane2.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);
        jTabbedPane2.setEnabled(false);

        jPanel3.setBackground(new java.awt.Color(28, 28, 28));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));

        jPanel7.setMinimumSize(new java.awt.Dimension(300, 100));

        jLabel1.setBackground(new java.awt.Color(51, 51, 51));
        jLabel1.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 51, 51));
        jLabel1.setText("Meus Compromissos");

        jLabel2.setBackground(new java.awt.Color(51, 51, 51));
        jLabel2.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 51));
        jLabel2.setText("Para o dia...");

        jLabel3.setBackground(new java.awt.Color(51, 51, 51));
        jLabel3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 51, 51));
        jLabel3.setText("Olá, jLabel3");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(338, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(21, 21, 21))
        );

        jPanel11.setMinimumSize(new java.awt.Dimension(300, 100));

        jLabel49.setBackground(new java.awt.Color(51, 51, 51));
        jLabel49.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(51, 51, 51));

        jLabel51.setBackground(new java.awt.Color(51, 51, 51));
        jLabel51.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(51, 51, 51));

        jLabel53.setBackground(new java.awt.Color(51, 51, 51));
        jLabel53.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 18)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(51, 51, 51));

        jLabel55.setBackground(new java.awt.Color(51, 51, 51));
        jLabel55.setFont(new java.awt.Font("Microsoft YaHei UI Light", 1, 18)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(51, 51, 51));

        jLabel58.setBackground(new java.awt.Color(51, 51, 51));
        jLabel58.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 18)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(51, 51, 51));

        jLabel59.setBackground(new java.awt.Color(51, 51, 51));
        jLabel59.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 18)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(51, 51, 51));

        jLabel54.setBackground(new java.awt.Color(51, 51, 51));
        jLabel54.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(51, 51, 51));

        jList1.setVisible(false);
        jScrollPane1.setViewportView(jList1);

        jLabel46.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel46.setText("Participantes:");
        jLabel46.setVisible(false);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel49, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel51)
                                    .addComponent(jLabel55, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addGap(39, 39, 39)
                                        .addComponent(jLabel53, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(42, 42, 42)
                                        .addComponent(jLabel54, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(64, 64, 64))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(jLabel58)
                                .addGap(149, 149, 149)
                                .addComponent(jLabel59))
                            .addComponent(jLabel46)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 606, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel51)
                .addGap(26, 26, 26)
                .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel55)
                .addGap(61, 61, 61)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel58)
                    .addComponent(jLabel59))
                .addGap(157, 157, 157)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(jLabel54))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jLabel46)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(21, 21, 21))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(136, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("", jPanel3);

        jPanel2.setBackground(new java.awt.Color(28, 28, 28));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 5));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        titulocompromisso.setBackground(new java.awt.Color(51, 51, 51));
        titulocompromisso.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        titulocompromisso.setForeground(new java.awt.Color(255, 255, 255));
        titulocompromisso.setText("Adicione um título ao seu compromisso");
        titulocompromisso.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        titulocompromisso.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        titulocompromisso.setMargin(new java.awt.Insets(4, 10, 4, 6));
        titulocompromisso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titulocompromissoActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Criando Compromisso para o dia");

        jLabel36.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText(".");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane3.setViewportView(jTextArea1);

        jLabel37.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setText("Descrição:");

        jLabel38.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("Local do compromisso:");

        jTextField3.setText("Brasil");

        dcAlteraData1.setBackground(new java.awt.Color(51, 51, 51));
        dcAlteraData1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        dcAlteraData1.setMaxSelectableDate(new java.util.Date(4131662490000L));
        dcAlteraData1.setMinSelectableDate(new java.util.Date(-2208974310000L));

        jLabel39.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setText("Data final do compromisso:");

        horaInicio.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        horaInicio.setText("00");

        minInicio.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        minInicio.setText("00");

        horaFim.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        horaFim.setText("00");

        minFim.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        minFim.setText("00");

        jLabel40.setText(":");

        jLabel41.setText(":");

        jLabel42.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(255, 255, 255));
        jLabel42.setText("Hora de Início do Compromisso");

        jLabel43.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setText("Hora do fim do Compromisso");

        jButton6.setBackground(new java.awt.Color(0, 153, 255));
        jButton6.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Criar Compromisso");
        jButton6.setBorder(javax.swing.BorderFactory.createEmptyBorder(-1, -1, -1, -1));
        jButton6.setBorderPainted(false);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        tabelContatosCompromisso.setAutoCreateRowSorter(true);
        tabelContatosCompromisso.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        tabelContatosCompromisso.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Nome", "Telefone 1", "Telefone 2", "E-mail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelContatosCompromisso.setGridColor(new java.awt.Color(51, 51, 51));
        tabelContatosCompromisso.setRowHeight(40);
        tabelContatosCompromisso.setRowMargin(10);
        tabelContatosCompromisso.setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tabelContatosCompromisso.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tabelContatosCompromissoMouseReleased(evt);
            }
        });
        jScrollPane4.setViewportView(tabelContatosCompromisso);

        jLabel44.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Adicionar Contato ao Compromisso");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(133, 133, 133)
                                .addComponent(jLabel45))
                            .addComponent(jLabel44)
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel35)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel36))
                            .addComponent(jLabel37)
                            .addComponent(jLabel38))
                        .addGap(421, 421, 421))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(dcAlteraData1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel39, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel42)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(54, 54, 54)
                                        .addComponent(horaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(minInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(26, 26, 26)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel43)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(57, 57, 57)
                                        .addComponent(horaFim, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(minFim, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titulocompromisso, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(27, 27, 27))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel35)
                    .addComponent(jLabel36))
                .addGap(18, 18, 18)
                .addComponent(titulocompromisso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jLabel37)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(jLabel38)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel39)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(dcAlteraData1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel42)
                            .addComponent(jLabel43))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(horaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(minInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(horaFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel40)
                            .addComponent(jLabel41)
                            .addComponent(minFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(52, 52, 52)
                .addComponent(jLabel44)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel45)
                .addGap(48, 48, 48)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(74, 74, 74))
        );

        jTabbedPane2.addTab("", jPanel2);

        javax.swing.GroupLayout pinicioLayout = new javax.swing.GroupLayout(pinicio);
        pinicio.setLayout(pinicioLayout);
        pinicioLayout.setHorizontalGroup(
            pinicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pinicioLayout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        pinicioLayout.setVerticalGroup(
            pinicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pinicioLayout.createSequentialGroup()
                .addGroup(pinicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1000, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPrincipal.addTab("  Inicio  ", pinicio);

        pcontatos.setBackground(new java.awt.Color(51, 51, 51));

        tabbedPaneContato.setBackground(new java.awt.Color(51, 51, 51));
        tabbedPaneContato.setBorder(javax.swing.BorderFactory.createEmptyBorder(-3, -3, -3, -3));
        tabbedPaneContato.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        paneListarContato.setBackground(new java.awt.Color(61, 61, 61));
        paneListarContato.setBorder(javax.swing.BorderFactory.createEmptyBorder(-3, -3, -3, -3));

        jLabel4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Meus contatos");

        jButton2.setBackground(new java.awt.Color(0, 153, 204));
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Buscar");
        jButton2.setBorderPainted(false);

        jButton3.setBackground(new java.awt.Color(0, 153, 204));
        jButton3.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Adicionar Contato");
        jButton3.setBorderPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        tabelContatos.setAutoCreateRowSorter(true);
        tabelContatos.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        tabelContatos.setForeground(new java.awt.Color(255, 255, 255));
        tabelContatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Nome", "Telefone 1", "Telefone 2", "E-mail"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelContatos.setGridColor(new java.awt.Color(51, 51, 51));
        tabelContatos.setRowHeight(40);
        tabelContatos.setRowMargin(10);
        tabelContatos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tabelContatosMouseReleased(evt);
            }
        });
        jScrollPane2.setViewportView(tabelContatos);

        jButton4.setBackground(new java.awt.Color(0, 153, 204));
        jButton4.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Atualizar");
        jButton4.setBorderPainted(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paneListarContatoLayout = new javax.swing.GroupLayout(paneListarContato);
        paneListarContato.setLayout(paneListarContatoLayout);
        paneListarContatoLayout.setHorizontalGroup(
            paneListarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneListarContatoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paneListarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paneListarContatoLayout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(paneListarContatoLayout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 680, Short.MAX_VALUE)
                    .addGroup(paneListarContatoLayout.createSequentialGroup()
                        .addComponent(jTextField2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(3, 3, 3)))
                .addGap(15, 15, 15))
        );
        paneListarContatoLayout.setVerticalGroup(
            paneListarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paneListarContatoLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel4)
                .addGap(26, 26, 26)
                .addGroup(paneListarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 720, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(paneListarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(102, Short.MAX_VALUE))
        );

        tabbedPaneContato.addTab("", paneListarContato);

        panelAdicionarContato.setBackground(new java.awt.Color(61, 61, 61));

        tituloAdicionarContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        tituloAdicionarContato.setForeground(new java.awt.Color(255, 255, 255));
        tituloAdicionarContato.setText("Adicionar contato");

        labelFoto.setBackground(new java.awt.Color(204, 204, 204));
        labelFoto.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        labelFoto.setForeground(new java.awt.Color(255, 255, 255));
        labelFoto.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelFoto.setText("Selecionar foto");
        labelFoto.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        labelFoto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelFoto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                labelFotoMouseReleased(evt);
            }
        });

        btnVoltarAdicionarContato.setBackground(new java.awt.Color(0, 153, 204));
        btnVoltarAdicionarContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        btnVoltarAdicionarContato.setForeground(new java.awt.Color(255, 255, 255));
        btnVoltarAdicionarContato.setText("Voltar");
        btnVoltarAdicionarContato.setBorderPainted(false);
        btnVoltarAdicionarContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarAdicionarContatoActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nome:");

        fieldNomeContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Data de Nascimento");

        jLabel7.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("E-mail:");

        fieldEmailContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Telefone 1:");

        fieldTelefone1Contato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Telefone 2:");

        fieldTelefone2Contato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N

        jDateChooser1.setBackground(new java.awt.Color(51, 51, 51));
        jDateChooser1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        jDateChooser1.setMaxSelectableDate(new java.util.Date(1670212891000L));
        jDateChooser1.setMinSelectableDate(new java.util.Date(-2208974309000L));

        javax.swing.GroupLayout panelAdicionarContatoLayout = new javax.swing.GroupLayout(panelAdicionarContato);
        panelAdicionarContato.setLayout(panelAdicionarContatoLayout);
        panelAdicionarContatoLayout.setHorizontalGroup(
            panelAdicionarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdicionarContatoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelAdicionarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAdicionarContatoLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(panelAdicionarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tituloAdicionarContato)
                            .addGroup(panelAdicionarContatoLayout.createSequentialGroup()
                                .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(panelAdicionarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(panelAdicionarContatoLayout.createSequentialGroup()
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(fieldEmailContato, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelAdicionarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(panelAdicionarContatoLayout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(panelAdicionarContatoLayout.createSequentialGroup()
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(fieldNomeContato, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panelAdicionarContatoLayout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldTelefone1Contato, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldTelefone2Contato))))
                    .addComponent(btnVoltarAdicionarContato))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelAdicionarContatoLayout.setVerticalGroup(
            panelAdicionarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdicionarContatoLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(tituloAdicionarContato)
                .addGroup(panelAdicionarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelAdicionarContatoLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(labelFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelAdicionarContatoLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(panelAdicionarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(fieldNomeContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(panelAdicionarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jDateChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelAdicionarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fieldEmailContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))))
                .addGap(29, 29, 29)
                .addGroup(panelAdicionarContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(fieldTelefone1Contato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(fieldTelefone2Contato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 524, Short.MAX_VALUE)
                .addComponent(btnVoltarAdicionarContato, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
        );

        tabbedPaneContato.addTab("", panelAdicionarContato);

        tabbedPaneContato2.setBackground(new java.awt.Color(61, 61, 61));
        tabbedPaneContato2.setBorder(javax.swing.BorderFactory.createEmptyBorder(-3, -3, -3, -3));
        tabbedPaneContato2.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        jPanel4.setBackground(new java.awt.Color(61, 61, 61));

        showFotoPerfilContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        showFotoPerfilContato.setForeground(new java.awt.Color(255, 255, 255));
        showFotoPerfilContato.setText("Sem Foto");
        showFotoPerfilContato.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        showNomeContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 36)); // NOI18N
        showNomeContato.setForeground(new java.awt.Color(255, 255, 255));
        showNomeContato.setText("Selecione um contato");

        jLabel15.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Idade:");

        jLabel16.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("E-mail:");

        jLabel17.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Telefone 1:");

        jLabel18.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Telefone 2:");

        jLabel19.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Logradouro:");

        jLabel20.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Endereço");

        jLabel21.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Número:");

        jLabel22.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Cidade:");

        jLabel23.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Estado:");

        jLabel24.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("País:");

        showLogradouroContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        showLogradouroContato.setForeground(new java.awt.Color(255, 255, 255));
        showLogradouroContato.setText("aguardando...");

        showNumeroContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        showNumeroContato.setForeground(new java.awt.Color(255, 255, 255));
        showNumeroContato.setText("aguardando...");

        showCidadeContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        showCidadeContato.setForeground(new java.awt.Color(255, 255, 255));
        showCidadeContato.setText("aguardando...");

        showEstadoContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        showEstadoContato.setForeground(new java.awt.Color(255, 255, 255));
        showEstadoContato.setText("aguardando...");

        showPaisContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        showPaisContato.setForeground(new java.awt.Color(255, 255, 255));
        showPaisContato.setText("aguardando...");

        showIdadeContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        showIdadeContato.setForeground(new java.awt.Color(255, 255, 255));
        showIdadeContato.setText("aguardando...");

        showEmailContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        showEmailContato.setForeground(new java.awt.Color(255, 255, 255));
        showEmailContato.setText("aguardando...");

        showTelefone1Contato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        showTelefone1Contato.setForeground(new java.awt.Color(255, 255, 255));
        showTelefone1Contato.setText("aguardando...");

        showTelefone2Contato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        showTelefone2Contato.setForeground(new java.awt.Color(255, 255, 255));
        showTelefone2Contato.setText("aguardando...");

        jButton5.setBackground(new java.awt.Color(0, 153, 204));
        jButton5.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Editar Contato");
        jButton5.setBorderPainted(false);
        jButton5.setEnabled(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(showNomeContato, javax.swing.GroupLayout.PREFERRED_SIZE, 411, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(showFotoPerfilContato, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(showEmailContato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel15)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(showIdadeContato, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(showTelefone1Contato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(showTelefone2Contato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showNumeroContato, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel22)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showCidadeContato, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showEstadoContato, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(showPaisContato, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                    .addComponent(jLabel19)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(showLogradouroContato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(jSeparator5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton5)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showNomeContato)
                .addGap(46, 46, 46)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(showIdadeContato))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(showEmailContato))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel17)
                            .addComponent(showTelefone1Contato))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel18)
                            .addComponent(showTelefone2Contato))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(showFotoPerfilContato, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(jLabel20)
                .addGap(37, 37, 37)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(showLogradouroContato))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(showNumeroContato))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(showCidadeContato))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(showEstadoContato))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(showPaisContato))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        tabbedPaneContato2.addTab("", jPanel4);

        jPanel8.setBackground(new java.awt.Color(61, 61, 61));

        btnSalvarAdicionarContato.setBackground(new java.awt.Color(0, 153, 204));
        btnSalvarAdicionarContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        btnSalvarAdicionarContato.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvarAdicionarContato.setText("Salvar");
        btnSalvarAdicionarContato.setBorderPainted(false);
        btnSalvarAdicionarContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarAdicionarContatoActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Estado:");

        comboBoxSiglaEstado.setBackground(new java.awt.Color(51, 51, 51));
        comboBoxSiglaEstado.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        comboBoxSiglaEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        comboBoxSiglaEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabel12.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Número:");

        fieldNumeroContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N

        jLabel13.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Cidade:");

        fieldCidadeContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        fieldCidadeContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldCidadeContatoActionPerformed(evt);
            }
        });

        fieldPaisContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N

        jLabel14.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("País:");

        jLabel10.setFont(new java.awt.Font("Microsoft YaHei UI Light", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Logradouro:");

        fieldLogradouroContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSalvarAdicionarContato))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldNumeroContato, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(fieldLogradouroContato, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(fieldCidadeContato, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(comboBoxSiglaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 187, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(fieldPaisContato, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(fieldLogradouroContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(fieldNumeroContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(fieldCidadeContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(62, 62, 62)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(comboBoxSiglaEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(fieldPaisContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 362, Short.MAX_VALUE)
                .addComponent(btnSalvarAdicionarContato, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
        );

        tabbedPaneContato2.addTab("", jPanel8);

        jPanel9.setBackground(new java.awt.Color(61, 61, 61));

        showFotoPerfilContato2.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        showFotoPerfilContato2.setForeground(new java.awt.Color(255, 255, 255));
        showFotoPerfilContato2.setText("Sem Foto");
        showFotoPerfilContato2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel25.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Data de Nascimento:");

        jLabel26.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setText("E-mail:");

        jLabel27.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Telefone 1:");

        jLabel28.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("Telefone 2:");

        jLabel29.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("Endereço");

        jLabel30.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("Logradouro:");

        jLabel31.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setText("Número:");

        jLabel32.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setText("Cidade:");

        jLabel33.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 255, 255));
        jLabel33.setText("Estado:");

        jLabel34.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setText("País:");

        dcAlteraData.setBackground(new java.awt.Color(51, 51, 51));
        dcAlteraData.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 12)); // NOI18N

        cbAlteraEstado.setBackground(new java.awt.Color(51, 51, 51));
        cbAlteraEstado.setFont(new java.awt.Font("Microsoft YaHei Light", 0, 14)); // NOI18N
        cbAlteraEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        cbAlteraEstado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        tfAlteraNome.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 18)); // NOI18N
        tfAlteraNome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfAlteraNomeActionPerformed(evt);
            }
        });

        btnSalvarAlteracoesContato.setBackground(new java.awt.Color(0, 153, 204));
        btnSalvarAlteracoesContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        btnSalvarAlteracoesContato.setForeground(new java.awt.Color(255, 255, 255));
        btnSalvarAlteracoesContato.setText("Salvar Alterações");
        btnSalvarAlteracoesContato.setBorderPainted(false);
        btnSalvarAlteracoesContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarAlteracoesContatoActionPerformed(evt);
            }
        });

        btnExcluirContato.setBackground(new java.awt.Color(255, 51, 51));
        btnExcluirContato.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 15)); // NOI18N
        btnExcluirContato.setForeground(new java.awt.Color(255, 255, 255));
        btnExcluirContato.setText("Excluir Contato");
        btnExcluirContato.setBorderPainted(false);
        btnExcluirContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirContatoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSalvarAlteracoesContato)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tfAlteraNome, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(showFotoPerfilContato2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(31, 31, 31)
                            .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel26)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfAlteraEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel27)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfAlteraTelefone1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel9Layout.createSequentialGroup()
                                    .addComponent(jLabel28)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(tfAlteraTelefone2, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnExcluirContato)
                                    .addGroup(jPanel9Layout.createSequentialGroup()
                                        .addComponent(jLabel25)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(dcAlteraData, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel31)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfAlteraNum, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel32)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfAlteraCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel33)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cbAlteraEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel34)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfAlteraPais, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel9Layout.createSequentialGroup()
                            .addComponent(jLabel30)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tfAlteraLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, 473, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(74, 74, 74))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfAlteraNome, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExcluirContato, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(dcAlteraData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel26)
                            .addComponent(tfAlteraEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27)
                            .addComponent(tfAlteraTelefone1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(tfAlteraTelefone2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(showFotoPerfilContato2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addComponent(jLabel29)
                .addGap(37, 37, 37)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(tfAlteraLogradouro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator14, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(tfAlteraNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator15, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel32)
                    .addComponent(tfAlteraCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator16, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(cbAlteraEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator17, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(tfAlteraPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator18, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalvarAlteracoesContato, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(95, Short.MAX_VALUE))
        );

        tabbedPaneContato2.addTab("tab3", jPanel9);

        javax.swing.GroupLayout pcontatosLayout = new javax.swing.GroupLayout(pcontatos);
        pcontatos.setLayout(pcontatosLayout);
        pcontatosLayout.setHorizontalGroup(
            pcontatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pcontatosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPaneContato)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPaneContato2, javax.swing.GroupLayout.PREFERRED_SIZE, 677, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pcontatosLayout.setVerticalGroup(
            pcontatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pcontatosLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(pcontatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(tabbedPaneContato)
                    .addComponent(tabbedPaneContato2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPrincipal.addTab("Contatos", pcontatos);

        Logout.setBackground(new java.awt.Color(51, 51, 51));

        javax.swing.GroupLayout LogoutLayout = new javax.swing.GroupLayout(Logout);
        Logout.setLayout(LogoutLayout);
        LogoutLayout.setHorizontalGroup(
            LogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1401, Short.MAX_VALUE)
        );
        LogoutLayout.setVerticalGroup(
            LogoutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1064, Short.MAX_VALUE)
        );

        tabbedPrincipal.addTab("Sair", Logout);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabbedPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 1045, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jCalendar1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jCalendar1PropertyChange
        jTabbedPane2.setSelectedIndex(0);
        dataSql = new java.sql.Date(jCalendar1.getDate().getTime());
        jLabel2.setText(dataSql.toString());
        DefaultTableModel dtm11 = (DefaultTableModel) tabelCompromisso.getModel();
        dtm11.setRowCount(0);
        
        listaInicio();
    }//GEN-LAST:event_jCalendar1PropertyChange

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTabbedPane2.setSelectedIndex(1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        jLabel36.setText(sdf.format(dataSql));
        
        DefaultTableModel dtm12 = (DefaultTableModel) tabelContatosCompromisso.getModel();
        dtm12.setRowCount(0);
        
        buscarTabela();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void titulocompromissoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titulocompromissoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titulocompromissoActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        tabbedPaneContato.setSelectedIndex(1);
        tabbedPaneContato2.setSelectedIndex(1);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void labelFotoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelFotoMouseReleased
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);

        int option = fileChooser.showOpenDialog(this);

        ImageIcon fotoPerfilContato;

        if(option == JFileChooser.APPROVE_OPTION){
            String caminho = fileChooser.getSelectedFile().getPath();
            fileFoto = new File(caminho);
            fotoPerfilContato = new ImageIcon(caminho);
           
            Image image = fotoPerfilContato.getImage(); // transform it 
            
            Image newimg = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
            
            fotoPerfilContatoToSave = newimg;
            
            ImageIcon imageIcon = new ImageIcon(newimg);  // transform it back

            labelFoto.setIcon(imageIcon);

        }else{
           JOptionPane.showMessageDialog(null,"Não foi possível adicionar a imagem");
        }
    }//GEN-LAST:event_labelFotoMouseReleased

    private void btnSalvarAdicionarContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarAdicionarContatoActionPerformed
        nome_contato = fieldNomeContato.getText();
        data_nasc_contato = jDateChooser1.getDate();
        email_contato = fieldEmailContato.getText();
        telefone1_contato = Long.parseLong(fieldTelefone1Contato.getText());
        telefone2_contato = Long.parseLong(fieldTelefone2Contato.getText());
        
        logradouro_contato = fieldLogradouroContato.getText();
        numero_contato = Integer.parseInt(fieldNumeroContato.getText());
        cidade_contato = fieldCidadeContato.getText();        
        sigla_estado_contato = comboBoxSiglaEstado.getSelectedItem().toString();        
        pais_contato = fieldPaisContato.getText();        
        
        Pessoa pessoa_contato = new Pessoa();
        
        pessoa_contato.setNome(nome_contato);
        pessoa_contato.setData_nasc(data_nasc_contato);
        pessoa_contato.setEmail(email_contato);
        pessoa_contato.setTelefone1(telefone1_contato);
        pessoa_contato.setTelefone2(telefone2_contato);
        pessoa_contato.setFoto(fileFoto);
        
        pessoa_contato.setLogradouro(logradouro_contato);
        pessoa_contato.setNumero(numero_contato);
        pessoa_contato.setCidade(cidade_contato);
        pessoa_contato.setSigla_estado(sigla_estado_contato);
        pessoa_contato.setPais(pais_contato);
         
        pessoa_contato_list.add(pessoa_contato);
        
        CadastrarUsuario cdu = new CadastrarUsuario();
        
        cdu.setPessoaContato(pessoa_contato_list);
        
        try {
            cdu.CadastrarPessoaContato();
            cdu.ContatoPertenceUsuario(idUsuario);
            try { Thread.sleep (1000); } catch (InterruptedException ex) {}
            
            tabbedPaneContato.setSelectedIndex(0);
            tabbedPaneContato2.setSelectedIndex(0);
        } catch (IOException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSalvarAdicionarContatoActionPerformed

    private void btnVoltarAdicionarContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarAdicionarContatoActionPerformed
        tabbedPaneContato.setSelectedIndex(0);
        tabbedPaneContato2.setSelectedIndex(0);
    }//GEN-LAST:event_btnVoltarAdicionarContatoActionPerformed

    private void tabelContatosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelContatosMouseReleased
        id_contato_now = Integer.parseInt(tabelContatos.getValueAt(tabelContatos.getSelectedRow(), 0).toString());
        BuscaPessoaBD bp = new BuscaPessoaBD();
        
        try {
            pessoa_contato_selecionada = bp.buscaContato(id_contato_now);
            
            showNomeContato.setText(pessoa_contato_selecionada.get(0).getNome());
            byte[] byteArray=pessoa_contato_selecionada.get(0).getFoto_banco(); //need to initialize it
            ImageIcon imageIcon = new ImageIcon(byteArray);
            imageIcon.getImage();
            Image image = imageIcon.getImage(); // transform it 
            
            Image newimg = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH);
            
            imageIcon = new ImageIcon(newimg);
            
            showFotoPerfilContato.setIcon(imageIcon);
            
            showFotoPerfilContato2.setIcon(imageIcon);

            showIdadeContato.setText(String.valueOf(getIdade(pessoa_contato_selecionada.get(0).getData_nasc())));
            
            showEmailContato.setText(pessoa_contato_selecionada.get(0).getEmail());
            
            showTelefone1Contato.setText(String.valueOf(pessoa_contato_selecionada.get(0).getTelefone1()));
                    
            showTelefone2Contato.setText(String.valueOf(pessoa_contato_selecionada.get(0).getTelefone2()));
            
            showLogradouroContato.setText(pessoa_contato_selecionada.get(0).getLogradouro());
            
            showNumeroContato.setText(String.valueOf(pessoa_contato_selecionada.get(0).getNumero()));
            
            showCidadeContato.setText(pessoa_contato_selecionada.get(0).getCidade());
            
            showEstadoContato.setText(pessoa_contato_selecionada.get(0).getSigla_estado());
            
            showPaisContato.setText(pessoa_contato_selecionada.get(0).getPais());
            
            jButton5.setEnabled(true);
            
            tabbedPaneContato2.setSelectedIndex(0);
        } catch (IOException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_tabelContatosMouseReleased

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        DefaultTableModel dtm = (DefaultTableModel) tabelContatos.getModel();
        dtm.setRowCount(0);
        
        buscarTabela();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void tfAlteraNomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfAlteraNomeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfAlteraNomeActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        tabbedPaneContato2.setSelectedIndex(2);
        
        tfAlteraNome.setText(pessoa_contato_selecionada.get(0).getNome());
        
        dcAlteraData.setDate(pessoa_contato_selecionada.get(0).getData_nasc());
        
        tfAlteraEmail.setText(pessoa_contato_selecionada.get(0).getEmail());
        
        tfAlteraTelefone1.setText(String.valueOf(pessoa_contato_selecionada.get(0).getTelefone1()));
        
        tfAlteraTelefone2.setText(String.valueOf(pessoa_contato_selecionada.get(0).getTelefone2()));
        
        tfAlteraLogradouro.setText(pessoa_contato_selecionada.get(0).getLogradouro());
        tfAlteraNum.setText(String.valueOf(pessoa_contato_selecionada.get(0).getNumero())); 
        tfAlteraCidade.setText(pessoa_contato_selecionada.get(0).getCidade());
        tfAlteraPais.setText(pessoa_contato_selecionada.get(0).getPais());

    }//GEN-LAST:event_jButton5ActionPerformed

    private void btnSalvarAlteracoesContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarAlteracoesContatoActionPerformed
        java.sql.Date dataSql = new java.sql.Date(dcAlteraData.getDate().getTime());
        Connection con;
        
        String query = "UPDATE tb_pessoa SET NOME = ?, DATA_NASC = ?, EMAIL = ?, TELEFONE1 = ?, TELEFONE2 = ? WHERE (ID_PESSOA = ?)";
        PreparedStatement ps;
        ResultSet rs;

        try {
            ConnectionFactory cf = new ConnectionFactory();
            con = cf.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, tfAlteraNome.getText());
            ps.setDate(2, dataSql);
            ps.setString(3, tfAlteraEmail.getText());
            ps.setInt(4, Integer.parseInt(tfAlteraTelefone1.getText()));
            ps.setInt(5, Integer.parseInt(tfAlteraTelefone2.getText()));
            ps.setInt(6, id_contato_now);
            ps.execute();

            con.commit();
            ps.close();
            
            con.close();
            try { Thread.sleep (1000); } catch (InterruptedException ex) {}
            
            tabbedPaneContato2.setSelectedIndex(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        Connection con2;
        
        String query2 = "UPDATE tb_endereco SET LOGRADOURO = ?, NUMERO = ?, CIDADE = ?, SIGLA_ESTADO = ?, PAIS = ? WHERE (ID_PESSOA = ?)";
        PreparedStatement ps2;
        ResultSet rs2;

        try {
            ConnectionFactory cf2 = new ConnectionFactory();
            con2 = cf2.getConnection();
            con2.setAutoCommit(false);
            ps2 = con2.prepareStatement(query2);
            ps2.setString(1,tfAlteraLogradouro.getText());
            ps2.setInt(2, Integer.parseInt(tfAlteraNum.getText()));
            ps2.setString(3, tfAlteraCidade.getText());
            ps2.setString(4, cbAlteraEstado.getSelectedItem().toString());
            ps2.setString(5, tfAlteraPais.getText());
            ps2.setInt(6, id_contato_now);
            ps2.execute();

            con2.commit();
            ps2.close();
            
            JOptionPane.showMessageDialog(null, "Os dados do contato foram alterados");
            con2.close();
            try { Thread.sleep (1000); } catch (InterruptedException ex) {}
            
            tabbedPaneContato2.setSelectedIndex(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }//GEN-LAST:event_btnSalvarAlteracoesContatoActionPerformed

    private void btnExcluirContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirContatoActionPerformed
        Connection con;
        
        String query = "UPDATE tb_pessoa SET ATIVO = 0 WHERE (EMAIL = ?)";
        PreparedStatement ps;
        ResultSet rs;

        try {
            ConnectionFactory cf = new ConnectionFactory();
            con = cf.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, pessoa_contato_selecionada.get(0).getEmail());
            ps.execute();

            con.commit();
            ps.close();
            
            JOptionPane.showMessageDialog(null, "O contato foi excluido com sucesso");
            con.close();
            try { Thread.sleep (1000); } catch (InterruptedException ex) {}
            
            tabbedPaneContato2.setSelectedIndex(0);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }//GEN-LAST:event_btnExcluirContatoActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed

        Connection con;

        String query = "INSERT INTO tb_compromisso VALUES (default,?,?,?,?,?,?,?,?)";
        PreparedStatement ps;

        java.sql.Date dataSql2 = new java.sql.Date(dcAlteraData1.getDate().getTime());

        String horaMinInicio = horaInicio.getText() +":"+ minInicio.getText();
        
        String horaMinFim = horaFim.getText() +":"+ minFim.getText();
        
         try {
            con = ConnectionFactory.getConnection();
            con.setAutoCommit(false);

            ps = con.prepareStatement(query);
            ps.setString(1, titulocompromisso.getText());
            ps.setString(2, jTextArea1.getText());
            ps.setString(3, jTextField3.getText());
            ps.setDate(4, dataSql);
            ps.setDate(5, dataSql2);
            ps.setString(6, horaMinInicio);
            ps.setString(7, horaMinFim);
            ps.setInt(8,idUsuario);
            ps.execute();

            con.commit();
            ps.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Compromisso Criado!");
            
            titulocompromisso.setText("");
            jTextArea1.setText("");
            jTextField3.setText("");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
         
        Connection con3;
        
        String query3 = "SELECT ID_COMPROMISSO FROM tb_compromisso ORDER BY ID_COMPROMISSO DESC LIMIT 1";
        PreparedStatement ps3;
        ResultSet rs3;

        try {
            ConnectionFactory cf3 = new ConnectionFactory();
            con3 = cf3.getConnection();
            con3.setAutoCommit(false);
            ps3 = con3.prepareStatement(query3);
            rs3 = ps3.executeQuery();
            con3.commit();

            while (rs3.next()) {
                id_compromisso_FK = rs3.getInt("ID_COMPROMISSO");
            }
            ps3.close();
            con3.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
         
        int[] id_pessoa_compromisso = tabelContatosCompromisso.getSelectedRows();
        
        for(int i = 0; i < id_pessoa_compromisso.length; i++){
        Connection con2;
        
        String query2 = "INSERT INTO tb_contato_pertence_compromisso VALUES (?,?)";
        PreparedStatement ps2;
        
         try {
            
            int id_pessoa_compromisso_row = Integer.parseInt(tabelContatosCompromisso.getValueAt(id_pessoa_compromisso[i], 0).toString()); 
            con2 = ConnectionFactory.getConnection();
            con2.setAutoCommit(false);

            ps2 = con2.prepareStatement(query2);
            ps2.setInt(1, id_compromisso_FK);
            ps2.setInt(2, id_pessoa_compromisso_row);
            ps2.execute();

            con2.commit();
            ps2.close();
            con2.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
        
        jTabbedPane2.setSelectedIndex(0);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void tabelContatosCompromissoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelContatosCompromissoMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_tabelContatosCompromissoMouseReleased

    private void tabelCompromissoMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelCompromissoMouseReleased
        id_compromisso_now = Integer.parseInt(tabelCompromisso.getValueAt(tabelCompromisso.getSelectedRow(), 5).toString());
        
        Connection con;
        
        String query = "SELECT * FROM tb_compromisso WHERE ID_COMPROMISSO = ?";
        PreparedStatement ps;
        ResultSet rs;
        String titulo = "";
        String descricao = "";
        String local = "";
        String dataInicio = "";
        String dataFim = "";
        String hInicio = "";
        String hFim = "";
        try {
            ConnectionFactory cf = new ConnectionFactory();
            con = cf.getConnection();
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setInt(1, id_compromisso_now);
            rs = ps.executeQuery();
            
            con.commit();
            
            
            while (rs.next()) {
                id_compromisso_now = rs.getInt("ID_COMPROMISSO");
                titulo = rs.getString("TITULO");
                descricao = rs.getString("DESCRICAO");
                local = rs.getString("LOCAL_COMPROMISSO");
                dataInicio = rs.getString("DATA_INICIO");
                dataFim = rs.getString("DATA_FINAL");
                hInicio = rs.getString("HORARIO_INICIO");
                hFim = rs.getString("HORARIO_FINAL");

            }
            ps.close();
            con.close();
            
            jLabel51.setText(titulo);
            
            jLabel49.setText("Descrição: " + descricao);
            
            jLabel55.setText("Local: " + local);
            
            jLabel58.setText("Data de Início: " + dataInicio);
            
            jLabel59.setText("Data Final: " + dataFim);
            
            jLabel53.setText("Hora Início: " + hInicio);
            
            jLabel54.setText("Hora Final: " + hFim);
            
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
        
        Connection con2;
        
        String query2 = "SELECT * FROM tb_pessoa WHERE ID_PESSOA IN (SELECT ID_PESSOA_CONTATO FROM tb_contato_pertence_compromisso WHERE ID_COMPROMISSO = ?)";
        PreparedStatement ps2;
        ResultSet rs2;
        String[] nome_contato = new String[10];
        int index = 0;
        try {
            ConnectionFactory cf2 = new ConnectionFactory();
            con2 = cf2.getConnection();
            con2.setAutoCommit(false);
            ps2 = con2.prepareStatement(query2);
            ps2.setInt(1, id_compromisso_now);
            rs2 = ps2.executeQuery();
            
            con2.commit();
            
            DefaultListModel list = new DefaultListModel();
            while (rs2.next()) {
                jLabel46.setVisible(true);
                jList1.setVisible(true);
                nome_contato[index] = rs2.getString("NOME");
                
                
                list.addElement(nome_contato[index]);
                
                index++;
            }
            jList1.setModel(list);
            ps2.close();
            con2.close();
            
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        
    }//GEN-LAST:event_tabelCompromissoMouseReleased

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        DefaultTableModel dtm13 = (DefaultTableModel) tabelCompromisso.getModel();
        dtm13.setRowCount(0);
        listaInicio();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void tabbedPrincipalMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabbedPrincipalMouseReleased
        DefaultTableModel dtm12 = (DefaultTableModel) tabelContatos.getModel();
        dtm12.setRowCount(0);
        
        buscarTabela();
        
        int selected = tabbedPrincipal.getSelectedIndex();
        if(selected == 2){
            this.idUsuario = 0;
            this.logado = false;
            
            this.setVisible(false);
            TelaLogin tl = new TelaLogin();
            tl.setLocationRelativeTo(null);
            tl.setVisible(true);

            ImageIcon image = new ImageIcon("./util/calendar.png");  

            tl.setIconImage(image.getImage());
            
        }
    }//GEN-LAST:event_tabbedPrincipalMouseReleased

    private void fieldCidadeContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldCidadeContatoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldCidadeContatoActionPerformed
    
    public int getIdade(Date data_nasc){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        
        Date data_now = new Date(System.currentTimeMillis());
        
        int res = Integer.parseInt(formatter.format(data_now)) - Integer.parseInt(formatter.format(data_nasc));
        
        return res;
    }
    
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
                java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(Tela.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new Tela().setVisible(true);
                }
                
            });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Logout;
    private javax.swing.JButton btnExcluirContato;
    private javax.swing.JButton btnSalvarAdicionarContato;
    private javax.swing.JButton btnSalvarAlteracoesContato;
    private javax.swing.JButton btnVoltarAdicionarContato;
    private javax.swing.JComboBox<String> cbAlteraEstado;
    private javax.swing.JComboBox<String> comboBoxSiglaEstado;
    private com.toedter.calendar.JDateChooser dcAlteraData;
    private com.toedter.calendar.JDateChooser dcAlteraData1;
    private javax.swing.JTextField fieldCidadeContato;
    private javax.swing.JTextField fieldEmailContato;
    private javax.swing.JTextField fieldLogradouroContato;
    private javax.swing.JTextField fieldNomeContato;
    private javax.swing.JTextField fieldNumeroContato;
    private javax.swing.JTextField fieldPaisContato;
    private javax.swing.JTextField fieldTelefone1Contato;
    private javax.swing.JTextField fieldTelefone2Contato;
    private javax.swing.JTextField horaFim;
    private javax.swing.JTextField horaInicio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private com.toedter.calendar.JCalendar jCalendar1;
    private com.toedter.calendar.JDateChooser jDateChooser1;
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
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator14;
    private javax.swing.JSeparator jSeparator15;
    private javax.swing.JSeparator jSeparator16;
    private javax.swing.JSeparator jSeparator17;
    private javax.swing.JSeparator jSeparator18;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JLabel labelFoto;
    private javax.swing.JTextField minFim;
    private javax.swing.JTextField minInicio;
    private javax.swing.JPanel paneListarContato;
    private javax.swing.JPanel panelAdicionarContato;
    private javax.swing.JPanel pcontatos;
    private javax.swing.JPanel pinicio;
    private javax.swing.JLabel showCidadeContato;
    private javax.swing.JLabel showEmailContato;
    private javax.swing.JLabel showEstadoContato;
    private javax.swing.JLabel showFotoPerfilContato;
    private javax.swing.JLabel showFotoPerfilContato2;
    private javax.swing.JLabel showIdadeContato;
    private javax.swing.JLabel showLogradouroContato;
    private javax.swing.JLabel showNomeContato;
    private javax.swing.JLabel showNumeroContato;
    private javax.swing.JLabel showPaisContato;
    private javax.swing.JLabel showTelefone1Contato;
    private javax.swing.JLabel showTelefone2Contato;
    private javax.swing.JTabbedPane tabbedPaneContato;
    private javax.swing.JTabbedPane tabbedPaneContato2;
    private javax.swing.JTabbedPane tabbedPrincipal;
    public javax.swing.JTable tabelCompromisso;
    public javax.swing.JTable tabelContatos;
    public javax.swing.JTable tabelContatosCompromisso;
    private javax.swing.JTextField tfAlteraCidade;
    private javax.swing.JTextField tfAlteraEmail;
    private javax.swing.JTextField tfAlteraLogradouro;
    private javax.swing.JTextField tfAlteraNome;
    private javax.swing.JTextField tfAlteraNum;
    private javax.swing.JTextField tfAlteraPais;
    private javax.swing.JTextField tfAlteraTelefone1;
    private javax.swing.JTextField tfAlteraTelefone2;
    private javax.swing.JLabel tituloAdicionarContato;
    private javax.swing.JTextField titulocompromisso;
    // End of variables declaration//GEN-END:variables
}
