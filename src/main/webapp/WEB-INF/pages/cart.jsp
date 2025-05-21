<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>SportsVerse - Your Cart</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link href="https://fonts.googleapis.com/css2?family=Orbitron&family=Audiowide&family=Rajdhani:wght@400;700&display=swap" rel="stylesheet">
    <style>
        .cart-section {
            padding: 2rem 20px;
            max-width: 1400px;
            margin: 0 auto;
            margin-top: 135px;
            display: flex;
            gap: 2rem;
            flex-wrap: wrap;
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
        .cart-items {
            flex: 2;
            min-width: 300px;
        }
        .cart-summary {
            flex: 1;
            min-width: 300px;
            background: #ffffff;
            border-radius: 15px;
            padding: 20px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            position: sticky;
            top: 20px;
        }
        body.dark-mode .cart-summary {
            background: #2e2e2e;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
        }
        .cart-card {
            background: #ffffff;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s, box-shadow 0.3s;
            display: flex;
            height: 180px;
            margin-bottom: 20px;
            animation: fadeIn 0.5s ease-in;
        }
        .cart-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 12px 25px rgba(0, 0, 0, 0.2);
        }
        body.dark-mode .cart-card {
            background: #333333;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
        }
        .cart-image {
            width: 120px;
            height: 100%;
            background-size: cover;
            background-position: center;
            border-radius: 15px 0 0 15px;
        }
        .cart-info {
            padding: 15px;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            background: linear-gradient(to bottom, #ffffff, #f0f0f0);
        }
        body.dark-mode .cart-info {
            background: linear-gradient(to bottom, #2e2e2e, #3a3a3a);
        }
        .cart-name {
            font-family: 'Rajdhani', sans-serif;
            font-size: 18px;
            font-weight: 700;
            color: #333333;
            margin-bottom: 8px;
            line-height: 1.4;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }
        body.dark-mode .cart-name {
            color: #f0f0f0;
        }
        .cart-card:hover .cart-name {
            color: #f57224;
        }
        .cart-price {
            font-family: 'Rajdhani', sans-serif;
            font-size: 16px;
            color: #f57224;
            font-weight: 700;
            margin-bottom: 8px;
        }
        .quantity-picker {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .quantity-btn {
            background: #f57224;
            color: #ffffff;
            border: none;
            border-radius: 50%;
            width: 30px;
            height: 30px;
            cursor: pointer;
            transition: background 0.3s, transform 0.2s;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 16px;
        }
        .quantity-btn:hover {
            background: #e55e15;
            transform: scale(1.1);
        }
        .quantity-btn:disabled {
            background: #ccc;
            cursor: not-allowed;
        }
        body.dark-mode .quantity-btn {
            background: #f57224;
        }
        body.dark-mode .quantity-btn:hover {
            background: #e55e15;
        }
        .quantity-input {
            width: 50px;
            padding: 6px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-family: 'Rajdhani', sans-serif;
            font-size: 16px;
            text-align: center;
            background: #fff;
        }
        body.dark-mode .quantity-input {
            background: #444;
            color: #f0f0f0;
            border-color: #666;
        }
        .cart-total {
            font-family: 'Rajdhani', sans-serif;
            font-size: 16px;
            color: #333333;
            font-weight: 700;
        }
        body.dark-mode .cart-total {
            color: #f0f0f0;
        }
        .remove-btn {
            background: #e74c3c;
            color: #ffffff;
            border: none;
            border-radius: 50%;
            width: 30px;
            height: 30px;
            cursor: pointer;
            transition: background 0.3s, transform 0.2s;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        .remove-btn:hover {
            background: #c0392b;
            transform: scale(1.1);
        }
        body.dark-mode .remove-btn {
            background: #e74c3c;
        }
        body.dark-mode .remove-btn:hover {
            background: #c0392b;
        }
        .summary-title {
            font-family: 'Rajdhani', sans-serif;
            font-size: 24px;
            color: #333333;
            margin-bottom: 20px;
            border-bottom: 2px solid #f57224;
            padding-bottom: 8px;
        }
        body.dark-mode .summary-title {
            color: #f0f0f0;
        }
        .summary-items {
            margin-bottom: 20px;
        }
        .summary-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            font-family: 'Rajdhani', sans-serif;
            font-size: 15px;
            color: #333333;
        }
        .summary-item-name {
            flex: 2;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
        }
        .summary-item-details {
            flex: 1;
            text-align: right;
        }
        body.dark-mode .summary-item {
            color: #f0f0f0;
        }
        .summary-total {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            font-family: 'Rajdhani', sans-serif;
            font-size: 16px;
            color: #333333;
            font-weight: 700;
        }
        body.dark-mode .summary-total {
            color: #f0f0f0;
        }
        .grand-total {
            font-family: 'Rajdhani', sans-serif;
            font-size: 20px;
            color: #f57224;
            font-weight: 700;
            margin-top: 20px;
            text-align: right;
        }
        .checkout-btn, .clear-cart-btn {
            background: #f57224;
            color: #ffffff;
            border: none;
            border-radius: 8px;
            padding: 12px 24px;
            cursor: pointer;
            transition: background 0.3s, transform 0.2s;
            font-family: 'Rajdhani', sans-serif;
            font-size: 18px;
            width: 100%;
            margin-top: 10px;
        }
        .checkout-btn:hover, .clear-cart-btn:hover {
            background: #e55e15;
            transform: scale(1.05);
        }
        .empty-cart {
            text-align: center;
            padding: 3rem;
            background: #f9f9f9;
            border-radius: 15px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
        }
        body.dark-mode .empty-cart {
            background: #2e2e2e;
            color: #f0f0f0;
        }
        .empty-cart i {
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
        .cart-message {
            background: #f8d7da;
            color: #721c24;
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-family: 'Rajdhani', sans-serif;
            font-size: 16px;
            text-align: center;
        }
        .cart-message.success {
            background: #d4edda;
            color: #155724;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
        @media (max-width: 992px) {
            .cart-section {
                flex-direction: column;
            }
            .cart-summary {
                position: static;
            }
        }
        @media (max-width: 768px) {
            .cart-card {
                flex-direction: column;
                height: auto;
            }
            .cart-image {
                width: 100%;
                height: 150px;
                border-radius: 15px 15px 0 0;
            }
            .cart-info {
                padding: 10px;
            }
        }
    </style>
</head>
<body class="light-mode">
    <%@ include file="header.jsp"%>

    <section class="cart-section">
        <div class="cart-items">
            <h2 class="section-title">Your Cart</h2>
            <c:if test="${not empty sessionScope.cartMessage}">
                <div class="cart-message ${sessionScope.cartMessage.contains('successfully') ? 'success' : ''}">
                    ${sessionScope.cartMessage}
                    <c:remove var="cartMessage" scope="session"/>
                </div>
            </c:if>
            <c:choose>
                <c:when test="${not empty cartItems}">
                    <c:set var="subtotal" value="0" />
                    <c:forEach var="cartItem" items="${cartItems}">
                        <c:forEach var="product" items="${cartProducts}">
                            <c:if test="${product.productId == cartItem.productId}">
                                <div class="cart-card">
                                    <div class="cart-image"
                                         style="background-image: url('<c:choose><c:when test="${product.mainImageUrl.startsWith('http://') || product.mainImageUrl.startsWith('https://')}">${product.mainImageUrl}</c:when><c:otherwise>${pageContext.request.contextPath}${product.mainImageUrl}</c:otherwise></c:choose>');"></div>
                                    <div class="cart-info">
                                        <h3 class="cart-name">${product.productName}</h3>
                                        <p class="cart-price">
                                            Rs. <fmt:formatNumber value="${product.productPrice}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                                        </p>
                                        <div class="cart-actions">
                                            <div class="quantity-picker">
                                                <form action="${pageContext.request.contextPath}/cart" method="post" style="display: inline;">
                                                    <input type="hidden" name="action" value="update" />
                                                    <input type="hidden" name="cartId" value="${cartItem.cartId}" />
                                                    <input type="hidden" name="quantity" value="${cartItem.cartQuantity - 1}" />
                                                    <button type="submit" class="quantity-btn minus" ${cartItem.cartQuantity <= 1 ? 'disabled' : ''}>-</button>
                                                </form>
                                                <input type="number" class="quantity-input" value="${cartItem.cartQuantity}" min="1" max="${product.stockQuantity}" readonly />
                                                <form action="${pageContext.request.contextPath}/cart" method="post" style="display: inline;">
                                                    <input type="hidden" name="action" value="update" />
                                                    <input type="hidden" name="cartId" value="${cartItem.cartId}" />
                                                    <input type="hidden" name="quantity" value="${cartItem.cartQuantity + 1}" />
                                                    <button type="submit" class="quantity-btn plus" ${cartItem.cartQuantity >= product.stockQuantity ? 'disabled' : ''}>+</button>
                                                </form>
                                            </div>
                                            <p class="cart-total">
                                                Total: Rs. <span class="item-total"><fmt:formatNumber value="${product.productPrice * cartItem.cartQuantity}" type="number" minFractionDigits="2" maxFractionDigits="2" /></span>
                                            </p>
                                            <form action="${pageContext.request.contextPath}/cart" method="post">
                                                <input type="hidden" name="cartId" value="${cartItem.cartId}" />
                                                <input type="hidden" name="action" value="remove" />
                                                <button type="submit" class="remove-btn" title="Remove from Cart">
                                                    <i class="fas fa-trash"></i>
                                                </button>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                                <c:set var="subtotal" value="${subtotal + (product.productPrice * cartItem.cartQuantity)}" />
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-cart">
                        <i class="fas fa-shopping-cart"></i>
                        <p>Your cart is empty.</p>
                        <a href="${pageContext.request.contextPath}/explore" class="continue-shopping">Continue Shopping</a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <c:if test="${not empty cartItems}">
            <div class="cart-summary">
                <h3 class="summary-title">Order Summary</h3>
                <div class="summary-items">
                    <c:forEach var="cartItem" items="${cartItems}">
                        <c:forEach var="product" items="${cartProducts}">
                            <c:if test="${product.productId == cartItem.productId}">
                                <div class="summary-item">
                                    <span class="summary-item-name">${product.productName}</span>
                                    <span class="summary-item-details">
                                        ${cartItem.cartQuantity} x Rs. <fmt:formatNumber value="${product.productPrice}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                                    </span>
                                </div>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </div>
                <div class="summary-total">
                    <span>Subtotal</span>
                    <span>Rs. <fmt:formatNumber value="${subtotal}" type="number" minFractionDigits="2" maxFractionDigits="2" /></span>
                </div>
                <div class="summary-total">
                    <span>Tax (10% VAT)</span>
                    <c:set var="tax" value="${subtotal * 0.10}" />
                    <span>Rs. <fmt:formatNumber value="${tax}" type="number" minFractionDigits="2" maxFractionDigits="2" /></span>
                </div>
                <div class="summary-total">
                    <span>Discount</span>
                    <c:set var="discount" value="0" />
                    <span>Rs. <fmt:formatNumber value="${discount}" type="number" minFractionDigits="2" maxFractionDigits="2" /></span>
                </div>
                <div class="grand-total">
                    Total: Rs. <fmt:formatNumber value="${subtotal + tax - discount}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                </div>
                <form action="${pageContext.request.contextPath}/checkout" method="get">
                    <button type="submit" class="checkout-btn">Proceed to Checkout</button>
                </form>
                <form action="${pageContext.request.contextPath}/cart" method="post">
                    <input type="hidden" name="action" value="clear" />
                    <button type="submit" class="clear-cart-btn">Clear Cart</button>
                </form>
            </div>
        </c:if>
    </section>

    <%@ include file="footer.jsp"%>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>