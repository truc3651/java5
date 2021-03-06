package com.asm.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "departs")
public class Departs implements Serializable{

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "Name can't be null")
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "departs")
	private Set<Staffs> staffses = new HashSet<Staffs>(0);

	public Departs() {
	}

	public Departs(int id) {
		this.id = id;
	}

	public Departs(int id, String name, Set<Staffs> staffses) {
		this.id = id;
		this.name = name;
		this.staffses = staffses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Staffs> getStaffses() {
		return this.staffses;
	}

	public void setStaffses(Set<Staffs> staffses) {
		this.staffses = staffses;
	}

}
