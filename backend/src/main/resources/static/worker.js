console.log("Service Worker Loaded...");

self.addEventListener("push", e => {
  const data = e.data.json();
  console.log("Push Recieved at SW...");
  console.log("data at service worker - ");
  console.log(data);
  var opt = {
  }
  self.registration.showNotification(data, {
    body: "HARDCODED",
  });
});