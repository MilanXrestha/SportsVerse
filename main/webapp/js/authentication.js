document.addEventListener('DOMContentLoaded', function() {
    // Input field focus/blur effects
    const inputs = document.querySelectorAll(".input-field");
    inputs.forEach((inp) => {
        inp.addEventListener("focus", () => {
            inp.classList.add("active");
        });
        inp.addEventListener("blur", () => {
            if (inp.value != "") return;
            inp.classList.remove("active");
        });
    });

    // Toggle between sign-up and sign-in modes
    const toggle_btn = document.querySelectorAll(".toggle");
    const main = document.querySelector("main");
    toggle_btn.forEach((btn) => {
        btn.addEventListener("click", (e) => {
            e.preventDefault();
            main.classList.toggle("sign-up-mode");
        });
    });

    // Carousel slider functionality
    const bullets = document.querySelectorAll(".bullets span");
    const images = document.querySelectorAll(".image");

    function moveSlider() {
        let activeBullet = document.querySelector(".bullets span.active");
        let index = activeBullet ? parseInt(activeBullet.dataset.value) : 1;
        index = index < bullets.length ? index + 1 : 1;

        let currentImage = document.querySelector(`.img-${index}`);
        images.forEach((img) => img.classList.remove("show"));
        currentImage.classList.add("show");

        const textSlider = document.querySelector(".text-group");
        textSlider.style.transform = `translateY(${-(index - 1) * 35}px)`;

        bullets.forEach((bull) => bull.classList.remove("active"));
        bullets[index - 1].classList.add("active");
    }

    // Auto-rotate slider every 3 seconds
    setInterval(moveSlider, 3000);
    bullets.forEach((bullet) => {
        bullet.addEventListener("click", moveSlider);
    });

    // Toggle password visibility
    document.querySelectorAll('.toggle-password').forEach(toggle => {
        toggle.addEventListener('click', () => {
            const passwordInput = toggle.closest('.password-wrap').querySelector('input[type="password"], input[type="text"]');
            const eyeIcon = toggle.querySelector('i');
            const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
            passwordInput.setAttribute('type', type);
            eyeIcon.classList.toggle('fa-eye');
            eyeIcon.classList.toggle('fa-eye-slash');
        });
    });

    // Registration form step navigation
    const registrationSteps = document.querySelectorAll('.sign-up-form .step');
    const stepIndicators = document.querySelectorAll('.sign-up-form .step-indicator');
    const progressFill = document.querySelector('.sign-up-form .progress-fill');
    const nextButtons = document.querySelectorAll('.sign-up-form .next-btn');
    const backButtons = document.querySelectorAll('.sign-up-form .back-btn');
    const form = document.querySelector('.sign-up-form');
    let currentStep = 0;

    // Initialize form
    showStep(currentStep);
    updateProgress();

    // Next button click handler
    nextButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            if (validateStep(currentStep)) {
                currentStep++;
                showStep(currentStep);
                updateProgress();
            }
        });
    });

    // Back button click handler
    backButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            e.preventDefault();
            currentStep--;
            showStep(currentStep);
            updateProgress();
        });
    });

    // Step indicator click handler
    stepIndicators.forEach(indicator => {
        indicator.addEventListener('click', function() {
            const stepToGo = parseInt(this.getAttribute('data-step')) - 1;
            if (stepToGo < currentStep) {
                currentStep = stepToGo;
                showStep(currentStep);
                updateProgress();
            }
        });
    });

    function showStep(stepNumber) {
        registrationSteps.forEach(step => step.classList.remove('active'));
        registrationSteps[stepNumber].classList.add('active');

        stepIndicators.forEach((indicator, index) => {
            indicator.classList.toggle('active', index <= stepNumber);
        });
    }

    function updateProgress() {
        const progressPercentage = (currentStep / (registrationSteps.length - 1)) * 100;
        progressFill.style.width = `${progressPercentage}%`;
    }

    function validateStep(step) {
        let isValid = true;
        const currentStepEl = registrationSteps[step];
        const inputs = currentStepEl.querySelectorAll('input[required], select[required]');

        inputs.forEach(input => {
            if (!input.value.trim()) {
                input.classList.add('error');
                isValid = false;
            } else {
                input.classList.remove('error');
            }
        });

        if (!isValid) {
            currentStepEl.classList.add('shake');
            setTimeout(() => {
                currentStepEl.classList.remove('shake');
            }, 500);
        }

        return isValid;
    }

    // Initialize any fields with values
    document.querySelectorAll('.input-field').forEach(input => {
        if (input.value) {
            input.classList.add('active');
        }
    });

    // Password strength meter
    const passwordInput = document.getElementById("password");
    const confirmPasswordInput = document.getElementById("confirmPassword");
    const strengthMeter = document.querySelector(".strength-meter");
    const strengthText = document.querySelector(".strength-text");
    const errorText = document.querySelector(".error-text");

    passwordInput.addEventListener("input", () => {
        const value = passwordInput.value;
        const strength = calculateStrength(value);
        updateStrengthMeter(strength);
        validatePasswordMatch();
    });

    confirmPasswordInput.addEventListener("input", validatePasswordMatch);

    function calculateStrength(password) {
        let score = 0;
        if (password.length > 6) score++;
        if (/[A-Z]/.test(password)) score++;
        if (/[0-9]/.test(password)) score++;
        if (/[^A-Za-z0-9]/.test(password)) score++;
        return score;
    }

    function updateStrengthMeter(score) {
        let color, label;
        switch (score) {
            case 0:
            case 1:
                color = "red";
                label = "Weak";
                break;
            case 2:
                color = "orange";
                label = "Fair";
                break;
            case 3:
                color = "yellow";
                label = "Good";
                break;
            case 4:
                color = "green";
                label = "Strong";
                break;
        }

        strengthMeter.style.width = `${(score / 4) * 100}%`;
        strengthMeter.style.backgroundColor = color;
		strengthText.textContent = `Password Strength: ${label}`;
    }

    function validatePasswordMatch() {
        const password = passwordInput.value;
        const confirmPassword = confirmPasswordInput.value;
        if (confirmPassword && password !== confirmPassword) {
            errorText.textContent = "Passwords do not match";
            errorText.classList.add("show");
            confirmPasswordInput.classList.add("error");
        } else {
            errorText.textContent = "";
            errorText.classList.remove("show");
            confirmPasswordInput.classList.remove("error");
        }
    }

    // Prevent form submission if passwords don't match
    form.addEventListener("submit", function(e) {
        if (passwordInput.value !== confirmPasswordInput.value) {
            e.preventDefault();
            errorText.textContent = "Passwords do not match";
            errorText.classList.add("show");
            confirmPasswordInput.classList.add("error");
            registrationSteps[currentStep].classList.add('shake');
            setTimeout(() => {
                registrationSteps[currentStep].classList.remove('shake');
            }, 500);
        }
    });
});