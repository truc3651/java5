package com.asm.entity;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "staffs")
public class Staffs {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id = 0;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_depart")
	private Departs departs;
	
	private String name = "";
	
	private Boolean gender = true;
	
	private Date birthday = new Date(System.currentTimeMillis());
	
	private String photo = "";
	private String email = "";
	private String phone = "";
	private String address = "";
	
	private Double salary = 0.0;
	private String notes = "";
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "staffs")
	private Set<Records> recordses = new HashSet<Records>(0);
	
	@Transient
    public String getPhotosImagePath() {
        if (photo == null || (Integer)id == null) return null;
         
        return "/photos/staff/" + photo;
    }
	
	@Override
	public String toString() {
		return "Staff [id=" + id + ", name=" + name + 
				", gender=" + gender + ", birthday=" + birthday + 
				", photo=" + photo + ", email=" + email +
				", phone=" + phone + ", address=" + address +
				", salary=" + salary + ", notes=" + notes;
	}

	public Staffs() {
	}

	public Staffs(int id) {
		this.id = id;
	}

	public Staffs(int id, Departs departs, String name, Boolean gender, Date birthday, String photo, String email,
			String phone, String address, Double salary, String notes,
			Set<Records> recordses) {
		this.id = id;
		this.departs = departs;
		this.name = name;
		this.gender = gender;
		this.birthday = birthday;
		this.photo = photo;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.salary = salary;
		this.notes = notes;
		this.recordses = recordses;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Departs getDeparts() {
		return this.departs;
	}

	public void setDeparts(Departs departs) {
		this.departs = departs;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getGender() {
		return this.gender;
	}

	public void setGender(Boolean gender) {
		this.gender = gender;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Double getSalary() {
		return this.salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Set<Records> getRecordses() {
		return this.recordses;
	}

	public void setRecordses(Set<Records> recordses) {
		this.recordses = recordses;
	}

}
