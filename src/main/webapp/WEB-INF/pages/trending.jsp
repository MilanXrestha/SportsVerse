<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>SportsVerse - Trending Products</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/trending.css" />
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

	<!-- Shop by Category Section -->
	<section class="category-section">
		<h2 class="section-title">Shop by Categories</h2>
		<div class="category-scroll-wrapper">
			<button class="category-btn prev-category-btn"
				aria-label="Previous Category">
				<i class="fas fa-chevron-left"></i>
			</button>
			<div class="category-scroll-container">
				<div class="category-cards">
					<c:forEach var="category" items="${categoryMap.keySet()}">
						<a
							href="${pageContext.request.contextPath}/explore?categoryId=${category.categoryId}"
							class="category-card"
							style="background-image: url('<c:choose><c:when test="${category.categoryImage.startsWith('http://') || category.categoryImage.startsWith('https://')}">${category.categoryImage}</c:when><c:otherwise>${pageContext.request.contextPath}${category.categoryImage}</c:otherwise></c:choose>');">
							<div class="category-name">${category.categoryName}</div>
						</a>
					</c:forEach>
					<c:if test="${empty categoryMap}">
						<p>No categories available. Please add categories in the admin
							dashboard.</p>
					</c:if>
				</div>
			</div>
			<button class="category-btn next-category-btn"
				aria-label="Next Category">
				<i class="fas fa-chevron-right"></i>
			</button>
		</div>
	</section>

	<!-- Trending Products Section -->
	<section class="trending-products-section">
		<h2 class="section-title">Trending Products</h2>
		<div class="trending-products-grid">
			<c:forEach var="product" items="${trendingProducts}">
				<a
					href="${pageContext.request.contextPath}/product?productId=${product.productId}"
					class="product-card">
					<div class="product-image"
						style="background-image: url('<c:choose><c:when test="${product.mainImageUrl.startsWith('http://') || product.mainImageUrl.startsWith('https://')}">${product.mainImageUrl}</c:when><c:otherwise>${pageContext.request.contextPath}${product.mainImageUrl}</c:otherwise></c:choose>');"></div>
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
								value="${pageContext.request.contextPath}/trending" />
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
							<fmt:formatNumber value="${product.productPrice}" type="number"
								minFractionDigits="2" maxFractionDigits="2" />
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
			<c:if test="${empty trendingProducts}">
				<p>No trending products available at the moment.</p>
			</c:if>
		</div>
	</section>

	<!-- Weekly Best Deal Section -->
	<section class="weekly-deal-section">
		<h2 class="section-title">WEEKLY BEST DEAL</h2>
		<div class="deal-timer">
			<div class="timer-box">
				<span class="timer-value" data-time="days">00</span> <span
					class="timer-label">Days</span>
			</div>
			<div class="timer-box">
				<span class="timer-value" data-time="hours">00</span> <span
					class="timer-label">Hours</span>
			</div>
			<div class="timer-box">
				<span class="timer-value" data-time="minutes">00</span> <span
					class="timer-label">Minutes</span>
			</div>
			<div class="timer-box">
				<span class="timer-value" data-time="seconds">00</span> <span
					class="timer-label">Seconds</span>
			</div>
		</div>
		<div class="deal-cards">
			<a class="deal-card"
				href="${pageContext.request.contextPath}/products?category=jerseys"
				style="--bg-img:url('${pageContext.request.contextPath}/resources/images/loginSlider/login_slider_three.jpg')">
				<div class="deal-overlay">
					<h3 class="deal-title">Official Club Jerseys 2025</h3>
					<p class="deal-offer">Get up to 25% off</p>
					<span class="deal-button">Shop Now</span>
				</div>
			</a> <a class="deal-card"
				href="${pageContext.request.contextPath}/products?category=boots"
				style="--bg-img:url('${pageContext.request.contextPath}/resources/images/others/boot_deal.jpg')">
				<div class="deal-overlay">
					<h3 class="deal-title">Limited-Time Boot Deals</h3>
					<p class="deal-offer">Get up to 20% off</p>
					<span class="deal-button">Shop Now</span>
				</div>
			</a>
		</div>
	</section>

	<%@ include file="footer.jsp"%>
	<script src="${pageContext.request.contextPath}/js/header.js"></script>
	<script src="${pageContext.request.contextPath}/js/trending.js"></script>
</body>
</html>