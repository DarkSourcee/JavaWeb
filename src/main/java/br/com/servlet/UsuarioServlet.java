package br.com.servlet;

import br.com.DAO.UsuarioDAO;
import br.com.DTO.UsuarioDTO;
import br.com.util.PasswordUtils;
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
@WebServlet(name = "cadastroUsuario", urlPatterns = {"/view_usuario/cadastroUsuario",
                                                     "/view_usuario/login",
                                                     "/view_usuario/frm_listagem",
                                                     "/view_usuario/frm_cadastrar_usuario",
                                                     "/view_usuario/frm_editar"})
public class UsuarioServlet extends HttpServlet {
    private String rota;

    public UsuarioServlet() {
        this.rota = null;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lógica para lidar com requisições GET
        
        String action = null;
        
        if (this.rota == null) {
            action = request.getServletPath();
        } else {
            action = request.getServletPath() + "view_usuario/frm_login.jsp";
        }
        
         switch (action) {
            case "/view_usuario/frm_listagem":
                response.sendRedirect(request.getContextPath() + "/view_usuario/frm_listagem.jsp");
                break;
            case "/view_usuario/frm_cadastrar_usuario":
                response.sendRedirect(request.getContextPath() + "/view_usuario/frm_cadastro_usuario.jsp");
                break;
            case "/view_usuario/frm_editar":
                String id = request.getParameter("id");
                response.sendRedirect(request.getContextPath() + "/view_usuario/frm_editar.jsp?id="+id);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/index.html");
                break;
         }        
    }
    
    private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response)
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
                request.setAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
                this.rota = "/view_usuario/frm_login.jsp";
                doGet(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("mensagemErro", "Erro ao cadastrar usuário: " + ex.getMessage());
            }
            //response.getWriter().println("Usuário cadastrado com sucesso!");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Erro ao cadastrar usuário", e);
        }
    }
    
    private void realizarLogin(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Obtém parâmetros do formulário de login
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        try {
            // Obtém o hash da senha armazenado no banco de dados para o usuário com o login fornecido
            String hashSenhaArmazenada = usuarioDAO.obterHashSenha(login);

            if (hashSenhaArmazenada != null) {
                // Verifica se a senha fornecida pelo usuário corresponde ao hash armazenado no banco de dados
                boolean senhaCorreta = PasswordUtils.verifyPassword(senha, hashSenhaArmazenada);

                if (senhaCorreta) {
                    // Login válido, redireciona para a página inicial (ou outra página desejada)
                    response.sendRedirect(request.getContextPath() + "/index.html");
                } else {
                    // Senha incorreta, retorna uma mensagem de erro para o usuário
                    response.getWriter().println("Credenciais inválidas. Verifique seu login e senha.");
                }
            } else {
                // Hash de senha não encontrado no banco de dados
                response.getWriter().println("Usuário não encontrado ou credenciais inválidas.");
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao validar login", e);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
        
         switch (action) {
            case "/view_usuario/cadastroUsuario":
                cadastrarUsuario(request, response);
                break;
            case "/view_usuario/login":
                realizarLogin(request, response);
                break;
            case "/view_usuario/frm_editar":
                doPut(request, response);
            default:
                break;
         }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Obtém parâmetros do formulário
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        // Cria um objeto UsuarioDTO com os dados do formulário
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(Integer.parseInt(id));
        usuarioDTO.setNome(nome);
        usuarioDTO.setEmail(email);
        usuarioDTO.setLogin(login);
        usuarioDTO.setSenha(senha);

        // Chama o método cadastrarUsuario da classe DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            try {
                usuarioDAO.editarUsuario(usuarioDTO);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.getWriter().println("Usuário editado com sucesso!");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Erro ao editar usuário", e);
        }        
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Obtém parâmetros do formulário
        String id = request.getParameter("id");

        // Cria um objeto UsuarioDTO com os dados do formulário
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(Integer.parseInt(id));

        // Chama o método cadastrarUsuario da classe DAO
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            try {
                usuarioDAO.deletarUsuario(usuarioDTO);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.getWriter().println("Usuário deletado com sucesso!");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Erro ao deletar usuário", e);
        }
    }
}
