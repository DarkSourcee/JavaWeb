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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "UsuarioServlet", urlPatterns = { "/view_usuario/cadastroUsuario", "/view_usuario/login",
        "/view_usuario/frm_listagem", "/view_usuario/frm_cadastrar_usuario", "/view_usuario/frm_editar" })
public class UsuarioServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UsuarioServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        switch (action) {
            case "/view_usuario/frm_listagem":
                System.out.println("estou aqui");
                request.getRequestDispatcher("/view_usuario/frm_listagem.jsp").forward(request, response);
                break;
            case "/view_usuario/frm_cadastrar_usuario":
                request.getRequestDispatcher("/view_usuario/frm_cadastro_usuario.jsp").forward(request, response);
                break;
            case "/view_usuario/frm_editar":
                String id = request.getParameter("id");
                request.setAttribute("id", id);
                request.getRequestDispatcher("/view_usuario/frm_editar.jsp").forward(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/index.html");
                break;
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
            default:
                response.sendRedirect(request.getContextPath() + "/index.html");
                break;
        }
    }

    private void cadastrarUsuario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String senha = PasswordUtils.hashPassword(request.getParameter("senha"));

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(nome);
        usuarioDTO.setEmail(email);
        usuarioDTO.setLogin(login);
        usuarioDTO.setSenha(senha);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.cadastrarUsuario(usuarioDTO);
            request.setAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
            request.getRequestDispatcher("/view_usuario/frm_login.jsp").forward(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao cadastrar usuário", ex);
            request.setAttribute("mensagemErro", "Erro ao cadastrar usuário: " + ex.getMessage());
            request.getRequestDispatcher("/view_usuario/frm_cadastro_usuario.jsp").forward(request, response);
        }
    }

    private void realizarLogin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            String hashSenhaArmazenada = usuarioDAO.obterHashSenha(login);

            if (hashSenhaArmazenada != null && PasswordUtils.verifyPassword(senha, hashSenhaArmazenada)) {
                response.sendRedirect(request.getContextPath() + "/index.html");
            } else {
                request.setAttribute("mensagemErro", "Credenciais inválidas.");
                request.getRequestDispatcher("/view_usuario/frm_login.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao validar login", e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String senha = PasswordUtils.hashPassword(request.getParameter("senha"));

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(Integer.parseInt(id));
        usuarioDTO.setNome(nome);
        usuarioDTO.setEmail(email);
        usuarioDTO.setLogin(login);
        usuarioDTO.setSenha(senha);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.editarUsuario(usuarioDTO);
            response.getWriter().println("Usuário editado com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao editar usuário", ex);
            response.getWriter().println("Erro ao editar usuário: " + ex.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(Integer.parseInt(id));

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        try {
            usuarioDAO.deletarUsuario(usuarioDTO);
            response.getWriter().println("Usuário deletado com sucesso!");
        } catch (SQLException | ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao deletar usuário", ex);
            response.getWriter().println("Erro ao deletar usuário: " + ex.getMessage());
        }
    }
}
