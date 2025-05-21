<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<jsp:useBean id="cartService"
	class="com.sportsverse.service.CartService" scope="request" />
<c:set var="cartCount"
	value="${sessionScope.userId != null ? cartService.getCartItemCount(sessionScope.userId) : 0}" />

<nav class="navbar">
	<div class="container">
		<div class="top-bar">
			<!-- Logo + Tagline -->
			<a href="${pageContext.request.contextPath}/dashboard"
				class="logo-link">
				<div class="logo-container">
					<div class="logo" id="logo">
						<span>S</span><span>p</span><span><i
							class="fas fa-basketball football"></i></span> <span>r</span><span>t</span><span>s</span><span>V</span><span>E</span><span>R</span><span>S</span><span>E</span>
					</div>
					<div class="tagline">One Platform, Infinite Sports</div>
				</div>
			</a>

			<!-- Hamburger Menu with Dropdown -->
			<div class="hamburger-menu">
				<button class="hamburger" aria-label="Menu">
					<i class="fas fa-bars"></i>
				</button>
				<div class="hamburger-dropdown">
					<ul>
						<c:forEach var="entry" items="${categoryMap}">
							<li class="category-item"><a
								href="${pageContext.request.contextPath}/explore?categoryId=${entry.key.categoryId}">
									<c:out value="${entry.key.categoryName}" /> <c:if
										test="${not empty entry.value}">
										<i class="fas fa-chevron-right category-icon"></i>
									</c:if>
							</a> <c:if test="${not empty entry.value}">
									<ul class="subcategory-list">
										<c:forEach var="subcategory" items="${entry.value}">
											<li><a
												href="${pageContext.request.contextPath}/explore?categoryId=${entry.key.categoryId}&subcategoryId=${subcategory.subcategoryId}">
													<c:out value="${subcategory.subcategoryName}" />
											</a></li>
										</c:forEach>
									</ul>
								</c:if></li>
						</c:forEach>
						<c:if test="${empty categoryMap}">
							<li><a href="#">No Categories Available</a></li>
						</c:if>
					</ul>
				</div>
			</div>

			<!-- Search Bar -->
			<div class="search-bar">
				<form action="${pageContext.request.contextPath}/search"
					method="get" class="search-form">
					<div class="input-wrapper">
						<input type="text" name="query" id="search-input"
							placeholder="Search entire store here..." required>
						<button type="button" id="clear-btn" class="clear-btn"
							aria-label="Clear Search">
							<i class="fas fa-times"></i>
						</button>
					</div>
					<button type="submit">Search</button>
				</form>
			</div>

			<div class="user-actions">
				<a href="${pageContext.request.contextPath}/wishlist"> <i
					class="fas fa-heart"></i><span>Wish List</span>
				</a> <a href="${pageContext.request.contextPath}/cart"> <i
					class="fas fa-shopping-bag"></i><span>Your Cart</span> <c:if
						test="${cartCount > 0}">
						<span class="cart-count">${cartCount}</span>
					</c:if>
				</a>

				<!-- Dynamic User Account Dropdown -->
				<div class="account-dropdown">
					<c:choose>
						<c:when test="${not empty sessionScope.username}">
							<button class="account-btn">
								<i class="fas fa-user-circle"></i> <span>${sessionScope.username}</span>
								<i class="fas fa-caret-down"></i>
							</button>
							<div class="dropdown-content">
								<a href="${pageContext.request.contextPath}/profile"><i
									class="fas fa-user"></i> Profile</a>
								<c:if test="${cookie.role.value == 'admin'}">
									<a href="${pageContext.request.contextPath}/admin/dashboard"><i
										class="fas fa-tachometer-alt"></i> Dashboard</a>
								</c:if>
								<a href="${pageContext.request.contextPath}/orders"><i
									class="fas fa-clipboard-list"></i> Orders</a>
								<form action="${pageContext.request.contextPath}/logout"
									method="post">
									<button type="submit" class="dropdown-item logout-btn">
										<i class="fas fa-sign-out-alt"></i> Logout
									</button>
								</form>
							</div>
						</c:when>
						<c:otherwise>
							<button class="account-btn">
								<i class="fas fa-user"></i> <span>My Account</span> <i
									class="fas fa-caret-down"></i>
							</button>
							<div class="dropdown-content">
								<a href="${pageContext.request.contextPath}/login"><i
									class="fas fa-sign-in-alt"></i> Login</a> <a
									href="${pageContext.request.contextPath}/register"><i
									class="fas fa-user-plus"></i> Register</a>
							</div>
						</c:otherwise>
					</c:choose>
				</div>
			</div>

			<button class="theme-toggle" aria-label="Toggle Theme">
				<i class="fas fa-moon"></i>
			</button>
		</div>

		<div class="main-nav">
			<a href="${pageContext.request.contextPath}/home"
				class="${activePage == 'home' ? 'active' : ''}">Home</a> <a
				href="${pageContext.request.contextPath}/explore"
				class="${activePage == 'explore' ? 'active' : ''}">Products</a> <a
				href="${pageContext.request.contextPath}/trending"
				class="${activePage == 'trending' ? 'active' : ''}">Trending</a> <a
				href="${pageContext.request.contextPath}/newarrivals"
				class="${activePage == 'new-arrivals' ? 'active' : ''}">New
				Arrivals</a> <a href="${pageContext.request.contextPath}/contact"
				class="${activePage == 'about' ? 'active' : ''}">Contact Us</a> <a
				href="${pageContext.request.contextPath}/services"
				class="${activePage == 'services' ? 'active' : ''}">Services</a> <a
				href="${pageContext.request.contextPath}/faq"
				class="${activePage == 'faq' ? 'active' : ''}">FAQ</a>
		</div>
	</div>
</nav>