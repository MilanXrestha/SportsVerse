<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>SportsVerse - Checkout</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
    <link href="https://fonts.googleapis.com/css2?family=Orbitron&family=Audiowide&family=Rajdhani:wght@400;700&display=swap" rel="stylesheet">
    <style>
        .checkout-section {
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
        .checkout-container {
            display: flex;
            gap: 2rem;
            flex-wrap: wrap;
        }
        .checkout-details, .order-summary {
            flex: 1;
            min-width: 300px;
            background: #ffffff;
            border-radius: 15px;
            padding: 20px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
            animation: fadeIn 0.5s ease-in;
        }
        body.dark-mode .checkout-details, body.dark-mode .order-summary {
            background: #333333;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.3);
        }
        .form-group {
            margin-bottom: 1rem;
        }
        .form-group label {
            font-family: 'Rajdhani', sans-serif;
            font-size: 16px;
            color: #333333;
            display: block;
            margin-bottom: 5px;
        }
        body.dark-mode .form-group label {
            color: #f0f0f0;
        }
        .form-group input, .form-group select, .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #cccccc;
            border-radius: 8px;
            font-family: 'Rajdhani', sans-serif;
            font-size: 15px;
            color: #333333;
            background: #ffffff;
            transition: border-color 0.3s;
        }
        body.dark-mode .form-group input, body.dark-mode .form-group select, body.dark-mode .form-group textarea {
            background: #444444;
            color: #f0f0f0;
            border-color: #555555;
        }
        .form-group input:focus, .form-group select:focus, .form-group textarea:focus {
            border-color: #f57224;
            outline: none;
        }
        .order-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            font-family: 'Rajdhani', sans-serif;
            font-size: 15px;
            color: #333333;
        }
        body.dark-mode .order-item {
            color: #f0f0f0;
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
        .order-total, .order-subtotal, .order-tax {
            font-family: 'Rajdhani', sans-serif;
            font-size: 16px;
            color: #f57224;
            font-weight: 700;
            text-align: right;
            margin-top: 10px;
        }
        .error-message, .success-message {
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 20px;
            font-family: 'Rajdhani', sans-serif;
            font-size: 16px;
            text-align: center;
        }
        .error-message {
            background: #f8d7da;
            color: #721c24;
        }
        .success-message {
            background: #d4edda;
            color: #155724;
        }
        .checkout-btn {
            display: block;
            width: 100%;
            padding: 12px;
            background: #f57224;
            color: #ffffff;
            border: none;
            border-radius: 8px;
            font-family: 'Rajdhani', sans-serif;
            font-size: 18px;
            text-align: center;
            cursor: pointer;
            transition: background 0.3s;
            margin-top: 1rem;
        }
        .checkout-btn:hover {
            background: #e55e15;
        }
        .action-btn {
            display: inline-block;
            color: #f57224;
            text-decoration: none;
            font-family: 'Rajdhani', sans-serif;
            font-size: 16px;
            margin: 10px 10px 0 0;
            transition: color 0.3s;
        }
        .action-btn:hover {
            color: #e55e15;
        }
        .address-form {
            margin-top: 20px;
        }
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(20px); }
            to { opacity: 1; transform: translateY(0); }
        }
        @media (max-width: 992px) {
            .checkout-container {
                flex-direction: column;
            }
        }
    </style>
