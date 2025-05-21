document.addEventListener('DOMContentLoaded', () => {
    // Category Scroll
    const categoryContainer = document.querySelector('.category-scroll-container');
    const prevCategoryBtn = document.querySelector('.prev-category-btn');
    const nextCategoryBtn = document.querySelector('.next-category-btn');

    if (categoryContainer && prevCategoryBtn && nextCategoryBtn) {
        const cardWidth = 230; // Width of a category card (200px) + margin (15px each side)

        prevCategoryBtn.addEventListener('click', () => {
            categoryContainer.scrollBy({
                left: -cardWidth * 2,
                behavior: 'smooth'
            });
        });

        nextCategoryBtn.addEventListener('click', () => {
            categoryContainer.scrollBy({
                left: cardWidth * 2,
                behavior: 'smooth'
            });
        });

        // Touch/swipe support for category scroll
        let categoryTouchStartX = 0;
        let categoryTouchEndX = 0;

        categoryContainer.addEventListener('touchstart', (e) => {
            categoryTouchStartX = e.changedTouches[0].screenX;
        });

        categoryContainer.addEventListener('touchend', (e) => {
            categoryTouchEndX = e.changedTouches[0].screenX;
            if (categoryTouchStartX - categoryTouchEndX > 50) {
                categoryContainer.scrollBy({
                    left: cardWidth * 2,
                    behavior: 'smooth'
                });
            } else if (categoryTouchEndX - categoryTouchStartX > 50) {
                categoryContainer.scrollBy({
                    left: -cardWidth * 2,
                    behavior: 'smooth'
                });
            }
        });

        // Show/hide category buttons based on scroll position
        function updateCategoryButtons() {
            const isScrollable = categoryContainer.scrollWidth > categoryContainer.clientWidth;
            const scrollLeft = categoryContainer.scrollLeft;
            const scrollRight = categoryContainer.scrollWidth - (categoryContainer.clientWidth + scrollLeft);

            // Debug logs
            console.log(`isScrollable: ${isScrollable}, scrollLeft: ${scrollLeft}, scrollRight: ${scrollRight}`);

            prevCategoryBtn.style.display = isScrollable && Math.abs(scrollLeft) > 0.1 ? 'flex' : 'none';
            nextCategoryBtn.style.display = isScrollable && scrollRight > 0.1 ? 'flex' : 'none';
        }

        // Debounce scroll event to prevent excessive updates
        let scrollTimeout;
        categoryContainer.addEventListener('scroll', () => {
            clearTimeout(scrollTimeout);
            scrollTimeout = setTimeout(updateCategoryButtons, 100);
        });

        window.addEventListener('resize', updateCategoryButtons);
        updateCategoryButtons(); // Initial check
    }

    // Timer for Weekly Deal
    const timerValues = document.querySelectorAll('.timer-value');
    const endDate = new Date();
    endDate.setDate(endDate.getDate() + 7);

    function updateTimer() {
        const now = new Date();
        const timeLeft = endDate - now;

        if (timeLeft <= 0) {
            timerValues.forEach(val => val.textContent = '00');
            return;
        }

        const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
        const hours = Math.floor((timeLeft % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        const minutes = Math.floor((timeLeft % (1000 * 60 * 60)) / (1000 * 60));
        const seconds = Math.floor((timeLeft % (1000 * 60)) / 1000);

        timerValues.forEach(val => {
            const timeType = val.getAttribute('data-time');
            if (timeType === 'days') val.textContent = days.toString().padStart(2, '0');
            if (timeType === 'hours') val.textContent = hours.toString().padStart(2, '0');
            if (timeType === 'minutes') val.textContent = minutes.toString().padStart(2, '0');
            if (timeType === 'seconds') val.textContent = seconds.toString().padStart(2, '0');
        });
    }

    updateTimer();
    setInterval(updateTimer, 1000);
});