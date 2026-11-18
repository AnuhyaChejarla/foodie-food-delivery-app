package com.tap.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone cannot be blank")
    @Size(min = 10, max = 10, message = "Phone number must be 10 digits")
    private String phone;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Address cannot be blank")
    private String address;
    
    private String role = "CUSTOMER";

    public User() {
    }

    public User(int userId, String name, String email, String phone, String password, String address) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.address = address;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
	    return "User [userId=" + userId + ", name=" + name + ", email=" + email
	            + ", phone=" + phone + ", address=" + address + "]";
	}

   
    
    
    
    
}