(function () {
  function isAuthorMode() {
    return (
      document.documentElement.classList.contains("aem-AuthorLayer-Edit") ||
      typeof Granite !== "undefined"
    );
  }

  function init() {
    document.querySelectorAll(".pt-wrapper").forEach(function (wrapper) {
      // don't run tab logic in author mode
      if (isAuthorMode()) return;

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

      // accordion
      document.addEventListener("DOMContentLoaded", function () {
        document.querySelectorAll(".pd-accordion-btn").forEach(function (btn) {
          btn.addEventListener("click", function () {
            var item = btn.closest(".pd-accordion-item");
            item.classList.toggle("is-open");
          });
        });
      });

      // show more/less
      wrapper.querySelectorAll(".pd-showmore-btn").forEach(function (btn) {
        btn.addEventListener("click", function () {
          var wrap = btn.closest(".pd-overview-text");
          var open = wrap.classList.toggle("is-expanded");
          btn.textContent = open ? "Show Less" : "Show More";
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
