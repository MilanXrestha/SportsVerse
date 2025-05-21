<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>SportsVerse - ${product.productName}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/product.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link href="https://fonts.googleapis.com/css2?family=Orbitron&family=Audiowide&family=Rajdhani:wght@400;700&display=swap" rel="stylesheet">
</head>
<body class="light-mode">
    <%@ include file="header.jsp"%>

    <section class="product-section">
        <div class="product-container">
            <!-- Product Image with Zoom -->
            <div class="product-image-container">
                <div class="product-image"
                     style="background-image: url('<c:choose><c:when test="${product.mainImageUrl.startsWith('http://') || product.mainImageUrl.startsWith('https://')}">${product.mainImageUrl}</c:when><c:otherwise>${pageContext.request.contextPath}${product.mainImageUrl}</c:otherwise></c:choose>');"
                     data-zoom-image="${product.mainImageUrl.startsWith('http://') || product.mainImageUrl.startsWith('https://') ? product.mainImageUrl : pageContext.request.contextPath.concat(product.mainImageUrl)}">
                    <div class="zoom-lens"></div>
                </div>
                <div class="zoom-result"></div>
            </div>

            <!-- Product Details -->
            <div class="product-details">
                <div class="product-details-card">
                    <h1 class="product-name">${product.productName}</h1>
                    <p class="product-brand">Brand: ${brandName}</p>
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
                        <span class="rating-value">${product.productRating} / 5 (${reviewCount} reviews)</span>
                    </div>
                    <p class="product-price">
                        Rs. <fmt:formatNumber value="${product.productPrice}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                    </p>
                    <p class="product-description">${product.productDescription}</p>
                    <p class="product-material"><strong>Material:</strong> ${product.productMaterial}</p>
                    <p class="product-size"><strong>Size:</strong> ${product.productSize}</p>
                    <p class="product-gender"><strong>Gender:</strong> ${product.gender}</p>
                    <p class="product-stock">
                        <strong>Availability:</strong>
                        <c:choose>
                            <c:when test="${product.stockQuantity > 0}">
                                In Stock (${product.stockQuantity} available)
                            </c:when>
                            <c:otherwise>
                                Out of Stock
                            </c:otherwise>
                        </c:choose>
                    </p>

                    <!-- Wishlist and Cart Buttons -->
                    <div class="product-actions">
                        <form action="${pageContext.request.contextPath}/wishlist" method="post" class="wishlist-form">
                            <input type="hidden" name="productId" value="${product.productId}" />
                            <input type="hidden" name="action" value="${not empty wishlistStatus[product.productId] ? 'remove' : 'add'}" />
                            <input type="hidden" name="wishlistId" value="${wishlistStatus[product.productId]}" />
                            <input type="hidden" name="redirect" value="${pageContext.request.contextPath}/product?productId=${product.productId}" />
                            <button type="submit"
                                    class="wishlist-btn ${not empty wishlistStatus[product.productId] ? 'in-wishlist' : ''}"
                                    title="${not empty wishlistStatus[product.productId] ? 'Remove from Wishlist' : 'Add to Wishlist'}">
                                <i class="${not empty wishlistStatus[product.productId] ? 'fas' : 'far'} fa-heart"></i>
                            </button>
                        </form>
                        <form action="${pageContext.request.contextPath}/cart" method="post" class="cart-form">
                            <input type="hidden" name="productId" value="${product.productId}" />
                            <input type="hidden" name="action" value="add" />
                            <button type="submit" class="action-btn add-to-cart"
                                    title="Add to Cart"
                                    <c:if test="${product.stockQuantity <= 0}">disabled</c:if>>
                                <i class="fas fa-cart-plus"></i> Add to Cart
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <!-- Reviews Section -->
        <div class="reviews-section">
            <h2 class="section-title">Customer Reviews</h2>
            <c:choose>
                <c:when test="${not empty reviews}">
                    <div class="reviews-container">
                        <c:forEach var="review" items="${reviews}">
                            <div class="review-card">
                                <div class="review-header">
                                    <div class="review-avatar">
                                        <i class="fas fa-user-circle"></i>
                                    </div>
                                    <div class="review-header-info">
                                        <span class="review-user">User ${review.userId}</span>
                                        <span class="review-date">
                                            <fmt:formatDate value="${review.reviewDate}" pattern="MMM dd, yyyy" />
                                        </span>
                                        <c:if test="${review.verified}">
                                            <span class="verified-badge">Verified</span>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="review-rating">
                                    <c:set var="rating" value="${review.rating}" />
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
                                    <span class="rating-value">${review.rating} / 5</span>
                                </div>
                                <p class="review-comment">${review.reviewComment}</p>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <p class="no-reviews">No reviews yet. Be the first to review this product!</p>
                </c:otherwise>
            </c:choose>

            <!-- Review Submission Form -->
            <c:if test="${not empty sessionScope.userId}">
                <div class="review-form-container">
                    <h3 class="review-form-title">Write a Review</h3>
                    <form action="${pageContext.request.contextPath}/product/review" method="post" class="review-form">
                        <input type="hidden" name="productId" value="${product.productId}" />
                        <div class="rating-selector">
                            <label for="rating">Rating:</label>
                            <div class="star-rating">
                                <input type="radio" name="rating" id="star5" value="5" required />
                                <label for="star5"><i class="fas fa-star"></i></label>
                                <input type="radio" name="rating" id="star4" value="4" />
                                <label for="star4"><i class="fas fa-star"></i></label>
                                <input type="radio" name="rating" id="star3" value="3" />
                                <label for="star3"><i class="fas fa-star"></i></label>
                                <input type="radio" name="rating" id="star2" value="2" />
                                <label for="star2"><i class="fas fa-star"></i></label>
                                <input type="radio" name="rating" id="star1" value="1" />
                                <label for="star1"><i class="fas fa-star"></i></label>
                            </div>
                        </div>
                        <div class="review-comment-field">
                            <label for="reviewComment">Comment:</label>
                            <textarea name="reviewComment" id="reviewComment" rows="5" placeholder="Write your review here..." required></textarea>
                        </div>
                        <button type="submit" class="submit-review-btn">Submit Review</button>
                    </form>
                </div>
            </c:if>
            <c:if test="${empty sessionScope.userId}">
                <p class="login-to-review">
                    <a href="${pageContext.request.contextPath}/login">Log in</a> to write a review.
                </p>
            </c:if>
        </div>
    </section>

    <%@ include file="footer.jsp"%>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <script src="${pageContext.request.contextPath}/js/product.js"></script>
</body>
</html>