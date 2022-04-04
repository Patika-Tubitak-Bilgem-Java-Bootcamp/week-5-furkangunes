function displayToken() {
    document.getElementById("token").innerHTML = window.localStorage.getItem("token");
}

function displayData(data) {
    let html = `<p>First Name: ${data["firstName"]}</p><p>Last Name: ${data["lastName"]}</p><br>`;
    for (const datum of data["accounts"]) {
        html += "<p>IBAN: " + datum + "</p>";
    }
    document.getElementById("container").innerHTML = html;
}

function getData() {
    fetch("/user", {
        headers: {
            token: window.localStorage.getItem("token")
        }
    })
    .then(res => {
        if (res.ok) return res.json();
        window.location.href = "/auth";
    })
    .then(data => displayData(data));
};