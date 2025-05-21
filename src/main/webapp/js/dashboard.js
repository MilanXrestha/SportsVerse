// Sidebar Toggle
let isSidebarCollapsed = false;

function toggleSidebar() {
	const sidebar = document.getElementById('sidebar');
	const mainContent = document.getElementById('mainContent');
	const sidebarIcon = document.getElementById('sidebarIcon');
	const toggleText = document.querySelector('.toggle-text');

	isSidebarCollapsed = !isSidebarCollapsed;

	sidebar.classList.toggle('collapsed', isSidebarCollapsed);
	sidebar.classList.toggle('active', !isSidebarCollapsed);
	mainContent.classList.toggle('expanded', !isSidebarCollapsed);

	if (isSidebarCollapsed) {
		sidebarIcon.classList.remove('fa-angles-left');
		sidebarIcon.classList.add('fa-angles-right');
		toggleText.style.display = 'none';
	} else {
		sidebarIcon.classList.remove('fa-angles-right');
		sidebarIcon.classList.add('fa-angles-left');
		toggleText.style.display = 'inline';
	}
}

// Theme Toggle
const themeToggle = document.getElementById('themeToggle');
const body = document.body;

function applyInitialTheme() {
	if (localStorage.getItem('theme') === 'dark') {
		body.classList.add('dark-mode');
		themeToggle.classList.replace('fa-moon', 'fa-sun');
	}
	return body.classList.contains('dark-mode');
}

// Profile Dropdown
let profileDropdownVisible = false;

function toggleProfileDropdown() {
	const dropdown = document.getElementById('profileDropdown');
	profileDropdownVisible = !profileDropdownVisible;
	dropdown.style.display = profileDropdownVisible ? 'block' : 'none';
}

// Section Navigation
function showSection(sectionId) {
	document.querySelectorAll('.section').forEach(section => section.classList.remove('active'));
	const targetSection = document.getElementById(sectionId);
	if (targetSection) {
		targetSection.classList.add('active');
	} else {
		console.error(`Section ${sectionId} not found`);
		return;
	}

	document.querySelectorAll('.sidebar-item').forEach(item => item.classList.remove('active'));
	const activeItem = document.querySelector(`.sidebar-item[data-section="${sectionId}"]`);
	if (activeItem) activeItem.classList.add('active');

	if (sectionId === 'dashboard') {
		console.log('Initializing dashboard stock charts...');
		initializeStockCharts();
	} else if (sectionId === 'analytics') {
		console.log('Initializing analytics charts...');
		initializeStockCharts();
		initializeBrandPieChart();
		initializeCategoryPieChart();
		initializeOrderStatusDoughnutChart();
		initializePaymentStatusDoughnutChart();
	}
}

function confirmLogout(event) {
	event.preventDefault();  // prevent default anchor behavior
	if (confirm('Are you sure you want to logout?')) {
		document.getElementById('logoutForm').submit();
	}
}

// Chart.js Initialization
let stockChartInstance = null;
let analyticsStockChartInstance = null;
let brandPieChartInstance = null;
let categoryPieChartInstance = null;
let orderStatusDoughnutChartInstance = null;
let paymentStatusDoughnutChartInstance = null;

// Initialize both stock charts for stockChart and analyticsStockChart
function initializeStockCharts(attempt = 1, maxAttempts = 5) {
	const stockCanvas = document.getElementById('stockChart');
	const analyticsCanvas = document.getElementById('analyticsStockChart');

	if (!stockCanvas && !analyticsCanvas) {
		console.error('Both stock chart canvases not found');
		return;
	}

	if (!window.productStockData || !window.productStockData.labels || !window.productStockData.quantities) {
		console.error('Product stock data is missing or invalid:', window.productStockData);
		return;
	}
	if (typeof Chart === 'undefined') {
		if (attempt <= maxAttempts) {
			console.warn(`Chart.js not loaded, retrying (${attempt}/${maxAttempts})...`);
			setTimeout(() => initializeStockCharts(attempt + 1, maxAttempts), 500);
			return;
		}
		console.error('Chart.js failed to load after maximum attempts.');
		return;
	}

	const isDarkMode = body.classList.contains('dark-mode');
	const chartColors = {
		bar: isDarkMode ? '#e0e0e0' : '#1c2526',
		barHover: isDarkMode ? '#b0b0b0' : '#3a4448',
		grid: isDarkMode ? '#4a4a4a' : '#e0e0e0',
		text: isDarkMode ? '#e0e0e0' : '#1c2526',
		background: isDarkMode ? '#2c2c2c' : '#ffffff'
	};

	// Chart configuration shared by both charts
	const chartConfig = {
		type: 'bar',
		data: {
			labels: window.productStockData.labels,
			datasets: [{
				label: 'Stock Quantity',
				data: window.productStockData.quantities,
				backgroundColor: chartColors.bar,
				hoverBackgroundColor: chartColors.barHover,
				borderWidth: 0
			}]
		},
		options: {
			responsive: true,
			maintainAspectRatio: false,
			plugins: {
				legend: { display: false },
				title: { display: false }
			},
			scales: {
				x: {
					grid: { display: false },
					ticks: {
						color: chartColors.text,
						font: { family: 'Rajdhani', size: 14, weight: '600' },
						maxRotation: 0,
						minRotation: 0
					}
				},
				y: {
					beginAtZero: true,
					grid: { color: chartColors.grid },
					ticks: {
						color: chartColors.text,
						font: { family: 'Rajdhani', size: 14, weight: '600' },
						callback: value => value.toLocaleString()
					}
				}
			}
		}
	};

	// Destroy previous instances if exist
	if (stockChartInstance) stockChartInstance.destroy();
	if (analyticsStockChartInstance) analyticsStockChartInstance.destroy();

	try {
		if (stockCanvas) {
			stockChartInstance = new Chart(stockCanvas, chartConfig);
			console.log('Dashboard stock chart initialized');
		}
		if (analyticsCanvas) {
			analyticsStockChartInstance = new Chart(analyticsCanvas, chartConfig);
			console.log('Analytics stock chart initialized');
		}
	} catch (error) {
		console.error('Failed to initialize stock charts:', error);
	}
}

