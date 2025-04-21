<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar">
	<div class="container">
		<div class="top-bar">
			<a href="${pageContext.request.contextPath}/home"
				style="text-decoration: none;">
				<section class="logo">
					<div class="top">SportsVerse</div>
					<div class="bottom" aria-hidden="true">SportsVerse</div>
				</section>
				<div class="tagline">One Platform, Infinite Sports</div>
			</a>

			<button class="hamburger" aria-label="Menu">
				<i class="fas fa-bars"></i>
			</button>

			<div class="search-bar">
				<input type="text" placeholder="Search entire store here...">
				<button>Search</button>
			</div>

			<div class="user-actions">
				<a href="#"><i class="fas fa-heart"></i><span>Wish List</span></a> <a
					href="#"><i class="fas fa-shopping-bag"></i><span>Your
						Cart</span></a>

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
									class="fas fa-clipboard-list"></i> Orders</a> <a
									href="${pageContext.request.contextPath}/settings"><i
									class="fas fa-cog"></i> Settings</a>
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
			<a href="${pageContext.request.contextPath}/home">Home</a> 
			<a href="${pageContext.request.contextPath}/products">Products</a> 
			<a href="${pageContext.request.contextPath}/trending">Trending</a> <a
				href="${pageContext.request.contextPath}/new-arrivals">New
				Arrivals</a> <a href="${pageContext.request.contextPath}/blog">Blog</a>
			<a href="${pageContext.request.contextPath}/about">About Us</a> <a
				href="${pageContext.request.contextPath}/services">Services</a> <a
				href="${pageContext.request.contextPath}/faq">FAQ</a>
		</div>
	</div>
</nav>
