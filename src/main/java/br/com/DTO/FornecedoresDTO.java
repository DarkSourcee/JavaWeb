package br.com.DTO;

public class FornecedoresDTO {
    private int id_fornecedor;
    private String nome_empresa;
    private String contato_principal;
    private String telefone;
    private String email;
    private String cep;
    private String endereco;
    private String cidade;
    private String uf;
    
    public FornecedoresDTO(int id_fornecedor
                          , String nome_empresa
                          , String contato_principal
                          , String telefone
                          , String email
                          , String cep
                          , String endereco
                          , String cidade
                          , String uf) {
        this.id_fornecedor = id_fornecedor;
        this.nome_empresa = nome_empresa;
        this.contato_principal = contato_principal;
        this.telefone = telefone;
        this.email = email;
        this.cep = cep;
        this.endereco = endereco;
        this.cidade = cidade;
        this.uf = uf;
    }
    
    public FornecedoresDTO(int id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }
    
    public FornecedoresDTO() {
        
    }
    
    //id
    public int getIdFornecedor() {
        return id_fornecedor;
    }
    
    public void setIdFornecedor(int id_fornecedor) {
        this.id_fornecedor = id_fornecedor;
    }
    
    
    //nome
    public String getNomeEmpresa() {
        return nome_empresa;
    }
    
    public void setNomeEmpresa(String nome_empresa) {
        this.nome_empresa = nome_empresa;
    }
    
    //contato principal
    public String getContatoPrincipal() {
        return contato_principal;
    }
    
    public void setContatoPrincipal(String contato_principal) {
        this.contato_principal = contato_principal;
    }
    
    //telefone
    public String getTelefone() {
        return telefone;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    //email
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    //cep
    public String getCep() {
        return cep;
    }
    
    public void setCep(String cep) {
        this.cep = cep;
    }
    
    //endereco
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    //cidade
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    //uf
    public String getUf() {
        return uf;
    }
    
    public void setUf(String uf) {
        this.uf = uf;
    }

    public void setId(int parseInt) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
