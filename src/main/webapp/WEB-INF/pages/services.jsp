<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>SportsVerse | Our Services</title>

<!-- Stylesheets -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
<link
	href="https://fonts.googleapis.com/css2?family=Orbitron&family=Audiowide&family=Roboto:wght@400;700&display=swap"
	rel="stylesheet">

<style>
body {
	margin: 0;
	font-family: 'Roboto', sans-serif;
	background-color: #fff;
	color: #333;
	transition: background-color 0.3s ease, color 0.3s ease;
}

body.dark-mode {
	background-color: #121212;
	color: #f1f1f1;
}

.services-container {
	max-width: 1200px;
	margin: 170px auto 60px;
	padding: 0 20px;
}

.services-header {
	font-family: 'Audiowide', cursive;
	font-size: 2.5rem;
	color: #f57224;
	text-align: center;
	margin-bottom: 1rem;
	text-transform: uppercase;
}

.subtitle {
	text-align: center;
	font-size: 1.125rem;
	color: #666;
	margin-bottom: 3rem;
	max-width: 700px;
	margin-left: auto;
	margin-right: auto;
}

body.dark-mode .subtitle {
	color: #aaa;
}

.services-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
	gap: 1.5rem;
	margin-bottom: 2rem;
}

.service-card {
	background-color: #fff;
	border-radius: 16px;
	padding: 2rem;
	text-align: center;
	transition: all 0.3s ease;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
	cursor: default;
}

body.dark-mode .service-card {
	background-color: #222;
	color: #f1f1f1;
}

.service-card:hover {
	box-shadow: 0 10px 20px rgba(0, 0, 0, 0.2);
	transform: translateY(-5px);
}

.service-icon {
	font-size: 3rem;
	color: #f57224;
	margin-bottom: 1rem;
}

.service-title {
	font-size: 1.25rem;
	font-weight: 700;
	color: #f57224;
	margin-bottom: 0.75rem;
}

.service-description {
	font-size: 1rem;
	line-height: 1.6;
	color: #333;
}

body.dark-mode .service-description {
	color: #ddd;
}

.text-link {
	color: #f57224;
	text-decoration: none;
	font-weight: 500;
}

.text-link:hover {
	text-decoration: underline;
}

.cta-section {
	text-align: center;
	padding: 2rem;
	background-color: #f8f8f8;
	border-radius: 16px;
	margin-top: 2rem;
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

body.dark-mode .cta-section {
	background-color: #2a2a2a;
}

.cta-title {
	font-size: 1.5rem;
	font-weight: 700;
	color: #f57224;
	margin-bottom: 1rem;
}

.cta-button {
	display: inline-block;
	padding: 0.75rem 1.5rem;
	background-color: #f57224;
	color: #fff;
	border-radius: 8px;
	text-decoration: none;
	font-weight: 500;
	transition: background-color 0.3s ease;
}

.cta-button:hover {
	background-color: #e55b00;
}

/* Responsive */
@media ( max-width : 768px) {
	.services-header {
		font-size: 2rem;
	}
	.service-title {
		font-size: 1.125rem;
	}
	.service-description {
		font-size: 0.9375rem;
	}
}

@media ( max-width : 480px) {
	.services-header {
		font-size: 1.75rem;
	}
	.service-title {
		font-size: 1rem;
	}
	.service-description {
		font-size: 0.875rem;
	}
}
</style>
</head>
<body class="light-mode">
	<%@ include file="header.jsp"%>

	<main class="services-container">
		<h1 class="services-header">Our Services</h1>
		<p class="subtitle">Explore everything SportsVerse has to offer —
			from premium gear to exceptional support and customer benefits.</p>

		<section class="services-grid" aria-label="Our Services">
			<div class="service-card" tabindex="0">
				<i class="fas fa-dumbbell service-icon"></i>
				<h2 class="service-title">Wide Range of Products</h2>
				<p class="service-description">Top-quality equipment and apparel
					for cricket, football, fitness, and more.</p>
			</div>

			<div class="service-card" tabindex="0">
				<i class="fas fa-truck-fast service-icon"></i>
				<h2 class="service-title">Nationwide Delivery</h2>
				<p class="service-description">
					Fast delivery to every corner of Nepal. <a href="/shipping"
						class="text-link">Learn more</a>.
				</p>
			</div>

			<div class="service-card" tabindex="0">
				<i class="fas fa-headset service-icon"></i>
				<h2 class="service-title">Customer Support</h2>
				<p class="service-description">Reach out via email or phone —
					we're here to help 9 AM – 6 PM, Mon–Fri.</p>
			</div>

			<div class="service-card" tabindex="0">
				<i class="fas fa-store service-icon"></i>
				<h2 class="service-title">In-Store Pickup</h2>
				<p class="service-description">Order online and pick up from
					Kathmandu, Pokhara & more locations.</p>
			</div>

			<div class="service-card" tabindex="0">
				<i class="fas fa-shield-halved service-icon"></i>
				<h2 class="service-title">Warranty Support</h2>
				<p class="service-description">Enjoy warranties on most items.
					Check product pages for details.</p>
			</div>

			<div class="service-card" tabindex="0">
				<i class="fas fa-tools service-icon"></i>
				<h2 class="service-title">Maintenance Services</h2>
				<p class="service-description">We help you care for your sports
					gear. Contact us for pricing and availability.</p>
			</div>

			<div class="service-card" tabindex="0">
				<i class="fas fa-wallet service-icon"></i>
				<h2 class="service-title">Flexible Payments</h2>
				<p class="service-description">Pay via card, eSewa, Khalti, or
					cash on delivery — your choice!</p>
			</div>

			<div class="service-card" tabindex="0">
				<i class="fas fa-gift service-icon"></i>
				<h2 class="service-title">Rewards Program</h2>
				<p class="service-description">
					Earn points and unlock discounts. <a href="/rewards"
						class="text-link">Join now</a>.
				</p>
			</div>

			<div class="service-card" tabindex="0">
				<i class="fas fa-user-graduate service-icon"></i>
				<h2 class="service-title">Expert Advice</h2>
				<p class="service-description">Need help choosing gear? Get
					guidance from our in-house sports experts.</p>
			</div>
		</section>

		<section class="cta-section">
			<h2 class="cta-title">Ready to Gear Up?</h2>
			<p class="subtitle">Explore our full collection and elevate your
				game with SportsVerse.</p>
			<a href="${pageContext.request.contextPath}/explore"
				class="cta-button">Shop Now</a>




		</section>
	</main>

	<%@ include file="footer.jsp"%>

	<script>
    // Focus support
    document.querySelectorAll('.service-card').forEach(card => {
        card.addEventListener('keydown', e => {
            if (e.key === 'Enter' || e.key === ' ') {
                e.preventDefault();
                card.focus();
            }
        });
    });

    // CTA click
    document.querySelector('.cta-button').addEventListener('click', e => {
        e.preventDefault();
        window.location.href = e.target.href;
    });
</script>

	<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>
