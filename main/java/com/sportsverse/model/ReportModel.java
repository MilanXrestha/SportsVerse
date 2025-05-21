package com.sportsverse.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportModel {
	private int reportId;
	private int userId;
	private String userName; // Added for displaying user name
	private String reportType;
	private String reportScope;
	private Date startDate;
	private Date endDate;
	private int totalOrders;
	private double totalSpent;
	private int totalProducts; // For Inventory report
	private int lowStockCount; // For Inventory report
	private List<SaleItem> salesItems;
	private List<PurchaseItem> purchaseItems;
	private List<CartItem> cartItems;
	private List<InventoryItem> inventoryItems;

	public ReportModel() {
		this.salesItems = new ArrayList<>();
		this.purchaseItems = new ArrayList<>();
		this.cartItems = new ArrayList<>();
		this.inventoryItems = new ArrayList<>();
	}

	// Getters and Setters
	public int getReportId() {
		return reportId;
	}

	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportScope() {
		return reportScope;
	}

	public void setReportScope(String reportScope) {
		this.reportScope = reportScope;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public int getTotalOrders() {
		return totalOrders;
	}

	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}

	public double getTotalSpent() {
		return totalSpent;
	}

	public void setTotalSpent(double totalSpent) {
		this.totalSpent = totalSpent;
	}

	public int getTotalProducts() {
		return totalProducts;
	}

	public void setTotalProducts(int totalProducts) {
		this.totalProducts = totalProducts;
	}

	public int getLowStockCount() {
		return lowStockCount;
	}

	public void setLowStockCount(int lowStockCount) {
		this.lowStockCount = lowStockCount;
	}

	public List<SaleItem> getSalesItems() {
		return salesItems;
	}

	public void setSalesItems(List<SaleItem> salesItems) {
		this.salesItems = salesItems;
	}

	public List<PurchaseItem> getPurchaseItems() {
		return purchaseItems;
	}

	public void setPurchaseItems(List<PurchaseItem> purchaseItems) {
		this.purchaseItems = purchaseItems;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public List<InventoryItem> getInventoryItems() {
		return inventoryItems;
	}

	public void setInventoryItems(List<InventoryItem> inventoryItems) {
		this.inventoryItems = inventoryItems;
	}

	// Inner Classes
	public static class SaleItem {
		private int productId;
		private String productName;
		private String userName; // Added for all users report
		private int quantitySold;
		private double totalAmount;

		public SaleItem(int productId, String productName, String userName, int quantitySold, double totalAmount) {
			this.productId = productId;
			this.productName = productName;
			this.userName = userName;
			this.quantitySold = quantitySold;
			this.totalAmount = totalAmount;
		}

		public int getProductId() {
			return productId;
		}

		public String getProductName() {
			return productName;
		}

		public String getUserName() {
			return userName;
		}

		public int getQuantitySold() {
			return quantitySold;
		}

		public double getTotalAmount() {
			return totalAmount;
		}
	}

	public static class PurchaseItem {
		private int orderId;
		private int productId;
		private String productName;
		private int quantity;
		private double amount;

		public PurchaseItem(int orderId, int productId, String productName, int quantity, double amount) {
			this.orderId = orderId;
			this.productId = productId;
			this.productName = productName;
			this.quantity = quantity;
			this.amount = amount;
		}

		public int getOrderId() {
			return orderId;
		}

		public int getProductId() {
			return productId;
		}

		public String getProductName() {
			return productName;
		}

		public int getQuantity() {
			return quantity;
		}

		public double getAmount() {
			return amount;
		}
	}

	public static class CartItem {
		private int productId;
		private String productName;
		private int quantity;

		public CartItem(int productId, String productName, int quantity) {
			this.productId = productId;
			this.productName = productName;
			this.quantity = quantity;
		}

		public int getProductId() {
			return productId;
		}

		public String getProductName() {
			return productName;
		}

		public int getQuantity() {
			return quantity;
		}
	}

	public static class InventoryItem {
		private int productId;
		private String productName;
		private int stockQuantity;
		private double price;
		private boolean lowStock;

		public InventoryItem(int productId, String productName, int stockQuantity, double price, boolean lowStock) {
			this.productId = productId;
			this.productName = productName;
			this.stockQuantity = stockQuantity;
			this.price = price;
			this.lowStock = lowStock;
		}

		public int getProductId() {
			return productId;
		}

		public String getProductName() {
			return productName;
		}

		public int getStockQuantity() {
			return stockQuantity;
		}

		public double getPrice() {
			return price;
		}

		public boolean isLowStock() {
			return lowStock;
		}
	}
}
