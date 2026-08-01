// ================================
// API URL
// ================================



const BASE_URL = "https://hospital-management-system-qa6u.onrender.com";
const API_URL = `${BASE_URL}/departments`;


const DOCTOR_API_URL = `${BASE_URL}/doctors`;

// ================================
// DOM Elements
// ================================

const departmentTableBody = document.getElementById("departmentTableBody");
const departmentCount = document.getElementById("departmentCount");

const departmentModal = document.getElementById("departmentModal");
const departmentForm = document.getElementById("departmentForm");

const addDepartmentBtn = document.getElementById("addDepartmentBtn");
const cancelBtn = document.getElementById("cancelBtn");
const closeBtn = document.querySelector(".close-btn");

const searchInput = document.getElementById("searchDepartment");

const departmentPublicId = document.getElementById("departmentPublicId");
const departmentName = document.getElementById("departmentName");
const assignHeadDoctorModal =
    document.getElementById("assignHeadDoctorModal");

const assignHeadDoctorForm =
    document.getElementById("assignHeadDoctorForm");

const assignDepartmentPublicId =
    document.getElementById("assignDepartmentPublicId");

const headDoctorSelect =
    document.getElementById("headDoctorSelect");

const closeAssignModal =
    document.getElementById("closeAssignModal");

const cancelAssignBtn =
    document.getElementById("cancelAssignBtn");
//const headDoctorPublicId = document.getElementById("headDoctorPublicId");

// ================================
// Global Variables
// ================================

let departments = [];
let filteredDepartments = [];

// ================================
// PAGE LOAD
// ================================

document.addEventListener("DOMContentLoaded", () => {
    loadDepartments();
});

// ================================
// LOAD DEPARTMENTS
// ================================

async function loadDepartments() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Failed to load departments");
        }

        departments = await response.json();

        filteredDepartments = [...departments];

        renderDepartments();

    } catch (error) {

        console.error(error);

        alert("Unable to fetch departments.");

    }

}

// ================================
// RENDER DEPARTMENTS
// ================================

