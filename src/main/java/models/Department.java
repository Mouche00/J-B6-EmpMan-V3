package models;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "departments")
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	private String name;

	@OneToMany(mappedBy = "department")
	private Set<Employee> employees;
	
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(Set<Employee> employees) {
		this.employees = employees;
	}

	public Department(String name) {
		this.name = name;
	}

	public Department(){}
}