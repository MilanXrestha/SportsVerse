<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>SportsVerse - Contact Us</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/contact.css" />
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

	<section class="contact-section">
		<h2 class="section-title">Contact Us</h2>

		<div class="contact-container">
			<div class="contact-form">
				<h3 class="form-title">Send Us a Message</h3>
				<c:if test="${not empty successMessage}">
					<p class="success-message">${successMessage}</p>
				</c:if>
				<c:if test="${not empty errorMessage}">
					<p class="error-message">${errorMessage}</p>
				</c:if>
				<form action="${pageContext.request.contextPath}/contact"
					method="post" id="contact-form">
					<div class="form-group">
						<label for="name">Name</label> <input type="text" id="name"
							name="name" required />
					</div>
					<div class="form-group">
						<label for="email">Email</label> <input type="email" id="email"
							name="email" required />
					</div>
					<div class="form-group">
						<label for="subject">Subject</label> <input type="text"
							id="subject" name="subject" required />
					</div>
					<div class="form-group">
						<label for="message">Message</label>
						<textarea id="message" name="message" rows="6" required></textarea>
					</div>
					<button type="submit" class="submit-btn">Send Message</button>
				</form>
			</div>

			<div class="contact-info">
				<h3 class="info-title">Get in Touch</h3>
				<div class="info-item">
					<i class="fas fa-envelope"></i>
					<p>
						Email: <a href="mailto:support@sportsverse.com">support@sportsverse.com</a>
					</p>
				</div>
				<div class="info-item">
					<i class="fas fa-phone"></i>
					<p>Phone: +977-123-456-7890</p>
				</div>
				<div class="info-item">
					<i class="fas fa-map-marker-alt"></i>
					<p>Address: Kathmandu, Nepal</p>
				</div>
			</div>
		</div>
	</section>

	<%@ include file="footer.jsp"%>

	<script src="${pageContext.request.contextPath}/js/header.js"></script>
	<script src="${pageContext.request.contextPath}/js/contact.js"></script>
</body>
</html>