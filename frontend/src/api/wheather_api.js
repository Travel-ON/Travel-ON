const HOST = "https://api.openweathermap.org/";

/* eslint-disable */

export default {
  weather: {
    currentWeather: () => HOST + "data/2.5/weather",
    foreWeather: () => HOST + "data/2.5/forecast/hourly",
  }
}