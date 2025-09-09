
// Smooth scroll for navbar links
document.querySelectorAll('.nav-menu a').forEach(link => {
  link.addEventListener('click', function(e) {
    const target = document.querySelector(this.getAttribute('href'));
    if (target) {
      e.preventDefault();
      target.scrollIntoView({ behavior: 'smooth' });
    }
  });
});

// FAQ accordion toggle
document.querySelectorAll('.faq details').forEach(detail => {
  detail.addEventListener('click', () => {
    document.querySelectorAll('.faq details').forEach(d => {
      if (d !== detail) d.removeAttribute('open');
    });
  });
});

// Hero badge animation on scroll
window.addEventListener('scroll', () => {
  const badges = document.querySelectorAll('.hero-badges .badge');
  const windowHeight = window.innerHeight;
  badges.forEach(badge => {
    const badgeTop = badge.getBoundingClientRect().top;
    if (badgeTop < windowHeight - 50) {
      badge.style.transform = 'scale(1.05)';
      badge.style.transition = 'transform 0.4s ease';
    } else {
      badge.style.transform = 'scale(1)';
    }
  });
});
