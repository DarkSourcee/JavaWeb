package br.com.servlet;

import br.com.DAO.UsuarioDAO;
import br.com.DTO.UsuarioDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marcelo.paula
 */
@WebServlet(name = "cadastroUsuario", urlPatterns = {"/view_usuario/cadastroUsuario"})
public class UsuarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lógica para lidar com requisições GET
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>Método GET foi chamado</h1>");
        out.println("</body></html>");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtém parâmetros do formulário
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        // Cria um objeto UsuarioDTO com os dados do formulário
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(nome);
        usuarioDTO.setEmail(email);
        usuarioDTO.setLogin(login);
        usuarioDTO.setSenha(senha);

        // Chama o método cadastrarUsuario da classe DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            try {
                usuarioDAO.cadastrarUsuario(usuarioDTO);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.getWriter().println("Usuário cadastrado com sucesso!");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Erro ao cadastrar usuário", e);
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
    }
}
