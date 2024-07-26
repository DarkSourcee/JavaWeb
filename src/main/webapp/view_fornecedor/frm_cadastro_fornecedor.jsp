<%-- 
    Document   : frm_cadastro_usuario
    Created on : 17 de jun. de 2024, 16:32:34
    Author     : marcelo.paula
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cadastrar fornecedor</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
    <%-- Verifica se existe uma mensagem de sucesso --%>
    <% if (request.getAttribute("mensagemSucesso") != null) { %>
        <div class="alert alert-success" role="alert">
            <%= request.getAttribute("mensagemSucesso") %>
        </div>
    <% } %>

    <%-- Verifica se existe uma mensagem de erro --%>
    <% if (request.getAttribute("mensagemErro") != null) { %>
        <div class="alert alert-danger" role="alert">
            <%= request.getAttribute("mensagemErro") %>
        </div>
    <% } %>
    
    <div class="container mt-5">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card">
                    <div class="card-header bg-success text-white">
                        <h5 class="card-title mb-0">Cadastrar Fornecedor</h5>
                    </div>
                    <div class="card-body">
                        <form action="cadastroFornecedor" method="post">
                            <div class="mb-3">
                                <label for="nomeEmpresa" class="form-label">Nome empresa</label>
                                <input type="text" class="form-control" id="nomeEmpresa" name="nomeEmpresa" placeholder="Digite o nome da empresa" required>
                            </div>
                            <div class="mb-3">
                                <label for="contatoPrincipal" class="form-label">Contato principal</label>
                                <input type="text" class="form-control" id="contatoPrincipal" name="contatoPrincipal" placeholder="Digite o contato principal" required>
                            </div>
                            <div class="mb-3">
                                <label for="telefone" class="form-label">Telefon</label>
                                <input type="tel" class="form-control" id="telefone" name="telefone" placeholder="Digite o telefone" required>
                            </div>
                            <div class="mb-3">
                                <label for="email" class="form-label">E-mail</label>
                                <input type="email" class="form-control" id="email" name="email" placeholder="Digite seu e-mail" required>
                            </div>
                            <div class="mb-3">
                                <label for="cep" class="form-label">Cep</label>
                                <input type="text" class="form-control" id="cep" name="cep" placeholder="Digite seu CEP" required>
                            </div>
                            <div class="mb-3">
                                <label for="endereco" class="form-label">Endereço</label>
                                <input type="text" class="form-control" id="endereco" name="endereco" placeholder="Digite seu endereço" required>
                            </div>
                            <div class="mb-3">
                                <label for="cidade" class="form-label">Cidade</label>
                                <input type="text" class="form-control" id="cidade" name="cidade" placeholder="Digite sua cidade" required>
                            </div>
                            <div class="mb-3">
                                <label for="uf-select">Unidade Federativa</label>
                                <select id="uf-select" class="form-control">
                                    <option>Selecione uma UF</option>
                                </select>
                            </div>
                            <button type="submit" class="btn btn-success">Cadastrar</button>
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
        
        document.addEventListener('DOMContentLoaded', function() {
            const cepInput = document.getElementById('cep');
            const enderecoInput = document.getElementById('endereco');
            const cidadeInput = document.getElementById('cidade');
            const ufSelect = document.getElementById('uf-select');

            // Função para preencher o select de UF
            function populateUFs() {
                fetch('https://brasilapi.com.br/api/ibge/uf/v1')
                    .then(response => response.json())
                    .then(data => {
                        data.forEach(uf => {
                            const option = document.createElement('option');
                            option.value = uf.sigla;
                            option.textContent = uf.sigla; // Mostrar a sigla da UF no texto
                            ufSelect.appendChild(option);
                        });
                    })
                    .catch(error => {
                        console.error('Erro ao buscar UFs:', error);
                    });
            }

            // Evento blur no campo CEP
            cepInput.addEventListener('blur', function() {
                const cep = cepInput.value.replace(/\D/g, ''); // Remove caracteres não numéricos

                if (cep.length === 8) {
                    fetch(`https://viacep.com.br/ws/${cep}/json/`)
                        .then(response => response.json())
                        .then(data => {
                            if (data.erro) {
                                alert('CEP não encontrado.');
                            } else {
                                enderecoInput.value = data.logradouro;
                                cidadeInput.value = data.localidade;
                                ufSelect.value = data.uf; // Preenche o campo de UF com a sigla retornada
                            }
                        })
                        .catch(error => alert('Erro ao buscar informações do CEP.'));
                }
            });


            // Inicializa o preenchimento do select de UF
            populateUFs();
        });

    </script>
</body>
</html>
