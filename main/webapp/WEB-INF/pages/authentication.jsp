<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Login And Register | SportsVerse</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/authentication.css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css" />
</head>
<body>
	<main>
		<div class="box">
			<div class="inner-box">
				<div class="forms-wrap">
					<!-- Sign In Form -->
					<form action="login" method="post" class="sign-in-form">
						<div class="logo">
							<span>S</span><span>p</span><span><i
								class="fas fa-basketball football"></i></span><span>r</span><span>t</span><span>s</span><span>V</span><span>E</span><span>R</span><span>S</span><span>E</span>
						</div>

						<div class="heading">
							<h2>Welcome</h2>
							<h6>Not registered yet?</h6>
							<a href="#" class="toggle">Sign up</a>
						</div>

						<div class="actual-form">
							<div class="input-wrap">
								<input type="text" name="username" class="input-field"
									value="${savedUsername != null ? savedUsername : ''}"
									autocomplete="off" required /> <label>Username</label>
							</div>

							<div class="input-wrap password-wrap">
								<input type="password" name="password" class="input-field"
									autocomplete="off" required /> <label>Password</label> <span
									class="toggle-password"><i class="fa-solid fa-eye"></i></span>
							</div>

							<div class="options">
								<input type="checkbox" id="remember" name="remember"
									${savedUsername != null ? "checked" : ""} /> <label
									for="remember">Remember me</label>
							</div>

							<button type="submit" class="sign-btn">Sign In</button>

							<div class="google-btn">
								<img
									src="https://developers.google.com/identity/images/g-logo.png"
									alt="Google Logo" /> Sign in with Google
							</div>

							<p class="text">
								Trouble logging in? <a href="#">Click here for help</a>
							</p>
						</div>
					</form>

					<!-- 4-Step Sign Up Form -->
					<form action="${pageContext.request.contextPath}/register"
						method="POST" autocomplete="off" class="sign-up-form"
						id="registrationForm">
						<div class="heading">
							<h2>Create Account</h2>
							<h6>Already have an account?</h6>
							<a href="#" class="toggle">Sign in</a>
						</div>

						<div class="actual-form">
							<!-- Step Indicators -->
							<div class="step-indicators">
								<div class="step-indicator active" data-step="1">1</div>
								<div class="step-indicator" data-step="2">2</div>
								<div class="step-indicator" data-step="3">3</div>
								<div class="step-indicator" data-step="4">4</div>
								<div class="progress-bar">
									<div class="progress-fill"></div>
								</div>
							</div>

							<!-- Step 1: Personal Info -->
							<div class="step step-1 active">
								<div class="input-wrap">
									<input type="text" name="firstName" class="input-field"
										value="${param.firstName}" autocomplete="off" required /> <label>First
										Name</label>
								</div>
								<div class="input-wrap">
									<input type="text" name="lastName" class="input-field"
										value="${param.lastName}" autocomplete="off" required /> <label>Last
										Name</label>
								</div>
								<div class="input-wrap">
									<input type="email" name="email" class="input-field"
										value="${param.email}" autocomplete="off" required /> <label>Email</label>
								</div>
								<div class="input-wrap">
									<input type="text" name="username" class="input-field"
										value="${param.username}" autocomplete="off" required /> <label>Username</label>
								</div>
								<div class="step-buttons">
									<button type="button" class="sign-btn next-btn">Next</button>
								</div>
							</div>

							<!-- Step 2: Contact & Referral -->
							<div class="step step-2">
								<div class="input-wrap">
									<input type="tel" name="phone" class="input-field"
										value="${param.phone}" autocomplete="off" required /> <label>Phone
										Number</label>
								</div>
								<div class="input-wrap select-wrap">
									<select name="gender" class="input-field" required>
										<option value="" disabled selected>Select Gender</option>
										<option value="male"
											${param.gender == 'male' ? 'selected' : ''}>Male</option>
										<option value="female"
											${param.gender == 'female' ? 'selected' : ''}>Female</option>
										<option value="other"
											${param.gender == 'other' ? 'selected' : ''}>Other</option>
										<option value="prefer-not-to-say"
											${param.gender == 'prefer-not-to-say' ? 'selected' : ''}>Prefer
											not to say</option>
									</select> <label style="font-size: 14px; top: -10px;">Gender</label>
								</div>
								<div class="input-wrap select-wrap">
									<select name="contactMethod" class="input-field" required>
										<option value="" disabled selected>Select contact
											method</option>
										<option value="email"
											${param.contactMethod == 'email' ? 'selected' : ''}>Email</option>
										<option value="phone"
											${param.contactMethod == 'phone' ? 'selected' : ''}>Phone</option>
									</select> <label style="font-size: 14px; top: -10px;">Preferred
										Contact Method</label>
								</div>
								<div class="input-wrap select-wrap">
									<select name="referralSource" class="input-field" required>
										<option value="" disabled selected>Where did you hear
											about us?</option>
										<option value="social_media"
											${param.referralSource == 'social_media' ? 'selected' : ''}>Social
											Media</option>
										<option value="friends_family"
											${param.referralSource == 'friends_family' ? 'selected' : ''}>Friends/Family</option>
										<option value="search_engine"
											${param.referralSource == 'search_engine' ? 'selected' : ''}>Search
											Engine</option>
										<option value="advertisement"
											${param.referralSource == 'advertisement' ? 'selected' : ''}>Advertisement</option>
										<option value="other"
											${param.referralSource == 'other' ? 'selected' : ''}>Other</option>
									</select> <label style="font-size: 14px; top: -10px;">Referral
										Source</label>
								</div>
								<div class="step-buttons">
									<button type="button" class="sign-btn back-btn">Back</button>
									<button type="button" class="sign-btn next-btn">Next</button>
								</div>
							</div>

							<!-- Step 3: Address Information -->
							<div class="step step-3">
								<div class="input-wrap">
									<input type="text" name="streetAddress" class="input-field"
										value="${param.streetAddress}" autocomplete="off" required />
									<label>Street Address</label>
								</div>
								<div class="input-wrap">
									<input type="text" name="city" class="input-field"
										value="${param.city}" autocomplete="off" required /> <label>City</label>
								</div>
								<div class="input-wrap select-wrap">
									<select name="nepalState" class="input-field" required>
										<option value="" disabled selected>Select your state</option>
										<option value="Koshi"
											${param.nepalState == 'Koshi' ? 'selected' : ''}>Koshi</option>
										<option value="Madhesh"
											${param.nepalState == 'Madhesh' ? 'selected' : ''}>Madhesh</option>
										<option value="Bagmati"
											${param.nepalState == 'Bagmati' ? 'selected' : ''}>Bagmati</option>
										<option value="Gandaki"
											${param.nepalState == 'Gandaki' ? 'selected' : ''}>Gandaki</option>
										<option value="Lumbini"
											${param.nepalState == 'Lumbini' ? 'selected' : ''}>Lumbini</option>
										<option value="Karnali"
											${param.nepalState == 'Karnali' ? 'selected' : ''}>Karnali</option>
										<option value="Sudurpaschim"
											${param.nepalState == 'Sudurpaschim' ? 'selected' : ''}>Sudurpaschim</option>
									</select> <label style="font-size: 14px; top: -10px;">State/Province</label>
								</div>
								<div class="input-wrap select-wrap">
									<select name="sameAddress" class="input-field" required>
										<option value="" disabled selected>Is shipping
											address the same?</option>
										<option value="yes"
											${param.sameAddress == 'yes' ? 'selected' : ''}>Yes</option>
										<option value="no"
											${param.sameAddress == 'no' ? 'selected' : ''}>No</option>
									</select> <label style="font-size: 14px; top: -10px;">Shipping
										Address</label>
								</div>
								<div class="step-buttons">
									<button type="button" class="sign-btn back-btn">Back</button>
									<button type="button" class="sign-btn next-btn">Next</button>
								</div>
							</div>

							<!-- Step 4: Account Security -->
							<div class="step step-4">


								<small class="strength-text">Password Strength: </small>
								<div class="password-strength">
									<div class="strength-meter"></div>
								</div>


								<div class="input-wrap password-wrap">
									<input type="password" name="password" id="password"
										class="input-field" autocomplete="new-password" required /> <label>Password</label>
									<span class="toggle-password"><i class="fa-solid fa-eye"></i></span>
								</div>
								<div class="input-wrap password-wrap">
									<input type="password" name="confirmPassword"
										id="confirmPassword" class="input-field"
										autocomplete="new-password" required /> <label>Confirm
										Password</label> <span class="toggle-password"><i
										class="fa-solid fa-eye"></i></span> <small class="error-text"></small>
								</div>
								<div class="options">
									<input type="checkbox" id="terms" name="terms" required /> <label
										for="terms">I agree to the <a href="#">Terms of
											Service</a> and <a href="#">Privacy Policy</a></label>
								</div>
								<div class="step-buttons">
									<button type="button" class="sign-btn back-btn">Back</button>
									<button type="submit" class="sign-btn">Complete
										Registration</button>
								</div>
							</div>
						</div>
					</form>
				</div>

				<!-- Carousel Section -->
				<div class="carousel">
					<div class="images-wrapper">
						<img
							src="${pageContext.request.contextPath}/resources/images/loginSlider/login_slider_one.jpg"
							class="image img-1 show" alt="Sports Gear" /> <img
							src="${pageContext.request.contextPath}/resources/images/loginSlider/login_slider_two.jpg"
							class="image img-2" alt="Fitness Equipment" /> <img
							src="${pageContext.request.contextPath}/resources/images/loginSlider/login_slider_three.jpg"
							class="image img-3" alt="Team Sports" />
					</div>

					<div class="text-slider">
						<div class="text-wrap">
							<div class="text-group">
								<h2>Welcome to Your Sports Universe</h2>
								<h2>Everything Sports. All in One Place</h2>
								<h2>Top Brands. Best Deals. Fast Delivery</h2>
							</div>
						</div>

						<div class="bullets">
							<span class="active" data-value="1"></span> <span data-value="2"></span>
							<span data-value="3"></span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</main>

	<!-- Modal for displaying error or status messages -->
	<div id="popup"
		class="popup ${not empty error || not empty status ? 'show' : ''}">
		<div class="popup-content">
			<c:if test="${not empty error}">
				<p class="error-msg">${error}</p>
			</c:if>

			<c:if test="${not empty status}">
				<p class="success-msg">
					<c:choose>
						<c:when test="${status == 'success'}">Registration successful! 🎉 Welcome to SPORTSVERSE! You can now log in and explore the platform.</c:when>
						<c:when test="${status == 'emailExists'}">The email you provided already exists in our system. Please use a different email address or <a
								href="/reset-password">reset your password</a> if you've forgotten it.</c:when>
						<c:when test="${status == 'usernameExists'}">The username you selected is already taken. Please choose another unique username.</c:when>
						<c:when test="${status == 'invalidFirstName'}">The first name you entered is invalid. Please ensure it only contains letters and doesn't have any special characters.</c:when>
						<c:when test="${status == 'invalidLastName'}">The last name you entered is invalid. Please ensure it only contains letters and doesn't have any special characters.</c:when>
						<c:when test="${status == 'invalidEmail'}">The email address you provided is invalid. Please enter a valid email address (e.g., user@example.com).</c:when>
						<c:when test="${status == 'invalidUsername'}">The username must be alphanumeric and start with a letter. Please make sure your username follows this format.</c:when>
						<c:when test="${status == 'invalidPhone'}">The phone number you entered is invalid. Please ensure it follows the correct format (e.g., 9843941637).</c:when>
						<c:when test="${status == 'invalidGender'}">Please select a valid gender from the available options.</c:when>
						<c:when test="${status == 'invalidContactMethod'}">Please select your preferred contact method, such as email or phone.</c:when>
						<c:when test="${status == 'invalidReferralSource'}">Please select a referral source. This helps us understand how you found SPORTSVERSE.</c:when>
						<c:when test="${status == 'invalidStreetAddress'}">The street address cannot be empty. Please provide a valid street address for shipping.</c:when>
						<c:when test="${status == 'invalidCity'}">The city field cannot be left empty. Please enter your city.</c:when>
						<c:when test="${status == 'invalidState'}">Please select your state from the dropdown list. If your state is not listed, contact support.</c:when>
						<c:when test="${status == 'invalidSameAddress'}">Please select whether your shipping address is the same as your permanent address.</c:when>
						<c:when test="${status == 'invalidPassword'}">Your password is too weak. Please choose a stronger password with a mix of letters, numbers, and special characters.</c:when>
						<c:when test="${status == 'passwordMismatch'}">The passwords you entered do not match. Please ensure both password fields are identical.</c:when>
						<c:when test="${status == 'failed'}">Something went wrong while registering your account. Please try again later or contact support if the problem persists.</c:when>
						<c:when test="${status == 'error'}">An internal server error occurred. Please try again later, and if the issue persists, contact support.</c:when>
						<c:otherwise>Unknown status: ${status}. Please contact support for further assistance.</c:otherwise>
					</c:choose>
				</p>
			</c:if>

			<button
				onclick="document.getElementById('popup').style.display='none';">Close</button>
		</div>
	</div>

	<script src="${pageContext.request.contextPath}/js/authentication.js"></script>
</body>
</html>