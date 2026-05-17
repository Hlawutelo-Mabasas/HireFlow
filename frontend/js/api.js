const BASE_URL = "http://localhost:8080/api";

function showAlert(elementId, message, type = "success") {
    const el = document.getElementById(elementId);
    if (!el) return;
    el.className = `alert alert-${type} show`;
    el.textContent = message;
    setTimeout(() => { el.classList.remove("show"); }, 4000);
}

function saveSession(user) {
    localStorage.setItem("hireflow_user", JSON.stringify(user));
}

function getSession() {
    const data = localStorage.getItem("hireflow_user");
    return data ? JSON.parse(data) : null;
}

function clearSession() {
    localStorage.removeItem("hireflow_user");
}

function redirectIfNotLoggedIn() {
    const user = getSession();
    if (!user) {
        window.location.href = "../pages/login.html";
    }
    return user;
}

async function registerUser(name, email, password, role, companyName = "") {
    try {
        const res = await fetch(`${BASE_URL}/users/register`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name, email, password, role, companyName })
        });
        if (!res.ok) {
            const errData = await res.json();
            return { error: errData.error || "Registration failed" };
        }
        return res.json();
    } catch (err) {
        return { error: "Cannot connect to backend. Is Spring Boot running on port 8080?" };
    }
}

async function loginUser(email, password) {
    try {
        const res = await fetch(`${BASE_URL}/users/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ email, password })
        });
        if (!res.ok) {
            const errData = await res.json();
            return { error: errData.error || "Login failed" };
        }
        return res.json();
    } catch (err) {
        return { error: "Cannot connect to backend. Is Spring Boot running on port 8080?" };
    }
}

async function getAllUsers() {
    try {
        const res = await fetch(`${BASE_URL}/users/all`);
        return res.json();
    } catch (e) { return []; }
}

async function getAllCompanies() {
    try {
        const res = await fetch(`${BASE_URL}/users/companies`);
        return res.json();
    } catch (e) { return []; }
}

async function getAllCandidates() {
    try {
        const res = await fetch(`${BASE_URL}/users/candidates`);
        return res.json();
    } catch (e) { return []; }
}

async function deleteUser(userId) {
    const res = await fetch(`${BASE_URL}/users/${userId}`, { method: "DELETE" });
    return res.json();
}

async function postJob(companyId, title, description, location, salary) {
    const res = await fetch(`${BASE_URL}/jobs/post?companyId=${companyId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ title, description, location, salary })
    });
    return res.json();
}

async function getAllOpenJobs() {
    try {
        const res = await fetch(`${BASE_URL}/jobs/open`);
        return res.json();
    } catch (e) { return []; }
}

async function getAllJobs() {
    try {
        const res = await fetch(`${BASE_URL}/jobs/all`);
        return res.json();
    } catch (e) { return []; }
}

async function getJobsByCompany(companyId) {
    try {
        const res = await fetch(`${BASE_URL}/jobs/company/${companyId}`);
        return res.json();
    } catch (e) { return []; }
}

async function closeJob(jobId) {
    const res = await fetch(`${BASE_URL}/jobs/${jobId}/close`, { method: "PATCH" });
    return res.json();
}

async function deleteJob(jobId) {
    const res = await fetch(`${BASE_URL}/jobs/${jobId}`, { method: "DELETE" });
    return res.json();
}

async function applyForJob(candidateId, jobId, coverLetter) {
    const res = await fetch(`${BASE_URL}/applications/apply`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ candidateId: String(candidateId), jobId: String(jobId), coverLetter })
    });
    return res.json();
}

async function updateApplicationStatus(applicationId, status) {
    const res = await fetch(`${BASE_URL}/applications/${applicationId}/status`, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ status })
    });
    return res.json();
}

async function getApplicationsByJob(jobId) {
    try {
        const res = await fetch(`${BASE_URL}/applications/job/${jobId}`);
        return res.json();
    } catch (e) { return []; }
}

async function getApplicationsByCandidate(candidateId) {
    try {
        const res = await fetch(`${BASE_URL}/applications/candidate/${candidateId}`);
        return res.json();
    } catch (e) { return []; }
}

async function getAllApplications() {
    try {
        const res = await fetch(`${BASE_URL}/applications/all`);
        return res.json();
    } catch (e) { return []; }
}

async function getSubscription(companyId) {
    try {
        const res = await fetch(`${BASE_URL}/subscriptions/company/${companyId}`);
        return res.json();
    } catch (e) { return null; }
}

async function upgradeToPro(companyId) {
    const res = await fetch(`${BASE_URL}/subscriptions/company/${companyId}/upgrade`, { method: "PATCH" });
    return res.json();
}

async function getAllSubscriptions() {
    try {
        const res = await fetch(`${BASE_URL}/subscriptions/all`);
        return res.json();
    } catch (e) { return []; }
}
