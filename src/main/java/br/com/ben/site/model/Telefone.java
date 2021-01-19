package br.com.ben.site.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Telefone {

	@SerializedName("id")
	@Expose
	private long id;
	@SerializedName("idTipoTelefone")
	@Expose
	private long idTipoTelefone;
	@SerializedName("ddd")
	@Expose
	private String ddd;
	@SerializedName("telefone")
	@Expose
	private String telefone;
	@SerializedName("status")
	@Expose
	private long status;
	@SerializedName("idPessoa")
	@Expose
	private long idPessoa;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdTipoTelefone() {
		return idTipoTelefone;
	}

	public void setIdTipoTelefone(long idTipoTelefone) {
		this.idTipoTelefone = idTipoTelefone;
	}

	public String getDdd() {
		return ddd;
	}

	public void setDdd(String ddd) {
		this.ddd = ddd;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

	public long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(long idPessoa) {
		this.idPessoa = idPessoa;
	}
}