// ================================
// API URL
// ================================

const API_URL = "http://localhost:8080/Departments";

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
const headDoctorPublicId = document.getElementById("headDoctorPublicId");

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
                    onclick="editDepartment('${department.publicId}')">

                    <i class="fa-solid fa-pen"></i>

                </button>

                <button
                    class="action-btn delete-btn"
                    onclick="deleteDepartment('${department.publicId}')">

                    <i class="fa-solid fa-trash"></i>

                </button>

            </td>
        `;

        departmentTableBody.appendChild(row);

    });

    departmentCount.textContent =
        `Showing ${filteredDepartments.length} Department${filteredDepartments.length > 1 ? "s" : ""}`;

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

// ================================
// EDIT DEPARTMENT
// ================================

function editDepartment(publicId) {

    const department = departments.find(d => d.publicId === publicId);

    if (!department) return;

    document.getElementById("modalTitle").textContent = "Edit Department";

    departmentPublicId.value = department.publicId;

    departmentName.value = department.name;

    headDoctorPublicId.value = department.headDoctor?.publicid || "";

    departmentModal.classList.add("active");

}

// ================================
// SAVE DEPARTMENT
// ================================

departmentForm.addEventListener("submit", async (event) => {

    event.preventDefault();

    const departmentData = {

        name: departmentName.value.trim(),

        headDoctorPublicId: headDoctorPublicId.value.trim()

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
// INITIAL LOAD
// ================================

document.addEventListener("DOMContentLoaded", () => {

    loadDepartments();

});