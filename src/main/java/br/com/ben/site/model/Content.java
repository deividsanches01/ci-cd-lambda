package br.com.ben.site.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Content {
	@SerializedName("cep")
	@Expose
	private long cep;
	@SerializedName("logradouro")
	@Expose
	private String logradouro;
	@SerializedName("numero")
	@Expose
	private int numero;
	@SerializedName("complemento")
	@Expose
	private String complemento;
	@SerializedName("bairro")
	@Expose
	private String bairro;
	@SerializedName("cidade")
	@Expose
	private String cidade;
	@SerializedName("uf")
	@Expose
	private String uf;
	@SerializedName("geoinfo")
	@Expose
	private Geoinfo geoinfo;
	@SerializedName("distancia")
	@Expose
	private Distancia distancia;
	@SerializedName("estabelecimentos")
	@Expose
	private List<Estabelecimentos> estabelecimentos;

	public long getCep() {
		return cep;
	}

	public void setCep(long cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public Geoinfo getGeoinfo() {
		return geoinfo;
	}

	public void setGeoinfo(Geoinfo geoinfo) {
		this.geoinfo = geoinfo;
	}

	public Distancia getDistancia() {
		return distancia;
	}

	public void setDistancia(Distancia distancia) {
		this.distancia = distancia;
	}

	public List<Estabelecimentos> getEstabelecimentos() {
		return estabelecimentos;
	}

	public void setEstabelecimentos(List<Estabelecimentos> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}
}
