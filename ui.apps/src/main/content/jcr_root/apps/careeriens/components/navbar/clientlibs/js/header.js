(function () {
  "use strict";

  function initNavbar() {
    var navbar = document.querySelector(".navbar");
    if (!navbar || navbar.dataset.navInit) return;
    navbar.dataset.navInit = "1";

    var items = navbar.querySelectorAll(".nav-links > .nav-item");

    items.forEach(function (item) {
      var panel = item.querySelector(".dropdown-panel");
      if (!panel) return;
      var hasAnySub = panel.querySelector(".sub-panel");
      if (hasAnySub) {
        panel.classList.add("has-sub-panels");
      }
    });

    function closeAll() {
      items.forEach(function (i) {
        i.classList.remove("is-open");
        var t = i.querySelector(".has-dropdown");
        if (t) t.setAttribute("aria-expanded", "false");
      });
    }

    items.forEach(function (item) {
      var trigger = item.querySelector(".has-dropdown");
      if (!trigger) return;

      item.addEventListener("mouseenter", function () {
        closeAll();
        item.classList.add("is-open");
        trigger.setAttribute("aria-expanded", "true");
      });

      item.addEventListener("mouseleave", function () {
        item.classList.remove("is-open");
        trigger.setAttribute("aria-expanded", "false");
      });

      trigger.addEventListener("click", function () {
        var wasOpen = item.classList.contains("is-open");
        closeAll();
        if (!wasOpen) {
          item.classList.add("is-open");
          trigger.setAttribute("aria-expanded", "true");
        }
      });
    });

    document.addEventListener("click", function (e) {
      if (!navbar.contains(e.target)) closeAll();
    });
  }

  document.addEventListener("DOMContentLoaded", initNavbar);
  window.addEventListener("load", initNavbar);
  setTimeout(initNavbar, 300);
  setTimeout(initNavbar, 1000);
})();
