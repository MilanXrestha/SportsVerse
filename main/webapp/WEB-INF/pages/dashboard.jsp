<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Admin Dashboard | SportsVerse</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Audiowide&family=Rajdhani:wght@400;700&display=swap"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/dashboard.css">

<script
	src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>

</head>
<body>
	<!-- Navbar -->
	<nav class="navbar">
		<a href="${pageContext.request.contextPath}/dashboard"
			class="logo-link">
			<div class="logo" id="logo">
				<span>S</span><span>p</span><span><i
					class="fas fa-basketball football"></i></span><span>r</span><span>t</span><span>s</span><span>V</span><span>E</span><span>R</span><span>S</span><span>E</span>
			</div>
		</a>

		<form action="${pageContext.request.contextPath}/dashboard"
			method="get" class="search-bar" id="searchForm">
			<input type="hidden" name="action" value="searchProducts"> <input
				type="text" id="searchInput" name="searchQuery"
				placeholder="Search products by name..."
				value="${param.searchQuery}"> <i
				class="fas fa-search search-icon"
				onclick="document.getElementById('searchForm').submit()"></i> <i
				class="fas fa-times clear-icon" style="display: none;"
				onclick="clearSearch()"></i>
			<button type="submit" style="display: none;"></button>
		</form>


		<div class="navbar-right">
			<div class="theme-toggle">
				<i class="fas fa-moon" id="themeToggle"></i>
			</div>
			<div class="profile" id="profileButton">
				<img
					src="${pageContext.request.contextPath}/resources/images/others/admin.jpg"
					alt="Profile Image" class="navbar-profile-img"> <span>Admin</span>
				<i class="fas fa-caret-down dropdown-icon"></i>

				<div class="profile-dropdown" id="profileDropdown">
					<form action="${pageContext.request.contextPath}/logout"
						method="post" id="logoutForm" style="display: inline;">
						<a href="#" onclick="confirmLogout(event)"><i
							class="fas fa-sign-out-alt"></i> Logout</a>
					</form>
				</div>
			</div>
		</div>
	</nav>

	<!-- Sidebar -->
	<div class="sidebar" id="sidebar">
		<div class="sidebar-toggle" id="sidebarToggle">
			<div class="sidebar-profile">
				<img
					src="${pageContext.request.contextPath}/resources/images/others/admin.jpg"
					alt="Profile Image" class="sidebar-profile-img">
				<div class="sidebar-profile-text">
					<span class="sidebar-profile-title">Milan Shrestha</span> <span
						class="sidebar-profile-subtitle">Admin</span>
				</div>
			</div>
			<i class="fas fa-angles-left" id="sidebarIcon"></i>
		</div>
		<ul>
			<li data-section="dashboard" class="sidebar-item active"><i
				class="fas fa-tachometer-alt"></i> <span>Dashboard</span></li>
			<li data-section="users" class="sidebar-item"><i
				class="fas fa-users"></i> <span>Users</span></li>
			<li data-section="products" class="sidebar-item"><i
				class="fas fa-box"></i> <span>Products</span></li>
			<li data-section="brands" class="sidebar-item"><i
				class="fas fa-tags"></i> <span>Brand</span></li>
			<li data-section="categories" class="sidebar-item"><i
				class="fas fa-list"></i> <span>Category</span></li>
			<li data-section="subcategories" class="sidebar-item"><i
				class="fas fa-list-alt"></i> <span>Subcategory</span></li>
			<li data-section="sliders" class="sidebar-item"><i
				class="fas fa-images"></i> <span>Slider</span></li>
			<li data-section="orders" class="sidebar-item"><i
				class="fas fa-shopping-cart"></i> <span>Orders</span></li>
			<li data-section="carts" class="sidebar-item"><i
				class="fas fa-shopping-basket"></i> <span>Cart</span></li>
			<li data-section="payment-methods" class="sidebar-item"><i
				class="fas fa-money-check-alt"></i> <span>Payment Method</span></li>
			<li data-section="payments" class="sidebar-item"><i
				class="fas fa-credit-card"></i> <span>Payment</span></li>
			<li data-section="reviews" class="sidebar-item"><i
				class="fas fa-star"></i> <span>Review</span></li>
			<li data-section="generate-report" class="sidebar-item"><i
				class="fas fa-table"></i> <span>Generate Report</span></li>
			<li data-section="analytics" class="sidebar-item"><i
				class="fas fa-chart-line"></i> <span>Analytics</span></li>
			<li data-section="logout" class="sidebar-item"
				style="cursor: pointer;" onclick="confirmLogout(event)"><i
				class="fas fa-sign-out-alt"></i> <span>Logout</span></li>

		</ul>
	</div>

	<!-- Main Content -->
	<main class="main-content expanded" id="mainContent">
		<!-- Dashboard Section -->
		<section id="dashboard" class="section active">
			<div class="dashboard-cards">
				<div class="card">
					<i class="fas fa-users card-icon"></i>
					<h3>Total Users</h3>
					<p id="totalUsers">${totalUsers}</p>
				</div>
				<div class="card">
					<i class="fas fa-shopping-cart card-icon"></i>
					<h3>Total Orders</h3>
					<p id="totalOrders">${totalOrders}</p>
				</div>
				<div class="card">
					<i class="fas fa-box-open card-icon"></i>
					<h3>Low Stock Products</h3>
					<p id="lowStock">${lowStockProducts}</p>
				</div>
				<div class="card">
					<i class="fas fa-tags card-icon"></i>
					<h3>Total Brands</h3>
					<p id="totalBrands">${totalBrands}</p>
				</div>
				<div class="card">
					<i class="fas fa-list card-icon"></i>
					<h3>Total Categories</h3>
					<p id="totalCategories">${totalCategories}</p>
				</div>
				<div class="card">
					<i class="fas fa-box card-icon"></i>
					<h3>Total Products</h3>
					<p id="totalProducts">${totalProducts}</p>
				</div>
				<div class="card">
					<i class="fas fa-star card-icon"></i>
					<h3>Featured Products</h3>
					<p id="featuredProducts">${featuredProducts}</p>
				</div>
				<div class="card">
					<i class="fas fa-dollar-sign card-icon"></i>
					<h3>Total Revenues</h3>
					<p id="totalRevenues">
						<fmt:formatNumber value="${totalRevenues}" type="currency"
							currencySymbol="₨ " />
					</p>
				</div>
			</div>
			<div class="recently-joined-customers">
				<div class="section-header">
					<h3 class="section-subtitle">Recently Joined Customers</h3>
					<button onclick="showSection('users')" class="view-all-btn">View
						All</button>
				</div>
				<table>
					<thead>
						<tr>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Gender</th>
							<th>Email</th>
							<th>Phone</th>
						</tr>
					</thead>
					<tbody id="recentCustomers">
						<c:forEach var="user" items="${recentUsers}" varStatus="loop">
							<c:if test="${loop.index < 3}">
								<tr>
									<td>${user.firstName}</td>
									<td>${user.lastName}</td>
									<td>${user.gender}</td>
									<td>${user.email}</td>
									<td>${user.phone}</td>
								</tr>
							</c:if>
						</c:forEach>
						<c:if test="${empty recentUsers}">
							<tr>
								<td colspan="5">No recent users available</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>

			<div class="chart-container">
				<!-- Product Stock Chart -->
				<div class="chart-card chart-bar">
					<h3 class="section-subtitle">Product Stock Levels</h3>
					<canvas id="stockChart" style="max-height: 400px;"></canvas>
				</div>
			</div>

		</section>

		<!-- Users Section -->
		<section id="users" class="section">
			<div class="section-header">
				<h2>
					<i class="fas fa-users section-title-icon"></i> Users
				</h2>
			</div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Gender</th>
						<th>Email</th>
						<th>Phone</th>
						<th>Contact Preference</th>
						<th>Address</th>
						<th>Role</th>
					</tr>
				</thead>
				<tbody id="usersTable">
					<c:forEach var="user" items="${users}">
						<tr>
							<td>${user.userId}</td>
							<td>${user.firstName}</td>
							<td>${user.lastName}</td>
							<td>${user.gender}</td>
							<td>${user.email}</td>
							<td>${user.phone}</td>
							<td>${user.contactPreference}</td>
							<td class="address-column"><c:forEach var="address"
									items="${addresses}">
									<c:if test="${address.userId == user.userId}">
                                        ${address.streetAddress}, ${address.city}, ${address.nepalState} (${address.addressType})<br>
									</c:if>
								</c:forEach> <c:if test="${empty addresses}">
                                    No Address
                                </c:if></td>
							<td>${user.role}</td>
						</tr>
					</c:forEach>
					<c:if test="${empty users}">
						<tr>
							<td colspan="9">No users available</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</section>

		<!-- Products Section -->
		<section id="products" class="section">
			<div class="section-header">
				<h2>
					<i class="fas fa-box section-title-icon"></i> Products
				</h2>
				<button onclick="showSection('add-product')" class="add-product-btn">Add
					Product</button>
			</div>


			<c:if test="${not empty param.searchQuery and empty products}">
				<p
					style="color: #dc3545; font-family: 'Rajdhani', sans-serif; margin-bottom: 20px;">
					No products found matching "
					<c:out value="${param.searchQuery}" />
					"
				</p>
			</c:if>

			<div class="product-cards" id="productCards">
				<c:forEach var="product" items="${products}">
					<div class="product-card" data-product-id="${product.productId}">


						<c:choose>
							<c:when test="${fn:startsWith(product.mainImageUrl, 'http')}">
								<img src="${product.mainImageUrl}" alt="${product.productName}"
									class="product-image">
							</c:when>
							<c:otherwise>
								<img
									src="${pageContext.request.contextPath}${product.mainImageUrl}"
									alt="${product.productName}" class="product-image">
							</c:otherwise>
						</c:choose>


						<h4 class="product-name">${product.productName}</h4>
						<div class="details">
							<p>
								<span class="label">ID:</span> <span class="value product-id">${product.productId}</span>
							</p>
							<p>
								<span class="label">Price:</span> <span
									class="value product-price"><fmt:formatNumber
										value="${product.productPrice}" type="currency"
										currencySymbol="₨ " /></span>
							</p>
							<p>
								<span class="label">Brand:</span> <span
									class="value product-brand"> <c:forEach var="brand"
										items="${brands}">
										<c:if test="${brand.brandId == product.brandId}">${brand.brandName}</c:if>
									</c:forEach>
								</span>
							</p>
							<p>
								<span class="label">Stock:</span> <span
									class="value product-stock">${product.stockQuantity}</span>
							</p>
							<p>
								<span class="label">Category:</span> <span
									class="value product-category"> <c:forEach
										var="subcategory" items="${subcategories}">
										<c:if
											test="${subcategory.subcategoryId == product.subcategoryId}">
											<c:forEach var="category" items="${categories}">
												<c:if
													test="${category.categoryId == subcategory.categoryId}">${category.categoryName}</c:if>
											</c:forEach>
										</c:if>
									</c:forEach>
								</span>
							</p>
							<p>
								<span class="label">Featured:</span> <span
									class="value product-featured">${product.featured ? 'Yes' : 'No'}</span>
							</p>
							<p>
								<span class="label">Subcategory:</span> <span
									class="value product-subcategory"> <c:forEach
										var="subcategory" items="${subcategories}">
										<c:if
											test="${subcategory.subcategoryId == product.subcategoryId}">${subcategory.subcategoryName}</c:if>
									</c:forEach>
								</span>
							</p>
							<p>
								<span class="label">Size:</span> <span
									class="value product-size">${product.productSize}</span>
							</p>
							<p>
								<span class="label">Gender:</span> <span
									class="value product-gender">${product.gender}</span>
							</p>
						</div>
						<div class="material-description">
							<p>
								<span class="label">Material:</span> <span
									class="value product-material">${product.productMaterial}</span>
							</p>
							<p>
								<span class="label">Description:</span> <span
									class="value description product-description">${product.productDescription}</span>
							</p>
						</div>

						<hr
							style="margin-bottom: 15px; height: 1px; border: none; background-color: #ccc;">

						<div class="action-buttons">
							<form action="${pageContext.request.contextPath}/dashboard"
								method="get" class="action-form" style="display: inline;">
								<input type="hidden" name="action" value="editProduct">
								<input type="hidden" name="id" value="${product.productId}">
								<button type="submit" class="edit-btn">
									<i class="fas fa-edit"></i> Edit
								</button>
							</form>

							<form action="${pageContext.request.contextPath}/dashboard"
								method="post" class="action-form" style="display: inline;">
								<input type="hidden" name="action" value="deleteProduct">
								<input type="hidden" name="id" value="${product.productId}">
								<button type="submit" class="delete-btn"
									onclick="return confirm('Delete Product ID: ${product.productId}?')">
									<i class="fas fa-trash"></i> Delete
								</button>
							</form>
						</div>

					</div>
				</c:forEach>

				<c:if test="${empty products}">
					<p>No products available</p>
				</c:if>
			</div>
		</section>

		<!-- Add/Edit Product Section -->
		<section id="add-product" class="section">
			<h2>
				<i class="fas fa-plus section-title-icon" id="add-product-icon"></i>
				<span id="add-product-title">${empty product ? 'Add New Product' : 'Edit Product'}</span>
			</h2>
			<div class="product-form-card">
				<form id="productForm"
					action="${pageContext.request.contextPath}/dashboard?action=${empty product ? 'addProduct' : 'editProduct'}"
					method="post">
					<input type="hidden" id="productId" name="productId"
						value="${product.productId}">
					<div class="form-grid">
						<div class="form-group">
							<label for="productName">Product Name <span
								class="required">*</span></label> <input type="text" id="productName"
								name="productName" value="${product.productName}" required>
						</div>
						<div class="form-group">
							<label for="subcategoryId">Subcategory <span
								class="required">*</span></label> <select id="subcategoryId"
								name="subcategoryId" required>
								<option value="">Select Subcategory</option>
								<c:forEach var="subcategory" items="${subcategories}">
									<option value="${subcategory.subcategoryId}"
										${product.subcategoryId == subcategory.subcategoryId ? 'selected' : ''}>${subcategory.subcategoryName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label for="brandId">Brand <span class="required">*</span></label>
							<select id="brandId" name="brandId" required>
								<option value="">Select Brand</option>
								<c:forEach var="brand" items="${brands}">
									<option value="${brand.brandId}"
										${product.brandId == brand.brandId ? 'selected' : ''}>${brand.brandName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label for="gender">Gender <span class="required">*</span></label>
							<select id="gender" name="gender" required>
								<option value="">Select Gender</option>
								<option value="Male"
									${product.gender == 'Male' ? 'selected' : ''}>Male</option>
								<option value="Female"
									${product.gender == 'Female' ? 'selected' : ''}>Female</option>
								<option value="Unisex"
									${product.gender == 'Unisex' ? 'selected' : ''}>Unisex</option>
							</select>
						</div>
						<div class="form-group">
							<label for="productMaterial">Material</label> <input type="text"
								id="productMaterial" name="productMaterial"
								value="${product.productMaterial}">
						</div>
						<div class="form-group">
							<label for="productPrice">Price (₨) <span
								class="required">*</span></label> <input type="number" id="productPrice"
								name="productPrice" value="${product.productPrice}" step="0.01"
								min="0" required>
						</div>
						<div class="form-group">
							<label for="productSize">Size</label> <input type="text"
								id="productSize" name="productSize"
								value="${product.productSize}">
						</div>
						<div class="form-group">
							<label for="stockQuantity">Stock Quantity <span
								class="required">*</span></label> <input type="number"
								id="stockQuantity" name="stockQuantity"
								value="${product.stockQuantity}" min="0" required>
						</div>
						<div class="form-group">
							<label for="mainImageUrl">Image URL</label> <input type="text"
								id="mainImageUrl" name="mainImageUrl"
								value="${product.mainImageUrl}">
						</div>
						<div class="form-group">
							<label for="productRating">Rating (0.0–5.0)</label> <input
								type="number" id="productRating" name="productRating"
								value="${product.productRating}" step="0.1" min="0" max="5">
						</div>
						<div class="form-group checkbox-group">
							<label for="isFeatured"> <input type="checkbox"
								id="isFeatured" name="isFeatured" value="true"
								${product.featured ? 'checked' : ''}> <span
								class="label-text">Featured Product</span>
							</label>
						</div>
						<div class="form-group full-width">
							<label for="productDescription">Description</label>
							<textarea id="productDescription" name="productDescription"
								rows="5">${product.productDescription}</textarea>
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="submit-btn">${empty product ? 'Add Product' : 'Update Product'}</button>

						<a
							href="${pageContext.request.contextPath}/dashboard?action=cancelEdit"
							class="cancel-btn">Cancel</a>
					</div>
				</form>
			</div>
		</section>

		<!-- Brands Section -->
		<section id="brands" class="section">
			<div class="section-header">
				<h2>
					<i class="fas fa-tags section-title-icon"></i> Brands
				</h2>
				<button onclick="showSection('add-brand')" class="add-product-btn">Add
					Brand</button>
			</div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Brand Name</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="brandsTable">
					<c:forEach var="brand" items="${brands}">
						<tr data-brand-id="${brand.brandId}">
							<td>${brand.brandId}</td>
							<td>${brand.brandName}</td>
							<td>
								<div class="action-buttons">
									<form action="${pageContext.request.contextPath}/dashboard"
										method="get" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="editBrand">
										<input type="hidden" name="id" value="${brand.brandId}">
										<button type="submit" class="edit-btn">
											<i class="fas fa-edit"></i> Edit
										</button>
									</form>

									<form action="${pageContext.request.contextPath}/dashboard"
										method="post" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="deleteBrand">
										<input type="hidden" name="id" value="${brand.brandId}">
										<button type="submit" class="delete-btn"
											onclick="return confirm('Delete Brand ID: ${brand.brandId}?')">
											<i class="fas fa-trash"></i> Delete
										</button>
									</form>
								</div>

							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty brands}">
						<tr>
							<td colspan="3">No brands available</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</section>

		<!-- Add/Edit Brand Section -->
		<section id="add-brand" class="section">
			<h2>
				<i class="fas fa-plus section-title-icon" id="add-brand-icon"></i> <span
					id="add-brand-title">${empty brand ? 'Add New Brand' : 'Edit Brand'}</span>
			</h2>
			<div class="product-form-card">
				<form id="brandForm"
					action="${pageContext.request.contextPath}/dashboard?action=${empty brand ? 'addBrand' : 'editBrand'}"
					method="post">
					<input type="hidden" id="brandId" name="brandId"
						value="${brand.brandId}">
					<div class="form-grid">
						<div class="form-group">
							<label for="brandName">Brand Name</label> <input type="text"
								id="brandName" name="brandName" value="${brand.brandName}"
								required>
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="submit-btn">${empty brand ? 'Add Brand' : 'Update Brand'}</button>
						<a
							href="${pageContext.request.contextPath}/dashboard?action=cancelEdit"
							class="cancel-btn">Cancel</a>

					</div>
				</form>
			</div>
		</section>

		<!-- Categories Section -->
		<section id="categories" class="section">
			<div class="section-header">
				<h2>
					<i class="fas fa-list section-title-icon"></i> Categories
				</h2>
				<button onclick="showSection('add-category')"
					class="add-product-btn">Add Category</button>
			</div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Category Name</th>
						<th>Image</th>
						<!-- New column -->
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="categoriesTable">
					<c:forEach var="category" items="${categories}">
						<tr data-category-id="${category.categoryId}">
							<td>${category.categoryId}</td>
							<td>${category.categoryName}</td>
							<td><c:choose>
									<c:when test="${not empty category.categoryImage}">
										<img src="${category.categoryImage}" alt="Category Image"
											style="width: 60px; height: auto; border-radius: 4px;" />
									</c:when>
									<c:otherwise>
										<span style="color: gray;">No image</span>
									</c:otherwise>
								</c:choose></td>
							<td>
								<div class="action-buttons">
									<form action="${pageContext.request.contextPath}/dashboard"
										method="get" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="editCategory">
										<input type="hidden" name="id" value="${category.categoryId}">
										<button type="submit" class="edit-btn">
											<i class="fas fa-edit"></i> Edit
										</button>
									</form>

									<form action="${pageContext.request.contextPath}/dashboard"
										method="post" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="deleteCategory">
										<input type="hidden" name="id" value="${category.categoryId}">
										<button type="submit" class="delete-btn"
											onclick="return confirm('Delete Category ID: ${category.categoryId}?')">
											<i class="fas fa-trash"></i> Delete
										</button>
									</form>
								</div>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty categories}">
						<tr>
							<td colspan="4">No categories available</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</section>

		<!-- Add/Edit Category Section -->
		<section id="add-category" class="section">
			<h2>
				<i class="fas fa-plus section-title-icon" id="add-category-icon"></i>
				<span id="add-category-title">${empty category ? 'Add New Category' : 'Edit Category'}</span>
			</h2>
			<div class="product-form-card">
				<form id="categoryForm"
					action="${pageContext.request.contextPath}/dashboard?action=${empty category ? 'addCategory' : 'editCategory'}"
					method="post">
					<input type="hidden" id="categoryId" name="categoryId"
						value="${category.categoryId}">
					<div class="form-grid">
						<div class="form-group">
							<label for="categoryName">Category Name</label> <input
								type="text" id="categoryName" name="categoryName"
								value="${category.categoryName}" required>
						</div>
						<div class="form-group">
							<label for="categoryImage">Image URL</label> <input type="text"
								id="categoryImage" name="categoryImage"
								value="${category.categoryImage}">
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="submit-btn">${empty category ? 'Add Category' : 'Update Category'}</button>
						<a
							href="${pageContext.request.contextPath}/dashboard?action=cancelEdit"
							class="cancel-btn">Cancel</a>
					</div>
				</form>
			</div>
		</section>


		<!-- Subcategories Section -->
		<section id="subcategories" class="section">
			<div class="section-header">
				<h2>
					<i class="fas fa-list-alt section-title-icon"></i> Subcategories
				</h2>
				<button onclick="showSection('add-subcategory')"
					class="add-product-btn">Add Subcategory</button>
			</div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Category</th>
						<th>Subcategory Name</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="subcategoriesTable">
					<c:forEach var="subcategory" items="${subcategories}">
						<tr data-subcategory-id="${subcategory.subcategoryId}">
							<td>${subcategory.subcategoryId}</td>
							<td><c:forEach var="category" items="${categories}">
									<c:if test="${category.categoryId == subcategory.categoryId}">${category.categoryName}</c:if>
								</c:forEach></td>
							<td>${subcategory.subcategoryName}</td>
							<td>
								<div class="action-buttons">
									<form action="${pageContext.request.contextPath}/dashboard"
										method="get" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="editSubcategory">
										<input type="hidden" name="id"
											value="${subcategory.subcategoryId}">
										<button type="submit" class="edit-btn">
											<i class="fas fa-edit"></i> Edit
										</button>
									</form>

									<form action="${pageContext.request.contextPath}/dashboard"
										method="post" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="deleteSubcategory">
										<input type="hidden" name="id"
											value="${subcategory.subcategoryId}">
										<button type="submit" class="delete-btn"
											onclick="return confirm('Delete Subcategory ID: ${subcategory.subcategoryId}?')">
											<i class="fas fa-trash"></i> Delete
										</button>
									</form>
								</div>

							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty subcategories}">
						<tr>
							<td colspan="4">No subcategories available</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</section>

		<!-- Add/Edit Subcategory Section -->
		<section id="add-subcategory" class="section">
			<h2>
				<i class="fas fa-plus section-title-icon" id="add-subcategory-icon"></i>
				<span id="add-subcategory-title">${empty subcategory ? 'Add New Subcategory' : 'Edit Subcategory'}</span>
			</h2>
			<div class="product-form-card">
				<form id="subcategoryForm"
					action="${pageContext.request.contextPath}/dashboard?action=${empty subcategory ? 'addSubcategory' : 'editSubcategory'}"
					method="post">
					<input type="hidden" id="subcategoryId" name="subcategoryId"
						value="${subcategory.subcategoryId}">
					<div class="form-grid">
						<div class="form-group">
							<label for="categoryId">Category</label> <select id="categoryId"
								name="categoryId" required>
								<option value="">Select Category</option>
								<c:forEach var="category" items="${categories}">
									<option value="${category.categoryId}"
										${subcategory.categoryId == category.categoryId ? 'selected' : ''}>${category.categoryName}</option>
								</c:forEach>
							</select>
						</div>
						<div class="form-group">
							<label for="subcategoryName">Subcategory Name</label> <input
								type="text" id="subcategoryName" name="subcategoryName"
								value="${subcategory.subcategoryName}" required>
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="submit-btn">${empty subcategory ? 'Add Subcategory' : 'Update Subcategory'}</button>
						<a
							href="${pageContext.request.contextPath}/dashboard?action=cancelEdit"
							class="cancel-btn">Cancel</a>
					</div>
				</form>
			</div>
		</section>

		<!-- Sliders Section -->
		<section id="sliders" class="section">
			<div class="section-header">
				<h2>
					<i class="fas fa-images section-title-icon"></i> Sliders
				</h2>
				<button onclick="showSection('add-slider')" class="slider-add-btn">
					<i class="fas fa-plus"></i> Add Slider
				</button>
			</div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Image Preview</th>
						<th>Text</th>
						<th>Sub Text</th>
						<th>Link</th>
						<th>Order</th>
						<th>Active</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="slidersTable">
					<c:forEach var="slider" items="${sliders}">
						<tr data-slider-id="${slider.sliderId}">
							<td>${slider.sliderId}</td>
							<td><c:choose>
									<c:when
										test="${slider.sliderImageUrl.startsWith('http://') || slider.sliderImageUrl.startsWith('https://')}">
										<img src="${slider.sliderImageUrl}" alt="Slider Image"
											class="slider-preview" />
									</c:when>
									<c:otherwise>
										<img
											src="${pageContext.request.contextPath}${slider.sliderImageUrl}"
											alt="Slider Image" class="slider-preview" />
									</c:otherwise>
								</c:choose></td>
							<td>${slider.sliderText}</td>
							<td>${slider.sliderSubText}</td>
							<td><a
								href="${pageContext.request.contextPath}${slider.sliderLink}"
								target="_blank" class="slider-link"> ${slider.sliderLink} </a></td>
							<td>${slider.sliderOrder}</td>
							<td>${slider.active ? 'Yes' : 'No'}</td>
							<td>
								<div class="action-buttons">
									<form action="${pageContext.request.contextPath}/dashboard"
										method="get" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="editSlider">
										<input type="hidden" name="id" value="${slider.sliderId}">
										<button type="submit" class="slider-edit-btn">
											<i class="fas fa-edit"></i> Edit
										</button>
									</form>
									<form action="${pageContext.request.contextPath}/dashboard"
										method="post" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="deleteSlider">
										<input type="hidden" name="id" value="${slider.sliderId}">
										<button type="submit" class="slider-delete-btn"
											onclick="return confirm('Delete Slider ID: ${slider.sliderId}?')">
											<i class="fas fa-trash"></i> Delete
										</button>
									</form>
								</div>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty sliders}">
						<tr>
							<td colspan="8">No sliders available</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</section>

		<!-- Add/Edit Slider Section -->
		<section id="add-slider" class="section">
			<h2>
				<i class="fas fa-plus section-title-icon" id="add-slider-icon"></i>
				<span id="add-slider-title">${empty slider ? 'Add New Slider' : 'Edit Slider'}</span>
			</h2>
			<div class="slider-form-card">
				<form id="sliderForm"
					action="${pageContext.request.contextPath}/dashboard?action=${empty slider ? 'addSlider' : 'editSlider'}"
					method="post" onsubmit="return validateSliderForm()">
					<input type="hidden" id="sliderId" name="sliderId"
						value="${slider.sliderId}">
					<div class="slider-form-grid">
						<div class="slider-form-group">
							<label for="sliderImageUrl">Image URL</label> <input type="text"
								id="sliderImageUrl" name="sliderImageUrl"
								value="${slider.sliderImageUrl}" required
								placeholder="e.g., /resources/images/homeSlider/img1.jpg or https://example.com/image.jpg">
							<div class="slider-image-preview-container">
								<img id="sliderImagePreview"
									src="${empty slider.sliderImageUrl ? '' : (slider.sliderImageUrl.startsWith('http://') || slider.sliderImageUrl.startsWith('https://') ? slider.sliderImageUrl : pageContext.request.contextPath.concat(slider.sliderImageUrl))}"
									alt="Image Preview"
									style="display: ${empty slider.sliderImageUrl ? 'none' : 'block'};" />
							</div>
						</div>
						<div class="slider-form-group">
							<label for="sliderText">Text</label> <input type="text"
								id="sliderText" name="sliderText" value="${slider.sliderText}"
								required placeholder="e.g., Welcome to SportsVerse">
						</div>
						<div class="slider-form-group">
							<label for="sliderSubText">Sub Text</label> <input type="text"
								id="sliderSubText" name="sliderSubText"
								value="${slider.sliderSubText}"
								placeholder="e.g., Shop the best sports gear!">
						</div>
						<div class="slider-form-group">
							<label for="sliderLink">CTA Link</label> <input type="text"
								id="sliderLink" name="sliderLink" value="${slider.sliderLink}"
								placeholder="e.g., /products?categoryId=1">
						</div>
						<div class="slider-form-group">
							<label for="sliderOrder">Order</label> <input type="number"
								id="sliderOrder" name="sliderOrder"
								value="${slider.sliderOrder}" min="0" required>
						</div>
						<div class="slider-form-group slider-checkbox-group">
							<label for="isActive"> <input type="checkbox"
								id="isActive" name="isActive" value="true"
								${slider.active ? 'checked' : ''}> <span
								class="slider-label-text">Active</span>
							</label>
						</div>
					</div>
					<div class="slider-form-actions" style = "margin-top:15px;">
						<button type="submit" class="slider-submit-btn">${empty slider ? 'Add Slider' : 'Update Slider'}</button>
						<a
							href="${pageContext.request.contextPath}/dashboard?action=cancelEdit"
							class="slider-cancel-btn">Cancel</a>
					</div>
				</form>
			</div>
			<script>
        function validateSliderForm() {
            const imageUrl = document.getElementById('sliderImageUrl').value;
            const sliderText = document.getElementById('sliderText').value;
            const sliderOrder = document.getElementById('sliderOrder').value;
            const urlPattern = /^(https?:\/\/.*\.(jpg|jpeg|png|gif)$)|(\/.*\.(jpg|jpeg|png|gif)$)/i;
            const linkPattern = /^\/[^\s]*$/;

            if (!imageUrl || !urlPattern.test(imageUrl)) {
                alert('Please enter a valid image URL (e.g., /resources/images/homeSlider/img1.jpg or https://example.com/image.jpg)');
                return false;
            }

            if (!sliderText.trim()) {
                alert('Slider Text is required');
                return false;
            }

            if (sliderOrder < 0) {
                alert('Slider Order must be a non-negative number');
                return false;
            }

            const sliderLink = document.getElementById('sliderLink').value;
            if (sliderLink && !linkPattern.test(sliderLink)) {
                alert('CTA Link must be a valid relative path (e.g., /products?categoryId=1)');
                return false;
            }

            return true;
        }

        document.addEventListener('DOMContentLoaded', function() {
            const sliderImageUrlInput = document.getElementById('sliderImageUrl');
            const imagePreview = document.getElementById('sliderImagePreview');

            if (sliderImageUrlInput && imagePreview) {
                sliderImageUrlInput.addEventListener('input', function() {
                    const url = sliderImageUrlInput.value;
                    if (url) {
                        if (url.startsWith('http://') || url.startsWith('https://')) {
                            imagePreview.src = url;
                        } else {
                            imagePreview.src = '${pageContext.request.contextPath}' + url;
                        }
                        imagePreview.style.display = 'block';
                    } else {
                        imagePreview.src = '';
                        imagePreview.style.display = 'none';
                    }
                });
            }
        });
    </script>
		</section>

		<!-- Orders Section -->
		<section id="orders" class="section">
			<div class="section-header">
				<h2>
					<i class="fas fa-shopping-cart section-title-icon"></i> Orders
				</h2>
			</div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>User</th>
						<th>Order Date</th>
						<th>Product</th>
						<th>Quantity Ordered</th>
						<th>Unit Price</th>
						<th>Total Amount</th>
						<th>Address</th>
						<th>Delivery Status</th>
						<th>Payment Method</th>
						<th>Payment Status</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="ordersTable">
					<c:forEach var="order" items="${orders}">
						<tr data-order-id="${order.orderId}">
							<td>${order.orderId}</td>
							<td><c:forEach var="user" items="${users}">
									<c:if test="${user.userId == order.userId}">${user.firstName} ${user.lastName}</c:if>
								</c:forEach></td>
							<td><fmt:formatDate value="${order.orderDate}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><c:forEach var="orderProduct"
									items="${order.orderProducts}" varStatus="loop">
									<c:forEach var="product" items="${products}">
										<c:if test="${product.productId == orderProduct.productId}">${product.productName}</c:if>
									</c:forEach>
									<c:if test="${!loop.last}">,</c:if>
								</c:forEach></td>
							<td><c:forEach var="orderProduct"
									items="${order.orderProducts}" varStatus="loop">
                            ${orderProduct.quantityOrdered}<c:if
										test="${!loop.last}">,</c:if>
								</c:forEach></td>
							<td><c:forEach var="orderProduct"
									items="${order.orderProducts}" varStatus="loop">
									<fmt:formatNumber value="${orderProduct.unitPrice}"
										type="currency" currencySymbol="₨ " />
									<c:if test="${!loop.last}">,</c:if>
								</c:forEach></td>
							<td><fmt:formatNumber value="${order.totalAmount}"
									type="currency" currencySymbol="₨ " /></td>
							<td><c:forEach var="address" items="${addresses}">
									<c:if test="${address.addressId == order.addressId}">${address.streetAddress}, ${address.city}</c:if>
								</c:forEach></td>
							<td>${order.orderStatus}</td>
							<td><c:forEach var="paymentMethod" items="${paymentMethods}">
									<c:if
										test="${paymentMethod.paymentMethodId == order.paymentMethodId}">${paymentMethod.methodName}</c:if>
								</c:forEach></td>
							<td>${order.orderPaymentStatus}</td>
							<td>
								<div class="action-buttons">
									<form action="${pageContext.request.contextPath}/dashboard"
										method="get" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="editOrder">
										<input type="hidden" name="id" value="${order.orderId}">
										<button type="submit" class="edit-btn">
											<i class="fas fa-edit"></i> Edit
										</button>
									</form>
									<form action="${pageContext.request.contextPath}/dashboard"
										method="post" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="deleteOrder">
										<input type="hidden" name="id" value="${order.orderId}">
										<button type="submit" class="delete-btn"
											onclick="return confirm('Delete Order ID: ${order.orderId}?')">
											<i class="fas fa-trash"></i> Delete
										</button>
									</form>
								</div>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty orders}">
						<tr>
							<td colspan="12">No orders available</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</section>

		<!-- Edit Order Section -->
		<section id="edit-order" class="section">
			<h2>
				<i class="fas fa-edit section-title-icon" id="edit-order-icon"></i>
				<span id="edit-order-title">Edit Order</span>
			</h2>
			<div class="product-form-card">
				<form id="orderForm"
					action="${pageContext.request.contextPath}/dashboard?action=editOrder"
					method="post">
					<input type="hidden" id="orderId" name="orderId"
						value="${order.orderId}">
					<div class="form-grid">
						<!-- Read-only Order Details -->
						<div class="form-group">
							<label>User</label>
							<p>
								<c:forEach var="user" items="${users}">
									<c:if test="${user.userId == order.userId}">${user.firstName} ${user.lastName}</c:if>
								</c:forEach>
							</p>
						</div>
						<div class="form-group">
							<label>Order Date</label>
							<p>
								<fmt:formatDate value="${order.orderDate}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</p>
						</div>
						<div class="form-group">
							<label>Product</label>
							<p>
								<c:forEach var="orderProduct" items="${order.orderProducts}"
									varStatus="loop">
									<c:forEach var="product" items="${products}">
										<c:if test="${product.productId == orderProduct.productId}">${product.productName}</c:if>
									</c:forEach>
									<c:if test="${!loop.last}">,</c:if>
								</c:forEach>
							</p>
						</div>
						<div class="form-group">
							<label>Quantity Ordered</label>
							<p>
								<c:forEach var="orderProduct" items="${order.orderProducts}"
									varStatus="loop">
                            ${orderProduct.quantityOrdered}<c:if
										test="${!loop.last}">,</c:if>
								</c:forEach>
							</p>
						</div>
						<div class="form-group">
							<label>Unit Price</label>
							<p>
								<c:forEach var="orderProduct" items="${order.orderProducts}"
									varStatus="loop">
									<fmt:formatNumber value="${orderProduct.unitPrice}"
										type="currency" currencySymbol="₨ " />
									<c:if test="${!loop.last}">,</c:if>
								</c:forEach>
							</p>
						</div>
						<div class="form-group">
							<label>Total Amount</label>
							<p>
								<fmt:formatNumber value="${order.totalAmount}" type="currency"
									currencySymbol="₨ " />
							</p>
						</div>
						<div class="form-group">
							<label>Address</label>
							<p>
								<c:forEach var="address" items="${addresses}">
									<c:if test="${address.addressId == order.addressId}">${address.streetAddress}, ${address.city}</c:if>
								</c:forEach>
							</p>
						</div>
						<div class="form-group">
							<label>Payment Method</label>
							<p>
								<c:forEach var="paymentMethod" items="${paymentMethods}">
									<c:if
										test="${paymentMethod.paymentMethodId == order.paymentMethodId}">${paymentMethod.methodName}</c:if>
								</c:forEach>
							</p>
						</div>
						<!-- Editable Fields -->
						<div class="form-group">
							<label for="orderStatus">Delivery Status</label> <select
								id="orderStatus" name="orderStatus" required>
								<option value="">Select Status</option>
								<option value="Pending"
									${order.orderStatus == 'Pending' ? 'selected' : ''}>Pending</option>
								<option value="Processing"
									${order.orderStatus == 'Processing' ? 'selected' : ''}>Processing</option>
								<option value="Shipped"
									${order.orderStatus == 'Shipped' ? 'selected' : ''}>Shipped</option>
								<option value="Delivered"
									${order.orderStatus == 'Delivered' ? 'selected' : ''}>Delivered</option>
								<option value="Cancelled"
									${order.orderStatus == 'Cancelled' ? 'selected' : ''}>Cancelled</option>
							</select>
						</div>
						<div class="form-group">
							<label for="orderPaymentStatus">Payment Status</label> <select
								id="orderPaymentStatus" name="orderPaymentStatus" required>
								<option value="">Select Payment Status</option>
								<option value="Pending"
									${order.orderPaymentStatus == 'Pending' ? 'selected' : ''}>Pending</option>
								<option value="Completed"
									${order.orderPaymentStatus == 'Completed' ? 'selected' : ''}>Completed</option>
								<option value="Failed"
									${order.orderPaymentStatus == 'Failed' ? 'selected' : ''}>Failed</option>
							</select>
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="submit-btn">Update Order</button>
						<a
							href="${pageContext.request.contextPath}/dashboard?action=cancelEdit"
							class="cancel-btn">Cancel</a>
					</div>
				</form>
			</div>
		</section>

		<!-- Carts Section -->
		<section id="carts" class="section">
			<div class="section-header">
				<h2>
					<i class="fas fa-shopping-basket section-title-icon"></i> Carts
				</h2>
			</div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>User</th>
						<th>Product</th>
						<th>Quantity</th>
						<th>Added At</th>
					</tr>
				</thead>
				<tbody id="cartsTable">
					<c:forEach var="cart" items="${carts}">
						<tr>
							<td>${cart.cartId}</td>
							<td><c:forEach var="user" items="${users}">
									<c:if test="${user.userId == cart.userId}">${user.firstName} ${user.lastName}</c:if>
								</c:forEach></td>
							<td><c:forEach var="product" items="${products}">
									<c:if test="${product.productId == cart.productId}">${product.productName}</c:if>
								</c:forEach></td>
							<td>${cart.cartQuantity}</td>
							<td><fmt:formatDate value="${cart.addedAt}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
						</tr>
					</c:forEach>
					<c:if test="${empty carts}">
						<tr>
							<td colspan="5">No carts available</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</section>

		<!-- Payment Methods Section -->
		<section id="payment-methods" class="section">
			<div class="section-header">
				<h2>
					<i class="fas fa-money-check-alt section-title-icon"></i> Payment
					Methods
				</h2>
				<button onclick="showSection('add-payment-method')"
					class="add-product-btn">Add Payment Method</button>
			</div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Method Name</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="paymentMethodsTable">
					<c:forEach var="paymentMethod" items="${paymentMethods}">
						<tr data-payment-method-id="${paymentMethod.paymentMethodId}">
							<td>${paymentMethod.paymentMethodId}</td>
							<td>${paymentMethod.methodName}</td>
							<td>
								<div class="action-buttons">
									<form action="${pageContext.request.contextPath}/dashboard"
										method="get" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="editPaymentMethod">
										<input type="hidden" name="id"
											value="${paymentMethod.paymentMethodId}">
										<button type="submit" class="edit-btn">
											<i class="fas fa-edit"></i> Edit
										</button>
									</form>
									<form action="${pageContext.request.contextPath}/dashboard"
										method="post" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="deletePaymentMethod">
										<input type="hidden" name="id"
											value="${paymentMethod.paymentMethodId}">
										<button type="submit" class="delete-btn"
											onclick="return confirm('Delete Payment Method: ${paymentMethod.methodName}?')">
											<i class="fas fa-trash"></i> Delete
										</button>
									</form>
								</div>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty paymentMethods}">
						<tr>
							<td colspan="3">No payment methods available</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</section>

		<!-- Add/Edit Payment Method Section -->
		<section id="add-payment-method" class="section">
			<h2>
				<i class="fas fa-plus section-title-icon"
					id="add-payment-method-icon"></i> <span
					id="add-payment-method-title">${empty paymentMethod ? 'Add New Payment Method' : 'Edit Payment Method'}</span>
			</h2>
			<div class="product-form-card">
				<form id="paymentMethodForm"
					action="${pageContext.request.contextPath}/dashboard?action=${empty paymentMethod ? 'addPaymentMethod' : 'editPaymentMethod'}"
					method="post">
					<input type="hidden" id="paymentMethodId" name="paymentMethodId"
						value="${paymentMethod.paymentMethodId}">
					<div class="form-grid">
						<div class="form-group">
							<label for="methodName">Method Name</label> <input type="text"
								id="methodName" name="methodName"
								value="${paymentMethod.methodName}" required maxlength="100"
								pattern=".{1,100}" title="Method name must be 1-100 characters">
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="submit-btn">${empty paymentMethod ? 'Add Payment Method' : 'Update Payment Method'}</button>
						<a
							href="${pageContext.request.contextPath}/dashboard?action=cancelEdit"
							class="cancel-btn">Cancel</a>
					</div>
				</form>
			</div>
		</section>

		<!-- Payments Section -->
		<section id="payments" class="section">
			<div class="section-header">
				<h2>
					<i class="fas fa-credit-card section-title-icon"></i> Payments
				</h2>
			</div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Order ID</th>
						<th>User</th>
						<th>Payment Method</th>
						<th>Amount</th>
						<th>Date</th>
						<th>Payment Status</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="paymentsTable">
					<c:forEach var="payment" items="${payments}">
						<tr data-payment-id="${payment.paymentId}">
							<td>${payment.paymentId}</td>
							<td>${payment.orderId}</td>
							<td><c:forEach var="order" items="${orders}">
									<c:if test="${order.orderId == payment.orderId}">
										<c:forEach var="user" items="${users}">
											<c:if test="${user.userId == order.userId}">${user.firstName} ${user.lastName}</c:if>
										</c:forEach>
									</c:if>
								</c:forEach></td>
							<td><c:set var="methodFound" value="false" /> <c:forEach
									var="paymentMethod" items="${paymentMethods}">
									<c:if
										test="${paymentMethod.paymentMethodId == payment.paymentMethodId}">
                                ${paymentMethod.methodName}
                                <c:set var="methodFound" value="true" />
									</c:if>
								</c:forEach> <c:if test="${!methodFound}">Unknown</c:if></td>
							<td><fmt:formatNumber value="${payment.paymentAmount}"
									type="currency" currencySymbol="₨" /></td>
							<td><fmt:formatDate value="${payment.paymentDate}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${payment.paymentStatus}</td>
							<td>
								<div class="action-buttons">
									<form action="${pageContext.request.contextPath}/dashboard"
										method="get" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="editPayment">
										<input type="hidden" name="id" value="${payment.paymentId}">
										<button type="submit" class="edit-btn">
											<i class="fas fa-edit"></i> Edit
										</button>
									</form>
									<form action="${pageContext.request.contextPath}/dashboard"
										method="post" class="action-form" style="display: inline;">
										<input type="hidden" name="action" value="deletePayment">
										<input type="hidden" name="id" value="${payment.paymentId}">
										<button type="submit" class="delete-btn"
											onclick="return confirm('Delete Payment ID: ${payment.paymentId} for Order ${payment.orderId}?')">
											<i class="fas fa-trash"></i> Delete
										</button>
									</form>
								</div>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty payments}">
						<tr>
							<td colspan="8">No payments available</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</section>

		<!-- Edit Payment Section -->
		<section id="edit-payment" class="section">
			<h2>
				<i class="fas fa-edit section-title-icon" id="edit-payment-icon"></i>
				<span id="edit-payment-title">Edit Payment</span>
			</h2>
			<div class="product-form-card">
				<form id="paymentForm"
					action="${pageContext.request.contextPath}/dashboard?action=editPayment"
					method="post">
					<input type="hidden" id="paymentId" name="paymentId"
						value="${payment.paymentId}">
					<div class="form-grid">
						<!-- Read-only Payment Details -->
						<div class="form-group">
							<label>Order ID</label>
							<p>${payment.orderId}</p>
						</div>
						<div class="form-group">
							<label>User</label>
							<p>
								<c:forEach var="order" items="${orders}">
									<c:if test="${order.orderId == payment.orderId}">
										<c:forEach var="user" items="${users}">
											<c:if test="${user.userId == order.userId}">${user.firstName} ${user.lastName}</c:if>
										</c:forEach>
									</c:if>
								</c:forEach>
							</p>
						</div>
						<div class="form-group">
							<label>Payment Method</label>
							<p>
								<c:forEach var="paymentMethod" items="${paymentMethods}">
									<c:if
										test="${paymentMethod.paymentMethodId == payment.paymentMethodId}">${paymentMethod.methodName}</c:if>
								</c:forEach>
							</p>
						</div>
						<div class="form-group">
							<label>Amount</label>
							<p>
								<fmt:formatNumber value="${payment.paymentAmount}"
									type="currency" currencySymbol="₨" />
							</p>
						</div>
						<div class="form-group">
							<label>Date</label>
							<p>
								<fmt:formatDate value="${payment.paymentDate}"
									pattern="yyyy-MM-dd HH:mm:ss" />
							</p>
						</div>
						<!-- Editable Field -->
						<div class="form-group">
							<label for="paymentStatus">Payment Status</label> <select
								id="paymentStatus" name="paymentStatus" required>
								<option value="">Select Status</option>
								<option value="Pending"
									${payment.paymentStatus == 'Pending' ? 'selected' : ''}>Pending</option>
								<option value="Completed"
									${payment.paymentStatus == 'Completed' ? 'selected' : ''}>Completed</option>
								<option value="Failed"
									${payment.paymentStatus == 'Failed' ? 'selected' : ''}>Failed</option>
							</select>
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="submit-btn">Update Payment</button>
						<a
							href="${pageContext.request.contextPath}/dashboard?action=cancelEdit"
							class="cancel-btn">Cancel</a>
					</div>
				</form>
			</div>
		</section>

		<!-- Reviews Section -->
		<section id="reviews" class="section">
			<div class="section-header">
				<h2>
					<i class="fas fa-star section-title-icon"></i> Reviews
				</h2>
			</div>
			<table>
				<thead>
					<tr>
						<th>ID</th>
						<th>Product</th>
						<th>User</th>
						<th>Rating</th>
						<th>Comment</th>
						<th>Date</th>
						<th>Verified</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody id="reviewsTable">
					<c:forEach var="review" items="${reviews}">
						<tr data-review-id="${review.reviewId}">
							<td>${review.reviewId}</td>
							<td><c:forEach var="product" items="${products}">
									<c:if test="${product.productId == review.productId}">${product.productName}</c:if>
								</c:forEach></td>
							<td><c:forEach var="user" items="${users}">
									<c:if test="${user.userId == review.userId}">${user.firstName} ${user.lastName}</c:if>
								</c:forEach></td>
							<td><fmt:formatNumber value="${review.rating}"
									minFractionDigits="2" maxFractionDigits="2" /></td>
							<td>${review.reviewComment}</td>
							<td><fmt:formatDate value="${review.reviewDate}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${review.verified ? 'Yes' : 'No'}</td>
							<td>
								<div class="action-buttons">
									<form
										action="${pageContext.request.contextPath}/dashboard?action=verifyReview&id=${review.reviewId}"
										method="post" style="display: inline;">
										<input type="hidden" name="isVerified"
											value="${!review.verified}">
										<button type="submit" class="edit-btn"
											onclick="return confirm('${review.verified ? 'Unverify' : 'Verify'} Review ID: ${review.reviewId}?')">
											<i class="fas fa-check"></i> ${review.verified ? 'Unverify' : 'Verify'}
										</button>
									</form>
									<form
										action="${pageContext.request.contextPath}/dashboard?action=deleteReview&id=${review.reviewId}"
										method="post" style="display: inline;">
										<button type="submit" class="delete-btn"
											onclick="return confirm('Delete Review ID: ${review.reviewId}?')">
											<i class="fas fa-trash"></i> Delete
										</button>
									</form>
								</div>
							</td>
						</tr>
					</c:forEach>
					<c:if test="${empty reviews}">
						<tr>
							<td colspan="8">No reviews available</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</section>


		<!-- Generate Report Section -->
		<section id="generate-report" class="section">
			<h2>
				<i class="fas fa-table section-title-icon"></i> Generate Report
			</h2>
			<div class="product-form-card">
				<form id="reportForm"
					action="${pageContext.request.contextPath}/dashboard?action=generateReport"
					method="post">
					<div class="form-grid">
						<div class="report-form-group">
							<label for="reportType">Report Type</label> <select
								id="reportType" name="reportType" required>
								<option value="" disabled selected>Select Type</option>
								<option value="Sales">Sales</option>
								<option value="User Activity">User Activity</option>
								<option value="Inventory">Inventory</option>
							</select>
						</div>

						<div class="report-form-group" id="userIdGroup">
							<label for="reportUserId"
								style="display: block; margin-bottom: 4px;">User</label> <select
								id="reportUserId" name="userId"
								style="display: block; width: 100%; margin-top: 8px;">
								<option value="" disabled selected>Select User</option>
								<option value="0">All Users</option>
								<c:forEach var="user" items="${users}">
									<option value="${user.userId}">${user.firstName}
										${user.lastName}</option>
								</c:forEach>
							</select>
						</div>


						<div class="report-form-group">
							<label for="reportScope">Scope</label> <select id="reportScope"
								name="reportScope" required>
								<option value="" disabled selected>Select Scope</option>
								<option value="Daily">Daily</option>
								<option value="Weekly">Weekly</option>
								<option value="Monthly">Monthly</option>
							</select>
						</div>
						<div class="report-form-group">
							<label for="startDate">Start Date</label> <input
								type="datetime-local" id="startDate" name="startDate" required>
						</div>
						<div class="report-form-group">
							<label for="endDate">End Date</label> <input
								type="datetime-local" id="endDate" name="endDate" required>
						</div>
					</div>
					<div class="form-actions">
						<button type="submit" class="submit-btn">
							<i class="fas fa-play"></i> Generate
						</button>
						<button type="button" class="cancel-btn"
							onclick="document.getElementById('reportForm').reset(); document.getElementById('userIdGroup').style.display = 'block';">
							<i class="fas fa-times"></i> Clear
						</button>
					</div>
				</form>
			</div>

			<!-- Display Generated Report -->
			<c:if test="${not empty report}">
				<div class="report-result">
					<h3 class="section-subtitle">Report Details</h3>
					<!-- Summary Table -->
					<table class="report-table">
						<thead>
							<tr>
								<th>ID</th>
								<th>User</th>
								<th>Type</th>
								<th>Scope</th>
								<th>Start Date</th>
								<th>End Date</th>
								<th>Total Orders</th>
								<th>Total Spent</th>
							</tr>
						</thead>
						<tbody>
							<tr data-report-id="${report.reportId}">
								<td>${report.reportId}</td>
								<td><c:choose>
										<c:when test="${report.userId != 0}">
                                    ${report.userName}
                                </c:when>
										<c:otherwise>All Users</c:otherwise>
									</c:choose></td>
								<td>${report.reportType}</td>
								<td>${report.reportScope}</td>
								<td><fmt:formatDate value="${report.startDate}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><fmt:formatDate value="${report.endDate}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td>${report.totalOrders}</td>
								<td><fmt:formatNumber value="${report.totalSpent}"
										type="currency" currencySymbol="₨ " /></td>
							</tr>
						</tbody>
					</table>

					<!-- Detailed Report -->
					<c:choose>
						<c:when test="${report.reportType == 'Sales'}">
							<h4>Sales Details</h4>
							<table class="report-table">
								<thead>
									<tr>
										<th>Product ID</th>
										<th>Product Name</th>
										<c:if test="${report.userId == 0}">
											<th>User</th>
										</c:if>
										<th>Quantity Sold</th>
										<th>Total Amount</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="saleItem" items="${report.salesItems}">
										<tr>
											<td>${saleItem.productId}</td>
											<td>${saleItem.productName}</td>
											<c:if test="${report.userId == 0}">
												<td>${saleItem.userName}</td>
											</c:if>
											<td>${saleItem.quantitySold}</td>
											<td><fmt:formatNumber value="${saleItem.totalAmount}"
													type="currency" currencySymbol="₨ " /></td>
										</tr>
									</c:forEach>
									<c:if test="${empty report.salesItems}">
										<tr>
											<td colspan="${report.userId == 0 ? 5 : 4}">No sales
												data available</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</c:when>
						<c:when test="${report.reportType == 'User Activity'}">
							<h4>User Activity: ${report.userName}</h4>
							<h5>Purchase History</h5>
							<table class="report-table">
								<thead>
									<tr>
										<th>Order ID</th>
										<th>Product ID</th>
										<th>Product Name</th>
										<th>Quantity</th>
										<th>Amount</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="purchaseItem" items="${report.purchaseItems}">
										<tr>
											<td>${purchaseItem.orderId}</td>
											<td>${purchaseItem.productId}</td>
											<td>${purchaseItem.productName}</td>
											<td>${purchaseItem.quantity}</td>
											<td><fmt:formatNumber value="${purchaseItem.amount}"
													type="currency" currencySymbol="₨ " /></td>
										</tr>
									</c:forEach>
									<c:if test="${empty report.purchaseItems}">
										<tr>
											<td colspan="5">No purchase history available</td>
										</tr>
									</c:if>
								</tbody>
							</table>
							<h5>Cart Items</h5>
							<table class="report-table">
								<thead>
									<tr>
										<th>Product ID</th>
										<th>Product Name</th>
										<th>Quantity</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="cartItem" items="${report.cartItems}">
										<tr>
											<td>${cartItem.productId}</td>
											<td>${cartItem.productName}</td>
											<td>${cartItem.quantity}</td>
										</tr>
									</c:forEach>
									<c:if test="${empty report.cartItems}">
										<tr>
											<td colspan="3">No items in cart</td>
										</tr>
									</c:if>
								</tbody>
							</table>
						</c:when>
						<c:when test="${report.reportType == 'Inventory'}">
							<h4>Inventory Details</h4>
							<table class="report-table">
								<thead>
									<tr>
										<th>Product ID</th>
										<th>Product Name</th>
										<th>Stock Quantity</th>
										<th>Price</th>
										<th>Low Stock</th>
										<th>Total Value</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach var="inventoryItem" items="${report.inventoryItems}">
										<tr>
											<td>${inventoryItem.productId}</td>
											<td>${inventoryItem.productName}</td>
											<td>${inventoryItem.stockQuantity}</td>
											<td><fmt:formatNumber value="${inventoryItem.price}"
													type="currency" currencySymbol="₨ " /></td>
											<td class="${inventoryItem.lowStock ? 'low-stock' : ''}">
												${inventoryItem.lowStock ? 'Yes' : 'No'}</td>
											<td><fmt:formatNumber
													value="${inventoryItem.stockQuantity * inventoryItem.price}"
													type="currency" currencySymbol="₨ " /></td>
										</tr>
									</c:forEach>
									<c:if test="${empty report.inventoryItems}">
										<tr>
											<td colspan="6">No inventory data available</td>
										</tr>
									</c:if>
								</tbody>
							</table>
							<h5>Inventory Summary</h5>
							<table class="report-table summary-table">
								<thead>
									<tr>
										<th>Metric</th>
										<th>Value</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th><i class="fas fa-box"></i> Total Products</th>
										<td>${report.totalProducts}</td>
									</tr>
									<tr>
										<th><i class="fas fa-box-open"></i> Low Stock Products</th>
										<td>${report.lowStockCount}</td>
									</tr>
									<tr>
										<th><i class="fas fa-coins"></i> Total Inventory Value</th>
										<td><fmt:formatNumber value="${report.totalSpent}"
												type="currency" currencySymbol="₨ " /></td>
									</tr>
								</tbody>
							</table>
						</c:when>
					</c:choose>
				</div>
			</c:if>

		</section>

		<!-- Analytics Section -->
		<section id="analytics" class="section">
			<h2>
				<i class="fas fa-chart-line section-title-icon"></i> Analytics
			</h2>
			<div class="chart-container">
				<!-- Pie Charts: Brand and Category Distribution -->
				<div class="chart-pie-row">
					<!-- Brand Distribution Pie Chart -->
					<div class="chart-card chart-pie">
						<h3 class="section-subtitle">Brand Distribution</h3>
						<canvas id="brandPieChart" style="max-height: 300px;"></canvas>
					</div>
					<!-- Category Distribution Pie Chart -->
					<div class="chart-card chart-pie">
						<h3 class="section-subtitle">Category Distribution</h3>
						<canvas id="categoryPieChart" style="max-height: 300px;"></canvas>
					</div>
				</div>
				<!-- Product Stock Chart -->
				<div class="chart-card chart-bar">
					<h3 class="section-subtitle">Product Stock Levels</h3>
					<canvas id="analyticsStockChart" style="max-height: 400px;"></canvas>
				</div>
				<!-- Doughnut Charts: Order and Payment Status -->
				<div class="chart-doughnut-row">
					<!-- Order Status Doughnut Chart -->
					<div class="chart-card chart-doughnut">
						<h3 class="section-subtitle">Order Status Distribution</h3>
						<canvas id="orderStatusDoughnutChart" style="max-height: 300px;"></canvas>
					</div>
					<!-- Payment Status Doughnut Chart -->
					<div class="chart-card chart-doughnut">
						<h3 class="section-subtitle">Payment Status Distribution</h3>
						<canvas id="paymentStatusDoughnutChart" style="max-height: 300px;"></canvas>
					</div>
				</div>
			</div>

			<!-- Pass product data to JavaScript -->
			<c:choose>
				<c:when test="${not empty products}">
					<script>
                window.productStockData = {
                    labels: [<c:forEach var="product" items="${products}" varStatus="loop">'${product.productName.replace("'", "\\'")}'${loop.last ? '' : ','}</c:forEach>],
                    quantities: [<c:forEach var="product" items="${products}" varStatus="loop">${product.stockQuantity}${loop.last ? '' : ','}</c:forEach>]
                };
                console.log('Product Stock Data:', window.productStockData);
            </script>
				</c:when>
				<c:otherwise>
					<p class="message error">No product data available for stock
						chart.</p>
				</c:otherwise>
			</c:choose>

			<!-- Pass analytics data to JavaScript -->
			<c:choose>
				<c:when
					test="${not empty products && not empty brands && not empty categories && not empty orders && not empty payments}">
					<script>
                window.analyticsData = {
                    brands: {
                        labels: [<c:forEach var="brand" items="${brands}" varStatus="loop">'${brand.brandName.replace("'", "\\'")}'${loop.last ? '' : ','}</c:forEach>],
                        counts: [
                            <c:forEach var="brand" items="${brands}" varStatus="loop">
                                <c:set var="count" value="0"/>
                                <c:forEach var="product" items="${products}">
                                    <c:if test="${product.brandId == brand.brandId}">
                                        <c:set var="count" value="${count + 1}"/>
                                    </c:if>
                                </c:forEach>
                                ${count}${loop.last ? '' : ','}
                            </c:forEach>
                        ]
                    },
                    categories: {
                        labels: [<c:forEach var="category" items="${categories}" varStatus="loop">'${category.categoryName.replace("'", "\\'")}'${loop.last ? '' : ','}</c:forEach>],
                        counts: [
                            <c:forEach var="category" items="${categories}" varStatus="loop">
                                <c:set var="count" value="0"/>
                                <c:forEach var="product" items="${products}">
                                    <c:forEach var="subcategory" items="${subcategories}">
                                        <c:if test="${subcategory.subcategoryId == product.subcategoryId && subcategory.categoryId == category.categoryId}">
                                            <c:set var="count" value="${count + 1}"/>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                                ${count}${loop.last ? '' : ','}
                            </c:forEach>
                        ]
                    },
                    orderStatus: {
                        labels: ['Pending', 'Processing', 'Shipped', 'Delivered', 'Cancelled'],
                        counts: [
                            <c:set var="pending" value="0"/>
                            <c:set var="processing" value="0"/>
                            <c:set var="shipped" value="0"/>
                            <c:set var="delivered" value="0"/>
                            <c:set var="cancelled" value="0"/>
                            <c:forEach var="order" items="${orders}">
                                <c:choose>
                                    <c:when test="${order.orderStatus == 'Pending'}">
                                        <c:set var="pending" value="${pending + 1}"/>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'Processing'}">
                                        <c:set var="processing" value="${processing + 1}"/>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'Shipped'}">
                                        <c:set var="shipped" value="${shipped + 1}"/>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'Delivered'}">
                                        <c:set var="delivered" value="${delivered + 1}"/>
                                    </c:when>
                                    <c:when test="${order.orderStatus == 'Cancelled'}">
                                        <c:set var="cancelled" value="${cancelled + 1}"/>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                            ${pending},${processing},${shipped},${delivered},${cancelled}
                        ]
                    },
                    paymentStatus: {
                        labels: ['Pending', 'Completed', 'Failed'],
                        counts: [
                            <c:set var="pending" value="0"/>
                            <c:set var="completed" value="0"/>
                            <c:set var="failed" value="0"/>
                            <c:forEach var="payment" items="${payments}">
                                <c:choose>
                                    <c:when test="${payment.paymentStatus == 'Pending'}">
                                        <c:set var="pending" value="${pending + 1}"/>
                                    </c:when>
                                    <c:when test="${payment.paymentStatus == 'Completed'}">
                                        <c:set var="completed" value="${completed + 1}"/>
                                    </c:when>
                                    <c:when test="${payment.paymentStatus == 'Failed'}">
                                        <c:set var="failed" value="${failed + 1}"/>
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                            ${pending},${completed},${failed}
                        ]
                    }
                };
                console.log('Analytics Data:', window.analyticsData);
            </script>
				</c:when>
				<c:otherwise>
					<p class="message error">No data available for analytics
						charts.</p>
				</c:otherwise>
			</c:choose>
		</section>

		<c:if test="${not empty message}">
			<div class="alert-popup show">
				<div class="alert-content">
					<div class="alert-msg">${message}</div>
					<button
						onclick="this.parentElement.parentElement.style.display='none'">OK</button>
				</div>
			</div>
		</c:if>

		<script>
		document.getElementById('searchInput').addEventListener('input', function() {
	        const clearIcon = document.querySelector('.clear-icon');
	        clearIcon.style.display = this.value.trim() !== '' ? 'inline-block' : 'none';
	    });

	    function clearSearch() {
	        const input = document.getElementById('searchInput');
	        input.value = '';
	        document.querySelector('.clear-icon').style.display = 'none';
	        window.location.href = '${pageContext.request.contextPath}/dashboard';
	    }

	    document.addEventListener('DOMContentLoaded', function() {
	        const input = document.getElementById('searchInput');
	        const clearIcon = document.querySelector('.clear-icon');
	        clearIcon.style.display = input.value.trim() !== '' ? 'inline-block' : 'none';
	    });