function renderDepartments() {

    departmentTableBody.innerHTML = "";

    if (filteredDepartments.length === 0) {

        departmentTableBody.innerHTML = `
            <tr>
                <td colspan="5" class="empty-state">
                    <i class="fa-solid fa-building"></i>
                    <p>No Departments Found</p>
                </td>
            </tr>
        `;

        departmentCount.textContent = "Showing 0 Departments";

        return;
    }

    filteredDepartments.forEach(department => {

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${department.publicId}</td>
            <td>${department.name}</td>
            <td>${department.headDoctor?.name || "-"}</td>
            <td>${department.headDoctor?.specialisation || "-"}</td>

            <td>

                <button
                    class="action-btn edit-btn"
                    onclick="editDepartment('${department.publicId}')"
                    title="Edit">

                    <i class="fa-solid fa-pen"></i>

                </button>

                <button
                    class="action-btn assign-btn"
                    onclick="openAssignHeadDoctorModal('${department.publicId}')"
                    title="Assign Head Doctor">

                    <i class="fa-solid fa-user-doctor"></i>

                </button>

                <button
                    class="action-btn delete-btn"
                    onclick="deleteDepartment('${department.publicId}')"
                    title="Delete">

                    <i class="fa-solid fa-trash"></i>

                </button>

            </td>
        `;

        departmentTableBody.appendChild(row);

    });

    departmentCount.textContent =
        `Showing ${filteredDepartments.length} Department${filteredDepartments.length > 1 ? "s" : ""}`;

}

async function openAssignHeadDoctorModal(publicId) {

    document.getElementById("assignDepartmentPublicId").value = publicId;

    const doctorSelect = document.getElementById("headDoctorSelect");

    doctorSelect.innerHTML = `
        <option value="">
            Select Doctor
        </option>
    `;

    try {

        const response = await fetch(
            `${API_URL}/${publicId}/doctors`
        );

        if (!response.ok) {

            throw new Error("Unable to fetch doctors");

        }

        const doctors = await response.json();
        console.log(doctors);
        doctors.forEach(doctor => {

            const option = document.createElement("option");

            option.value = doctor.publicId;

            option.textContent =
                `${doctor.name} (${doctor.specialisation})`;

            doctorSelect.appendChild(option);

        });

        document
            .getElementById("assignHeadDoctorModal")
            .classList.add("active");

    }

    catch (error) {

        console.error(error);

        alert("Unable to load doctors.");

    }

}
// ================================
// SEARCH
// ================================

searchInput.addEventListener("input", applySearch);

function applySearch() {

    const value = searchInput.value.trim().toLowerCase();

    filteredDepartments = departments.filter(department =>

        department.name.toLowerCase().includes(value) ||

        department.publicId.toLowerCase().includes(value) ||

        (department.headDoctor?.name || "").toLowerCase().includes(value)

    );

    renderDepartments();

}
// ================================
// OPEN MODAL
// ================================

addDepartmentBtn.addEventListener("click", () => {

    departmentForm.reset();

    departmentPublicId.value = "";

    document.getElementById("modalTitle").textContent = "Add Department";

    departmentModal.classList.add("active");

});

// ================================
// CLOSE MODAL
// ================================

cancelBtn.addEventListener("click", closeModal);

closeBtn.addEventListener("click", closeModal);

window.addEventListener("click", (event) => {

    if (event.target === departmentModal) {

        closeModal();

    }

});

function closeModal() {

    departmentForm.reset();

    departmentPublicId.value = "";

    departmentModal.classList.remove("active");

}
function closeAssignHeadDoctorModal() {

    assignHeadDoctorForm.reset();

    assignHeadDoctorModal.classList.remove("active");

}
closeAssignModal.addEventListener(
    "click",
    closeAssignHeadDoctorModal
);

cancelAssignBtn.addEventListener(
    "click",
    closeAssignHeadDoctorModal
);

window.addEventListener("click", event => {

    if (event.target === assignHeadDoctorModal) {

        closeAssignHeadDoctorModal();

    }

});
// ================================
// EDIT DEPARTMENT
// ================================

function editDepartment(publicId) {

    const department = departments.find(d => d.publicId === publicId);

    if (!department) return;

    document.getElementById("modalTitle").textContent = "Edit Department";

    departmentPublicId.value = department.publicId;

    departmentName.value = department.name;

//    headDoctorPublicId.value = department.headDoctor?.publicid || "";

    departmentModal.classList.add("active");

}

// ================================
// SAVE DEPARTMENT
// ================================

departmentForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    const departmentData = {

        name: departmentName.value.trim()

    };

    try {

        let response;

        // UPDATE

        if (departmentPublicId.value !== "") {

            response = await fetch(`${API_URL}/${departmentPublicId.value}`, {

                method: "PUT",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(departmentData)

            });

        }

        // ADD

        else {

            response = await fetch(API_URL, {

                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(departmentData)

            });

        }

        if (!response.ok) {

            throw new Error("Failed to save department");

        }

        closeModal();

        await loadDepartments();

    }

    catch (error) {

        console.error(error);

        alert("Unable to save department.");

    }

});

// ================================
// DELETE DEPARTMENT
// ================================

async function deleteDepartment(publicId) {

    const confirmDelete = confirm(
        "Are you sure you want to delete this department?"
    );

    if (!confirmDelete) return;

    try {

        const response = await fetch(`${API_URL}/${publicId}`, {

            method: "DELETE"

        });

        if (!response.ok) {

            throw new Error("Delete failed");

        }

        await loadDepartments();

    }

    catch (error) {

        console.error(error);

        alert("Unable to delete department.");

    }

}

// ================================
// REFRESH TABLE
// ================================

function refreshDepartments() {

    filteredDepartments = [...departments];

    renderDepartments();

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
// ASSIGN HEAD DOCTOR
// ================================

assignHeadDoctorForm.addEventListener("submit", async (event) => {

 console.log("FORM SUBMITTED");
    event.preventDefault();

    const departmentPublicId =
        assignDepartmentPublicId.value;

    const doctorPublicId = headDoctorSelect.value
    if (doctorPublicId === "") {

        alert("Please select a doctor.");

        return;

    }

    try {

        const response = await fetch(

            `${API_URL}/${departmentPublicId}/head-doctor`,

            {

                method: "PATCH",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({

                    headDoctorPublicId: doctorPublicId

                })

            }

        );

        if (!response.ok) {

            throw new Error("Unable to assign head doctor");

        }

        closeAssignHeadDoctorModal();

        await loadDepartments();

    }

    catch (error) {

        console.error(error);

        alert("Unable to assign head doctor.");

    }

});

// ================================
// INITIAL LOAD
// ================================

document.addEventListener("DOMContentLoaded", () => {

    loadDepartments();

});

console.log(assignHeadDoctorForm);
console.log(headDoctorSelect);