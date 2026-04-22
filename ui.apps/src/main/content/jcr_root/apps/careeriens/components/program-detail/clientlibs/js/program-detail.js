(function () {
  function init() {
    var wrappers = document.querySelectorAll(".pd-wrapper");

    wrappers.forEach(function (wrapper) {
      var btns = wrapper.querySelectorAll(".pd-tabnav-btn");
      var panels = wrapper.querySelectorAll(".pd-panel");

      function activateTab(index) {
        btns.forEach(function (b) {
          b.classList.remove("is-active");
        });
        panels.forEach(function (p) {
          p.classList.remove("is-active");
        });
        if (btns[index]) btns[index].classList.add("is-active");
        if (panels[index]) panels[index].classList.add("is-active");
      }

      btns.forEach(function (btn, i) {
        btn.addEventListener("click", function () {
          activateTab(i);
        });
      });

      activateTab(0);

      // Accordion
      wrapper.querySelectorAll(".pd-accordion-btn").forEach(function (btn) {
        btn.addEventListener("click", function () {
          var item = btn.closest(".pd-accordion-item");
          item.classList.toggle("is-open");
        });
      });

      // Show More / Less
      wrapper.querySelectorAll(".pd-showmore-btn").forEach(function (btn) {
        btn.addEventListener("click", function () {
          var textWrap = btn.closest(".pd-overview-text");
          var expanded = textWrap.classList.toggle("is-expanded");
          btn.textContent = expanded ? "Show Less" : "Show More";
        });
      });
    });
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", init);
  } else {
    init();
  }
})();
