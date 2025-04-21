<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SportsVerse Admin Dashboard</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<style>
/* Admin Dashboard Styles */
:root {
	--sidebar-width: 250px;
	--sidebar-collapsed-width: 80px;
	--header-height: 70px;
	--primary-color: #4361ee;
	--secondary-color: #3f37c9;
	--success-color: #4cc9f0;
	--danger-color: #f72585;
	--warning-color: #f8961e;
	--info-color: #4895ef;
	--light-color: #f8f9fa;
	--dark-color: #212529;
	--gray-color: #6c757d;
	--border-radius: 8px;
	--box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
	--transition: all 0.3s ease;
}

/* Base Styles */
body {
	font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
	background-color: #f5f7fa;
	margin: 0;
	padding: 0;
	color: #333;
	line-height: 1.6;
}

.admin-container {
	display: flex;
	min-height: 100vh;
}

/* Sidebar Styles */
.admin-sidebar {
	width: var(--sidebar-width);
	background: linear-gradient(180deg, var(--primary-color),
		var(--secondary-color));
	color: white;
	transition: var(--transition);
	display: flex;
	flex-direction: column;
	position: fixed;
	height: 100vh;
	z-index: 100;
	box-shadow: 2px 0 10px rgba(0, 0, 0, 0.1);
}

.sidebar-header {
	padding: 20px;
	text-align: center;
	border-bottom: 1px solid rgba(255, 255, 255, 0.1);
}

.admin-logo {
	display: flex;
	align-items: center;
	justify-content: center;
	font-size: 1.5rem;
	font-weight: 700;
	margin-bottom: 10px;
}

.admin-logo i {
	font-size: 2rem;
	margin-right: 10px;
}

.admin-role {
	font-size: 0.8rem;
	background: rgba(255, 255, 255, 0.2);
	padding: 5px 10px;
	border-radius: 20px;
	display: inline-block;
}

.sidebar-nav ul {
	list-style: none;
	padding: 0;
	margin: 20px 0;
}

.sidebar-nav li {
	margin: 5px 0;
}

.sidebar-nav a {
	display: flex;
	align-items: center;
	padding: 12px 20px;
	color: rgba(255, 255, 255, 0.8);
	text-decoration: none;
	transition: var(--transition);
	border-left: 3px solid transparent;
}

.sidebar-nav a i {
	margin-right: 10px;
	font-size: 1.1rem;
	width: 20px;
	text-align: center;
}

.sidebar-nav a:hover {
	background: rgba(255, 255, 255, 0.1);
	color: white;
	border-left: 3px solid white;
}

.sidebar-nav li.active a {
	background: rgba(255, 255, 255, 0.2);
	color: white;
	border-left: 3px solid white;
}

.sidebar-footer {
	margin-top: auto;
	padding: 20px;
}

.logout-btn {
	display: flex;
	align-items: center;
	width: 100%;
	padding: 10px 15px;
	background: rgba(255, 255, 255, 0.1);
	color: white;
	border: none;
	border-radius: var(--border-radius);
	cursor: pointer;
	transition: var(--transition);
	font-size: 0.9rem;
}

.logout-btn i {
	margin-right: 10px;
}

.logout-btn:hover {
	background: rgba(255, 255, 255, 0.2);
}

/* Main Content Styles */
.admin-main {
	flex: 1;
	margin-left: var(--sidebar-width);
	transition: var(--transition);
}

.admin-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 0 20px;
	height: var(--header-height);
	background: white;
	box-shadow: var(--box-shadow);
	position: sticky;
	top: 0;
	z-index: 90;
}

.header-left {
	display: flex;
	align-items: center;
}

.menu-toggle {
	background: none;
	border: none;
	font-size: 1.2rem;
	margin-right: 20px;
	cursor: pointer;
	color: var(--gray-color);
	display: none;
}

.admin-header h1 {
	font-size: 1.5rem;
	margin: 0;
	color: var(--dark-color);
}

.admin-profile {
	display: flex;
	align-items: center;
}

.admin-profile img {
	width: 40px;
	height: 40px;
	border-radius: 50%;
	margin-right: 10px;
	object-fit: cover;
}

