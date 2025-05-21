<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>SportsVerse - <c:out
		value="${not empty searchQuery ? 'Search Results for '.concat(searchQuery) : not empty subcategoryName ? subcategoryName.concat(' in ').concat(categoryName) : not empty categoryName ? 'Products in '.concat(categoryName) : 'Explore All Products'}" /></title>
<!-- Stylesheets -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/explore.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Orbitron&family=Audiowide&family=Rajdhani:wght@400;700&display=swap"
	rel="stylesheet">
</head>
<body class="light-mode">
	<%@ include file="header.jsp"%>

	<!-- All Products Section -->
	<section class="all-products-section">
		<h2 class="section-title">
			<c:choose>
				<c:when test="${not empty searchQuery}">
                    Search Results for "<c:out value="${searchQuery}" />"
                </c:when>
				<c:when test="${not empty subcategoryName}">
					<c:out value="${subcategoryName} in ${categoryName}" />
				</c:when>
				<c:when test="${not empty categoryName}">
                    Products in <c:out value="${categoryName}" />
				</c:when>
				<c:otherwise>
                    Explore All Products
                </c:otherwise>
			</c:choose>
		</h2>
		<c:if test="${not empty errorMessage}">
			<p class="error-message">
				<c:out value="${errorMessage}" />
			</p>
		</c:if>
		<c:forEach var="entry" items="${categoryProductMap}">
			<c:if test="${not empty entry.value}">
				<div class="category-section">
					<h3 class="category-title">${entry.key.categoryName}</h3>
					<div class="products-grid-container">
						<c:forEach var="product" items="${entry.value}">
							<a
								href="${pageContext.request.contextPath}/product?productId=${product.productId}"
								class="product-card">
								<div class="product-image"
									style="background-image: url('<c:choose><c:when test="${product.mainImageUrl.startsWith('http://') || product.mainImageUrl.startsWith('https://')}">${product.mainImageUrl}</c:when><c:otherwise>${pageContext.request.contextPath}${product.mainImageUrl}</c:otherwise></c:choose>');">
								</div>
								<div class="product-info">
									<form action="${pageContext.request.contextPath}/wishlist"
										method="post" class="wishlist-form">
										<input type="hidden" name="productId"
											value="${product.productId}" /> <input type="hidden"
											name="action"
											value="${not empty wishlistStatus[product.productId] ? 'remove' : 'add'}" />
										<input type="hidden" name="wishlistId"
											value="${wishlistStatus[product.productId]}" /> <input
											type="hidden" name="redirect"
											value="${pageContext.request.contextPath}/explore${not empty param.categoryId ? '?categoryId='.concat(param.categoryId) : ''}${not empty param.subcategoryId ? '&subcategoryId='.concat(param.subcategoryId) : ''}${not empty searchQuery ? '?query='.concat(searchQuery) : ''}" />
										<button type="submit"
											class="wishlist-btn ${not empty wishlistStatus[product.productId] ? 'in-wishlist' : ''}"
											title="${not empty wishlistStatus[product.productId] ? 'Remove from Wishlist' : 'Add to Wishlist'}">
											<i
												class="${not empty wishlistStatus[product.productId] ? 'fas' : 'far'} fa-heart"></i>
										</button>
									</form>
									<h3 class="product-name">${product.productName}</h3>
									<div class="product-rating">
										<c:set var="rating" value="${product.productRating}" />
										<c:forEach begin="1" end="5" var="i">
											<c:choose>
												<c:when test="${rating >= i}">
													<i class="fas fa-star"></i>
												</c:when>
												<c:when test="${rating >= i - 0.5}">
													<i class="fas fa-star-half-alt"></i>
												</c:when>
												<c:otherwise>
													<i class="far fa-star"></i>
												</c:otherwise>
											</c:choose>
										</c:forEach>
										<span class="rating-value">${product.productRating} / 5</span>
									</div>
									<p class="price">
										Rs.
										<fmt:formatNumber value="${product.productPrice}"
											type="number" minFractionDigits="2" maxFractionDigits="2" />
									</p>
									<form action="${pageContext.request.contextPath}/cart"
										method="post">
										<input type="hidden" name="productId"
											value="${product.productId}" /> <input type="hidden"
											name="action" value="add" />
										<button type="submit" class="action-btn add-to-cart"
											title="Add to Cart">
											<i class="fas fa-cart-plus"></i> Add to Cart
										</button>
									</form>
								</div>
							</a>
						</c:forEach>
					</div>
				</div>
			</c:if>
		</c:forEach>
		<c:if test="${empty categoryProductMap}">
			<p>
				No products found
				<c:if test="${not empty searchQuery}"> for "<c:out
						value="${searchQuery}" />"</c:if>
				<c:if test="${not empty subcategoryName}"> in "<c:out
						value="${subcategoryName} in ${categoryName}" />"</c:if>
				<c:if test="${not empty categoryName && empty subcategoryName}"> in "<c:out
						value="${categoryName}" />"</c:if>
				.
			</p>
		</c:if>
	</section>

	<%@ include file="footer.jsp"%>

	<script src="${pageContext.request.contextPath}/js/header.js"></script>
	<script src="${pageContext.request.contextPath}/js/explore.js"></script>
</body>
</html>