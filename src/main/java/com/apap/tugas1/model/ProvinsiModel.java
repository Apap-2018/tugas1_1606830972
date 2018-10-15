package com.apap.tugas1.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="provinsi")
public class ProvinsiModel implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public Double getPresentase_tunjangan() {
		return presentase_tunjangan;
	}

	public void setPresentase_tunjangan(Double presentase_tunjangan) {
		this.presentase_tunjangan = presentase_tunjangan;
	}

	@NotNull
	@Size(max=255)
	@Column(name="nama",nullable=false)
	private String nama;
	
	@NotNull
	@Column(name="presentase_tunjangan",nullable=false)
	private Double presentase_tunjangan;
	
	@OneToMany(mappedBy="provinsi", fetch=FetchType.LAZY, cascade= CascadeType.PERSIST)
	private List<InstansiModel> listInstansi;

}
