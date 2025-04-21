package com.sportsverse.model;

public class CustomerModel {
    private int customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private String phone;
    private String gender;
    private String contactMethod;
    private String streetAddress;
    private String city;
    private String nepalState;
    private String role;  // New field to store the role of the user

    // Constructor
    public CustomerModel() {
    }

    // Parameterized constructor (for quick initialization)
    public CustomerModel(int customerId, String firstName, String lastName, String email, String username, String password,
            String phone, String gender, String contactMethod, String streetAddress, String city, String nepalState, String role) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.contactMethod = contactMethod;
        this.streetAddress = streetAddress;
        this.city = city;
        this.nepalState = nepalState;
        this.role = role;
    }
    
    // Getters and setters
    public int getCustomerId() {
        return customerId;
    }
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getContactMethod() {
        return contactMethod;
    }

    public void setContactMethod(String contactMethod) {
        this.contactMethod = contactMethod;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNepalState() {
        return nepalState;
    }

    public void setNepalState(String nepalState) {
        this.nepalState = nepalState;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