</head>
<body class="light-mode">
    <%@ include file="header.jsp"%>

    <section class="checkout-section">
        <h2 class="section-title">Checkout</h2>
        <c:if test="${not empty sessionScope.checkoutMessage}">
            <div class="${sessionScope.checkoutMessage.contains('successfully') ? 'success-message' : 'error-message'}">
                ${sessionScope.checkoutMessage}
                <c:remove var="checkoutMessage" scope="session"/>
            </div>
        </c:if>
        <div class="checkout-container">
            <div class="checkout-details">
                <h3 class="section-title">Shipping Address</h3>
                <c:choose>
                    <c:when test="${hasShippingAddress}">
                        <form action="${pageContext.request.contextPath}/checkout" method="post">
                            <div class="form-group">
                                <label for="addressId">Select Shipping Address</label>
                                <select id="addressId" name="addressId" required>
                                    <option value="">Select an address</option>
                                    <c:forEach var="address" items="${addresses}">
                                        <option value="${address.addressId}">
                                            ${address.streetAddress}, ${address.city}, ${address.nepalState}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <label for="paymentMethodId">Payment Method</label>
                                <select id="paymentMethodId" name="paymentMethodId" required>
                                    <c:choose>
                                        <c:when test="${not empty paymentMethods}">
                                            <c:forEach var="method" items="${paymentMethods}">
                                                <option value="${method.paymentMethodId}">${method.methodName}</option>
                                            </c:forEach>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="">No payment methods available</option>
                                        </c:otherwise>
                                    </c:choose>
                                </select>
                            </div>
                            <button type="submit" class="checkout-btn">Place Order</button>
                        </form>
                        <a href="#updateAddressForm" class="action-btn" onclick="toggleForm('updateAddressForm')">Update Shipping Address</a>
                        <a href="#addAddressForm" class="action-btn" onclick="toggleForm('addAddressForm')">Add New Shipping Address</a>
                        <div id="updateAddressForm" class="address-form" style="display: none;">
                            <h3 class="section-title">Update Shipping Address</h3>
                            <form action="${pageContext.request.contextPath}/checkout" method="post">
                                <input type="hidden" name="action" value="updateAddress" />
                                <div class="form-group">
                                    <label for="updateAddressId">Select Address to Update</label>
                                    <select id="updateAddressId" name="addressId" required>
                                        <option value="">Select an address</option>
                                        <c:forEach var="address" items="${addresses}">
                                            <option value="${address.addressId}">
                                                ${address.streetAddress}, ${address.city}, ${address.nepalState}
                                            </option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="updateStreetAddress">Street Address</label>
                                    <input type="text" id="updateStreetAddress" name="streetAddress" required />
                                </div>
                                <div class="form-group">
                                    <label for="updateCity">City</label>
                                    <input type="text" id="updateCity" name="city" required />
                                </div>
                                <div class="form-group">
                                    <label for="updateNepalState">State</label>
                                    <select id="updateNepalState" name="nepalState" required>
                                        <option value="Bagmati">Bagmati</option>
                                        <option value="Gandaki">Gandaki</option>
                                        <option value="Lumbini">Lumbini</option>
                                        <option value="Karnali">Karnali</option>
                                        <option value="Sudurpashchim">Sudurpashchim</option>
                                        <option value="Province No. 1">Province No. 1</option>
                                        <option value="Province No. 2">Province No. 2</option>
                                    </select>
                                </div>
                                <button type="submit" class="checkout-btn">Update Address</button>
                            </form>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p>Please add a shipping address to proceed.</p>
                        <a href="#addAddressForm" class="action-btn" onclick="toggleForm('addAddressForm')">Add Shipping Address</a>
                    </c:otherwise>
                </c:choose>
                <div id="addAddressForm" class="address-form" style="display: none;">
                    <h3 class="section-title">Add Shipping Address</h3>
                    <form action="${pageContext.request.contextPath}/checkout" method="post">
                        <input type="hidden" name="action" value="addAddress" />
                        <div class="form-group">
                            <label for="streetAddress">Street Address</label>
                            <input type="text" id="streetAddress" name="streetAddress" required />
                        </div>
                        <div class="form-group">
                            <label for="city">City</label>
                            <input type="text" id="city" name="city" required />
                        </div>
                        <div class="form-group">
                            <label for="nepalState">State</label>
                            <select id="nepalState" name="nepalState" required>
                                <option value="Bagmati">Bagmati</option>
                                <option value="Gandaki">Gandaki</option>
                                <option value="Lumbini">Lumbini</option>
                                <option value="Karnali">Karnali</option>
                                <option value="Sudurpashchim">Sudurpashchim</option>
                                <option value="Province No. 1">Province No. 1</option>
                                <option value="Province No. 2">Province No. 2</option>
                            </select>
                        </div>
                        <button type="submit" class="checkout-btn">Save Address</button>
                    </form>
                </div>
            </div>
            <div class="order-summary">
                <h3 class="section-title">Order Summary</h3>
                <c:forEach var="item" items="${cartItems}" varStatus="status">
                    <div class="order-item">
                        <span class="order-item-name">${cartProducts[status.index].productName}</span>
                        <span class="order-item-details">
                            ${item.cartQuantity} x Rs. <fmt:formatNumber value="${cartProducts[status.index].productPrice}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                        </span>
                    </div>
                </c:forEach>
                <div class="order-subtotal">
                    Subtotal: Rs. <fmt:formatNumber value="${subtotal}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                </div>
                <div class="order-tax">
                    Tax (10%): Rs. <fmt:formatNumber value="${tax}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                </div>
                <div class="order-total">
                    Total: Rs. <fmt:formatNumber value="${total}" type="number" minFractionDigits="2" maxFractionDigits="2" />
                </div>
            </div>
        </div>
    </section>

    <%@ include file="footer.jsp"%>
    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <script>
        function toggleForm(formId) {
            const forms = ['addAddressForm', 'updateAddressForm'];
            forms.forEach(id => {
                const form = document.getElementById(id);
                form.style.display = id === formId ? 'block' : 'none';
            });
        }
    </script>
</body>
</html>