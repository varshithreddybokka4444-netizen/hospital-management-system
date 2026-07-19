S
// dashboard.js
// Handles dashboard counts, sidebar navigation, and header interactions
// for the Hospital Management System.
 
document.addEventListener("DOMContentLoaded", () => {
    initSidebarNavigation();
    loadDashboardCounts();
    initHeaderInteractions();
});
 
/* ------------------------------------------------------------------ */
/* 1. Data layer (localStorage-based, so counts persist across pages) */
/* ------------------------------------------------------------------ */
 
const STORAGE_KEYS = {
    patients: "hms_patients",
    doctors: "hms_doctors",
    departments: "hms_departments",
    appointments: "hms_appointments",
};
 
// Reads an array from localStorage, defaulting to an empty array.
function getData(key) {
    try {
        const raw = localStorage.getItem(key);
        return raw ? JSON.parse(raw) : [];
    } catch (err) {
        console.error(`Failed to parse localStorage key "${key}":`, err);
        return [];
    }
}
 
// Writes an array to localStorage.
function setData(key, value) {
    localStorage.setItem(key, JSON.stringify(value));
}
 
/* ------------------------------------------------------------------ */
/* 2. Populate the dashboard cards with real counts                   */
/* ------------------------------------------------------------------ */
 
function loadDashboardCounts() {
    const counts = {
        patients: getData(STORAGE_KEYS.patients).length,
        doctors: getData(STORAGE_KEYS.doctors).length,
        departments: getData(STORAGE_KEYS.departments).length,
        appointments: getData(STORAGE_KEYS.appointments).length,
    };
 
    updateCounter("patients", counts.patients);
    updateCounter("doctors", counts.doctors);
    updateCounter("departments", counts.departments);
    updateCounter("appointments", counts.appointments);
}
 
// Animates a count from 0 up to its target value for a nicer feel.
function updateCounter(elementId, target) {
    const el = document.getElementById(elementId);
    if (!el) return;
 
    let current = 0;
    const duration = 600; // ms
    const steps = Math.max(target, 1);
    const stepTime = Math.max(Math.floor(duration / steps), 20);
 
    if (target === 0) {
        el.textContent = "0";
        return;
    }
 
    const timer = setInterval(() => {
        current += 1;
        el.textContent = current;
        if (current >= target) {
            clearInterval(timer);
        }
    }, stepTime);
}
 
/* ------------------------------------------------------------------ */
/* 3. Sidebar navigation (active state + page routing)                */
/* ------------------------------------------------------------------ */
 
function initSidebarNavigation() {
    const navItems = document.querySelectorAll(".sidebar ul li");
 
    // Map each sidebar label to the page it should link to.
    // Update these filenames once the corresponding pages exist.
    const routeMap = {
        Dashboard: "index.html",
        Patients: "patients.html",
        Doctors: "doctors.html",
        Departments: "departments.html",
        Appoinments: "appointments.html", // matches the typo in the provided HTML
        Appointments: "appointments.html",
    };
 
    navItems.forEach((item) => {
        item.addEventListener("click", () => {
            // Update active styling immediately
            navItems.forEach((el) => el.classList.remove("active"));
            item.classList.add("active");
 
            // Figure out which page to go to based on the item's text
            const label = item.textContent.trim();
            const destination = routeMap[label];
 
            if (destination) {
                // Comment this out if you want the SPA-style active-only behavior
                // and don't have the other pages built yet.
                window.location.href = destination;
            }
        });
    });
}
 
/* ------------------------------------------------------------------ */
/* 4. Header interactions (notifications + profile menu)              */
/* ------------------------------------------------------------------ */
 
function initHeaderInteractions() {
    const bellIcon = document.querySelector(".profile .fa-bell");
    const userIcon = document.querySelector(".profile .fa-circle-user");
 
    if (bellIcon) {
        bellIcon.addEventListener("click", () => {
            alert("You have new notifications!");
        });
    }
 
    if (userIcon) {
        userIcon.addEventListener("click", () => {
            const choice = confirm("Do you want to log out?");
            if (choice) {
                // Clear session data and redirect to a login page if you have one
                alert("Logged out successfully.");
            }
        });
    }
}
 
/* ------------------------------------------------------------------ */
/* 5. Helper: call this from other pages after adding/removing records */
/*    e.g. addRecord("patients", { name: "John Doe", age: 34 });       */
/* ------------------------------------------------------------------ */
 
function addRecord(type, record) {
    const key = STORAGE_KEYS[type];
    if (!key) {
        console.error(`Unknown record type: ${type}`);
        return;
    }
    const data = getData(key);
    data.push(record);
    setData(key, data);
}
 
function removeRecord(type, index) {
    const key = STORAGE_KEYS[type];
    if (!key) {
        console.error(`Unknown record type: ${type}`);
        return;
    }
    const data = getData(key);
    data.splice(index, 1);
    setData(key, data);
}