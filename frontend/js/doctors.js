// ================================
// API URL
// ================================

const API_URL = "http://localhost:8080/doctors";
const DEPARTMENT_API_URL = "http://localhost:8080/departments";

// ================================
// DOM Elements
// ================================

const doctorTableBody =
    document.getElementById("doctorTableBody");

const doctorCount =
    document.getElementById("doctorCount");

const doctorModal =
    document.getElementById("doctorModal");

const doctorForm =
    document.getElementById("doctorForm");

const addDoctorBtn =
    document.getElementById("addDoctorBtn");

const cancelBtn =
    document.getElementById("cancelBtn");

const closeBtn =
    document.querySelector(".close-btn");

const searchInput =
    document.getElementById("searchDoctor");

const doctorPublicId =
    document.getElementById("doctorPublicId");

const doctorName =
    document.getElementById("doctorName");

const doctorSpecialisation =
    document.getElementById("doctorSpecialisation");

const doctorEmail =
    document.getElementById("doctorEmail");

const departmentSelect =
    document.getElementById("departmentSelect");

    // ================================
    // Global Variables
    // ================================

    let doctors = [];
    let filteredDoctors = [];

    // ================================
    // PAGE LOAD
    // ================================

    document.addEventListener("DOMContentLoaded", async () => {

        await loadDepartments();

        await loadDoctors();

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

        }

        catch (error) {

            console.error(error);

            alert("Unable to fetch doctors.");

        }

    }

    // ================================
    // LOAD DEPARTMENTS
    // ================================

    async function loadDepartments() {

        try {

            const response =
                await fetch(DEPARTMENT_API_URL);

            if (!response.ok) {

                throw new Error("Unable to load departments");

            }

            const departments = await response.json();

            departmentSelect.innerHTML = `

                <option value="">
                    Select Department
                </option>

            `;

            departments.forEach(department => {

                const option =
                    document.createElement("option");

                option.value = department.publicId;

                option.textContent = department.name;

                departmentSelect.appendChild(option);

            });

        }

        catch (error) {

            console.error(error);

            alert("Unable to load departments.");

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
                    <td colspan="6" class="empty-state">
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

               <td>
                   ${doctor.department ? doctor.department.name : "-"}
               </td>

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

                    addDoctorBtn.addEventListener("click", async () => {

                        doctorForm.reset();

                        doctorPublicId.value = "";

                        document.getElementById("modalTitle").textContent =
                            "Add Doctor";

                        await loadDepartments();

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

                        departmentSelect.value = "";

                        doctorModal.classList.remove("active");

                    }

                    // ================================
                    // EDIT DOCTOR
                    // ================================

                    function editDoctor(publicId) {

                        const doctor =
                            doctors.find(d => d.publicId === publicId);

                        if (!doctor) return;

                        doctorForm.reset();

                        document.getElementById("modalTitle").textContent =
                            "Edit Doctor";

                        doctorPublicId.value =
                            doctor.publicId;

                        doctorName.value =
                            doctor.name;

                        doctorSpecialisation.value =
                            doctor.specialisation;

                        doctorEmail.value =
                            doctor.email;

                        if (doctor.department) {

                            departmentSelect.value =
                                doctor.department.publicId;

                        }

                        doctorModal.classList.add("active");

                    }

                    // ================================
                    // SAVE DOCTOR
                    // ================================

                doctorForm.addEventListener("submit", async (event) => {

                    event.preventDefault();

                    if (departmentSelect.value === "") {

                        alert("Please select a department.");

                        return;

                    }

                    const doctorData = {

                        name: doctorName.value.trim(),

                        specialisation: doctorSpecialisation.value.trim(),

                        email: doctorEmail.value.trim(),

                        departmentPublicId: departmentSelect.value

                    };

                    try {

                        let response;

                        if (doctorPublicId.value !== "") {

                            response = await fetch(
                                `${API_URL}/${doctorPublicId.value}`,
                                {
                                    method: "PUT",

                                    headers: {
                                        "Content-Type": "application/json"
                                    },

                                    body: JSON.stringify(doctorData)
                                }
                            );

                        }

                        else {

                            response = await fetch(
                                API_URL,
                                {
                                    method: "POST",

                                    headers: {
                                        "Content-Type": "application/json"
                                    },

                                    body: JSON.stringify(doctorData)
                                }
                            );

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