.admin-profile span {
	font-weight: 500;
}

/* Dashboard Content Styles */
.dashboard-content {
	padding: 20px;
}

.stats-grid {
	display: grid;
	grid-template-columns: repeat(auto-fit, minmax(240px, 1fr));
	gap: 20px;
	margin-bottom: 30px;
}

.stat-card {
	background: white;
	border-radius: var(--border-radius);
	padding: 20px;
	box-shadow: var(--box-shadow);
	display: flex;
	align-items: center;
	transition: var(--transition);
}

.stat-card:hover {
	transform: translateY(-5px);
}

.stat-icon {
	width: 60px;
	height: 60px;
	border-radius: 50%;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-right: 15px;
	color: white;
	font-size: 1.5rem;
}

.bg-blue {
	background: linear-gradient(135deg, #4361ee, #3f37c9);
}

.bg-green {
	background: linear-gradient(135deg, #4cc9f0, #4895ef);
}

.bg-orange {
	background: linear-gradient(135deg, #f8961e, #f3722c);
}

.bg-purple {
	background: linear-gradient(135deg, #7209b7, #560bad);
}

.stat-info h3 {
	margin: 0;
	font-size: 1.8rem;
	color: var(--dark-color);
}

.stat-info p {
	margin: 5px 0 0;
	color: var(--gray-color);
	font-size: 0.9rem;
}

.content-grid {
	display: grid;
	grid-template-columns: 2fr 1fr;
	gap: 20px;
	margin-bottom: 30px;
}

.chart-container, .recent-orders {
	background: white;
	border-radius: var(--border-radius);
	padding: 20px;
	box-shadow: var(--box-shadow);
}

.chart-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 20px;
}

.chart-header h3 {
	margin: 0;
	font-size: 1.2rem;
}

.chart-filter {
	padding: 8px 12px;
	border-radius: var(--border-radius);
	border: 1px solid #ddd;
	background: white;
	font-size: 0.9rem;
}

.chart-placeholder {
	height: 300px;
	position: relative;
}

.orders-header {
	display: flex;
	justify-content: space-between;
	align-items: center;
	margin-bottom: 20px;
}

.orders-header h3 {
	margin: 0;
	font-size: 1.2rem;
}

.view-all {
	color: var(--primary-color);
	text-decoration: none;
	font-size: 0.9rem;
}

.view-all:hover {
	text-decoration: underline;
}

.orders-table {
	overflow-x: auto;
}

.orders-table table {
	width: 100%;
	border-collapse: collapse;
}

.orders-table th, .orders-table td {
	padding: 12px 15px;
	text-align: left;
	border-bottom: 1px solid #eee;
}

.orders-table th {
	font-weight: 600;
	color: var(--gray-color);
	font-size: 0.9rem;
	text-transform: uppercase;
}

.status {
	padding: 5px 10px;
	border-radius: 20px;
	font-size: 0.8rem;
	font-weight: 500;
}

.status.delivered {
	background: #e6f7ee;
	color: #00a854;
}

.status.shipped {
	background: #e6f7f7;
	color: #00a8a8;
}

.status.processing {
	background: #fff7e6;
	color: #fa8c16;
}

.status.cancelled {
	background: #fff1f0;
	color: #f5222d;
}

.action-btn {
	background: none;
	border: none;
	color: var(--gray-color);
	cursor: pointer;
	font-size: 1rem;
}

.widgets-grid {
	display: grid;
	grid-template-columns: 1fr 1fr;
	gap: 20px;
}

.popular-products, .activity-feed {
	background: white;
	border-radius: var(--border-radius);
	padding: 20px;
	box-shadow: var(--box-shadow);
}

.popular-products h3, .activity-feed h3 {
	margin-top: 0;
	margin-bottom: 20px;
	font-size: 1.2rem;
}

.product-list {
	display: flex;
	flex-direction: column;
	gap: 15px;
}

.product-item {
	display: flex;
	align-items: center;
	padding-bottom: 15px;
	border-bottom: 1px solid #eee;
}

.product-item:last-child {
	border-bottom: none;
	padding-bottom: 0;
}

.product-item img {
	width: 50px;
	height: 50px;
	border-radius: 8px;
	object-fit: cover;
	margin-right: 15px;
}

.product-info h4 {
	margin: 0;
	font-size: 0.95rem;
}

.product-info p {
	margin: 5px 0 0;
	font-size: 0.8rem;
	color: var(--gray-color);
}

.product-rating {
	margin-left: auto;
	color: #ffc107;
	font-weight: 500;
	font-size: 0.9rem;
}

.activity-list {
	display: flex;
	flex-direction: column;
	gap: 15px;
}

.activity-item {
	display: flex;
	align-items: flex-start;
	padding-bottom: 15px;
	border-bottom: 1px solid #eee;
}

.activity-item:last-child {
	border-bottom: none;
	padding-bottom: 0;
}

.activity-icon {
	width: 36px;
	height: 36px;
	border-radius: 50%;
	background: #f0f2f5;
	display: flex;
	align-items: center;
	justify-content: center;
	margin-right: 15px;
	color: var(--primary-color);
	font-size: 0.9rem;
}

.activity-content p {
	margin: 0;
	font-size: 0.9rem;
}

.activity-time {
	display: block;
	font-size: 0.8rem;
	color: var(--gray-color);
	margin-top: 3px;
}

/* Responsive Styles */
@media ( max-width : 1200px) {
	.content-grid {
		grid-template-columns: 1fr;
	}
	.widgets-grid {
		grid-template-columns: 1fr;
	}
}

@media ( max-width : 992px) {
	.admin-sidebar {
		width: var(--sidebar-collapsed-width);
		overflow: hidden;
	}
	.admin-sidebar .admin-logo span, .admin-sidebar .admin-role,
		.admin-sidebar .sidebar-nav a span, .admin-sidebar .logout-btn span {
		display: none;
	}
	.admin-sidebar .sidebar-nav a i, .admin-sidebar .logout-btn i {
		margin-right: 0;
		font-size: 1.3rem;
	}
	.admin-sidebar .sidebar-nav a {
		justify-content: center;
		padding: 15px 0;
	}
	.admin-main {
		margin-left: var(--sidebar-collapsed-width);
	}
	.menu-toggle {
		display: block;
	}
}

@media ( max-width : 768px) {
	.stats-grid {
		grid-template-columns: 1fr 1fr;
	}
	.admin-profile span {
		display: none;
	}
}

@media ( max-width : 576px) {
	.stats-grid {
		grid-template-columns: 1fr;
	}
	.admin-header {
		padding: 0 10px;
	}
	.dashboard-content {
		padding: 15px 10px;
	}
}
</style>
</head>
<body class="admin-body">
	<div class="admin-container">
		<!-- Sidebar Navigation -->
		<aside class="admin-sidebar">
			<div class="sidebar-header">
				<div class="admin-logo">
					<i class="fas fa-running"></i> <span>SportsVerse</span>
				</div>
				<div class="admin-role">Administrator</div>
			</div>

			<nav class="sidebar-nav">
				<ul>
					<li class="active"><a href="#"> <i
							class="fas fa-tachometer-alt"></i> <span>Dashboard</span></a></li>
					<li><a href="#"> <i class="fas fa-users"></i> <span>Users</span></a></li>
					<li><a href="#"> <i class="fas fa-tshirt"></i> <span>Products</span></a></li>
					<li><a href="#"> <i class="fas fa-shopping-cart"></i> <span>Orders</span></a></li>
					<li><a href="#"> <i class="fas fa-chart-line"></i> <span>Analytics</span></a></li>
					<li><a href="#"> <i class="fas fa-cog"></i> <span>Settings</span></a></li>
				</ul>
			</nav>

			<div class="sidebar-footer">
				<form action="${pageContext.request.contextPath}/logout"
					method="post" id="logoutForm">
					<button type="submit" class="logout-btn">
						<i class="fas fa-sign-out-alt"></i> <span>Logout</span>
					</button>
				</form>
			</div>
		</aside>

		<!-- Main Content -->
		<main class="admin-main">
			<!-- Top Navigation Bar -->
			<header class="admin-header">
				<div class="header-left">
					<button class="menu-toggle">
						<i class="fas fa-bars"></i>
					</button>
					<h1>Dashboard</h1>
				</div>
				<div class="header-right">
					<div class="admin-profile">
						<img
							src="https://ui-avatars.com/api/?name=Admin+User&background=random"
							alt="Admin"> <span>Admin User</span>
					</div>
				</div>
			</header>

			<!-- Dashboard Content -->
			<div class="dashboard-content">
				<!-- Stats Cards -->
				<div class="stats-grid">
					<div class="stat-card">
						<div class="stat-icon bg-blue">
							<i class="fas fa-users"></i>
						</div>
						<div class="stat-info">
							<h3>1,254</h3>
							<p>Total Users</p>
						</div>
					</div>
					<div class="stat-card">
						<div class="stat-icon bg-green">
							<i class="fas fa-tshirt"></i>
						</div>
						<div class="stat-info">
							<h3>568</h3>
							<p>Products</p>
						</div>
					</div>
					<div class="stat-card">
						<div class="stat-icon bg-orange">
							<i class="fas fa-shopping-cart"></i>
						</div>
						<div class="stat-info">
							<h3>2,430</h3>
							<p>Total Orders</p>
						</div>
					</div>
					<div class="stat-card">
						<div class="stat-icon bg-purple">
							<i class="fas fa-dollar-sign"></i>
						</div>
						<div class="stat-info">
							<h3>$48,290</h3>
							<p>Revenue</p>
						</div>
					</div>
				</div>

				<!-- Charts and Tables -->
				<div class="content-grid">
					<div class="chart-container">
						<div class="chart-header">
							<h3>Sales Overview</h3>
							<select class="chart-filter">
								<option>Last 7 Days</option>
								<option>Last Month</option>
								<option>Last Year</option>
							</select>
						</div>
						<div class="chart-placeholder">
							<canvas id="salesChart"></canvas>
						</div>
					</div>

					<div class="recent-orders">
						<div class="orders-header">
							<h3>Recent Orders</h3>
							<a href="#" class="view-all">View All</a>
						</div>
						<div class="orders-table">
							<table>
								<thead>
									<tr>
										<th>Order ID</th>
										<th>Customer</th>
										<th>Date</th>
										<th>Amount</th>
										<th>Status</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td>#SV-1001</td>
										<td>John Smith</td>
										<td>Jun 15, 2023</td>
										<td>$129.99</td>
										<td><span class="status delivered">Delivered</span></td>
										<td><button class="action-btn">
												<i class="fas fa-ellipsis-v"></i>
											</button></td>
									</tr>
									<tr>
										<td>#SV-1002</td>
										<td>Sarah Johnson</td>
										<td>Jun 14, 2023</td>
										<td>$89.50</td>
										<td><span class="status shipped">Shipped</span></td>
										<td><button class="action-btn">
												<i class="fas fa-ellipsis-v"></i>
											</button></td>
									</tr>
									<tr>
										<td>#SV-1003</td>
										<td>Michael Brown</td>
										<td>Jun 13, 2023</td>
										<td>$210.00</td>
										<td><span class="status processing">Processing</span></td>
										<td><button class="action-btn">
												<i class="fas fa-ellipsis-v"></i>
											</button></td>
									</tr>
									<tr>
										<td>#SV-1004</td>
										<td>Emily Davis</td>
										<td>Jun 12, 2023</td>
										<td>$65.25</td>
										<td><span class="status cancelled">Cancelled</span></td>
										<td><button class="action-btn">
												<i class="fas fa-ellipsis-v"></i>
											</button></td>
									</tr>
									<tr>
										<td>#SV-1005</td>
										<td>Robert Wilson</td>
										<td>Jun 11, 2023</td>
										<td>$154.99</td>
										<td><span class="status delivered">Delivered</span></td>
										<td><button class="action-btn">
												<i class="fas fa-ellipsis-v"></i>
											</button></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>

				<!-- Additional Widgets -->
				<div class="widgets-grid">
					<div class="popular-products">
						<h3>Popular Products</h3>
						<div class="product-list">
							<div class="product-item">
								<img src="https://via.placeholder.com/60" alt="Product">
								<div class="product-info">
									<h4>Running Shoes Pro</h4>
									<p>45 sales this week</p>
								</div>
								<div class="product-rating">
									<i class="fas fa-star"></i> 4.8
								</div>
							</div>
							<div class="product-item">
								<img src="https://via.placeholder.com/60" alt="Product">
								<div class="product-info">
									<h4>Yoga Mat Premium</h4>
									<p>32 sales this week</p>
								</div>
								<div class="product-rating">
									<i class="fas fa-star"></i> 4.9
								</div>
							</div>
							<div class="product-item">
								<img src="https://via.placeholder.com/60" alt="Product">
								<div class="product-info">
									<h4>Training Dumbbells</h4>
									<p>28 sales this week</p>
								</div>
								<div class="product-rating">
									<i class="fas fa-star"></i> 4.7
								</div>
							</div>
						</div>
					</div>

					<div class="activity-feed">
						<h3>Recent Activity</h3>
						<div class="activity-list">
							<div class="activity-item">
								<div class="activity-icon">
									<i class="fas fa-shopping-cart"></i>
								</div>
								<div class="activity-content">
									<p>New order #SV-1006 placed</p>
									<span class="activity-time">2 minutes ago</span>
								</div>
							</div>
							<div class="activity-item">
								<div class="activity-icon">
									<i class="fas fa-user-plus"></i>
								</div>
								<div class="activity-content">
									<p>New user registered</p>
									<span class="activity-time">15 minutes ago</span>
								</div>
							</div>
							<div class="activity-item">
								<div class="activity-icon">
									<i class="fas fa-tshirt"></i>
								</div>
								<div class="activity-content">
									<p>New product added</p>
									<span class="activity-time">1 hour ago</span>
								</div>
							</div>
							<div class="activity-item">
								<div class="activity-icon">
									<i class="fas fa-check-circle"></i>
								</div>
								<div class="activity-content">
									<p>Order #SV-1001 completed</p>
									<span class="activity-time">3 hours ago</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>

	<script>
		document.addEventListener('DOMContentLoaded', function() {
			// Toggle sidebar
			const menuToggle = document.querySelector('.menu-toggle');
			const sidebar = document.querySelector('.admin-sidebar');
			const mainContent = document.querySelector('.admin-main');

			if (menuToggle && sidebar) {
				menuToggle.addEventListener('click', function() {
					sidebar.classList.toggle('collapsed');
					mainContent.classList.toggle('sidebar-collapsed');
				});
			}

			// Initialize charts
			const salesChartCtx = document.getElementById('salesChart');
			if (salesChartCtx) {
				const salesChart = new Chart(salesChartCtx,
						{
							type : 'line',
							data : {
								labels : [ 'Jan', 'Feb', 'Mar', 'Apr', 'May',
										'Jun' ],
								datasets : [ {
									label : 'Sales',
									data : [ 12000, 19000, 15000, 18000, 22000,
											25000 ],
									backgroundColor : 'rgba(67, 97, 238, 0.2)',
									borderColor : 'rgba(67, 97, 238, 1)',
									borderWidth : 2,
									tension : 0.4,
									fill : true
								} ]
							},
							options : {
								responsive : true,
								maintainAspectRatio : false,
								plugins : {
									legend : {
										display : false
									}
								},
								scales : {
									y : {
										beginAtZero : true,
										grid : {
											color : 'rgba(0, 0, 0, 0.05)'
										}
									},
									x : {
										grid : {
											display : false
										}
									}
								}
							}
						});
			}

			// Logout confirmation
			const logoutForm = document.getElementById('logoutForm');
			if (logoutForm) {
				logoutForm.addEventListener('submit', function(e) {
					if (!confirm('Are you sure you want to logout?')) {
						e.preventDefault();
					}
				});
			}
			// Mobile sidebar toggle
			function handleResize() {
				if (window.innerWidth <= 992) {
					sidebar.classList.add('collapsed');
					mainContent.classList.add('sidebar-collapsed');
				} else {
					sidebar.classList.remove('collapsed');
					mainContent.classList.remove('sidebar-collapsed');
				}
			}

			window.addEventListener('resize', handleResize);
			handleResize();
		});
	</script>
</body>
</html>