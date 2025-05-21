<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>SportsVerse - Your Orders</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Orbitron&family=Audiowide&family=Rajdhani:wght@400;700&display=swap"
	rel="stylesheet">
<style>
.orders-section {
	padding: 2rem 20px;
	max-width: 1400px;
	margin: 0 auto;
	margin-top: 135px;
}

.section-title {
	font-family: 'Rajdhani', sans-serif;
	font-size: 32px;
	margin-bottom: 20px;
	border-bottom: 3px solid #f57224;
	display: inline-block;
	padding-bottom: 8px;
	color: #333333;
	text-transform: uppercase;
}

body.dark-mode .section-title {
	color: #ffffff;
}

.order-card {
	background: #ffffff;
	border-radius: 15px;
	padding: 20px;
	margin-bottom: 20px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
	transition: transform 0.3s, box-shadow 0.3s;
	animation: fadeIn 0.5s ease-in;
}

.order-card:hover {
	transform: translateY(-5px);
	box-shadow: 0 12px 25px rgba(0, 0, 0, 0.2);
}

body.dark-mode .order-card {
	background: #333333;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
}

.order-header {
	display: flex;
	justify-content: space-between;
	margin-bottom: 15px;
	font-family: 'Rajdhani', sans-serif;
	font-size: 18px;
	color: #333333;
}

body.dark-mode .order-header {
	color: #f0f0f0;
}

.order-header span {
	font-weight: 700;
}

.order-item {
	display: flex;
	justify-content: space-between;
	margin-bottom: 10px;
	font-family: 'Rajdhani', sans-serif;
	font-size: 15px;
	color: #333333;
}

.order-item-name {
	flex: 2;
	display: -webkit-box;
	-webkit-line-clamp: 2;
	-webkit-box-orient: vertical;
	overflow: hidden;
}

.order-item-details {
	flex: 1;
	text-align: right;
}

body.dark-mode .order-item {
	color: #f0f0f0;
}

.order-total {
	font-family: 'Rajdhani', sans-serif;
	font-size: 16px;
	color: #f57224;
	font-weight: 700;
	text-align: right;
	margin-top: 10px;
}

.empty-orders {
	text-align: center;
	padding: 3rem;
	background: #f9f9f9;
	border-radius: 15px;
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
}

body.dark-mode .empty-orders {
	background: #2e2e2e;
	color: #f0f0f0;
}

.empty-orders i {
	font-size: 48px;
	color: #f57224;
	margin-bottom: 20px;
}

.continue-shopping {
	display: inline-block;
	margin-top: 1rem;
	color: #f57224;
	text-decoration: none;
	font-family: 'Rajdhani', sans-serif;
	font-size: 18px;
	transition: color 0.3s;
}

.continue-shopping:hover {
	color: #e55e15;
}

.order-message {
	background: #d4edda;
	color: #155724;
	padding: 10px;
	border-radius: 8px;
	margin-bottom: 20px;
	font-family: 'Rajdhani', sans-serif;
	font-size: 16px;
	text-align: center;
}

@
keyframes fadeIn {from { opacity:0;
	transform: translateY(20px);
}

to {
	opacity: 1;
	transform: translateY(0);
}

}
@media ( max-width : 768px) {
	.order-header {
		flex-direction: column;
		gap: 10px;
	}
	.order-item {
		flex-direction: column;
		gap: 5px;
	}
	.order-item-details {
		text-align: left;
	}
}
</style>
</head>
<body class="light-mode">
	<%@ include file="header.jsp"%>

	<section class="orders-section">
		<h2 class="section-title">Your Orders</h2>
		<c:if test="${not empty sessionScope.orderMessage}">
			<div class="order-message">
				${sessionScope.orderMessage}
				<c:remove var="orderMessage" scope="session" />
			</div>
		</c:if>
		<c:choose>
			<c:when test="${not empty orders}">
				<c:forEach var="order" items="${orders}">
					<div class="order-card">
						<div class="order-header">
							<span>Order ID: ${order.orderId}</span> <span>Date: <fmt:formatDate
									value="${order.orderDate}" pattern="dd MMM yyyy HH:mm" /></span> <span>Status:
								${order.orderStatus}</span>
						</div>
						<c:forEach var="product" items="${order.orderProducts}">
							<div class="order-item">
								<span class="order-item-name">${order.productNames[product.productId]}</span>
								<span class="order-item-details">
									${product.quantityOrdered} x Rs. <fmt:formatNumber
										value="${product.unitPrice}" type="number"
										minFractionDigits="2" maxFractionDigits="2" />
								</span>
							</div>
						</c:forEach>
						<div class="order-total">
							Total: Rs.
							<fmt:formatNumber value="${order.totalAmount}" type="number"
								minFractionDigits="2" maxFractionDigits="2" />
						</div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<div class="empty-orders">
					<i class="fas fa-clipboard-list"></i>
					<p>You have no orders.</p>
					<a href="${pageContext.request.contextPath}/explore"
						class="continue-shopping">Continue Shopping</a>
				</div>
			</c:otherwise>
		</c:choose>
	</section>

	<%@ include file="footer.jsp"%>
	<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>