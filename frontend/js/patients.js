// ================================
// API URL
// ================================

const BASE_URL = "https://hospital-management-system-qa6u.onrender.com";

const API_URL = `${BASE_URL}/patients`;
// ================================
// DOM Elements
// ================================

const patientTableBody = document.getElementById("patientTableBody");
const patientCount = document.getElementById("patientCount");

const patientModal = document.getElementById("patientModal");

const patientForm = document.getElementById("patientForm");

const addPatientBtn = document.getElementById("addPatientBtn");
const cancelBtn = document.getElementById("cancelBtn");
const closeBtn = document.querySelector(".close-btn");

const searchInput = document.getElementById("searchPatient");
const genderFilter = document.getElementById("genderFilter");

const patientPublicId = document.getElementById("patientPublicId");
const patientName = document.getElementById("patientName");
const patientEmail = document.getElementById("patientEmail");
const patientBirthDate = document.getElementById("patientBirthDate");
const patientGender = document.getElementById("patientGender");
const patientBloodGroup = document.getElementById("patientBloodGroup");

// ================================
// Global Variables
// ================================

let patients = [];

let filteredPatients = [];
// ================================
// PAGE LOAD
// ================================

document.addEventListener("DOMContentLoaded", () => {

    loadPatients();

});

// ================================
// GET PATIENTS
// ================================

async function loadPatients() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Failed to load patients");
        }

        patients = await response.json();

        filteredPatients = [...patients];

        renderPatients();

    }

    catch (error) {

        console.error(error);

        alert("Unable to fetch patients.");

    }

}
// ================================
// RENDER PATIENTS
// ================================

function renderPatients() {

    patientTableBody.innerHTML = "";

    if (filteredPatients.length === 0) {

        patientTableBody.innerHTML = `
            <tr>
                <td colspan="7" class="empty-state">
                    <i class="fa-solid fa-user-slash"></i>
                    <p>No Patients Found</p>
                </td>
            </tr>
        `;

        patientCount.textContent = "Showing 0 Patients";

        return;
    }

    filteredPatients.forEach(patient => {

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${patient.publicId}</td>
            <td>${patient.name}</td>
            <td>${patient.age}</td>

            <td>
                <span class="gender-badge ${patient.gender.toLowerCase()}">
                    ${patient.gender}
                </span>
            </td>

            <td>${patient.email}</td>

            <td>${formatBloodGroup(patient.bloodGroup)}</td>

            <td>

                <button
                    class="action-btn edit-btn"
                    onclick="editPatient('${patient.publicId}')">

                    <i class="fa-solid fa-pen"></i>

                </button>

                <button
                    class="action-btn delete-btn"
                    onclick="deletePatient('${patient.publicId}')">

                    <i class="fa-solid fa-trash"></i>

                </button>

            </td>
        `;

        patientTableBody.appendChild(row);

    });

    patientCount.textContent =
        `Showing ${filteredPatients.length} Patient${filteredPatients.length > 1 ? "s" : ""}`;

}
// ================================
// FORMAT BLOOD GROUP
// ================================

function formatBloodGroup(group) {

    const bloodGroups = {

        "A_POSITIVE": "A+",
        "A_NEGATIVE": "A-",

        "B_POSITIVE": "B+",
        "B_NEGATIVE": "B-",

        "AB_POSITIVE": "AB+",
        "AB_NEGATIVE": "AB-",

        "O_POSITIVE": "O+",
        "O_NEGATIVE": "O-"

    };

    return bloodGroups[group] || group;

}
// ================================
// OPEN / CLOSE MODAL
// ================================

addPatientBtn.addEventListener("click", () => {

    patientForm.reset();

    patientPublicId.value = "";

    document.querySelector(".modal-header h2").textContent = "Add Patient";

    patientModal.classList.add("active");

});

cancelBtn.addEventListener("click", closeModal);

closeBtn.addEventListener("click", closeModal);

window.addEventListener("click", (event) => {

    if (event.target === patientModal) {

        closeModal();

    }

});

function closeModal() {

    patientForm.reset();

    patientPublicId.value = "";

    patientModal.classList.remove("active");

}
// ================================
// EDIT PATIENT
// ================================

function editPatient(publicId) {

    const patient = patients.find(p => p.publicId === publicId);

    if (!patient) return;

    document.querySelector(".modal-header h2").textContent = "Edit Patient";

    patientPublicId.value = patient.publicId;

    patientName.value = patient.name;

    patientEmail.value = patient.email;

    patientBirthDate.value = patient.birthDate;

    patientGender.value = patient.gender;

    patientBloodGroup.value = patient.bloodGroup;

    patientModal.classList.add("active");

}
// ================================
// SAVE PATIENT
// ================================

patientForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    const patientData = {

        name: patientName.value.trim(),

        email: patientEmail.value.trim(),

        birthDate: patientBirthDate.value,

        gender: patientGender.value,

        bloodGroup: patientBloodGroup.value

    };

    try {

        let response;

        // UPDATE
        if (patientPublicId.value !== "") {

            response = await fetch(`${API_URL}/${patientPublicId.value}`, {

                method: "PUT",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(patientData)

            });

        }

        // ADD
        else {

            response = await fetch(API_URL, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(patientData)

            });

        }

        if (!response.ok) {

            throw new Error("Failed to save patient");

        }

        closeModal();

        await loadPatients();

    }

    catch (error) {

        console.error(error);

        alert("Unable to save patient.");

    }

});
// ================================
// DELETE PATIENT
// ================================

async function deletePatient(publicId) {

    const confirmDelete = confirm(
        "Are you sure you want to delete this patient?"
    );

    if (!confirmDelete) return;

    try {

        const response = await fetch(`${API_URL}/${publicId}`, {

            method: "DELETE"

        });

        if (!response.ok) {

            throw new Error("Delete failed");

        }

        await loadPatients();

    }

    catch (error) {

        console.error(error);

        alert("Unable to delete patient.");

    }

}
// ================================
// SEARCH PATIENTS
// ================================

searchInput.addEventListener("input", applyFilters);

genderFilter.addEventListener("change", applyFilters);

function applyFilters() {

    const searchValue = searchInput.value.trim().toLowerCase();

    const selectedGender = genderFilter.value;

    filteredPatients = patients.filter(patient => {

        const matchesSearch =
            patient.name.toLowerCase().includes(searchValue) ||
            patient.publicId.toLowerCase().includes(searchValue) ||
            patient.email.toLowerCase().includes(searchValue);

        const matchesGender =
            selectedGender === "ALL" ||
            patient.gender === selectedGender;

        return matchesSearch && matchesGender;

    });

    renderPatients();

}
// ================================
// REFRESH TABLE
// ================================

function refreshPatients() {

    filteredPatients = [...patients];

    renderPatients();

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

    loadPatients();

});