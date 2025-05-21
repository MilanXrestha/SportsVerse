<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>SportsVerse | Blog</title>

<!-- Stylesheets -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
<link href="https://fonts.googleapis.com/css2?family=Orbitron&family=Audiowide&family=Roboto:wght@400;700&display=swap" rel="stylesheet">

<style>
body {
	font-family: 'Roboto', sans-serif;
}

.blog-container {
	max-width: 1200px;
	margin: 100px auto 60px;
	padding: 0 20px;
}

.blog-header {
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

/* Blog Grid */
.blog-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
	gap: 1.5rem;
}

/* Blog Card */
.blog-card {
	background-color: #fff;
	border-radius: 12px;
	padding: 1.5rem;
	transition: box-shadow 0.3s ease, transform 0.2s ease;
	cursor: default;
	box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

body.dark-mode .blog-card {
	background-color: #222;
	color: #f1f1f1;
}

.blog-card:hover {
	box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
	transform: translateY(-3px);
}

.blog-icon {
	font-size: 2.5rem;
	color: #f57224;
	margin-bottom: 1rem;
}

.blog-title {
	font-size: 1.25rem;
	font-weight: 700;
	color: #f57224;
	margin-bottom: 0.5rem;
}

.blog-snippet {
	font-size: 1rem;
	color: #333;
	line-height: 1.6;
	margin-bottom: 1rem;
}

body.dark-mode .blog-snippet {
	color: #ccc;
}

.read-more {
	color: #f57224;
	font-weight: 500;
	text-decoration: none;
}

.read-more:hover {
	text-decoration: underline;
}

/* CTA */
.cta-section {
	text-align: center;
	padding: 2rem;
	background-color: #f8f8f8;
	border-radius: 12px;
	margin-top: 3rem;
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
@media (max-width: 768px) {
	.blog-header {
		font-size: 2rem;
	}
	.blog-title {
		font-size: 1.125rem;
	}
	.blog-snippet {
		font-size: 0.9375rem;
	}
}

@media (max-width: 480px) {
	.blog-header {
		font-size: 1.75rem;
	}
	.blog-title {
		font-size: 1rem;
	}
	.blog-snippet {
		font-size: 0.875rem;
	}
}
</style>
</head>

<body class="light-mode">
	<%@ include file="header.jsp"%>

	<main class="blog-container">
		<h1 class="blog-header">SportsVerse Blog</h1>
		<p class="subtitle">Stay updated with the latest sports news, training tips, gear reviews, and more from the world of cricket, football, and beyond.</p>

		<section class="blog-grid" aria-label="Blog Posts">

			<div class="blog-card">
				<i class="fas fa-futbol blog-icon"></i>
				<h2 class="blog-title">Top 5 Football Drills to Improve Your Game</h2>
				<p class="blog-snippet">Master your ball control, speed, and agility with these essential drills for both beginners and advanced players.</p>
				<a href="/blog/football-drills" class="read-more">Read More</a>
			</div>

			<div class="blog-card">
				<i class="fas fa-baseball-ball blog-icon"></i>
				<h2 class="blog-title">Choosing the Perfect Cricket Bat</h2>
				<p class="blog-snippet">A comprehensive guide to selecting the right bat for your playing style, level, and budget.</p>
				<a href="/blog/cricket-bat-guide" class="read-more">Read More</a>
			</div>

			<div class="blog-card">
				<i class="fas fa-dumbbell blog-icon"></i>
				<h2 class="blog-title">Home Workouts for Athletes</h2>
				<p class="blog-snippet">No gym? No problem. Here's how to stay fit with just your bodyweight and minimal equipment.</p>
				<a href="/blog/home-workouts" class="read-more">Read More</a>
			</div>

			<div class="blog-card">
				<i class="fas fa-medal blog-icon"></i>
				<h2 class="blog-title">Top Sports Events to Watch This Season</h2>
				<p class="blog-snippet">Don’t miss these thrilling tournaments and matches in 2025 – from cricket world tours to football championships.</p>
				<a href="/blog/sports-events-2025" class="read-more">Read More</a>
			</div>

			<div class="blog-card">
				<i class="fas fa-shield-alt blog-icon"></i>
				<h2 class="blog-title">Why Sports Safety Gear Matters</h2>
				<p class="blog-snippet">Learn how protective equipment can prevent injuries and boost your confidence during play.</p>
				<a href="/blog/safety-gear" class="read-more">Read More</a>
			</div>

			<div class="blog-card">
				<i class="fas fa-heartbeat blog-icon"></i>
				<h2 class="blog-title">Nutrition Tips for Peak Performance</h2>
				<p class="blog-snippet">Fuel your body the right way with pre-game meals, hydration strategies, and recovery snacks.</p>
				<a href="/blog/nutrition-tips" class="read-more">Read More</a>
			</div>

		</section>

		<section class="cta-section">
			<h2 class="cta-title">Got a Story or Tip to Share?</h2>
			<p class="subtitle">We love hearing from fellow sports fans. Contribute your own articles or experiences.</p>
			<a href="/blog/contribute" class="cta-button">Contribute</a>
		</section>
	</main>

	<%@ include file="footer.jsp"%>
	<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>
