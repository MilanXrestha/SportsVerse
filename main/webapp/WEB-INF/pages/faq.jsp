<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>SportsVerse | FAQ</title>

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
/* Container */
.faq-container {
	max-width: 1200px;
	margin: 170px auto 60px;
	padding: 0 20px;
	font-family: 'Roboto', sans-serif;
}

body.dark-mode {
	background-color: #121212;
	color: #f1f1f1;
}

/* FAQ Header */
.faq-header {
	font-family: 'Audiowide', cursive;
	font-size: 2.5rem;
	color: #f57224;
	text-align: center;
	margin-bottom: 1rem;
	text-transform: uppercase;
}

/* Subtitle */
.subtitle {
	text-align: center;
	font-size: 1.125rem;
	color: #666;
	margin-bottom: 3rem;
	max-width: 700px;
	margin-left: auto;
	margin-right: auto;
}

/* FAQ Card */
.faq-card {
	background-color: #fff;
	border-radius: 12px;
	margin-bottom: 1.25rem;
	padding: 1.5rem;
	transition: box-shadow 0.3s ease, transform 0.2s ease;
	cursor: pointer;
	outline: none;
}

/* Light/Dark mode card background */
body.light-mode .faq-card {
	background-color: #fff;
	color: #333;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.15);
}

body.dark-mode .faq-card {
	background-color: #222;
	color: #f1f1f1;
	/* No shadow in dark mode */
}

.faq-card:hover, .faq-card:focus {
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
	transform: translateY(-2px);
}

body.dark-mode .faq-card:hover, body.dark-mode .faq-card:focus {
	transform: translateY(-2px);
}

/* FAQ Question */
.faq-question {
	font-weight: 700;
	font-size: 1.25rem;
	display: flex;
	justify-content: space-between;
	align-items: center;
	gap: 1rem;
	color: #333;
}

body.dark-mode .faq-question {
	color: #f1f1f1;
}

/* Icon */
.faq-question .icon {
	font-size: 1.25rem;
	color: #f57224;
	transition: transform 0.3s ease, color 0.3s ease;
	flex-shrink: 0;
}

/* FAQ Answer */
.faq-answer {
	font-size: 1rem;
	line-height: 1.6;
	max-height: 0;
	overflow: hidden;
	transition: max-height 0.4s ease-out, padding 0.3s ease-out, opacity
		0.3s ease-out;
	padding: 0;
	opacity: 0;
}

body.light-mode .faq-answer {
	color: #333;
}

body.dark-mode .faq-answer {
	color: #f1f1f1;
}

/* Show answer */
.faq-card.active .faq-answer {
	max-height: 500px;
	padding: 1rem 0;
	opacity: 1;
}

