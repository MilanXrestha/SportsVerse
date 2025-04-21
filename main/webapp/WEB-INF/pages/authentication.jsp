<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Login & Register | SportsVerse</title>

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
						<section class="logo">
							<div class="top">SportsVerse</div>
							<div class="bottom" aria-hidden="true">SportsVerse</div>
						</section>

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
						<section class="logo">
							<div class="top">SportsVerse</div>
							<div class="bottom" aria-hidden="true">SportsVerse</div>
						</section>

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
								<div class="input-row">
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

							<!-- Step 2: Contact & Location -->
							<div class="step step-2">
								<div class="input-wrap">
									<input type="tel" name="phone" class="input-field"
										value="${param.phone}" autocomplete="off" required /> <label>Phone
										Number</label>
								</div>

								<div class="input-wrap">
									<label style="font-size: 14px; color: #aaa; top: 10px;">Gender</label>
									<select name="gender" class="input-field" required
										style="top: 18px;">
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
									</select>
								</div>

								<div class="input-wrap">
									<label style="font-size: 14px; color: #aaa; top: 30px;">Preferred
										Contact Method</label> <select name="contactMethod"
										class="input-field" required style="top: 38px;">
										<option value="" disabled selected>Select contact
											method</option>
										<option value="email"
											${param.contactMethod == 'email' ? 'selected' : ''}>Email</option>
										<option value="phone"
											${param.contactMethod == 'phone' ? 'selected' : ''}>Phone</option>
									</select>
								</div>

								<div class="step-buttons" style="margin-top: 50px;">
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

								<div class="input-row">
									<div class="input-wrap">
										<input type="text" name="city" class="input-field"
											value="${param.city}" autocomplete="off" required /> <label>City</label>
									</div>
								</div>

								<div class="input-wrap">
									<label style="font-size: 14px; color: #aaa; top: 10px;">State/Province</label>
									<select name="nepalState" class="input-field" required
										style="top: 20px;">
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
									</select>
								</div>

								<div class="step-buttons">
									<button type="button" class="sign-btn back-btn">Back</button>
									<button type="button" class="sign-btn next-btn">Next</button>
								</div>
							</div>

							<!-- Step 4: Account Security -->
							<div class="step step-4">
								<!-- Password Strength Meter -->
								<div class="password-strength"
									style="margin-bottom: 20px; padding-bottom: 20px;">
									<div class="strength-meter"
										style="height: 6px; background: #eee; border-radius: 4px;"></div>
									<small class="strength-text" style="display: block;"></small>
								</div>

								<!-- Password Field -->
								<div class="input-wrap password-wrap"
									style="margin-bottom: 20px;">
									<input type="password" name="password" id="password"
										class="input-field" autocomplete="new-password" required /> <label>Password</label>
									<span class="toggle-password"><i class="fa-solid fa-eye"></i></span>
								</div>

								<!-- Confirm Password Field -->
								<div class="input-wrap password-wrap"
									style="margin-bottom: 20px;">
									<input type="password" name="confirmPassword"
										class="input-field" autocomplete="new-password" required /> <label>Confirm
										Password</label> <span class="toggle-password"><i
										class="fa-solid fa-eye"></i></span>
								</div>

								<!-- Terms and Conditions Checkbox -->
								<div class="options" style="margin-top: 40px;">
									<input type="checkbox" id="terms" name="terms" required /> <label
										for="terms"> I agree to the <a href="#">Terms of
											Service</a> and <a href="#">Privacy Policy</a>
									</label>
								</div>

								<!-- Navigation Buttons -->
								<div class="step-buttons" style="margin-top: 10px;">
									<button type="button" class="sign-btn back-btn"
										style="margin-right: 10px;">Back</button>
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

	<%-- Modal for displaying error or status messages --%>
	<div id="popup"
		class="popup ${not empty error || not empty status ? 'show' : ''}">
		<div class="popup-content">
			<c:if test="${not empty error}">
				<p class="error-msg">${error}</p>
			</c:if>

			<c:if test="${not empty status}">
				<p class="success-msg">
					<c:choose>
						<c:when test="${status == 'success'}">Registration successful! 🎉 Welcome to SPORTSVERSE!</c:when>
						<c:when test="${status == 'emailExists'}">Email already exists.</c:when>
						<c:when test="${status == 'usernameExists'}">Username already exists.</c:when>
						<c:when test="${status == 'invalidFirstName'}">First name is invalid.</c:when>
						<c:when test="${status == 'invalidLastName'}">Last name is invalid.</c:when>
						<c:when test="${status == 'invalidEmail'}">Invalid email address.</c:when>
						<c:when test="${status == 'invalidUsername'}">Username must be alphanumeric and start with a letter.</c:when>
						<c:when test="${status == 'invalidPhone'}">Invalid phone number.</c:when>
						<c:when test="${status == 'invalidGender'}">Please select a valid gender.</c:when>
						<c:when test="${status == 'invalidContactMethod'}">Please select a preferred contact method.</c:when>
						<c:when test="${status == 'invalidStreetAddress'}">Street address cannot be empty.</c:when>
						<c:when test="${status == 'invalidCity'}">City cannot be empty.</c:when>
						<c:when test="${status == 'invalidState'}">Please select your state.</c:when>
						<c:when test="${status == 'invalidPassword'}">Password must be strong.</c:when>
						<c:when test="${status == 'failed'}">Something went wrong while registering.</c:when>
						<c:when test="${status == 'error'}">Internal server error occurred.</c:when>
						<c:otherwise>Unknown status: ${status}</c:otherwise>
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