</script>

		<script>
    document.getElementById('reportType').addEventListener('change', function() {
        var userIdGroup = document.getElementById('userIdGroup');
        var reportUserId = document.getElementById('reportUserId');
        if (this.value === 'Inventory') {
            userIdGroup.style.display = 'none';
            reportUserId.value = '0'; // Default to 0 for Inventory
        } else {
            userIdGroup.style.display = 'block';
            // Remove 'All Users' option for User Activity
            reportUserId.innerHTML = '<option value="" disabled selected>Select User</option>';
            <c:forEach var="user" items="${users}">
            reportUserId.innerHTML += '<option value="${user.userId}">${user.firstName} ${user.lastName}</option>';
            </c:forEach>
            if (this.value === 'Sales') {
                reportUserId.innerHTML = '<option value="0">All Users</option>' + reportUserId.innerHTML;
            }
        }
    });

    // Ensure user dropdown is shown/hidden on page load based on report type
    document.addEventListener('DOMContentLoaded', function() {
        var reportType = document.getElementById('reportType').value;
        var userIdGroup = document.getElementById('userIdGroup');
        var reportUserId = document.getElementById('reportUserId');
        if (reportType === 'Inventory') {
            userIdGroup.style.display = 'none';
            reportUserId.value = '0';
        } else {
            userIdGroup.style.display = 'block';
            reportUserId.innerHTML = '<option value="" disabled selected>Select User</option>';
            <c:forEach var="user" items="${users}">
                reportUserId.innerHTML += '<option value="${user.userId}">${user.firstName} ${user.lastName}</option>';
            </c:forEach>
            if (reportType === 'Sales') {
                reportUserId.innerHTML = '<option value="0">All Users</option>' + reportUserId.innerHTML;
            }
        }
    });
</script>

		<script>
		var activeSection = '${activeSection}';
	</script>


		<script src="${pageContext.request.contextPath}/js/dashboard.js"></script>
</body>
</html>