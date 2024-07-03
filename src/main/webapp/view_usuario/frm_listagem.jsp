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
        <a href="frm_cadastrar_usuario">
            <button type="button" class="btn btn-primary btn-sm">Novo usuário</button>
        </a>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">ID</th>
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
                    <td><%= usuario.getId() %></td>
                    <td><%= usuario.getNome() %></td>
                    <td><%= usuario.getEmail() %></td>
                    <td><%= usuario.getLogin() %></td>
                    <td>
                        <!-- Botões de ação -->
                        <button type="button" onclick="atualizarUsuario(<%= usuario.getId() %>)" class="btn btn-primary btn-sm">Atualizar</button>
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
        function atualizarUsuario(idUsuario) {
            // Endpoint do servidor que lida com a exclusão de usuários
            const url = 'frm_editar?id=' + idUsuario;

            window.location.href = url;
        }
        
        // Função para deletar um usuário pelo ID
        function deletarUsuario(idUsuario) {
            // Endpoint do servidor que lida com a exclusão de usuários
            const url = 'cadastroUsuario?id=' + idUsuario;

            // Opções da requisição HTTP DELETE
            const requestOptions = {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            };

            // Realiza a requisição para o servidor
            fetch(url, requestOptions)
                .then(response => {
                    // Obtém o tipo de conteúdo da resposta
                    const contentType = response.headers.get('content-type');

                    // Se a resposta não for OK, processa o erro
                    if (!response.ok) {
                        if (contentType && contentType.includes('application/json')) {
                            // Se for JSON, retorna o erro em formato JSON
                            return response.json().then(json => { throw new Error(json.message) });
                        } else {
                            // Se não for JSON, retorna o erro como texto
                            return response.text().then(text => { throw new Error(text) });
                        }
                    }
                    // Se a resposta for OK, retorna o corpo da resposta em formato JSON
                    return response.json();
                })
                .then(data => {
                    // Log de sucesso e atualização da interface do usuário
                    console.log('Usuário deletado com sucesso:', data);
                    // Por exemplo, remover a linha da tabela correspondente ao usuário deletado
                    document.querySelector(`#usuario-${idUsuario}`).remove();
                    window.location.reload();
                })
                .catch(error => {
                    // Log de erro
                    console.error('Erro ao deletar usuário:', error);
                    // Exibe uma mensagem de erro para o usuário
                    alert('Erro ao deletar usuário: ' + error.message);
                    window.location.reload();
                });
        }

    </script>
</body>
</html>