function initializeBrandPieChart(attempt = 1, maxAttempts = 5) {
	const canvas = document.getElementById('brandPieChart');
	if (!canvas) {
		console.error('Brand pie chart canvas not found');
		return;
	}
	if (!window.analyticsData || !window.analyticsData.brands || !window.analyticsData.brands.labels || !window.analyticsData.brands.counts) {
		console.error('Brand analytics data is missing or invalid:', window.analyticsData?.brands);
		return;
	}
	if (typeof Chart === 'undefined') {
		if (attempt <= maxAttempts) {
			console.warn(`Chart.js not loaded for brand pie chart, retrying (${attempt}/${maxAttempts})...`);
			setTimeout(() => initializeBrandPieChart(attempt + 1, maxAttempts), 500);
			return;
		}
		console.error('Chart.js failed to load for brand pie chart after maximum attempts');
		return;
	}

	const isDarkMode = body.classList.contains('dark-mode');
	const chartColors = {
		text: isDarkMode ? '#e0e0e0' : '#1c2526',
		background: isDarkMode ? '#2c2c2c' : '#ffffff'
	};
	const pieColors = ['#007bff', '#28a745', '#dc3545', '#ffc107', '#17a2b8', '#6f42c1'];

	if (brandPieChartInstance) {
		brandPieChartInstance.destroy();
	}

	try {
		brandPieChartInstance = new Chart(canvas, {
			type: 'pie',
			data: {
				labels: window.analyticsData.brands.labels,
				datasets: [{
					data: window.analyticsData.brands.counts,
					backgroundColor: pieColors,
					hoverBackgroundColor: pieColors.map(color => color.replace('ff', 'cc')),
					borderWidth: 1,
					borderColor: chartColors.background
				}]
			},
			options: {
				responsive: true,
				maintainAspectRatio: false,
				plugins: {
					legend: {
						position: 'bottom',
						labels: {
							color: chartColors.text,
							font: { family: 'Rajdhani', size: 14, weight: '600' }
						}
					},
					title: { display: false }
				}
			}
		});
		console.log('Brand pie chart initialized successfully');
	} catch (error) {
		console.error('Failed to initialize brand pie chart:', error);
	}
}

function initializeCategoryPieChart(attempt = 1, maxAttempts = 5) {
	const canvas = document.getElementById('categoryPieChart');
	if (!canvas) {
		console.error('Category pie chart canvas not found');
		return;
	}
	if (!window.analyticsData || !window.analyticsData.categories || !window.analyticsData.categories.labels || !window.analyticsData.categories.counts) {
		console.error('Category analytics data is missing or invalid:', window.analyticsData?.categories);
		return;
	}
	if (typeof Chart === 'undefined') {
		if (attempt <= maxAttempts) {
			console.warn(`Chart.js not loaded for category pie chart, retrying (${attempt}/${maxAttempts})...`);
			setTimeout(() => initializeCategoryPieChart(attempt + 1, maxAttempts), 500);
			return;
		}
		console.error('Chart.js failed to load for category pie chart after maximum attempts');
		return;
	}

	const isDarkMode = body.classList.contains('dark-mode');
	const chartColors = {
		text: isDarkMode ? '#e0e0e0' : '#1c2526',
		background: isDarkMode ? '#2c2c2c' : '#ffffff'
	};
	const pieColors = ['#007bff', '#28a745', '#dc3545', '#ffc107', '#17a2b8', '#6f42c1'];

	if (categoryPieChartInstance) {
		categoryPieChartInstance.destroy();
	}

	try {
		categoryPieChartInstance = new Chart(canvas, {
			type: 'pie',
			data: {
				labels: window.analyticsData.categories.labels,
				datasets: [{
					data: window.analyticsData.categories.counts,
					backgroundColor: pieColors,
					hoverBackgroundColor: pieColors.map(color => color.replace('ff', 'cc')),
					borderWidth: 1,
					borderColor: chartColors.background
				}]
			},
			options: {
				responsive: true,
				maintainAspectRatio: false,
				plugins: {
					legend: {
						position: 'bottom',
						labels: {
							color: chartColors.text,
							font: { family: 'Rajdhani', size: 14, weight: '600' }
						}
					},
					title: { display: false }
				}
			}
		});
		console.log('Category pie chart initialized successfully');
	} catch (error) {
		console.error('Failed to initialize category pie chart:', error);
	}
}

