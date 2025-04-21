<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>SportsVerse - Online Sports Store</title>
<!-- Stylesheets -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/home.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css" />

<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>

<body class="light-mode">
	<%@ include file="header.jsp"%>

	<header class="hero-header"
		style="background-image: linear-gradient(135deg, rgba(0,0,0,0.7) 0%, rgba(0,0,0,0.3) 100%), 
               url('${pageContext.request.contextPath}/resources/images/homeSlider/img4.jpg');
               background-size: cover;
               background-position: center;
               margin-top: 80px; 
               height: 90vh; 
               min-height: 500px;
               width: 100%;
               position: relative; 
               display: flex; 
               justify-content: flex-start; 
               align-items: center; 
               overflow: hidden;
               padding-left: 10%;">

		<div class="hero-content"
			style="color: white; max-width: 600px; padding: 0; position: relative; z-index: 2; text-align: left;">

			<div
				style="border-left: 5px solid #ff6b00; padding-left: 30px; margin-bottom: 40px; transform: translateX(-35px);">
				<h1
					style="font-size: 4.5rem; font-weight: 800; margin: 0 0 15px 0; text-transform: uppercase; letter-spacing: 3px; line-height: 1; text-shadow: 3px 3px 6px rgba(0, 0, 0, 0.5);">
					<span style="color: white;">SPORTS</span><span
						style="color: #ff6b00;">VERSE</span>
				</h1>
			</div>

			<p
				style="font-size: 1.2rem; line-height: 1.8; letter-spacing: 1px; font-weight: 300; margin-bottom: 40px; padding-right: 20px;">
				Elevate your game with premium sports kits designed for champions.
				Precision-crafted for durability and peak performance.</p>

			<div style="display: flex; gap: 20px;">
				<button
					style="background: #ff6b00; color: white; border: none; padding: 15px 35px; font-size: 1rem; border-radius: 0; cursor: pointer; transition: all 0.3s ease; font-weight: 600; letter-spacing: 1px; text-transform: uppercase;">
					Shop Now</button>
				<button
					style="background: transparent; color: white; border: 2px solid white; padding: 15px 35px; font-size: 1rem; border-radius: 0; cursor: pointer; transition: all 0.3s ease; font-weight: 600; letter-spacing: 1px; text-transform: uppercase;">
					Explore</button>
			</div>
		</div>

		<!-- Decorative elements -->
		<div
			style="position: absolute; bottom: -50px; right: -50px; width: 300px; height: 300px; border-radius: 50%; background: rgba(255, 107, 0, 0.2); filter: blur(60px); z-index: 1;">
		</div>
	</header>

	<section class="products-section">
		<div class="section-header">
			<h2>KITS & ACCESSORIES</h2>
		</div>

		<div class="product-grid">
			<div class="product-card">
				<img
					src="${pageContext.request.contextPath}/resources/images/home/26.jpg"
					alt="Bayern Jersey" class="product-image">
				<div class="product-info">
					<h3>Professional Kit</h3>
					<p>High-quality Bayern jersey</p>
					<a href="#" class="product-button">Shop Now</a>
				</div>
			</div>

			<div class="product-card">
				<img
					src="${pageContext.request.contextPath}/resources/images/home/8.jpg"
					alt="Athletic Shoes" class="product-image">
				<div class="product-info">
					<h3>Athletic Footwear</h3>
					<p>Premium leather shoes</p>
					<a href="#" class="product-button">Shop Now</a>
				</div>
			</div>

			<div class="product-card">
				<img
					src="${pageContext.request.contextPath}/resources/images/home/13.jpg"
					alt="Cricket Kit" class="product-image">
				<div class="product-info">
					<h3>Cricket Kit</h3>
					<p>Professional cricket equipment</p>
					<a href="#" class="product-button">Shop Now</a>
				</div>
			</div>

			<div class="product-card">
				<img
					src="${pageContext.request.contextPath}/resources/images/home/6.jpg"
					alt="Basketball Jersey" class="product-image">
				<div class="product-info">
					<h3>Basketball Kit</h3>
					<p>High-performance basketball gear</p>
					<a href="#" class="product-button">Shop Now</a>
				</div>
			</div>

			<div class="product-card">
				<img
					src="${pageContext.request.contextPath}/resources/images/home/5.jpg"
					alt="Baseball Bat" class="product-image">
				<div class="product-info">
					<h3>Baseball Bat</h3>
					<p>Limited edition professional bat</p>
					<a href="#" class="product-button">Shop Now</a>
				</div>
			</div>

			<div class="product-card">
				<img
					src="${pageContext.request.contextPath}/resources/images/home/21.jpg"
					alt="Custom Jersey" class="product-image">
				<div class="product-info">
					<h3>Custom Jersey</h3>
					<p>Personalized jersey customization</p>
					<a href="#" class="product-button">Shop Now</a>
				</div>
			</div>
		</div>
	</section>

	<%@ include file="footer.jsp"%>

	<script src="${pageContext.request.contextPath}/js/header.js"></script>
	<script src="${pageContext.request.contextPath}/js/home.js"></script>
</body>
</html>