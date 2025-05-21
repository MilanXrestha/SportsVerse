package com.sportsverse.model;

/**
 * UserModel represents a user in the SportsVerse. It contains user-related
 * properties and provides getter and setter methods.
 */

public class UserModel {

	// Unique identifier for each user (auto-generated in the database)
	private int userId;

	private String firstName;
	private String lastName;
	private String email;
	private String username;
	private String password;
	private String phone;
	private String gender;
	private String contactPreference;
	private String role;

	// Default constructor. Initializes an empty UserModel object.
	public UserModel() {
	}

	/**
	 * Parameterized constructor. Creates a UserModel object with all fields
	 * initialized.
	 */
	public UserModel(int userId, String firstName, String lastName, String email, String username, String password,
			String phone, String gender, String contactPreference, String role) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.phone = phone;
		this.gender = gender;
		this.contactPreference = contactPreference;
		this.role = role;
	}

	// Getters and Setters
	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getContactPreference() {
		return contactPreference;
	}

	public void setContactPreference(String contactPreference) {
		this.contactPreference = contactPreference;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
