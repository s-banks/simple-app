package com.example.appddiction.models;

import javax.persistence.*;

@Entity
@Table(name = "admin")
public class Admin {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 50, nullable = false, unique = true)
	private String username;

	@Column(nullable = false, length = 100)
	private String password;

	@OneToOne
	@JoinColumn(name="employee_id" , unique = true, nullable = false)
	private Employee employee;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean isSuperAdmin;

	public Admin() {
	}

	public Admin(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Admin(long id, String username, String password, Employee employee, boolean isSuperAdmin) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.employee = employee;
		this.isSuperAdmin = isSuperAdmin;
	}

	public Admin(Admin copy) {
		id = copy.id;
		username = copy.username;
		password = copy.password;
		employee = copy.employee;
		isSuperAdmin = copy.isSuperAdmin;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public boolean getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}
}
