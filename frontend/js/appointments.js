
const BASE_URL = "https://hospital-management-system-qa6u.onrender.com";
const API_URL = "https://hospital-management-system-qa6u.onrender.com/Appointments";

const appointmentTableBody = document.getElementById("appointmentTableBody");
const appointmentCount = document.getElementById("appointmentCount");

const appointmentModal = document.getElementById("appointmentModal");
const appointmentForm = document.getElementById("appointmentForm");

const addAppointmentBtn = document.getElementById("addAppointmentBtn");
const cancelBtn = document.getElementById("cancelBtn");
const closeBtn = document.getElementById("closeModal");

const searchAppointment = document.getElementById("searchAppointment");

const appointmentPublicId = document.getElementById("appointmentPublicId");
const patientPublicId = document.getElementById("patientPublicId");
const doctorPublicId = document.getElementById("doctorPublicId");
const appointmentDate = document.getElementById("appointmentDate");
const appointmentTime = document.getElementById("appointmentTime");
const appointmentReason = document.getElementById("appointmentReason");

const patientSearch = document.getElementById("patientSearch");
const patientSearchResults = document.getElementById("patientSearchResults");

const doctorSearch = document.getElementById("doctorSearch");
const doctorSearchResults = document.getElementById("doctorSearchResults");

let appointments = [];
let patients = [];
let doctors = [];
let filteredAppointments = [];

async function loadAppointments() {
    try {
        const response = await fetch(API_URL);

        appointments = await response.json();

        filteredAppointments = [...appointments];

        renderAppointments(filteredAppointments);

    } catch (error) {
        console.error(error);
    }
}

async function loadPatients() {
    try {
        const response = await fetch(`${BASE_URL}/patients`);

        patients = await response.json();
console.log(patients);
    } catch (error) {
        console.error(error);
    }
}

async function loadDoctors() {
    try {

         const response = await fetch(`${BASE_URL}/doctors`);

        doctors = await response.json();

    } catch (error) {
        console.error(error);
    }
}

function renderAppointments(data) {

    appointmentTableBody.innerHTML = "";

    appointmentCount.textContent = `Showing ${data.length} Appointments`;

    data.forEach(appointment => {

        const row = document.createElement("tr");

        row.innerHTML = `
            <td>${appointment.publicId}</td>
            <td>${appointment.patient?.name ?? ""}</td>
            <td>${appointment.doctor?.name ?? ""}</td>
            <td>${new Date(appointment.appointmentTime).toLocaleString()}</td>
            <td>${appointment.reason}</td>

            <td>

                <button
                    class="edit-btn"
                    onclick="editAppointment('${appointment.publicId}')">

                    <i class="fa-solid fa-pen"></i>

                </button>

                <button
                    class="delete-btn"
                    onclick="deleteAppointment('${appointment.publicId}')">

                    <i class="fa-solid fa-trash"></i>

                </button>

            </td>
        `;

        appointmentTableBody.appendChild(row);

    });

}

searchAppointment.addEventListener("input", () => {

    const value = searchAppointment.value.toLowerCase();

    filteredAppointments = appointments.filter(appointment =>

        appointment.publicId.toLowerCase().includes(value) ||

        appointment.patient?.name.toLowerCase().includes(value) ||

        appointment.doctor?.name.toLowerCase().includes(value)

    );

    renderAppointments(filteredAppointments);

});
patientSearch.addEventListener("input", () => {

    const value = patientSearch.value.toLowerCase();

    patientSearchResults.innerHTML = "";

    if (value.length === 0) {

        return;

    }

    const filteredPatients = patients.filter(patient =>

        patient.name.toLowerCase().includes(value)

    );

    filteredPatients.forEach(patient => {

        const div = document.createElement("div");

        div.className = "search-result-item";

        div.textContent = `${patient.name} (${patient.publicId})`;

        div.addEventListener("click", () => {

            patientSearch.value = patient.name;

            patientPublicId.value = patient.publicId;

            patientSearchResults.innerHTML = "";

        });

        patientSearchResults.appendChild(div);

    });

});
doctorSearch.addEventListener("input", () => {

    const value = doctorSearch.value.toLowerCase();

    doctorSearchResults.innerHTML = "";

    if (value.length === 0) {

        return;

    }

    const filteredDoctors = doctors.filter(doctor =>

        doctor.name.toLowerCase().includes(value)

    );

    filteredDoctors.forEach(doctor => {

        const div = document.createElement("div");

        div.className = "search-result-item";

        div.textContent = `${doctor.name} (${doctor.publicId})`;

        div.addEventListener("click", () => {

            doctorSearch.value = doctor.name;

            doctorPublicId.value = doctor.publicId;

            doctorSearchResults.innerHTML = "";

        });

        doctorSearchResults.appendChild(div);

    });

});

addAppointmentBtn.addEventListener("click", () => {

    appointmentForm.reset();

    appointmentPublicId.value = "";

    document.getElementById("modalTitle").textContent = "Book Appointment";

    appointmentModal.style.display = "flex";

});

cancelBtn.addEventListener("click", closeModal);
closeBtn.addEventListener("click", closeModal);

function closeModal() {

    appointmentModal.style.display = "none";

}
function editAppointment(publicId) {

    const appointment = appointments.find(a => a.publicId === publicId);

    if (!appointment) return;

    appointmentPublicId.value = appointment.publicId;

    patientPublicId.value = appointment.patient?.publicId || "";

    doctorPublicId.value = appointment.doctor?.publicId || "";

    appointmentTime.value = appointment.appointmentTime.slice(0, 16);

    appointmentReason.value = appointment.reason;

    document.getElementById("modalTitle").textContent = "Edit Appointment";

    appointmentModal.style.display = "flex";

}

appointmentForm.addEventListener("submit", async (e) => {

    e.preventDefault();

    const data = {

        patientPublicId: patientPublicId.value,

        doctorPublicId: doctorPublicId.value,

        appointmentTime: `${appointmentDate.value}T${appointmentTime.value}`,

        reason: appointmentReason.value

    };

    try {

        if (appointmentPublicId.value) {

            await fetch(`${API_URL}/${appointmentPublicId.value}`, {

                method: "PUT",

                headers: {

                    "Content-Type": "application/json"

                },

                body: JSON.stringify(data)

            });

        } else {

            await fetch(API_URL, {

                method: "POST",

                headers: {

                    "Content-Type": "application/json"

                },

                body: JSON.stringify(data)

            });

        }

        closeModal();

        loadAppointments();

    } catch (error) {

        console.error(error);

    }

});

async function deleteAppointment(publicId) {

    if (!confirm("Are you sure you want to cancel this appointment?")) return;

    try {

        await fetch(`${API_URL}/${publicId}`, {

            method: "DELETE"

        });

        loadAppointments();

    } catch (error) {

        console.error(error);

    }

}

window.addEventListener("click", (e) => {

    if (e.target === appointmentModal) {

        closeModal();

    }

});

document.addEventListener("keydown", (e) => {

    if (e.key === "Escape") {

        closeModal();

    }

});

async function initializePage() {

    await loadPatients();

    await loadDoctors();

    await loadAppointments();

}

initializePage();