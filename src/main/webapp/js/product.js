document.addEventListener('DOMContentLoaded', function() {
	// Zoom Feature
	const image = document.querySelector('.product-image');
	const lens = document.querySelector('.zoom-lens');
	const result = document.querySelector('.zoom-result');
	const container = document.querySelector('.product-image-container');

	if (image && lens && result && container) {
		const zoomFactor = 4; // Magnification level
		const lensSize = 100; // Lens width/height in pixels

		// Set result background image
		const zoomImageUrl = image.dataset.zoomImage;
		result.style.backgroundImage = `url('${zoomImageUrl}')`;
		result.style.backgroundSize = `${image.offsetWidth * zoomFactor}px ${image.offsetHeight * zoomFactor}px`;

		image.addEventListener('mouseenter', () => {
			lens.style.display = 'block';
			result.style.display = 'block';
		});

		image.addEventListener('mouseleave', () => {
			lens.style.display = 'none';
			result.style.display = 'none';
		});

		image.addEventListener('mousemove', (e) => {
			const rect = image.getBoundingClientRect();
			const containerRect = container.getBoundingClientRect();

			// Mouse position relative to image
			let x = e.clientX - rect.left;
			let y = e.clientY - rect.top;

			// Keep lens within image bounds
			x = Math.max(lensSize / 2, Math.min(x, rect.width - lensSize / 2));
			y = Math.max(lensSize / 2, Math.min(y, rect.height - lensSize / 2));

			// Position lens
			lens.style.left = `${x - lensSize / 2}px`;
			lens.style.top = `${y - lensSize / 2}px`;

			// Update magnified image position
			const bgX = ((x - lensSize / 2) / rect.width) * (rect.width * zoomFactor);
			const bgY = ((y - lensSize / 2) / rect.height) * (rect.height * zoomFactor);
			result.style.backgroundPosition = `-${bgX}px -${bgY}px`;
		});

		// Adjust zoom on window resize
		window.addEventListener('resize', () => {
			result.style.backgroundSize = `${image.offsetWidth * zoomFactor}px ${image.offsetHeight * zoomFactor}px`;
		});
	}

	// Review Form Validation
	const reviewForm = document.querySelector('.review-form');
	if (reviewForm) {
		reviewForm.addEventListener('submit', (e) => {
			const rating = reviewForm.querySelector('input[name="rating"]:checked');
			const comment = reviewForm.querySelector('#reviewComment').value.trim();

			if (!rating) {
				e.preventDefault();
				alert('Please select a rating.');
			} else if (comment === '') {
				e.preventDefault();
				alert('Please enter a review comment.');
			}
		});
	}
});