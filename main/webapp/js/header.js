const themeToggleButton = document.querySelector('.theme-toggle');
const body = document.body;
const navbar = document.querySelector('.navbar');
let lastScroll = 0;

// Initialize theme
if (localStorage.getItem('theme') === 'dark') {
	body.classList.add('dark-mode');
	body.classList.remove('light-mode');
	themeToggleButton.innerHTML = '<i class="fas fa-sun"></i>';
} else {
	body.classList.add('light-mode');
	body.classList.remove('dark-mode');
	themeToggleButton.innerHTML = '<i class="fas fa-moon"></i>';
}

// Theme toggle
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

	// Reset dropdown visibility to rely on CSS hover
	document.querySelectorAll('.hamburger-dropdown, .dropdown-content, .subcategory-list').forEach(d => {
		d.style.display = '';
	});

	// Reset hamburger icon
	const hamburgerIcon = document.querySelector('.hamburger i');
	if (hamburgerIcon) {
		hamburgerIcon.classList.remove('fa-angle-up');
		hamburgerIcon.classList.add('fa-bars');
	}
});

// Scroll behavior
window.addEventListener('scroll', () => {
	const currentScroll = window.pageYOffset;

	if (currentScroll <= 0) {
		navbar.classList.remove('hide-top');
		return;
	}

	if (currentScroll > lastScroll) {
		navbar.classList.add('hide-top');
	} else {
		navbar.classList.remove('hide-top');
	}

	lastScroll = currentScroll;
});

// Dropdown functionality (account and hamburger)
document.addEventListener('DOMContentLoaded', function() {
	// Account dropdown
	const accountDropdowns = document.querySelectorAll('.account-dropdown');
	accountDropdowns.forEach(dropdown => {
		const btn = dropdown.querySelector('.account-btn');
		const content = dropdown.querySelector('.dropdown-content');

		btn.addEventListener('click', (e) => {
			e.stopPropagation();
			const isShowing = content.style.display === 'block';
			document.querySelectorAll('.dropdown-content, .hamburger-dropdown, .subcategory-list').forEach(d => {
				d.style.display = '';
			});
			content.style.display = isShowing ? '' : 'block';

			// Reset hamburger icon
			const hamburgerIcon = document.querySelector('.hamburger i');
			if (hamburgerIcon) {
				hamburgerIcon.classList.remove('fa-angle-up');
				hamburgerIcon.classList.add('fa-bars');
			}
		});
	});

	// Hamburger dropdown
	const hamburgerMenu = document.querySelector('.hamburger-menu');
	const hamburgerBtn = hamburgerMenu?.querySelector('.hamburger');
	const hamburgerDropdown = hamburgerMenu?.querySelector('.hamburger-dropdown');
	const hamburgerIcon = hamburgerBtn?.querySelector('i');

	if (hamburgerBtn && hamburgerDropdown && hamburgerIcon) {
		// Click to toggle dropdown
		hamburgerBtn.addEventListener('click', (e) => {
			e.stopPropagation();
			const isShowing = hamburgerDropdown.style.display === 'block';
			document.querySelectorAll('.dropdown-content, .hamburger-dropdown, .subcategory-list').forEach(d => {
				d.style.display = '';
			});
			hamburgerDropdown.style.display = isShowing ? '' : 'block';

			// Toggle icon
			if (isShowing) {
				hamburgerIcon.classList.remove('fa-angle-up');
				hamburgerIcon.classList.add('fa-bars');
			} else {
				hamburgerIcon.classList.remove('fa-bars');
				hamburgerIcon.classList.add('fa-angle-up');
			}
		});

		// Hover to show dropdown
		hamburgerMenu.addEventListener('mouseenter', () => {
			hamburgerDropdown.style.display = 'block';
			hamburgerIcon.classList.remove('fa-bars');
			hamburgerIcon.classList.add('fa-angle-up');
		});

		// Reset on mouseleave
		hamburgerMenu.addEventListener('mouseleave', () => {
			hamburgerDropdown.style.display = '';
			hamburgerIcon.classList.remove('fa-angle-up');
			hamburgerIcon.classList.add('fa-bars');
			document.querySelectorAll('.subcategory-list').forEach(d => {
				d.style.display = '';
			});
		});
	}

	// Close all dropdowns on click outside
	document.addEventListener('click', (e) => {
		if (!e.target.closest('.hamburger-menu') && !e.target.closest('.account-dropdown')) {
			document.querySelectorAll('.dropdown-content, .hamburger-dropdown, .subcategory-list').forEach(content => {
				content.style.display = '';
			});
			// Reset hamburger icon
			if (hamburgerIcon) {
				hamburgerIcon.classList.remove('fa-angle-up');
				hamburgerIcon.classList.add('fa-bars');
			}
		}
	});

	// Search
	const searchInput = document.getElementById("search-input");
	const clearBtn = document.getElementById("clear-btn");

	searchInput.addEventListener("input", () => {
		clearBtn.style.display = searchInput.value ? "block" : "none";
	});

	clearBtn.addEventListener("click", () => {
		searchInput.value = "";
		clearBtn.style.display = "none";
		searchInput.focus();
	});
});