/* Rotate icon on active */
.faq-card.active .icon {
	transform: rotate(180deg);
	color: #e55b00;
	/* Links in answers */ . text-link { color : #f57224;
	text-decoration: none;
	font-weight: 500;
}

.text-link:hover {
	text-decoration: underline;
}

/* Responsive */
@media ( max-width : 768px) {
	.faq-container {
		max-width: 100%;
		margin: 80px 1rem 40px;
		padding: 0 1rem;
	}
	.faq-header {
		font-size: 2rem;
	}
	.faq-question {
		font-size: 1.125rem;
	}
	.faq-answer {
		font-size: 0.9375rem;
	}
}

@media ( max-width : 480px) {
	.faq-header {
		font-size: 1.75rem;
	}
	.faq-question {
		font-size: 1rem;
	}
	.faq-answer {
		font-size: 0.875rem;
	}
}
</style>
</head>
<body class="light-mode">
	<%@ include file="header.jsp"%>

	<main class="faq-container">
		<h1 class="faq-header">Frequently Asked Questions</h1>
		<p class="subtitle">Explore answers to common questions about
			SportsVerse, your go-to store for cricket, football, volleyball, and
			more.</p>

		<section class="faq-list" aria-label="Frequently Asked Questions">
			<div class="faq-card" tabindex="0" role="button"
				aria-expanded="false" aria-controls="faq1">
				<div class="faq-question">
					What types of sports products do you sell? <i
						class="fa-solid fa-chevron-down icon" aria-hidden="true"></i>
				</div>
				<div class="faq-answer" id="faq1">We offer a wide range of
					sports products, including cricket bats and balls, football gear,
					volleyball equipment, badminton rackets, and apparel for running,
					fitness, and more.</div>
			</div>

			<div class="faq-card" tabindex="0" role="button"
				aria-expanded="false" aria-controls="faq2">
				<div class="faq-question">
					How can I track my order? <i class="fa-solid fa-chevron-down icon"
						aria-hidden="true"></i>
				</div>
				<div class="faq-answer" id="faq2">Once your order ships,
					you’ll receive an email with a tracking number and a link to track
					your package through our delivery partners.</div>
			</div>

			<div class="faq-card" tabindex="0" role="button"
				aria-expanded="false" aria-controls="faq3">
				<div class="faq-question">
					What is your return policy? <i
						class="fa-solid fa-chevron-down icon" aria-hidden="true"></i>
				</div>
				<div class="faq-answer" id="faq3">
					We accept returns within 30 days of delivery for unused items in
					original packaging. Some exclusions may apply. Visit our <a
						href="/returns" class="text-link">Returns & Refunds</a> page for
					details.
				</div>
			</div>

			<div class="faq-card" tabindex="0" role="button"
				aria-expanded="false" aria-controls="faq4">
				<div class="faq-question">
					Can I change or cancel my order? <i
						class="fa-solid fa-chevron-down icon" aria-hidden="true"></i>
				</div>
				<div class="faq-answer" id="faq4">
					Orders can be modified or canceled within 1 hour of placement.
					Contact our support team at <a
						href="mailto:support@sportsverse.com.np" class="text-link">support@sportsverse.com.np</a>.
				</div>
			</div>

			<div class="faq-card" tabindex="0" role="button"
				aria-expanded="false" aria-controls="faq5">
				<div class="faq-question">
					How can I contact customer support? <i
						class="fa-solid fa-chevron-down icon" aria-hidden="true"></i>
				</div>
				<div class="faq-answer" id="faq5">
					Reach our support team via email at <a
						href="mailto:support@sportsverse.com.np" class="text-link">support@sportsverse.com.np</a>
					or call +977-1-5970000, Monday to Friday, 9 AM to 6 PM.
				</div>
			</div>

			<div class="faq-card" tabindex="0" role="button"
				aria-expanded="false" aria-controls="faq6">
				<div class="faq-question">
					Do you have a loyalty program? <i
						class="fa-solid fa-chevron-down icon" aria-hidden="true"></i>
				</div>
				<div class="faq-answer" id="faq6">
					Yes! Join our SportsVerse Rewards program to earn points on every
					purchase and redeem exclusive discounts at our stores in Kathmandu,
					Pokhara, and beyond. <a href="/rewards" class="text-link">Learn
						more</a>.
				</div>
			</div>

			<div class="faq-card" tabindex="0" role="button"
				aria-expanded="false" aria-controls="faq7">
				<div class="faq-question">
					What payment methods do you accept? <i
						class="fa-solid fa-chevron-down icon" aria-hidden="true"></i>
				</div>
				<div class="faq-answer" id="faq7">We accept Visa, MasterCard,
					eSewa, Khalti, and cash on delivery.</div>
			</div>

			<div class="faq-card" tabindex="0" role="button"
				aria-expanded="false" aria-controls="faq8">
				<div class="faq-question">
					How do I reset my password? <i
						class="fa-solid fa-chevron-down icon" aria-hidden="true"></i>
				</div>
				<div class="faq-answer" id="faq8">Click “Forgot Password” on
					the login page and follow the instructions to reset your password
					via email.</div>
			</div>

			<div class="faq-card" tabindex="0" role="button"
				aria-expanded="false" aria-controls="faq9">
				<div class="faq-question">
					Are your products covered by warranty? <i
						class="fa-solid fa-chevron-down icon" aria-hidden="true"></i>
				</div>
				<div class="faq-answer" id="faq9">Most products, including
					cricket and football gear, come with a manufacturer’s warranty.
					Check the product description for specific warranty details.</div>
			</div>

			<div class="faq-card" tabindex="0" role="button"
				aria-expanded="false" aria-controls="faq10">
				<div class="faq-question">
					Do you offer delivery to rural areas? <i
						class="fa-solid fa-chevron-down icon" aria-hidden="true"></i>
				</div>
				<div class="faq-answer" id="faq10">Yes, we deliver to most
					urban and rural areas. Delivery times may vary based on location.
					Contact our support team for details.</div>
			</div>
		</section>
	</main>

	<%@ include file="footer.jsp"%>

	<script>
        document.querySelectorAll('.faq-card').forEach(card => {
            const toggleCard = () => {
                const isActive = card.classList.contains('active');
                document.querySelectorAll('.faq-card.active').forEach(openCard => {
                    openCard.classList.remove('active');
                    openCard.setAttribute('aria-expanded', 'false');
                });
                if (!isActive) {
                    card.classList.add('active');
                    card.setAttribute('aria-expanded', 'true');
                }
            };

            card.addEventListener('click', toggleCard);

            card.addEventListener('keydown', e => {
                if (e.key === 'Enter' || e.key === ' ') {
                    e.preventDefault();
                    toggleCard();
                }
            });
        });
    </script>
	<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>