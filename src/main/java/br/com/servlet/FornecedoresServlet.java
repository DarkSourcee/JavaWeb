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

@WebServlet(name = "FornecedoresServlet", urlPatterns = {"/FornecedoresServlet"})
public class FornecedoresServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
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
