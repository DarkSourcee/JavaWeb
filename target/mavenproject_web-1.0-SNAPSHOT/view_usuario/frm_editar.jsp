<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="br.com.DAO.UsuarioDAO"%>
<%@page import="br.com.DTO.UsuarioDTO"%> 

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Editar usuário</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIieHz" crossorigin="anonymous"></script>
</head>
<body>
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h5 class="card-title mb-0">Cadastrar usuário</h5>
                    </div>
                    <div class="card-body">
                        <form action="cadastroUsuario" method="post">
                            <% 
                                try {
                                    List<UsuarioDTO> usuarios = new UsuarioDAO().encontrarUsuario();
                                    for (UsuarioDTO usuario : usuarios) {
                            %>
                            <div class="mb-3 hidden">
                                <label for="id" class="form-label">Nome</label>
                                <input value="<%= usuario.getId() %>" type="text" class="form-control" id="id" name="id" placeholder="ID usuário" required>
                            </div>
                            <div class="mb-3">
                                <label for="nome" class="form-label">Nome</label>
                                <input value="<%= usuario.getNome() %>" type="text" class="form-control" id="nome" name="nome" placeholder="Digite seu nome completo" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">E-mail</label>
                                <input value="<%= usuario.getEmail() %>" type="email" class="form-control" id="email" name="email" placeholder="Digite seu e-mail" required>
                            </div>
                            <div class="mb-3">
                                <label for="login" class="form-label">Login</label>
                                <input value="<%= usuario.getLogin() %>" type="text" class="form-control" id="login" name="login" placeholder="Escolha um nome de usuário" required>
                            </div>
                            <div class="mb-3">
                                <label for="senha" class="form-label">Senha</label>
                                <input value="<%= usuario.getSenha() %>" type="password" class="form-control" id="senha" name="senha" placeholder="Digite sua senha" required>
                            </div>
                            <button type="submit" class="btn btn-success">Editar</button>
                            <button type="button" onclick="toUrl()" class="btn btn-danger">Cancelar</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        function toUrl() {
            window.location.href = "frm_listagem";
        }
    </script>
</body>
</html>
