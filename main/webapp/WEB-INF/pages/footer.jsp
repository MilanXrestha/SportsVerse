<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<footer class="site-footer">
	<div class="footer-container">
		<div class="footer-brand">
			<a href="${pageContext.request.contextPath}/dashboard"
				class="footer-logo-link">
				<div class="footer-logo" id="footer-logo">
					<span>S</span><span>p</span><span><i
						class="fas fa-basketball footer-football"></i></span> <span>r</span><span>t</span><span>s</span><span>V</span><span>E</span><span>R</span><span>S</span><span>E</span>
				</div>
				<div class="footer-tagline">One Platform, Infinite Sports</div>
			</a>
			<p>Your trusted sport gear partner</p>
			<div class="footer-social">
				<h3>Reach Us</h3>
				<div class="footer-social-icons">
					<a href="https://facebook.com" target="_blank"
						aria-label="Facebook"><i class="fab fa-facebook-f"></i></a> <a
						href="https://twitter.com" target="_blank" aria-label="Twitter"><i
						class="fab fa-twitter"></i></a> <a href="https://instagram.com"
						target="_blank" aria-label="Instagram"><i
						class="fab fa-instagram"></i></a> <a href="https://linkedin.com"
						target="_blank" aria-label="LinkedIn"><i
						class="fab fa-linkedin-in"></i></a>
				</div>
			</div>
		</div>

		<div class="footer-links">
			<h3>Quick Links</h3>
			<ul>
				<li><a href="${pageContext.request.contextPath}/home">Home</a></li>
				<li><a href="${pageContext.request.contextPath}/explore">Products</a></li>
				<li><a href="${pageContext.request.contextPath}/trending">Trending</a></li>
				<li><a href="${pageContext.request.contextPath}/newarrivals">New
						Arrivals</a></li>
				<li><a href="${pageContext.request.contextPath}/contact">Contact
						Us</a></li>
				<li><a href="${pageContext.request.contextPath}/services">Services</a></li>
				<li><a href="${pageContext.request.contextPath}/faq">FAQ</a></li>
			</ul>
		
		</div>

		<div class="footer-newsletter">
			<h3>Subscribe to Our Newsletter</h3>
			<p>Stay updated with the latest sports gear and exclusive offers!</p>
			<c:if test="${not empty newsletterMessage}">
				<p
					class="newsletter-message ${newsletterSuccess ? 'success' : 'error'}">${newsletterMessage}</p>
			</c:if>
			<form action="${pageContext.request.contextPath}/newsletter"
				method="post" class="newsletter-form">
				<div class="input-wrapper">
					<input type="email" name="email" placeholder="Enter your email"
						required />
					<button type="submit" class="subscribe-btn">Subscribe</button>
				</div>
			</form>
		</div>
	</div>

	<div class="footer-bottom">
		<p>© 2025 SportsVerse. All Rights Reserved.</p>
	</div>
</footer>