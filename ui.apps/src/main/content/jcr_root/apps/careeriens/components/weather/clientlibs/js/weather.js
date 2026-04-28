(function () {
  async function update(widget) {
    try {
      const location = widget.dataset.location;
      const apiKey = widget.dataset.apikey;
      const city = widget.dataset.city;

      const url =
        "https://api.tomorrow.io/v4/weather/forecast" +
        "?location=" +
        encodeURIComponent(location) +
        "&apikey=" +
        apiKey +
        "&timesteps=1m" +
        "&fields=temperature,humidity,windSpeed";

      const res = await fetch(url);
      if (!res.ok) throw new Error("API error");

      const data = await res.json();

      const timelines = data.timelines || {};
      const entry =
        timelines.minutely?.[0] ||
        timelines.hourly?.[0] ||
        timelines.daily?.[0];

      if (!entry) throw new Error("No data");

      const v = entry.values;

      widget.querySelector(".ww-city").textContent = city;
      widget.querySelector(".ww-temp").textContent = Math.round(v.temperature);
      widget.querySelector(".ww-humidity").textContent =
        Math.round(v.humidity) + "%";
      widget.querySelector(".ww-wind").textContent =
        Math.round(v.windSpeed) + " km/h";
      widget.querySelector(".ww-updated").textContent =
        "Updated " +
        new Date().toLocaleTimeString([], {
          hour: "2-digit",
          minute: "2-digit",
        });
    } catch (err) {
      widget.querySelector(".ww-updated").textContent = "Failed to fetch";
      console.error(err);
    }
  }

  function init() {
    document.querySelectorAll(".weather-widget").forEach((widget) => {
      const interval = (parseInt(widget.dataset.refresh, 10) || 60) * 1000;

      update(widget);
      setInterval(() => update(widget), interval);
    });
  }

  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", init);
  } else {
    init();
  }
})();
