<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en" class="light-mode">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>SportsVerse - My Profile</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/header.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/profile.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/footer.css">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
<link
	href="https://fonts.googleapis.com/css2?family=Audiowide&family=Rajdhani:wght@400;700&display=swap"
	rel="stylesheet">

<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&family=Montserrat:wght@600;700&display=swap"
	rel="stylesheet">
</head>
<body>
	<!-- Block Admin Access -->
	<c:if test="${user.role == 'admin'}">
		<c:redirect url="/admin/dashboard" />
	</c:if>

	<%@ include file="header.jsp"%>

	<div class="profile-section">
		<!-- Section Header -->
		<div class="section-header">
			<h2>MY PROFILE</h2>
			<div class="greeting">Hello, ${user.username}</div>
			<div class="section-divider"></div>
		</div>

		<!-- Personal Information -->
		<div class="profile-card">
			<div class="card-header">
				<i class="fas fa-user"></i>
				<h3>Personal Information</h3>
			</div>
			<c:choose>
				<c:when test="${verifiedAction == 'personal'}">
					<form action="${pageContext.request.contextPath}/profile/update"
						method="post">
						<input type="hidden" name="action" value="personal">
						<div class="form-grid">
							<div class="form-group">
								<label>First Name</label> <input type="text" name="firstName"
									value="${user.firstName}" required>
							</div>
							<div class="form-group">
								<label>Last Name</label> <input type="text" name="lastName"
									value="${user.lastName}" required>
							</div>
							<div class="form-group">
								<label>Email</label> <input type="email" name="email"
									value="${user.email}" required>
							</div>
							<div class="form-group">
								<label>Phone</label> <input type="tel" name="phone"
									value="${user.phone}">
							</div>
							<div class="form-group">
								<label>Gender</label> <select name="gender">
									<option value="Male" ${user.gender == 'Male' ? 'selected' : ''}>Male</option>
									<option value="Female"
										${user.gender == 'Female' ? 'selected' : ''}>Female</option>
									<option value="Other"
										${user.gender == 'Other' ? 'selected' : ''}>Other</option>
								</select>
							</div>
							<div class="form-group">
								<label>Contact Preference</label> <select
									name="contactPreference">
									<option value="Email"
										${user.contactPreference == 'Email' ? 'selected' : ''}>Email</option>
									<option value="Phone"
										${user.contactPreference == 'Phone' ? 'selected' : ''}>Phone</option>
								</select>
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">
								<i class="fas fa-save"></i> Save Changes
							</button>
							<form action="${pageContext.request.contextPath}/profile/cancel"
								method="post">
								<input type="hidden" name="action" value="personal">
								<button type="submit" class="btn btn-secondary">
									<i class="fas fa-times"></i> Cancel
								</button>
							</form>
						</div>
					</form>
				</c:when>
				<c:otherwise>
					<div class="profile-info">
						<div class="info-row">
							<span class="info-label"><i class="fas fa-user"></i> First
								Name</span> <span class="info-value">${user.firstName}</span>
						</div>
						<div class="info-row">
							<span class="info-label"><i class="fas fa-user"></i> Last
								Name</span> <span class="info-value">${user.lastName}</span>
						</div>
						<div class="info-row">
							<span class="info-label"><i class="fas fa-envelope"></i>
								Email</span> <span class="info-value">${user.email}</span>
						</div>
						<div class="info-row">
							<span class="info-label"><i class="fas fa-phone"></i>
								Phone</span> <span class="info-value">${user.phone}</span>
						</div>
						<div class="info-row">
							<span class="info-label"><i class="fas fa-venus-mars"></i>
								Gender</span> <span class="info-value">${user.gender}</span>
						</div>
						<div class="info-row">
							<span class="info-label"><i class="fas fa-comment"></i>
								Contact Preference</span> <span class="info-value">${user.contactPreference}</span>
						</div>
					</div>
					<button onclick="showVerifyModal('personal')" class="btn btn-edit">
						<i class="fas fa-edit"></i> Edit Information
					</button>
				</c:otherwise>
			</c:choose>
		</div>

		<!-- Permanent Address -->
		<div class="profile-card">
			<div class="card-header">
				<i class="fas fa-home"></i>
				<h3>Permanent Address</h3>
			</div>
			<c:choose>
				<c:when test="${verifiedAction == 'permanent'}">
					<form action="${pageContext.request.contextPath}/profile/update"
						method="post">
						<input type="hidden" name="action" value="permanent">
						<div class="form-grid">
							<div class="form-group">
								<label>Street Address</label> <input type="text"
									name="streetAddress" value="${permanentAddress.streetAddress}"
									required>
							</div>
							<div class="form-group">
								<label>City</label> <input type="text" name="city"
									value="${permanentAddress.city}" required>
							</div>
							<div class="form-group">
								<label>State</label> <input type="text" name="nepalState"
									value="${permanentAddress.nepalState}" required>
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">
								<i class="fas fa-save"></i> Save Address
							</button>
							<form action="${pageContext.request.contextPath}/profile/cancel"
								method="post">
								<input type="hidden" name="action" value="permanent">
								<button type="submit" class="btn btn-secondary">
									<i class="fas fa-times"></i> Cancel
								</button>
							</form>
						</div>
					</form>
				</c:when>
				<c:otherwise>
					<div class="profile-info">
						<div class="info-row">
							<span class="info-label"><i class="fas fa-road"></i>
								Street Address</span> <span class="info-value">${permanentAddress.streetAddress}</span>
						</div>
						<div class="info-row">
							<span class="info-label"><i class="fas fa-city"></i> City</span>
							<span class="info-value">${permanentAddress.city}</span>
						</div>
						<div class="info-row">
							<span class="info-label"><i class="fas fa-map"></i> State</span>
							<span class="info-value">${permanentAddress.nepalState}</span>
						</div>
					</div>
					<button onclick="showVerifyModal('permanent')" class="btn btn-edit">
						<i class="fas fa-edit"></i> Edit Address
					</button>
				</c:otherwise>
			</c:choose>
		</div>

		<!-- Shipping Address -->
		<div class="profile-card">
			<div class="card-header">
				<i class="fas fa-truck"></i>
				<h3>Shipping Address</h3>
			</div>
			<c:choose>
				<c:when test="${verifiedAction == 'shipping'}">
					<form action="${pageContext.request.contextPath}/profile/update"
						method="post">
						<input type="hidden" name="action" value="shipping">
						<div class="form-grid">
							<div class="form-group">
								<label>Street Address</label> <input type="text"
									name="streetAddress" value="${shippingAddress.streetAddress}"
									required>
							</div>
							<div class="form-group">
								<label>City</label> <input type="text" name="city"
									value="${shippingAddress.city}" required>
							</div>
							<div class="form-group">
								<label>State</label> <input type="text" name="nepalState"
									value="${shippingAddress.nepalState}" required>
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">
								<i class="fas fa-save"></i> Save Address
							</button>
							<button type="button" class="btn btn-secondary"
								onclick="copyPermanentAddress()">
								<i class="fas fa-copy"></i> Copy Permanent Address
							</button>
							<form action="${pageContext.request.contextPath}/profile/cancel"
								method="post">
								<input type="hidden" name="action" value="shipping">
								<button type="submit" class="btn btn-secondary">
									<i class="fas fa-times"></i> Cancel
								</button>
							</form>
						</div>
					</form>
				</c:when>
				<c:otherwise>
					<div class="profile-info">
						<div class="info-row">
							<span class="info-label"><i class="fas fa-road"></i>
								Street Address</span> <span class="info-value">${shippingAddress.streetAddress}</span>
						</div>
						<div class="info-row">
							<span class="info-label"><i class="fas fa-city"></i> City</span>
							<span class="info-value">${shippingAddress.city}</span>
						</div>
						<div class="info-row">
							<span class="info-label"><i class="fas fa-map"></i> State</span>
							<span class="info-value">${shippingAddress.nepalState}</span>
						</div>
					</div>
					<button onclick="showVerifyModal('shipping')" class="btn btn-edit">
						<i class="fas fa-edit"></i> Edit Address
					</button>
				</c:otherwise>
			</c:choose>
		</div>

		<!-- Password -->
		<div class="profile-card">
			<div class="card-header">
				<i class="fas fa-lock"></i>
				<h3>Change Password</h3>
			</div>
			<c:choose>
				<c:when test="${verifiedAction == 'password'}">
					<form action="${pageContext.request.contextPath}/profile/update"
						method="post">
						<input type="hidden" name="action" value="password">
						<div class="form-grid">
							<div class="form-group">
								<label>New Password</label> <input type="password"
									name="newPassword" id="newPassword" required
									oninput="checkPasswordStrength()">
								<div class="password-strength">
									<div class="strength-bar">
										<div id="strengthBar"></div>
									</div>
									<span id="strengthText"></span>
								</div>
							</div>
							<div class="form-group">
								<label>Confirm Password</label> <input type="password"
									name="confirmPassword" id="confirmPassword" required
									oninput="checkPasswordMatch()">
								<div class="password-match" id="passwordMatch"></div>
							</div>
						</div>
						<div class="form-actions">
							<button type="submit" class="btn btn-primary">
								<i class="fas fa-save"></i> Change Password
							</button>
							<form action="${pageContext.request.contextPath}/profile/cancel"
								method="post">
								<input type="hidden" name="action" value="password">
								<button type="submit" class="btn btn-secondary">
									<i class="fas fa-times"></i> Cancel
								</button>
							</form>
						</div>
					</form>
				</c:when>
				<c:otherwise>
					<p class="password-info">For security reasons, you'll need to
						verify your current password to change it.</p>
					<button onclick="showVerifyModal('password')" class="btn btn-edit">
						<i class="fas fa-key"></i> Change Password
					</button>
				</c:otherwise>
			</c:choose>
		</div>

		<!-- Delete Account -->
		<div class="profile-card danger-zone">
			<div class="card-header">
				<i class="fas fa-exclamation-triangle"></i>
				<h3>Delete Account</h3>
			</div>
			<c:choose>
				<c:when test="${verifiedAction == 'delete'}">
					<form action="${pageContext.request.contextPath}/profile/delete"
						method="post">
						<p class="delete-warning">
							<strong>Warning:</strong> This action is irreversible and will
							permanently delete your account, including all personal
							information, order history, saved addresses, and preferences.
							This cannot be undone.
						</p>
						<div class="form-actions">
							<button type="submit" class="btn btn-danger">
								<i class="fas fa-trash"></i> Confirm Delete
							</button>
							<form action="${pageContext.request.contextPath}/profile/cancel"
								method="post">
								<input type="hidden" name="action" value="delete">
								<button type="submit" class="btn btn-secondary">
									<i class="fas fa-times"></i> Cancel
								</button>
							</form>
						</div>
					</form>
				</c:when>
				<c:otherwise>
					<p class="delete-warning">
						<strong>Warning:</strong> Deleting your account is permanent and
						will erase all your data, including personal details, order
						history, and saved addresses. This action cannot be undone.
					</p>
					<button onclick="showVerifyModal('delete')" class="btn btn-danger">
						<i class="fas fa-trash"></i> Delete Account
					</button>
				</c:otherwise>
			</c:choose>
		</div>

		<!-- Verification Modal -->
		<div id="verifyModal" class="modal">
			<div class="modal-content">
				<span class="close" onclick="hideVerifyModal()">×</span>
				<div class="modal-header">
					<i class="fas fa-lock"></i>
					<h3>Verify Password</h3>
				</div>
				<form id="verifyForm"
					action="${pageContext.request.contextPath}/profile/verify"
					method="post">
					<input type="hidden" name="action" id="verifyAction">
					<div class="form-group">
						<label>Enter your password to continue</label> <input
							type="password" name="password" placeholder="Current password"
							required>
					</div>
					<div class="form-actions">
						<button type="submit" class="btn btn-primary">
							<i class="fas fa-check"></i> Verify
						</button>
						<button type="button" onclick="hideVerifyModal()"
							class="btn btn-secondary">
							<i class="fas fa-times"></i> Cancel
						</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Success/Error Messages -->
	<c:if test="${not empty successMessage}">
		<div class="success-message">${successMessage}</div>
	</c:if>
	<c:if test="${not empty errorMessage}">
		<div class="error-message">${errorMessage}</div>
	</c:if>
	<c:if test="${not empty validationErrors}">
		<div class="error-message">
			<c:forEach var="error" items="${validationErrors}">
                    ${error}<br>
			</c:forEach>
		</div>
	</c:if>

	<%@ include file="footer.jsp"%>
	<script src="${pageContext.request.contextPath}/js/header.js"></script>
	<script>
        // Load theme from localStorage
        if (localStorage.getItem('theme') === 'dark') {
            document.documentElement.classList.remove('light-mode');
            document.documentElement.classList.add('dark-mode');
        } else {
            document.documentElement.classList.remove('dark-mode');
            document.documentElement.classList.add('light-mode');
        }

        // Modal Functions
        function showVerifyModal(action) {
            document.getElementById('verifyAction').value = action;
            document.getElementById('verifyModal').classList.add('show');
            setTimeout(() => {
                const section = document.getElementById(action + '-section');
                if (section) {
                    section.scrollIntoView({ behavior: 'smooth', block: 'start' });
                }
            }, 500);
        }

        function hideVerifyModal() {
            document.getElementById('verifyModal').classList.remove('show');
        }

        // Copy Permanent Address to Shipping Address
        function copyPermanentAddress() {
            document.querySelector('input[name="streetAddress"]').value = '${fn:escapeXml(permanentAddress.streetAddress)}';
            document.querySelector('input[name="city"]').value = '${fn:escapeXml(permanentAddress.city)}';
            document.querySelector('input[name="nepalState"]').value = '${fn:escapeXml(permanentAddress.nepalState)}';
        }

        // Password Strength Meter
        function checkPasswordStrength() {
            const password = document.getElementById('newPassword').value;
            const strengthBar = document.getElementById('strengthBar');
            const strengthText = document.getElementById('strengthText');
            const regex = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;

            if (password.length === 0) {
                strengthBar.className = '';
                strengthText.textContent = '';
            } else if (password.length < 8) {
                strengthBar.className = 'strength-weak';
                strengthText.textContent = 'Weak';
            } else if (regex.test(password)) {
                strengthBar.className = 'strength-strong';
                strengthText.textContent = 'Strong';
            } else {
                strengthBar.className = 'strength-medium';
                strengthText.textContent = 'Medium';
            }
        }

        // Password Match Validation
        function checkPasswordMatch() {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            const passwordMatch = document.getElementById('passwordMatch');

            if (confirmPassword.length === 0) {
                passwordMatch.textContent = '';
                passwordMatch.className = 'password-match';
            } else if (newPassword === confirmPassword) {
                passwordMatch.textContent = 'Passwords match';
                passwordMatch.className = 'password-match match';
            } else {
                passwordMatch.textContent = 'Passwords do not match';
                passwordMatch.className = 'password-match mismatch';
            }
        }
    </script>
</body>
</html>