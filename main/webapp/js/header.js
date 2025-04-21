const themeToggleButton = document.querySelector('.theme-toggle');
const body = document.body;
const navbar = document.querySelector('.navbar');
let lastScroll = 0;

if (localStorage.getItem('theme') === 'dark') {
	body.classList.add('dark-mode');
	body.classList.remove('light-mode');
	themeToggleButton.innerHTML = '<i class="fas fa-sun"></i>';
}

themeToggleButton.addEventListener('click', () => {
	body.classList.toggle('dark-mode');
	body.classList.toggle('light-mode');

	if (body.classList.contains('dark-mode')) {
		localStorage.setItem('theme', 'dark');
		themeToggleButton.innerHTML = '<i class="fas fa-sun"></i>';
	} else {
		localStorage.setItem('theme', 'light');
		themeToggleButton.innerHTML = '<i class="fas fa-moon"></i>';
	}
});



window.addEventListener('scroll', () => {
	const currentScroll = window.pageYOffset;

	if (currentScroll <= 0) {
		// At top of page - show everything
		navbar.classList.remove('hide-top');
		return;
	}

	if (currentScroll > lastScroll) {
		// Scrolling down - hide top bar
		navbar.classList.add('hide-top');
	} else {
		// Scrolling up - show top bar
		navbar.classList.remove('hide-top');
	}

	lastScroll = currentScroll;
});

// Account dropdown functionality
document.addEventListener('DOMContentLoaded', function() {
	const accountDropdowns = document.querySelectorAll('.account-dropdown');

	accountDropdowns.forEach(dropdown => {
		const btn = dropdown.querySelector('.account-btn');
		const content = dropdown.querySelector('.dropdown-content');

		// Toggle dropdown on click
		btn.addEventListener('click', (e) => {
			e.stopPropagation();
			const isShowing = content.style.display === 'block';
			document.querySelectorAll('.dropdown-content').forEach(d => {
				d.style.display = 'none';
			});
			content.style.display = isShowing ? 'none' : 'block';
		});
	});

	// Close dropdown when clicking outside
	document.addEventListener('click', function() {
		document.querySelectorAll('.dropdown-content').forEach(content => {
			content.style.display = 'none';
		});
	});
});