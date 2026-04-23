(function () {
  function isAuthorMode() {
    return (
      document.documentElement.classList.contains("aem-AuthorLayer-Edit") ||
      typeof Granite !== "undefined"
    );
  }

  function initTabs() {
    if (isAuthorMode()) return;

    document.querySelectorAll(".pt-wrapper").forEach(function (wrapper) {
      var slots = wrapper.querySelectorAll(".pt-slot");
      var navList = wrapper.querySelector(".pt-tabnav-list");
      var buttons = [];

      slots.forEach(function (slot) {
        var panel = slot.querySelector("[data-tab-label]");
        if (!panel) return;

        var label = panel.getAttribute("data-tab-label") || "Tab";
        var li = document.createElement("li");
        var btn = document.createElement("button");
        btn.className = "pt-tabnav-btn";
        btn.textContent = label;
        btn.type = "button";
        li.appendChild(btn);
        navList.appendChild(li);
        buttons.push({ btn: btn, slot: slot });

        btn.addEventListener("click", function () {
          buttons.forEach(function (b) {
            b.btn.classList.remove("is-active");
            b.slot.classList.remove("is-active");
          });
          btn.classList.add("is-active");
          slot.classList.add("is-active");
        });
      });

      if (buttons.length > 0) {
        buttons[0].btn.classList.add("is-active");
        buttons[0].slot.classList.add("is-active");
      }

      // show more/less — only in publish
      wrapper.querySelectorAll(".pd-showmore-btn").forEach(function (btn) {
        btn.addEventListener("click", function () {
          var wrap = btn.closest(".pd-overview-text");
          var open = wrap.classList.toggle("is-expanded");
          btn.textContent = open ? "Show Less" : "Show More";
        });
      });
    });
  }

  // ── Accordion — runs in BOTH author and publish ──
  function initAccordions() {
    document.querySelectorAll(".pd-accordion-btn").forEach(function (btn) {
      btn.addEventListener("click", function () {
        btn.closest(".pd-accordion-item").classList.toggle("is-open");
      });
    });
  }

  // ── Boot ─────────────────────────────────────────
  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", function () {
      initTabs();
      initAccordions();
    });
  } else {
    initTabs();
    initAccordions();
  }
})();
