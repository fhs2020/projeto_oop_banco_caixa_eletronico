/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.NativeMath.round;
import new_bank_poo_pos_graduação.Account;

/**
 *
 * @author usr_developer
 */
public class ConectaBanco {
    
    public Statement stm;
    public PreparedStatement preparedStatement = null;
    public ResultSet rs;
    private String driver = "com.mysql.jdbc.Driver";
    private String caminho = "jdbc:mysql://localhost:3306/new_bank_fhs?zeroDateTimeBehavior=convertToNull";
    private String usuario = "root";
    private String senhaBanco = "123456";
    public Connection conn;
    
    
    
    public void conexao(){
       
        try {
            
            System.setProperty("jdbc.Drivers", driver);
            conn = DriverManager.getConnection(caminho, usuario, senhaBanco);
            JOptionPane.showMessageDialog(null, "Conectatdo com sucesso! Bem vindo ao melhor banco do mundo!");
            
        } catch (SQLException ex) {
            Logger.getLogger(ConectaBanco.class.getName()).log(Level.SEVERE, null, ex);
             JOptionPane.showMessageDialog(null, "Erro de conexão!\n Erro: " + ex.getMessage());
        }
    }
    
    
    public void insertRecordIntoDbUserTable(float saldo, int numeroConta, String senha, String tipoConta, String nomeCliente) throws SQLException {

               Statement statement = null;
                                                    
               String query = "INSERT INTO conta ("
                                                    + " saldo,"
                                                    + " ContaId,"
                                                    + " NumeroConta,"
                                                    + " DataAbertura,"
                                                    + " transacaoAnterior,"
                                                    + " senha,"
                                                    + " tipoConta,"
                                                    + " NomeCliente,"                   
                                                    + " clienteid ) VALUES ("
                                                    + "?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
		try {
                            Connection dbConn = DriverManager.getConnection(caminho, usuario, senhaBanco);
                    
                            preparedStatement = dbConn.prepareStatement(query);
                    
                            preparedStatement.setFloat(1, saldo);
                            preparedStatement.setInt(2, numeroConta);
                            preparedStatement.setInt(3, numeroConta);
                            preparedStatement.setDate(4, null);
                            preparedStatement.setFloat(5, 00);
                            preparedStatement.setString(6, senha);
                            preparedStatement.setString(7, tipoConta);
                            preparedStatement.setString(8, nomeCliente);
                            preparedStatement.setInt(9, numeroConta);
                            preparedStatement.executeUpdate();
                        
			System.out.println("Conta criada com sucesso!");

		} catch (SQLException e) {

			System.out.println("Nao inseriu os dados no banco corretamente!SS");

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (conn != null) {
				conn.close();
			}

		}

	}
    
    
        public void buscarSaldo(int numeroConta, String senha) throws SQLException {
		
		Statement statement = null;
                Scanner scanner = new Scanner(System.in);
                
                String query =  "SELECT * from conta WHERE  NumeroConta = ? and senha = ?";
                
		try {
                            Connection dbConn = DriverManager.getConnection(caminho, usuario, senhaBanco);
                            
                            preparedStatement = dbConn.prepareStatement(query);
                            preparedStatement.setInt(1, numeroConta);
                            preparedStatement.setString(2, senha);
                            
                            ResultSet rs = preparedStatement.executeQuery();
                            
                            int numContaN = 0;
                            
                            while (rs.next()) {
                                
                                float saldoN = rs.getFloat("saldo");
                                int contaIdN = rs.getInt("ContaId");
                                numContaN = rs.getInt("NumeroConta");
                                String nomeCli = rs.getString("NomeCliente");
                                
                                if (nomeCli != null && numContaN == numeroConta){
                                   
                                   DecimalFormat df = new DecimalFormat("####0.00");
                                    
                                    System.out.println("\n");
                                    System.out.println("***** Seja bem vindo " + nomeCli + "!");
                                    System.out.println("***** Saldo disponivel $R  " +  df.format(saldoN));
                                    System.out.println("\n");
                                    System.out.println("Precione enter para continuar");
                                    scanner.nextLine();
                                }
                                else{
                                
                                    System.out.println("Numero de conta ou senha invalida!");
                                }
                             
                            }
                            
                            if (numContaN != numeroConta){
                            
                                System.out.println("****** Numero de conta ou senha invalida! Tente novamente ******");
                                System.out.println("\n");
                            }
                           
                             Account cliente1 = new Account();
                             cliente1.mostrarMenu();

                } catch (SQLException e) {

			System.out.println("Nao inseriu os dados no banco corretamente!");

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (conn != null) {
				conn.close();
			}

		}
	}
    
    
    public void desconecta(){
    
        try {
            conn.close();
            JOptionPane.showMessageDialog(null, "Conexão foi fechada com sucesso");
            
        } catch (SQLException ex) {
           JOptionPane.showMessageDialog(null, "Erro ao tentar fechar conexão!\n Erro: " + ex.getMessage());
        }
    }
    
    
    
}