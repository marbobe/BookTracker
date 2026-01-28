document.addEventListener('DOMContentLoaded', () => {
    const stars = document.querySelectorAll('#star-container .star');
    const scoreInput = document.getElementById('score');

    if (!stars.length || !scoreInput) return;

    stars.forEach(star => {
        star.addEventListener('click', () => {
            const val = star.getAttribute('data-value');
            scoreInput.value = val;

            stars.forEach(s => {
                if (s.getAttribute('data-value') <= val) {
                    s.classList.add('selected');
                } else {
                    s.classList.remove('selected');
                }
            });
        });
    });
});
