package br.com.ben.site.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Estabelecimentos {

	@SerializedName("inativo")
	@Expose
	private boolean inativo;
	@SerializedName("bairro")
	@Expose
	private String bairro;
	@SerializedName("cep")
	@Expose
	private long cep;
	@SerializedName("cidade")
	@Expose
	private String cidade;
	@SerializedName("complemento")
	@Expose
	private String complemento;
	@SerializedName("dataCadastramento")
	@Expose
	private String dataCadastramento;
	@SerializedName("descricao")
	@Expose
	private String descricao;
	@SerializedName("distancia")
	@Expose
	private Distancia distancia;
	@SerializedName("email")
	@Expose
	private String email;
	@SerializedName("geoinfo")
	@Expose
	private Geoinfo geoinfo;
	@SerializedName("id")
	@Expose
	private long id;
	@SerializedName("logradouro")
	@Expose
	private String logradouro;
	@SerializedName("nome")
	@Expose
	private String nome;
	@SerializedName("nomeFantasia")
	@Expose
	private String nomeFantasia;
	@SerializedName("numero")
	@Expose
	private long numero;
	@SerializedName("produtos")
	@Expose
	private List<Produto> produtos = null;
	@SerializedName("telefones")
	@Expose
	private List<Telefone> telefones = null;
	@SerializedName("uf")
	@Expose
	private String uf;

	public boolean isInativo() {
		return inativo;
	}

	public void setInativo(boolean inativo) {
		this.inativo = inativo;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public long getCep() {
		return cep;
	}

	public void setCep(long cep) {
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getDataCadastramento() {
		return dataCadastramento;
	}

	public void setDataCadastramento(String dataCadastramento) {
		this.dataCadastramento = dataCadastramento;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Distancia getDistancia() {
		return distancia;
	}

	public void setDistancia(Distancia distancia) {
		this.distancia = distancia;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Geoinfo getGeoinfo() {
		return geoinfo;
	}

	public void setGeoinfo(Geoinfo geoinfo) {
		this.geoinfo = geoinfo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNomeFantasia() {
		return nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}
