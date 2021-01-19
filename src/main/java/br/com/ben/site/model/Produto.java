package br.com.ben.site.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Produto {

	@SerializedName("descricao")
	@Expose
	private String descricao;
	@SerializedName("id")
	@Expose
	private long id;
	@SerializedName("nome")
	@Expose
	private String nome;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
