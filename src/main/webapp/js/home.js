document.addEventListener('DOMContentLoaded', function() {
	// Slider Logic
	const slides = document.querySelectorAll('.slide');
	const dots = document.querySelectorAll('.dot');
	const prevBtn = document.querySelector('.prev-btn');
	const nextBtn = document.querySelector('.next-btn');
	const sliderContainer = document.querySelector('.slider-container');
	let currentIndex = 0;
	let autoSlideInterval;
	let isTransitioning = false;
	let touchStartX = 0;
	let touchEndX = 0;

	// Function to show a specific slide
	function showSlide(index) {
		if (isTransitioning) return;
		isTransitioning = true;

		slides.forEach((slide, i) => {
			slide.style.display = i === index ? 'block' : 'none';
			slide.style.opacity = i === index ? '1' : '0';
			slide.classList.toggle('active', i === index);
		});
		dots.forEach((dot, i) => {
			dot.classList.toggle('active', i === index);
		});
		currentIndex = index;

		// Reset transition flag after a shorter duration (0.3s)
		setTimeout(() => {
			isTransitioning = false;
		}, 300);
	}

	// Function to go to the next slide
	function nextSlide() {
		let newIndex = currentIndex + 1;
		if (newIndex >= slides.length) {
			newIndex = 0;
		}
		showSlide(newIndex);
	}

	// Function to go to the previous slide
	function prevSlide() {
		let newIndex = currentIndex - 1;
		if (newIndex < 0) {
			newIndex = slides.length - 1;
		}
		showSlide(newIndex);
	}

	function startAutoSlide() {
		stopAutoSlide(); // Clear any existing interval first
		autoSlideInterval = setInterval(nextSlide, 5000);
	}

	function stopAutoSlide() {
		if (autoSlideInterval) {
			clearInterval(autoSlideInterval);
			autoSlideInterval = null;
		}
	}

	// Initialize slider
	if (slides.length > 0) {
		showSlide(0);
		startAutoSlide();
	}

	// Navigation button events
	if (nextBtn) {
		nextBtn.addEventListener('click', () => {
			if (isTransitioning) return;
			stopAutoSlide();
			nextSlide();
			startAutoSlide();
		});
	}

	if (prevBtn) {
		prevBtn.addEventListener('click', () => {
			if (isTransitioning) return;
			stopAutoSlide();
			prevSlide();
			startAutoSlide();
		});
	}

	// Dot navigation
	dots.forEach(dot => {
		dot.addEventListener('click', () => {
			if (isTransitioning) return;
			stopAutoSlide();
			const index = parseInt(dot.getAttribute('data-index'));
			showSlide(index);
			startAutoSlide();
		});
	});

	// Pause auto-slide on button/dot hover
	const interactiveElements = document.querySelectorAll('.slider-btn, .dot');
	interactiveElements.forEach(el => {
		el.addEventListener('mouseenter', stopAutoSlide);
		el.addEventListener('mouseleave', startAutoSlide);
	});

	// Keyboard navigation
	document.addEventListener('keydown', (e) => {
		if (isTransitioning) return;
		if (e.key === 'ArrowLeft' && sliderContainer.contains(document.activeElement)) {
			stopAutoSlide();
			prevSlide();
			startAutoSlide();
		} else if (e.key === 'ArrowRight' && sliderContainer.contains(document.activeElement)) {
			stopAutoSlide();
			nextSlide();
			startAutoSlide();
		}
	});

	// Touch/swipe support
	if (sliderContainer) {
		sliderContainer.addEventListener('touchstart', (e) => {
			touchStartX = e.changedTouches[0].screenX;
		});

		sliderContainer.addEventListener('touchend', (e) => {
			if (isTransitioning) return;
			touchEndX = e.changedTouches[0].screenX;
			if (touchStartX - touchEndX > 50) {
				// Swipe left
				stopAutoSlide();
				nextSlide();
				startAutoSlide();
			} else if (touchEndX - touchStartX > 50) {
				// Swipe right
				stopAutoSlide();
				prevSlide();
				startAutoSlide();
			}
		});
	}

	// Make slider container focusable for keyboard navigation
	if (sliderContainer) {
		sliderContainer.tabIndex = 0;
	}

	// Category Scroll Logic
	const categoryScrollContainer = document.querySelector('.category-scroll-container');
	const prevCategoryBtn = document.querySelector('.prev-category-btn');
	const nextCategoryBtn = document.querySelector('.next-category-btn');

	if (categoryScrollContainer && prevCategoryBtn && nextCategoryBtn) {
		const cardWidth = 230; // Width of a category card (200px) + margin (15px each side)

		prevCategoryBtn.addEventListener('click', () => {
			categoryScrollContainer.scrollBy({
				left: -cardWidth * 2,
				behavior: 'smooth'
			});
		});

		nextCategoryBtn.addEventListener('click', () => {
			categoryScrollContainer.scrollBy({
				left: cardWidth * 2,
				behavior: 'smooth'
			});
		});

		// Touch/swipe support for category scroll
		let categoryTouchStartX = 0;
		let categoryTouchEndX = 0;

		categoryScrollContainer.addEventListener('touchstart', (e) => {
			categoryTouchStartX = e.changedTouches[0].screenX;
		});

		categoryScrollContainer.addEventListener('touchend', (e) => {
			categoryTouchEndX = e.changedTouches[0].screenX;
			if (categoryTouchStartX - categoryTouchEndX > 50) {
				categoryScrollContainer.scrollBy({
					left: cardWidth * 2,
					behavior: 'smooth'
				});
			} else if (categoryTouchEndX - categoryTouchStartX > 50) {
				categoryScrollContainer.scrollBy({
					left: -cardWidth * 2,
					behavior: 'smooth'
				});
			}
		});

		// Show/hide category buttons based on scroll position
		function updateCategoryButtons() {
			const isScrollable = categoryScrollContainer.scrollWidth > categoryScrollContainer.clientWidth;
			const scrollLeft = categoryScrollContainer.scrollLeft;
			const scrollRight = categoryScrollContainer.scrollWidth - (categoryScrollContainer.clientWidth + scrollLeft);

			prevCategoryBtn.style.display = isScrollable && scrollLeft > 1 ? 'block' : 'none';
			nextCategoryBtn.style.display = isScrollable && scrollRight > 1 ? 'block' : 'none';
		}

		categoryScrollContainer.addEventListener('scroll', updateCategoryButtons);
		window.addEventListener('resize', updateCategoryButtons);
		updateCategoryButtons(); // Initial check
	}

	// Featured Products Scroll Logic
	const featuredScrollContainer = document.querySelector('.featured-products-scroll-container');
	const prevFeaturedBtn = document.querySelector('.prev-featured-btn');
	const nextFeaturedBtn = document.querySelector('.next-featured-btn');

	if (featuredScrollContainer && prevFeaturedBtn && nextFeaturedBtn) {
		const cardWidth = 325; // Width (280px) + margin (15px each side) + gap (10px)

		prevFeaturedBtn.addEventListener('click', () => {
			featuredScrollContainer.scrollBy({
				left: -cardWidth * 2,
				behavior: 'smooth'
			});
		});

		nextFeaturedBtn.addEventListener('click', () => {
			featuredScrollContainer.scrollBy({
				left: cardWidth * 2,
				behavior: 'smooth'
			});
		});

		// Touch/swipe support for featured products scroll
		let featuredTouchStartX = 0;
		let featuredTouchEndX = 0;

		featuredScrollContainer.addEventListener('touchstart', (e) => {
			featuredTouchStartX = e.changedTouches[0].screenX;
		});

		featuredScrollContainer.addEventListener('touchend', (e) => {
			featuredTouchEndX = e.changedTouches[0].screenX;
			if (featuredTouchStartX - featuredTouchEndX > 50) {
				featuredScrollContainer.scrollBy({
					left: cardWidth * 2,
					behavior: 'smooth'
				});
			} else if (featuredTouchEndX - featuredTouchStartX > 50) {
				featuredScrollContainer.scrollBy({
					left: -cardWidth * 2,
					behavior: 'smooth'
				});
			}
		});

		// Show/hide featured buttons based on scroll position
		function updateFeaturedButtons() {
			const isScrollable = featuredScrollContainer.scrollWidth > featuredScrollContainer.clientWidth;
			const scrollLeft = featuredScrollContainer.scrollLeft;
			const scrollRight = featuredScrollContainer.scrollWidth - (featuredScrollContainer.clientWidth + scrollLeft);

			prevFeaturedBtn.style.display = isScrollable && scrollLeft > 1 ? 'flex' : 'none';
			nextFeaturedBtn.style.display = isScrollable && scrollRight > 1 ? 'flex' : 'none';
		}

		featuredScrollContainer.addEventListener('scroll', updateFeaturedButtons);
		window.addEventListener('resize', updateFeaturedButtons);
		updateFeaturedButtons(); // Initial check
	}


	// Adidas Products Scroll Logic
	const adidasScrollContainer = document.querySelector('.adidas-products-scroll-container');
	const prevAdidasBtn = document.querySelector('.prev-adidas-btn');
	const nextAdidasBtn = document.querySelector('.next-adidas-btn');

	if (adidasScrollContainer && prevAdidasBtn && nextAdidasBtn) {
		const cardWidth = 325; // Width (280px) + margin (15px each side) + gap (10px)

		prevAdidasBtn.addEventListener('click', () => {
			adidasScrollContainer.scrollBy({
				left: -cardWidth * 2,
				behavior: 'smooth'
			});
		});

		nextAdidasBtn.addEventListener('click', () => {
			adidasScrollContainer.scrollBy({
				left: cardWidth * 2,
				behavior: 'smooth'
			});
		});

		// Touch/swipe support for Adidas products scroll
		let adidasTouchStartX = 0;
		let adidasTouchEndX = 0;

		adidasScrollContainer.addEventListener('touchstart', (e) => {
			adidasTouchStartX = e.changedTouches[0].screenX;
		});

		adidasScrollContainer.addEventListener('touchend', (e) => {
			adidasTouchEndX = e.changedTouches[0].screenX;
			if (adidasTouchStartX - adidasTouchEndX > 50) {
				adidasScrollContainer.scrollBy({
					left: cardWidth * 2,
					behavior: 'smooth'
				});
			} else if (adidasTouchEndX - adidasTouchStartX > 50) {
				adidasScrollContainer.scrollBy({
					left: -cardWidth * 2,
					behavior: 'smooth'
				});
			}
		});

		// Show/hide Adidas buttons based on scroll position
		function updateAdidasButtons() {
			const isScrollable = adidasScrollContainer.scrollWidth > adidasScrollContainer.clientWidth;
			const scrollLeft = adidasScrollContainer.scrollLeft;
			const scrollRight = adidasScrollContainer.scrollWidth - (adidasScrollContainer.clientWidth + scrollLeft);

			prevAdidasBtn.style.display = isScrollable && scrollLeft > 1 ? 'flex' : 'none';
			nextAdidasBtn.style.display = isScrollable && scrollRight > 1 ? 'flex' : 'none';
		}

		adidasScrollContainer.addEventListener('scroll', updateAdidasButtons);
		window.addEventListener('resize', updateAdidasButtons);
		updateAdidasButtons(); // Initial check
	}




	// Weekly Deal Timer
	function startTimer() {
		const timerValues = document.querySelectorAll('.timer-value');
		const now = new Date();
		const nextTuesday = new Date(now);
		nextTuesday.setDate(now.getDate() + ((7 - now.getDay() + 2) % 7 || 7)); // Next Tuesday
		nextTuesday.setHours(23, 59, 59, 0); // 11:59 PM +0545

		function updateTimer() {
			const timeLeft = Math.max(0, Math.floor((nextTuesday - new Date()) / 1000));
			const days = Math.floor(timeLeft / (24 * 60 * 60));
			const hours = Math.floor((timeLeft % (24 * 60 * 60)) / (60 * 60));
			const minutes = Math.floor((timeLeft % (60 * 60)) / 60);
			const seconds = timeLeft % 60;

			timerValues.forEach(value => {
				const unit = value.getAttribute('data-time');
				value.textContent = unit === 'days' ? String(days).padStart(2, '0') :
					unit === 'hours' ? String(hours).padStart(2, '0') :
						unit === 'minutes' ? String(minutes).padStart(2, '0') :
							String(seconds).padStart(2, '0');
			});

			if (timeLeft <= 0) {
				clearInterval(timerInterval);
				alert('Weekly Best Deal has ended! Check back next week.');
				location.reload(); // Reload to reset timer
			}
		}

		updateTimer();
		const timerInterval = setInterval(updateTimer, 1000);
	}

	// Start timer when page loads
	window.onload = startTimer;
});