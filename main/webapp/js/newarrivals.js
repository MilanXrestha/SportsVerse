document.addEventListener('DOMContentLoaded', () => {
	// Scroll-to-top button
	const scrollToTopBtn = document.createElement('button');
	scrollToTopBtn.innerHTML = '<i class="fas fa-arrow-up"></i>';
	scrollToTopBtn.className = 'scroll-to-top';
	scrollToTopBtn.title = 'Back to Top';
	document.body.appendChild(scrollToTopBtn);

	// Show/hide scroll-to-top button based on scroll position
	window.addEventListener('scroll', () => {
		scrollToTopBtn.style.display = window.scrollY > 300 ? 'block' : 'none';
	});

	// Scroll to top when clicked
	scrollToTopBtn.addEventListener('click', () => {
		window.scrollTo({
			top: 0,
			behavior: 'smooth'
		});
	});
});