function initializeOrderStatusDoughnutChart(attempt = 1, maxAttempts = 5) {
	const canvas = document.getElementById('orderStatusDoughnutChart');
	if (!canvas) {
		console.error('Order status doughnut chart canvas not found');
		return;
	}
	if (!window.analyticsData || !window.analyticsData.orderStatus || !window.analyticsData.orderStatus.labels || !window.analyticsData.orderStatus.counts) {
		console.error('Order status analytics data is missing or invalid:', window.analyticsData?.orderStatus);
		return;
	}
	if (typeof Chart === 'undefined') {
		if (attempt <= maxAttempts) {
			console.warn(`Chart.js not loaded for order status doughnut chart, retrying (${attempt}/${maxAttempts})...`);
			setTimeout(() => initializeOrderStatusDoughnutChart(attempt + 1, maxAttempts), 500);
			return;
		}
		console.error('Chart.js failed to load for order status doughnut chart after maximum attempts');
		return;
	}

	const isDarkMode = body.classList.contains('dark-mode');
	const chartColors = {
		text: isDarkMode ? '#e0e0e0' : '#1c2526',
		background: isDarkMode ? '#2c2c2c' : '#ffffff'
	};
	const doughnutColors = ['#ffc107', '#17a2b8', '#28a745', '#007bff', '#dc3545'];

	if (orderStatusDoughnutChartInstance) {
		orderStatusDoughnutChartInstance.destroy();
	}

	try {
		orderStatusDoughnutChartInstance = new Chart(canvas, {
			type: 'doughnut',
			data: {
				labels: window.analyticsData.orderStatus.labels,
				datasets: [{
					data: window.analyticsData.orderStatus.counts,
					backgroundColor: doughnutColors,
					hoverBackgroundColor: doughnutColors.map(color => color.replace('ff', 'cc')),
					borderWidth: 1,
					borderColor: chartColors.background
				}]
			},
			options: {
				responsive: true,
				maintainAspectRatio: false,
				plugins: {
					legend: {
						position: 'bottom',
						labels: {
							color: chartColors.text,
							font: { family: 'Rajdhani', size: 14, weight: '600' }
						}
					},
					title: { display: false }
				}
			}
		});
		console.log('Order status doughnut chart initialized successfully');
	} catch (error) {
		console.error('Failed to initialize order status doughnut chart:', error);
	}
}

function initializePaymentStatusDoughnutChart(attempt = 1, maxAttempts = 5) {
	const canvas = document.getElementById('paymentStatusDoughnutChart');
	if (!canvas) {
		console.error('Payment status doughnut chart canvas not found');
		return;
	}
	if (!window.analyticsData || !window.analyticsData.paymentStatus || !window.analyticsData.paymentStatus.labels || !window.analyticsData.paymentStatus.counts) {
		console.error('Payment status analytics data is missing or invalid:', window.analyticsData?.paymentStatus);
		return;
	}
	if (typeof Chart === 'undefined') {
		if (attempt <= maxAttempts) {
			console.warn(`Chart.js not loaded for payment status doughnut chart, retrying (${attempt}/${maxAttempts})...`);
			setTimeout(() => initializePaymentStatusDoughnutChart(attempt + 1, maxAttempts), 500);
			return;
		}
		console.error('Chart.js failed to load for payment status doughnut chart after maximum attempts');
		return;
	}

	const isDarkMode = body.classList.contains('dark-mode');
	const chartColors = {
		text: isDarkMode ? '#e0e0e0' : '#1c2526',
		background: isDarkMode ? '#2c2c2c' : '#ffffff'
	};
	const doughnutColors = ['#ffc107', '#28a745', '#dc3545'];

	if (paymentStatusDoughnutChartInstance) {
		paymentStatusDoughnutChartInstance.destroy();
	}

	try {
		paymentStatusDoughnutChartInstance = new Chart(canvas, {
			type: 'doughnut',
			data: {
				labels: window.analyticsData.paymentStatus.labels,
				datasets: [{
					data: window.analyticsData.paymentStatus.counts,
					backgroundColor: doughnutColors,
					hoverBackgroundColor: doughnutColors.map(color => color.replace('ff', 'cc')),
					borderWidth: 1,
					borderColor: chartColors.background
				}]
			},
			options: {
				responsive: true,
				maintainAspectRatio: false,
				plugins: {
					legend: {
						position: 'bottom',
						labels: {
							color: chartColors.text,
							font: { family: 'Rajdhani', size: 14, weight: '600' }
						}
					},
					title: { display: false }
				}
			}
		});
		console.log('Payment status doughnut chart initialized successfully');
	} catch (error) {
		console.error('Failed to initialize payment status doughnut chart:', error);
	}
}

