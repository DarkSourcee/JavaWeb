<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.SQLException"%>
<%@page import="br.com.DAO.FornecedoresDAO"%>
<%@page import="br.com.DTO.FornecedoresDTO"%> 

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
        <h2>Lista de Fornecedores</h2>
        <a href="frm_cadastrar_fornecedor">
            <button type="button" class="btn btn-primary btn-sm">Novo fornecedor</button>
        </a>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nome Empresa</th>
                    <th scope="col">Contato Principal</th>
                    <th scope="col">Telefone</th>
                    <th scope="col">E-mail</th>
                    <th scope="col">CEP</th>
                    <th scope="col">Endereço</th>
                    <th scope="col">Cidade</th>
                    <th scope="col">UF</th>
                    <th scope="col">Ações</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    try {
                        List<FornecedoresDTO> DTO = new FornecedoresDAO().listarFornecedores();
                        for (FornecedoresDTO fornecedoresDTO : DTO) {
                %>
                <tr>
                    <td><%= fornecedoresDTO.getIdFornecedor() %></td>
                    <td><%= fornecedoresDTO.getNomeEmpresa() %></td>
                    <td><%= fornecedoresDTO.getContatoPrincipal() %></td>
                    <td><%= fornecedoresDTO.getTelefone() %></td>
                    <td><%= fornecedoresDTO.getEmail() %></td>
                    <td><%= fornecedoresDTO.getCep() %></td>
                    <td><%= fornecedoresDTO.getEndereco() %></td>
                    <td><%= fornecedoresDTO.getCidade() %></td>
                    <td><%= fornecedoresDTO.getUf() %></td>
                    <td>
                        <!-- Botões de ação -->
                        <button type="button" onclick="atualizarFornecedor(<%= fornecedoresDTO.getIdFornecedor() %>)" class="btn btn-primary btn-sm">Atualizar</button>
                        <button type="button" onclick="deletarFornecedor(<%= fornecedoresDTO.getIdFornecedor() %>)" class="btn btn-danger btn-sm">Deletar</button>
                    </td>
                </tr>
                <% 
                        }
                    } catch (SQLException e) {
                        // Tratamento de exceção, por exemplo:
                        out.println("<tr><td colspan=\"4\">Erro ao listar fornecedor " + e.getMessage() + "</td></tr>");
                    }
                %>
            </tbody>
        </table>
    </div>
            
    <script>
        function atualizarFornecedor(idFornecedor) {
            // Endpoint do servidor que lida com a exclusão de usuários
            const url = 'frm_editar?id=' + idFornecedor;

            window.location.href = url;
        }
        
        // Função para deletar um usuário pelo ID
        function deletarFornecedor(idFornecedor) {
            // Endpoint do servidor que lida com a exclusão de usuários
            const url = 'cadastroFornecedor?id=' + idFornecedor;

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
                    console.log('Fornecedor deletado com sucesso:', data);
                    // Por exemplo, remover a linha da tabela correspondente ao usuário deletado
                    document.querySelector(`#fornecedor-${idFornecedor}`).remove();
                    window.location.reload();
                })
                .catch(error => {
                    // Log de erro
                    console.error('Erro ao deletar fornecedor', error);
                    // Exibe uma mensagem de erro para o usuário
                    alert('Erro ao deletar fornecedor ' + error.message);
                    window.location.reload();
                });
        }

    </script>
</body>
</html>
