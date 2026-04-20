(function () {
  "use strict";

  function initHero() {
    var hero = document.querySelector(".hero");
    if (!hero || hero.dataset.heroInit) return;
    hero.dataset.heroInit = "1";

    var slides = hero.querySelectorAll(".hero-slide");
    if (slides.length < 2) {
      // Only one image — just show it, no slideshow needed
      if (slides.length === 1) slides[0].classList.add("is-active");
      return;
    }

    var current = 0;
    var interval = 5000; // ms between slides
    var speed = 1200; // CSS transition duration in ms

    // Activate first slide immediately
    slides[current].classList.add("is-active");

    setInterval(function () {
      var prev = current;
      current = (current + 1) % slides.length;

      // Bring next slide on top with opacity 0, then fade it in
      slides[current].classList.add("is-next");

      // Force reflow so transition fires
      slides[current].getBoundingClientRect();

      slides[current].classList.add("is-active");
      slides[current].classList.remove("is-next");

      // After transition completes remove active from old slide
      setTimeout(function () {
        slides[prev].classList.remove("is-active");
      }, speed);
    }, interval);
  }

  document.addEventListener("DOMContentLoaded", initHero);
  window.addEventListener("load", initHero);
  setTimeout(initHero, 300);
  setTimeout(initHero, 1000);
})();
