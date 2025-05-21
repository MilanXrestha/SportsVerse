<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>SportsVerse - My Wishlist</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/wishlist.css" />
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

	<section class="wishlist-section">
		<h2 class="section-title">My Wishlist</h2>
		<div class="wishlist-container">
			<c:choose>
				<c:when test="${not empty wishlistProducts}">
					<div class="wishlist-products-container">
						<c:forEach var="product" items="${wishlistProducts}">
							<div class="wishlist-product-card"
								data-wishlist-id="${product.wishlistId}">
								<div class="product-image"
									style="background-image: url('<c:choose><c:when test="${product.mainImageUrl.startsWith('http://') || product.mainImageUrl.startsWith('https://')}">${product.mainImageUrl}</c:when><c:otherwise>${pageContext.request.contextPath}${product.mainImageUrl}</c:otherwise></c:choose>');"></div>
								<div class="product-info">
									<form action="${pageContext.request.contextPath}/wishlist"
										method="post" class="wishlist-form">
										<input type="hidden" name="action" value="remove" /> <input
											type="hidden" name="wishlistId" value="${product.wishlistId}" />
										<input type="hidden" name="redirect"
											value="${pageContext.request.contextPath}/wishlist" />
										<button type="submit"
											class="wishlist-btn remove-from-wishlist"
											title="Remove from Wishlist">
											<i class="fas fa-trash"></i>
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
									<button class="action-btn add-to-cart"
										data-product-id="${product.productId}" title="Add to Cart">
										<i class="fas fa-cart-plus"></i> Add to Cart
									</button>
									<a
										href="${pageContext.request.contextPath}/product?productId=${product.productId}"
										class="view-product-link">View Product</a>
								</div>
							</div>
						</c:forEach>
					</div>
				</c:when>
				<c:otherwise>
					<p class="empty-wishlist">Your wishlist is empty. Start adding
						products!</p>
				</c:otherwise>
			</c:choose>
		</div>
	</section>

	<%@ include file="footer.jsp"%>
	<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>