package br.com.DAO;

import br.com.DTO.FornecedoresDTO;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FornecedoresDAO {
    public void cadastrarFornecedores(FornecedoresDTO DTO) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            if (!existeFornecedor(DTO.getNomeEmpresa(), DTO.getEmail())) {
                conn = Conexao.getConection();
                String sql = "insert into fornecedores (nome_empresa,contato_principal,telefone,email,cep,endereco,cidade,uf) values (?,?,?,?,?,?,?,?)";
                stmt = conn.prepareStatement(sql);

                stmt.setString(1, DTO.getNomeEmpresa());
                stmt.setString(2, DTO.getContatoPrincipal());
                stmt.setString(3, DTO.getTelefone());
                stmt.setString(4, DTO.getEmail());
                stmt.setString(5, DTO.getCep());
                stmt.setString(6, DTO.getEndereco());
                stmt.setString(7, DTO.getCidade());
                stmt.setString(8, DTO.getUf());

                stmt.executeUpdate(sql);
            }
            
        } catch (SQLException e) {
            Logger.getLogger(FornecedoresDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            Conexao.closeConnection(conn);
        }
    }
    
    public void editarFornecedores(FornecedoresDTO DTO) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConection();
            String sql = "update fornecedores set nome_empresa = ?,contato_principal =?,telefone =?,email =?,cep =?,endereco =?,cidade =?,uf =? WHERE id_fornecedor = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, DTO.getNomeEmpresa());
            stmt.setString(2, DTO.getContatoPrincipal());
            stmt.setString(3, DTO.getTelefone());
            stmt.setString(4, DTO.getEmail());
            stmt.setString(5, DTO.getCep());
            stmt.setString(6, DTO.getEndereco());
            stmt.setString(7, DTO.getCidade());
            stmt.setString(8, DTO.getUf());
            stmt.setInt(8, DTO.getIdFornecedor());

            stmt.executeUpdate(sql);
            
        } catch (SQLException e) {
            Logger.getLogger(FornecedoresDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            Conexao.closeConnection(conn);
        }
    }
    
    private boolean existeFornecedor(String nome_empresa, String email) {
        boolean existeFornecedor = false;
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = Conexao.getConection();
            String sql = "SELECT * FROM fornecedores WHERE nome_empresa = ? AND email = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, nome_empresa);
            stmt.setString(2, email);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                existeFornecedor = true;
            }
            
        } catch (Exception e) {
            Logger.getLogger(FornecedoresDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            Conexao.closeConnection(conn, stmt, rs);
        }
        
        return existeFornecedor;
    }
    
    public void deletarFornecedores(FornecedoresDTO DTO) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConection();
            String sql = "DELETE fornecedores WHERE id_fornecedor = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setInt(8, DTO.getIdFornecedor());

            stmt.executeUpdate(sql);
            
        } catch (SQLException e) {
            Logger.getLogger(FornecedoresDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            Conexao.closeConnection(conn);
        }
    }
    
    public List<FornecedoresDTO> listarFornecedores() throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<FornecedoresDTO> fornecedores = new ArrayList<>();
        
        try {
            conn = Conexao.getConection(); // Ajuste aqui para obter a conexão corretamente
            String sql = "SELECT id_fornecedor,nome_empresa,contato_principal,telefone,email,cep,endereco,cidade,uf FROM fornecedores";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Integer id_fornecedor = rs.getInt("id_fornecedor");
                String nome_empresa = rs.getString("nome_empresa");
                String contato_principal = rs.getString("contato_principal");
                String telefone = rs.getString("telefone");
                String email = rs.getString("email");
                String cep = rs.getString("cep");
                String endereco = rs.getString("endereco");
                String cidade = rs.getString("cidade");
                String uf = rs.getString("uf");
                
                FornecedoresDTO DTO = new FornecedoresDTO( id_fornecedor
                                                         , nome_empresa
                                                         , contato_principal
                                                         , telefone
                                                         , email
                                                         , cep
                                                         , endereco
                                                         , cidade
                                                         , uf);
                fornecedores.add(DTO);
            }
            
        } catch(SQLException e) {
            throw new SQLException("Erro ao listar fornecedores", e);
        } finally {
            Conexao.closeConnection(conn, stmt, rs); // Ajuste aqui para fechar a conexão corretamente
        }
        
        return fornecedores;
    }
}
