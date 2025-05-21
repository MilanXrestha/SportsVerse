package com.sportsverse.model;

/**
 * AddressModel represents a user's address in the SportsVerse application. It
 * includes information such as street address, city, state, and address type.
 * Each address is associated with a specific user through the userId foreign
 * key.
 */
public class AddressModel {
	private int addressId; // Auto-generated primary key
	private int userId; // Foreign key linking to User
	private String streetAddress;
	private String city;
	private String nepalState;
	private String addressType;

	// Default constructor. Initializes an empty AddressModel object.
	public AddressModel() {
	}

	/**
	 * Parameterized constructor. Initializes an AddressModel object with all
	 * fields.
	 */
	public AddressModel(int addressId, int userId, String streetAddress, String city, String nepalState,
			String addressType) {
		this.addressId = addressId;
		this.userId = userId;
		this.streetAddress = streetAddress;
		this.city = city;
		this.nepalState = nepalState;
		this.addressType = addressType;
	}

	// Getters and Setters
	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
}
