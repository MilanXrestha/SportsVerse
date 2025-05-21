package com.sportsverse.model;

/**
 * ReportOrderModel represents the many-to-many relationship between reports and orders.
 * It contains the association between a report and the orders included in that report.
 */
public class ReportOrderModel {

	// The ID of the report
	private int reportId;

	// The ID of the order
	private int orderId;

	// Default constructor
	public ReportOrderModel() {
	}

	/**
	 * Parameterized constructor to initialize both reportId and orderId.
	 */
	public ReportOrderModel(int reportId, int orderId) {
		this.reportId = reportId;
		this.orderId = orderId;
	}

	// Getters and Setters
	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
}
