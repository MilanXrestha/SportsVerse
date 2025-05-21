document.addEventListener('DOMContentLoaded', () => {
	const form = document.getElementById('contact-form');
	const successMessage = document.querySelector('.success-message');
	const errorMessage = document.querySelector('.error-message');

	form.addEventListener('submit', (e) => {
		// Client-side validation
		const name = form.querySelector('#name').value.trim();
		const email = form.querySelector('#email').value.trim();
		const subject = form.querySelector('#subject').value.trim();
		const message = form.querySelector('#message').value.trim();

		if (!name || !email || !subject || !message) {
			e.preventDefault();
			if (errorMessage) {
				errorMessage.style.display = 'block';
			} else {
				const error = document.createElement('p');
				error.className = 'error-message';
				error.textContent = 'All fields are required.';
				form.insertBefore(error, form.firstChild);
			}
			return;
		}

		// Basic email format check
		const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		if (!emailRegex.test(email)) {
			e.preventDefault();
			if (errorMessage) {
				errorMessage.textContent = 'Please enter a valid email address.';
				errorMessage.style.display = 'block';
			} else {
				const error = document.createElement('p');
				error.className = 'error-message';
				error.textContent = 'Please enter a valid email address.';
				form.insertBefore(error, form.firstChild);
			}
		}
	});

	// Hide messages after 5 seconds
	if (successMessage) {
		setTimeout(() => {
			successMessage.style.display = 'none';
		}, 5000);
	}
	if (errorMessage) {
		setTimeout(() => {
			errorMessage.style.display = 'none';
		}, 5000);
	}
});