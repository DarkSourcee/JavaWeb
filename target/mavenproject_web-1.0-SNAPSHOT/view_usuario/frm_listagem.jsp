<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@page import="br.com.DAO.UsuarioDAO"%>
<%@page import="br.com.DTO.UsuarioDTO"%> 

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Listagem de Usuários</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIieHz" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container mt-5">
        <h2>Lista de Usuários</h2>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">Nome</th>
                    <th scope="col">Email</th>
                    <th scope="col">Login</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    try {
                        List<UsuarioDTO> usuarios = new UsuarioDAO().listarUsuarios();
                        for (UsuarioDTO usuario : usuarios) {
                %>
                <tr>
                    <td><%= usuario.getNome() %></td>
                    <td><%= usuario.getEmail() %></td>
                    <td><%= usuario.getLogin() %></td>
                    <td>
                        <!-- Botões de ação -->
                        <button type="button" class="btn btn-primary btn-sm">Atualizar</button>
                        <button type="button" onclick="deletarUsuario(<%= usuario.getId() %>)" class="btn btn-danger btn-sm">Deletar</button>
                    </td>
                </tr>
                <% 
                        }
                    } catch (SQLException e) {
                        // Tratamento de exceção, por exemplo:
                        out.println("<tr><td colspan=\"4\">Erro ao listar usuários: " + e.getMessage() + "</td></tr>");
                    }
                %>
            </tbody>
        </table>
    </div>
            
    <script>
        // Função para deletar usuário
        function deletarUsuario(idUsuario) {
            const url = '/view_usuario/cadastroUsuario?id=' + idUsuario;
            const requestOptions = {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            };

            fetch(url, requestOptions)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Erro ao deletar usuário');
                    }
                    return response.json();
                })
                .then(data => {
                    console.log('Usuário deletado com sucesso:', data);
                    location.reload();
                })
                .catch(error => {
                    console.error('Erro ao deletar usuário:', error);
                });
        }

    </script>
</body>
</html>
