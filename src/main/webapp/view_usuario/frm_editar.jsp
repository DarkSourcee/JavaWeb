<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.sql.SQLException" %>
<%@ page import="br.com.DAO.UsuarioDAO" %>
<%@ page import="br.com.DTO.UsuarioDTO" %>

<%
    // Recebe o ID do usuário da URL
    int idUsuario = Integer.parseInt(request.getParameter("id"));
    String nome = "";
    String email = "";
    String login = "";
    
    // Crie um objeto UsuarioDTO para armazenar os dados do usuário
    UsuarioDTO usuario = null;
    try {
        usuario = new UsuarioDAO().encontrarUsuario(idUsuario);
        nome = usuario.getNome();
        email = usuario.getEmail();
        login = usuario.getLogin();
    } catch (SQLException e) {
        // Tratamento de exceção
        e.printStackTrace();
    }
    
    // Verifica se o usuário foi encontrado antes de continuar
    if (usuario == null) {
        // Tratamento caso o usuário não seja encontrado
        out.println("Usuário não encontrado.");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Editar Usuário</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIieHz" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container mt-5">
        <h2>Editar Usuário</h2>
        <form action="frm_editar" method="post">
            <input type="number" class="form-control" name="idUsuario" value="<%= idUsuario %>">
            <div class="mb-3">
                <label for="nome" class="form-label">Nome</label>
                <input type="text" class="form-control" id="nome" name="nome" value="<%= nome %>">
            </div>
            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="<%= email %>">
            </div>
            <div class="mb-3">
                <label for="login" class="form-label">Login</label>
                <input type="text" class="form-control" id="login" name="login" value="<%= login %>">
            </div>
            <div class="mb-3">
                <label for="senha" class="form-label">Nova Senha (opcional)</label>
                <input type="password" class="form-control" id="senha" name="senha">
            </div>

            <button type="submit" class="btn btn-primary">Salvar Alterações</button>
            <button type="button" class="btn btn-danger" onclick="toUrl()">Cancelar</button>
        </form>
    </div>
            
    <script>
        function toUrl() {
            window.location.href = "frm_listagem";
        }
    </script>
</body>
</html>
