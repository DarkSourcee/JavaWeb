package br.com.servlet;

import br.com.DAO.FornecedoresDAO;
import br.com.DTO.FornecedoresDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "FornecedoresServlet", urlPatterns = {"/FornecedoresServlet",
                                                         "/view_fornecedor/frm_listagem",
                                                         "/view_fornecedor/frm_cadastrar_fornecedor",
                                                         "/view_fornecedor/frm_editar",
                                                         "/view_fornecedor/cadastroFornecedor"})
public class FornecedoresServlet extends HttpServlet {
    
    private final String rota;

    public FornecedoresServlet() {
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
            case "/view_fornecedor/frm_listagem":
                response.sendRedirect(request.getContextPath() + "/view_fornecedor/frm_listagem.jsp");
                break;
            case "/view_fornecedor/frm_cadastrar_fornecedor":
                response.sendRedirect(request.getContextPath() + "/view_fornecedor/frm_cadastro_fornecedor.jsp");
                break;
            case "/view_fornecedor/frm_editar":
                String id = request.getParameter("id");
                response.sendRedirect(request.getContextPath() + "/view_fornecedor/frm_editar.jsp?id="+id);
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
            case "/view_fornecedor/cadastroFornecedor":
                cadastrarFornecedor(request, response);
                break;
            case "/view_fornecedor/editar_fornecedor":
                doPut(request, response);
            default:
                throw new AssertionError();
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String idForncedor      = request.getParameter("id_fornecedor");
        String nomeEmpresa      = request.getParameter("nomeEmpresa");
        String contatoPrincipal = request.getParameter("contatoPrincipal");
        String telefone         = request.getParameter("telefone");
        String email            = request.getParameter("email");
        String cep              = request.getParameter("cep");
        String endereco         = request.getParameter("endereco");
        String cidade           = request.getParameter("cidade");
        String uf               = request.getParameter("uf");   
        
        FornecedoresDTO DTO = new FornecedoresDTO();
        DTO.setId(Integer.parseInt(idForncedor));
        DTO.setNomeEmpresa(nomeEmpresa);
        DTO.setContatoPrincipal(contatoPrincipal);
        DTO.setTelefone(telefone);
        DTO.setEmail(email);
        DTO.setCep(cep);
        DTO.setEndereco(endereco);
        DTO.setCidade(cidade);
        DTO.setUf(uf);
        
        FornecedoresDAO DAO = new FornecedoresDAO();
        try {
            try {
                DAO.editarFornecedores(DTO);
                request.setAttribute("mensagemSucesso", "Fornecedor editado com sucesso!");
                //this.rota = "/view_usuario/frm_login.jsp";;
                doGet(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("mensagemErro", "Erro ao editar fornecedor: " + ex.getMessage());
            }
        } catch (ClassNotFoundException e) {
            throw new ServletException("Erro ao cadastrar o fornecedor", e);
        }
    }
    
    private void cadastrarFornecedor(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        // Obtém parâmetros do formulário
        String nomeEmpresa      = request.getParameter("nomeEmpresa");
        String contatoPrincipal = request.getParameter("contatoPrincipal");
        String telefone         = request.getParameter("telefone");
        String email            = request.getParameter("email");
        String cep              = request.getParameter("cep");
        String endereco         = request.getParameter("endereco");
        String cidade           = request.getParameter("cidade");
        String uf               = request.getParameter("uf");
        
        FornecedoresDTO DTO = new FornecedoresDTO();
        DTO.setNomeEmpresa(nomeEmpresa);
        DTO.setContatoPrincipal(contatoPrincipal);
        DTO.setTelefone(telefone);
        DTO.setEmail(email);
        DTO.setCep(cep);
        DTO.setEndereco(endereco);
        DTO.setCidade(cidade);
        DTO.setUf(uf);
        
        FornecedoresDAO DAO = new FornecedoresDAO();
        try {
            try {
                DAO.cadastrarFornecedores(DTO);
                request.setAttribute("mensagemSucesso", "Fornecedor cadastrado com sucesso!");
                //this.rota = "/view_usuario/frm_login.jsp";;
                doGet(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("mensagemErro", "Erro ao cadastrar fornecedor: " + ex.getMessage());
            }
        } catch (ClassNotFoundException e) {
            throw new ServletException("Erro ao cadastrar o fornecedor", e);
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Obtém parâmetros do formulário
        String id_fornecedor = request.getParameter("id_fornecedor");

        // Cria um objeto UsuarioDTO com os dados do formulário
        FornecedoresDTO DTO = new FornecedoresDTO();
        DTO.setId(Integer.parseInt(id_fornecedor));

        FornecedoresDAO DAO = new FornecedoresDAO();
        try {
            try {
                DAO.deletarFornecedores(DTO);
            } catch (SQLException ex) {
                Logger.getLogger(FornecedoresServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            response.getWriter().println("Fornecedor deletado com sucesso!");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Erro ao deletar fornecedor", e);
        }
    }
}
