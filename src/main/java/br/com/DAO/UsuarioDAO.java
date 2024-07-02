package br.com.DAO;

import br.com.DTO.UsuarioDTO;
import br.com.util.PasswordUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioDAO {
    // Método para cadastrar usuário no banco de dados
    public void cadastrarUsuario(UsuarioDTO usuario) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            if (!verificaUsuarioExistente(usuario.getLogin(), usuario.getSenha())) {
                conn = Conexao.getConection();  // Obter conexão com o banco de dados
                String sql = "insert into usuario (nome, email, login, senha) values (?, ?, ?, ?)";
                stmt = conn.prepareStatement(sql);

                String senhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());

                // Setar os parâmetros da query
                stmt.setString(1, usuario.getNome());
                stmt.setString(2, usuario.getEmail());
                stmt.setString(3, usuario.getLogin());
                stmt.setString(4, senhaCriptografada);

                // Executar a query de inserção
                stmt.executeUpdate();
            } else {
                try {
                    throw new Exception("Erro: algo deu errado!");
                } catch (Exception ex) {
                    Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } finally {
            // Fechar recursos (statement e conexão)
            Conexao.closeConnection(conn, stmt);
        }
    }
    
    //validar login
    public boolean validarLogin(String login, String senha) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean isValid = false;
        
        try {
            conn = Conexao.getConection();
            
            String sql = "SELECT login FROM usuario WHERE login = ? AND senha = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, login);
            
            rs = stmt.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("senha");
                isValid = PasswordUtils.verifyPassword(senha, hashedPassword); // Verifica se a senha corresponde ao hash
            }
        } finally {
            // Fechar recursos (statement e conexão)
            Conexao.closeConnection(conn, stmt);
        }
        
        return isValid;
    }
    
    private boolean verificaUsuarioExistente(String login, String email) throws SQLException{
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean existeUsuario = false;
        
        try {
            conn = Conexao.getConection();
            
            String sql = "SELECT login, email FROM usuario WHERE login = ? OR email = ?";
            stmt = conn.prepareStatement(sql);
            
            stmt.setString(1, login);
            stmt.setString(2, email);
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                existeUsuario = true;
            }
        } finally {
            // Fechar recursos (statement, result e conexão)
            Conexao.closeConnection(conn, stmt, rs);
        }
        
        return existeUsuario;
    }
    
    public void deletarUsuario(UsuarioDTO usuario) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexao.getConection();

            String sql = "DELETE FROM usuario WHERE id = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setInt(1, usuario.getId());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Usuário deletado com sucesso!");
            } else {
                System.out.println("Nenhum usuário foi deletado. Verifique o ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // rethrow the exception to be handled elsewhere
        } finally {
            // Fechar recursos (statement e conexão)
            Conexao.closeConnection(conn, stmt);
        }
    }
    
    public void editarUsuario(UsuarioDTO usuario) throws ClassNotFoundException, SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexao.getConection();  // Obter conexão com o banco de dados
            String sql = "update usuario set nome = ?, email = ?, login = ?, senha = ? where id = ?";
            stmt = conn.prepareStatement(sql);
            
            String senhaCriptografada = BCrypt.hashpw(usuario.getSenha(), BCrypt.gensalt());
            
            // Setar os parâmetros da query
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getLogin());
            stmt.setString(4, senhaCriptografada);
            stmt.setInt(5, usuario.getId());
            
            // Executar a query de inserção
            stmt.executeUpdate();
        } finally {
            // Fechar recursos (statement e conexão)
            Conexao.closeConnection(conn, stmt);
        }
    }
    
    public List<UsuarioDTO> listarUsuarios() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<UsuarioDTO> usuarios = new ArrayList<>();
        
        try {
            conn = Conexao.getConection(); // Ajuste aqui para obter a conexão corretamente
            String sql = "SELECT id, nome, email, login FROM usuario";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Integer id = rs.getInt("id");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String login = rs.getString("login");
                
                UsuarioDTO usuario = new UsuarioDTO(id, nome, email, login);
                usuarios.add(usuario);
            }
            
        } catch(SQLException e) {
            throw new SQLException("Erro ao listar usuários", e);
        } finally {
            Conexao.closeConnection(conn, stmt, rs); // Ajuste aqui para fechar a conexão corretamente
        }
        
        return usuarios;
    }

    public String obterHashSenha(String login) throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String hashSenha = null;

        try {
            conn = Conexao.getConection(); // Obter conexão com o banco de dados
            String sql = "SELECT senha FROM usuario WHERE login = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);

            rs = stmt.executeQuery();

            if (rs.next()) {
                hashSenha = rs.getString("senha");
            }
        } catch (SQLException e) {
            // Tratar exceções ou repassar para quem chamou o método
            throw new SQLException("Erro ao buscar hash da senha", e);
        } finally {
            // Fechar recursos (ResultSet, PreparedStatement e conexão)
            Conexao.closeConnection(conn, stmt, rs);
        }

        return hashSenha;
    }

}

