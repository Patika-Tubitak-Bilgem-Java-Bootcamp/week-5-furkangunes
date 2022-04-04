function login() {
    fetch("/auth", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            username: document.getElementById("username").value,
            password: document.getElementById("password").value
        })
    })
    .then(res => {
        if (res.ok) return res.text();
        throw new Error("Wrong credentials");
    })
    .then(data => {
        window.localStorage.setItem("token", data);
        window.location.href = "/home";
    })
    .catch(err => alert(err));
}