function updateChartColors(chartInstance, chartType) {
	if (!chartInstance) {
		console.warn(`Chart instance for ${chartType} is null, cannot update colors`);
		return false;
	}

	const isDarkMode = body.classList.contains('dark-mode');
	const chartColors = {
		bar: isDarkMode ? '#e0e0e0' : '#1c2526',
		barHover: isDarkMode ? '#b0b0b0' : '#3a4448',
		grid: isDarkMode ? '#4a4a4a' : '#e0e0e0',
		text: isDarkMode ? '#e0e0e0' : '#1c2526',
		background: isDarkMode ? '#2c2c2c' : '#ffffff'
	};

	try {
		if (chartType === 'bar') {
			chartInstance.options.scales.x.ticks.color = chartColors.text;
			chartInstance.options.scales.y.ticks.color = chartColors.text;
			chartInstance.options.scales.y.grid.color = chartColors.grid;
			chartInstance.data.datasets[0].backgroundColor = chartColors.bar;
			chartInstance.data.datasets[0].hoverBackgroundColor = chartColors.barHover;
		} else {
			chartInstance.options.plugins.legend.labels.color = chartColors.text;
			chartInstance.data.datasets[0].borderColor = chartColors.background;
		}
		chartInstance.update();
		console.log(`Updated colors for ${chartType} chart`);
		return true;
	} catch (error) {
		console.error(`Failed to update colors for ${chartType} chart:`, error);
		return false;
	}
}

function updateStockChartsTheme() {
	updateChartColors(stockChartInstance, 'bar');
	updateChartColors(analyticsStockChartInstance, 'bar');
}

function updateAnalyticsChartsTheme() {
	updateChartColors(brandPieChartInstance, 'pie');
	updateChartColors(categoryPieChartInstance, 'pie');
	updateChartColors(orderStatusDoughnutChartInstance, 'doughnut');
	updateChartColors(paymentStatusDoughnutChartInstance, 'doughnut');
}

// Event Listeners
document.addEventListener('DOMContentLoaded', () => {
	applyInitialTheme();
	showSection(typeof activeSection !== 'undefined' ? activeSection : 'dashboard');

	const sidebarToggle = document.getElementById('sidebarToggle');
	if (sidebarToggle) sidebarToggle.addEventListener('click', toggleSidebar);

	document.querySelectorAll('.sidebar-item').forEach(item => {
		item.addEventListener('click', () => {
			const sectionId = item.getAttribute('data-section');
			showSection(sectionId);
		});
	});

	if (themeToggle) {
		themeToggle.addEventListener('click', () => {
			body.classList.toggle('dark-mode');
			localStorage.setItem('theme', body.classList.contains('dark-mode') ? 'dark' : 'light');
			themeToggle.classList.toggle('fa-sun', body.classList.contains('dark-mode'));
			themeToggle.classList.toggle('fa-moon', !body.classList.contains('dark-mode'));
			updateStockChartsTheme();
			updateAnalyticsChartsTheme();
		});
	}

	const profileButton = document.getElementById('profileButton');
	if (profileButton) profileButton.addEventListener('click', toggleProfileDropdown);

	document.addEventListener('click', (e) => {
		if (!e.target.closest('.profile') && profileDropdownVisible) {
			document.getElementById('profileDropdown').style.display = 'none';
			profileDropdownVisible = false;
		}
	});

	const settingsLink = document.getElementById('settingsLink');
	if (settingsLink) {
		settingsLink.addEventListener('click', (e) => {
			e.preventDefault();
			showSection('settings');
			toggleProfileDropdown();
		});
	}

	const alertPopup = document.querySelector('.alert-popup');
	if (alertPopup) {
		setTimeout(() => {
			alertPopup.style.display = 'none';
		}, 4000);
	}
});