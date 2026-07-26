// ================================
// API URL
// ================================

const API_URL = "http://localhost:8080/doctors";

// ================================
// DOM Elements
// ================================

const doctorTableBody = document.getElementById("doctorTableBody");
const doctorCount = document.getElementById("doctorCount");

const doctorModal = document.getElementById("doctorModal");
const doctorForm = document.getElementById("doctorForm");

const addDoctorBtn = document.getElementById("addDoctorBtn");
const cancelBtn = document.getElementById("cancelBtn");
const closeBtn = document.querySelector(".close-btn");

const searchInput = document.getElementById("searchDoctor");

const doctorPublicId = document.getElementById("doctorPublicId");
const doctorName = document.getElementById("doctorName");
const doctorSpecialisation = document.getElementById("doctorSpecialisation");
const doctorEmail = document.getElementById("doctorEmail");

// ================================
// Global Variables
// ================================

let doctors = [];
let filteredDoctors = [];

// ================================
// PAGE LOAD
// ================================

document.addEventListener("DOMContentLoaded", () => {
    loadDoctors();
});

// ================================
// LOAD DOCTORS
// ================================

async function loadDoctors() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Failed to load doctors");
        }

        doctors = await response.json();

        filteredDoctors = [...doctors];

        renderDoctors();

    } catch (error) {

        console.error(error);

        alert("Unable to fetch doctors.");

    }

}

// ================================
// RENDER DOCTORS
// ================================

function renderDoctors() {

    doctorTableBody.innerHTML = "";

    if (filteredDoctors.length === 0) {

        doctorTableBody.innerHTML = `
            <tr>
                <td colspan="5" class="empty-state">
                    <i class="fa-solid fa-user-doctor"></i>
                    <p>No Doctors Found</p>
                </td>
            </tr>
        `;

        doctorCount.textContent = "Showing 0 Doctors";

        return;
    }

    filteredDoctors.forEach(doctor => {

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${doctor.publicId}</td>
            <td>${doctor.name}</td>
            <td>${doctor.specialisation}</td>
            <td>${doctor.email}</td>

            <td>

                <button
                    class="action-btn edit-btn"
                    onclick="editDoctor('${doctor.publicId}')">

                    <i class="fa-solid fa-pen"></i>

                </button>

                <button
                    class="action-btn delete-btn"
                    onclick="deleteDoctor('${doctor.publicId}')">

                    <i class="fa-solid fa-trash"></i>

                </button>

            </td>
        `;

        doctorTableBody.appendChild(row);

    });

    doctorCount.textContent =
        `Showing ${filteredDoctors.length} Doctor${filteredDoctors.length > 1 ? "s" : ""}`;

}

// ================================
// SEARCH DOCTORS
// ================================

searchInput.addEventListener("input", applySearch);

function applySearch() {

    const value = searchInput.value.trim().toLowerCase();

    filteredDoctors = doctors.filter(doctor =>

        doctor.name.toLowerCase().includes(value) ||

        doctor.publicId.toLowerCase().includes(value) ||

        doctor.email.toLowerCase().includes(value) ||

        doctor.specialisation.toLowerCase().includes(value)

    );

    renderDoctors();

}
// ================================
// OPEN MODAL
// ================================

addDoctorBtn.addEventListener("click", () => {

    doctorForm.reset();

    doctorPublicId.value = "";

    document.getElementById("modalTitle").textContent = "Add Doctor";

    doctorModal.classList.add("active");

});

// ================================
// CLOSE MODAL
// ================================

cancelBtn.addEventListener("click", closeModal);

closeBtn.addEventListener("click", closeModal);

window.addEventListener("click", (event) => {

    if (event.target === doctorModal) {

        closeModal();

    }

});

function closeModal() {

    doctorForm.reset();

    doctorPublicId.value = "";

    doctorModal.classList.remove("active");

}

// ================================
// EDIT DOCTOR
// ================================

function editDoctor(publicId) {

    const doctor = doctors.find(d => d.publicId === publicId);

    if (!doctor) return;

    document.getElementById("modalTitle").textContent = "Edit Doctor";

    doctorPublicId.value = doctor.publicId;

    doctorName.value = doctor.name;

    doctorSpecialisation.value = doctor.specialisation;

    doctorEmail.value = doctor.email;

    doctorModal.classList.add("active");

}

// ================================
// SAVE DOCTOR
// ================================

doctorForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    const doctorData = {

        name: doctorName.value.trim(),

        specialisation: doctorSpecialisation.value.trim(),

        email: doctorEmail.value.trim()

    };

    try {

        let response;

        // UPDATE

        if (doctorPublicId.value !== "") {

            response = await fetch(`${API_URL}/${doctorPublicId.value}`, {

                method: "PUT",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(doctorData)

            });

        }

        // ADD

        else {

            response = await fetch(API_URL, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(doctorData)

            });

        }

        if (!response.ok) {

            throw new Error("Failed to save doctor");

        }

        closeModal();

        await loadDoctors();

    }

    catch (error) {

        console.error(error);

        alert("Unable to save doctor.");

    }

});

// ================================
// DELETE DOCTOR
// ================================

async function deleteDoctor(publicId) {

    const confirmDelete = confirm(
        "Are you sure you want to delete this doctor?"
    );

    if (!confirmDelete) return;

    try {

        const response = await fetch(`${API_URL}/${publicId}`, {

            method: "DELETE"

        });

        if (!response.ok) {

            throw new Error("Delete failed");

        }

        await loadDoctors();

    }

    catch (error) {

        console.error(error);

        alert("Unable to delete doctor.");

    }

}

// ================================
// REFRESH TABLE
// ================================

function refreshDoctors() {

    filteredDoctors = [...doctors];

    renderDoctors();

}

// ================================
// ESC KEY CLOSE MODAL
// ================================

document.addEventListener("keydown", (event) => {

    if (event.key === "Escape") {

        closeModal();

    }

});

// ================================
// INITIAL LOAD
// ================================

document.addEventListener("DOMContentLoaded", () => {

    loadDoctors();

});